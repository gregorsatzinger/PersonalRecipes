package at.fhooe.smaproject.ui.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import at.fhooe.smaproject.R;
import at.fhooe.smaproject.databinding.ActivityRecipeDetailBinding;
import at.fhooe.smaproject.domain.RecipeRepository;
import at.fhooe.smaproject.domain.RecipeRepositoryImpl;
import at.fhooe.smaproject.models.Category;
import at.fhooe.smaproject.models.Recipe;
import at.fhooe.smaproject.ui.viewmodels.RecipeViewModel;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String RECIPE_ID_KEY = "RecipeId";
    static final int REQUEST_THUMBNAIL_CAPTURE = 1;
    static final int REQUEST_DESCRIPTION_IMAGE_CAPTURE=2;
    private RecipeRepository repo;
    private ActivityRecipeDetailBinding binding;
    private RecipeViewModel viewModel;
    private ImageView imvThumbnail;
    private String currentImagePath;
    private ChipGroup cgCategory;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabEdit;

    public static Intent createIntent(Context context, int canteenId) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_ID_KEY, canteenId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new RecipeRepositoryImpl(this);
        Intent intent = getIntent();
        int recipeId = intent.getIntExtra(RECIPE_ID_KEY, -1);
        Recipe currentRecipe = (recipeId == -1) ? new Recipe() : repo.findRecipeById(recipeId);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        viewModel = new RecipeViewModel(currentRecipe);
        binding.setViewModel(viewModel);

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Details");
        }
        imvThumbnail = findViewById(R.id.imvThumbnail);
        updateTitleImage();
        imvThumbnail.setOnClickListener((l) -> {
            if(viewModel.getIsEdit()) captureImageAndGetThumbnail();
        });

        cgCategory = findViewById(R.id.cgCategory);
        updateCategoryChipGroup(viewModel.getIsEdit(), viewModel.getRecipe());

        fabSave = findViewById(R.id.fabSave);
        fabSave.setOnClickListener(v -> {
            viewModel.setIsEdit(false);

            // get selected categories
            ArrayList<Category> categories = new ArrayList<>();
            cgCategory.getCheckedChipIds().forEach(chipId -> {
                Chip chip = cgCategory.findViewById(chipId);
                categories.add(new Category(chipId, chip.getText().toString()));
            });

            Recipe recipe = viewModel.getRecipe();
            recipe.setCategories(categories);
            updateCategoryChipGroup(viewModel.getIsEdit(), recipe);

            if(recipe.getId() > 0) {
                repo.updateRecipe(recipe);
            } else {
                repo.createRecipe(recipe);
            }
        });

        fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(v -> {
            viewModel.setIsEdit(true);
            updateCategoryChipGroup(viewModel.getIsEdit(), viewModel.getRecipe());
        });
    }

    private void updateCategoryChipGroup(boolean isEdit, Recipe recipe) {
        if(isEdit) {
            cgCategory.removeAllViews();
            repo.findAllCategories().forEach(c -> {
                boolean selected = recipe.getCategories().contains(c);
                cgCategory.addView(createChip(c, selected));
            });
        } else {
            cgCategory.removeAllViews();
            recipe.getCategories().forEach(c -> cgCategory.addView(createChip(c, false)));
        }
    }

    private void updateTitleImage() {
        imvThumbnail.setImageBitmap(viewModel.getRecipe().getTitleImage());
    }

    private boolean deleteFileByPath(String filePath) {
        return new File(filePath).delete();
    }

    private Bitmap readBitmapFromFile(String filePath) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                    FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            new File(filePath))
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_DESCRIPTION_IMAGE_CAPTURE) {
                Log.d("imageCaptureTest", "successfully captured and saved image.");
                Bitmap bitmap = readBitmapFromFile(currentImagePath);
            } else if (requestCode == REQUEST_THUMBNAIL_CAPTURE) {
                Log.d("imageCaptureTest", "successfully captured and thumbnailed image.");
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                viewModel.getRecipe().setTitleImage(bitmap);
                updateTitleImage();
            }
        }
    }

    private void captureImageAndGetThumbnail() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_THUMBNAIL_CAPTURE);
        }
    }

    private void captureAndSaveImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                // TODO
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_DESCRIPTION_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentImagePath = image.getAbsolutePath();
        return image;
    }

    private Chip createChip(Category c, boolean selected) {
        Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.item_chip, (ViewGroup) cgCategory, false);
        chip.setText(c.getName());
        chip.setId(c.getId());
        chip.setChecked(selected);
        Log.d("chiptest", "name: "+ c.getName() +", sel: "+selected);
        return chip;
    }
}