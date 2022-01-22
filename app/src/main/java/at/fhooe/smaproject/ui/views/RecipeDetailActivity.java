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
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.fhooe.smaproject.R;
import at.fhooe.smaproject.databinding.ActivityRecipeDetailBinding;
import at.fhooe.smaproject.domain.RecipeRepository;
import at.fhooe.smaproject.domain.RecipeRepositoryImpl;
import at.fhooe.smaproject.models.Recipe;
import at.fhooe.smaproject.ui.viewmodels.RecipeViewModel;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String RECIPE_ID_KEY = "RecipeId";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private RecipeRepository repo;
    private ActivityRecipeDetailBinding binding;
    private ImageView imvThumbnail;
    private String currentImagePath;

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
        binding.setViewModel(new RecipeViewModel(currentRecipe));

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Details");
        }
        imvThumbnail = findViewById(R.id.imvThumbnail);

        imvThumbnail.setOnClickListener((l) -> {
            dispatchTakePictureIntent();
        });
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // image successfully captured
            Log.d("imageCaptureTest", "successfully captured image.");

            /*if(deleteFileByPath(currentImagePath)) {
                Log.d("imageCaptureTest", "successfully deleted image.");
            } else {
                Log.d("imageCaptureTest", "unsuccessfully deleted image.");
            }*/
            imvThumbnail.setImageBitmap(readBitmapFromFile(currentImagePath));
        } else {
            Log.d("imageCaptureTest", "failed to capture image");
        }
    }

    private void dispatchTakePictureIntent() {
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
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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


}