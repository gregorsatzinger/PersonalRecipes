package at.fhooe.smaproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import at.fhooe.smaproject.R;
import at.fhooe.smaproject.domain.RecipeRepository;
import at.fhooe.smaproject.domain.RecipeRepositoryImpl;
import at.fhooe.smaproject.models.Category;
import at.fhooe.smaproject.models.Recipe;
import at.fhooe.smaproject.ui.adapter.RecipesAdapter;

public class RecipeOverviewActivity extends AppCompatActivity {
    private static final String TAG = RecipeOverviewActivity.class.toString();
    private final RecipesAdapter recipesAdapter = new RecipesAdapter();
    private BottomSheetBehavior<View> sheetBehavior;
    private SwipeRefreshLayout srlSwipeRefreshLayout;
    private RecipeRepository repo;
    private ChipGroup cgOrderBy;
    private ChipGroup cgCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);
        repo = new RecipeRepositoryImpl(this);

        ImageView filterIcon = findViewById(R.id.filterIcon);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        RecyclerView rcvRecipes = findViewById(R.id.rcvRecipes);
        rcvRecipes.setLayoutManager(new LinearLayoutManager(this));
        rcvRecipes.setAdapter(recipesAdapter);

        srlSwipeRefreshLayout = findViewById(R.id.srlSwipeRefreshLayout);
        srlSwipeRefreshLayout.setOnRefreshListener(this::updateCanteens);

        cgOrderBy = findViewById(R.id.cgOrderBy);
        cgOrderBy.setOnCheckedChangeListener((a, b) -> updateCanteens());

        cgCategory = findViewById(R.id.cgCategory);
        repo.findAllCategories().forEach(c -> cgCategory.addView(createChip(c)));

        sheetBehavior = BottomSheetBehavior.from(contentLayout);
        sheetBehavior.setFitToContents(false);
        sheetBehavior.setHideable(false);//prevents the bottom sheet from completely hiding off the screen
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//initially state to fully expanded


        filterIcon.setOnClickListener(v -> {
            toggleFilters();
        });
        findViewById(R.id.fabAdd).setOnClickListener(v -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            startActivity(intent);
        });


        TextInputEditText l = (TextInputEditText) findViewById(R.id.edtSearchEdit);
        l.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateCanteens();
            }
        });

        // great dao tests ((:
        /*CategoryDao dao = new CategoryDao(this);
        dao.findAll();
        Log.d("dbtest", dao.findById(2).getName());*/

        /*DescriptionImageDao dao = new DescriptionImageDao(this);
        Log.d("dbtest", dao.findById(1).getImagePath());
        dao.insert(new DbDescriptionImage(-1, "xPath", 2, 1));
        Log.d("dbtest", dao.findById(2).getImagePath());
        dao.delete(2);
        if(dao.findById(2) == null) Log.d("dbtest", "juhuuui");*/

        /*RecipeDao dao = new RecipeDao(this);
        Log.d("dbtest", dao.findById(1).getTitle());
        DbRecipe newRecipe = new DbRecipe(2, "xTitle", null,
                "xDescription", "xComment", 3);
        int insertid = dao.insert(newRecipe);
        Log.d("dbtest", "insertid: "+insertid);
        Log.d("dbtest", ""+dao.findById(2).getRating());
        newRecipe.setRating(4);
        dao.update(newRecipe);
        Log.d("dbtest", ""+dao.findById(2).getRating());
        dao.delete(2);
        if(dao.findById(2) == null) Log.d("dbtest", "juhuuui");*/

        /*RecipeCategoryDao dao = new RecipeCategoryDao(this);
        Log.d("dbtest", ""+dao.findByRecipeIdAndCategoryId(1,1).getRecipeId());
        dao.insert(new DbRecipeCategory(1,2));
        Log.d("dbtest", ""+dao.findByCategoryId(2).iterator().next().getRecipeId());
        dao.delete(1, 2);
        if(dao.findByCategoryId(2).size() == 0) Log.d("dbtest", "juhuuui");*/

        Recipe recipe = repo.findRecipeById(1);
        System.out.println();
        Recipe newRecipe = new Recipe(
                -1, "xTitle", null, "xDescription", "xComment",
                1, new ArrayList<Category>(Arrays.asList(new Category(2, ""), new Category(4, ""))),
                new ArrayList<String>(Arrays.asList("xPath1", "xPath2", "xPath3"))
        );
        repo.createRecipe(newRecipe);
        Collection<Recipe> coll = repo.findAllRecipes();
        System.out.println();
        newRecipe.setRating(4);
        ArrayList<Category> categories = newRecipe.getCategories();
        categories.remove(1);
        newRecipe.setCategories(categories);
        ArrayList<String> imagePaths = newRecipe.getDescriptionImagePaths();
        imagePaths.remove(1);
        newRecipe.setDescriptionImagePaths(imagePaths);
        repo.updateRecipe(newRecipe);
        coll = repo.findAllRecipes();
        System.out.println();
        repo.deleteRecipe(newRecipe.getId());
        coll = repo.findAllRecipes();
        System.out.println();

        updateCanteens();
    }

    private Chip createChip(Category c) {
        Chip chip = new Chip(this);
        chip.setText(c.getName());
        chip.setId(c.getId());
        return chip;
    }


    private void toggleFilters() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void updateCanteens() {
        srlSwipeRefreshLayout.setRefreshing(true);

        new AsyncTask<String, Void, Collection<Recipe>>() {
            @Override
            protected Collection<Recipe> doInBackground(String... params) {
                Collection<Recipe> recipes = repo.findAllRecipes();

                return recipes.stream()
                        .filter(r -> r.getTitle().toUpperCase().contains(params[0].toUpperCase()))
                        .filter(r ->  cgCategory.getCheckedChipIds().stream().allMatch(cId -> r.getCategories().stream().anyMatch(c -> c.getId() == cId)))
                        .sorted((o1, o2) -> {
                            switch (cgOrderBy.getCheckedChipId()) {
                                case 1: return o1.getRating() - o2.getRating();
                                case 2: return o2.getRating() - o1.getRating();
                                case 3: return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                                case 4: return o2.getTitle().compareToIgnoreCase(o1.getTitle());
                                default: return 0;
                            }
                        })
                        .collect(Collectors.toList());
            }

            @Override
            protected void onPostExecute(Collection<Recipe> recipes) {
                srlSwipeRefreshLayout.setRefreshing(false);
                recipesAdapter.displayRecipes(recipes);
            }
        }.execute(((TextInputLayout) findViewById(R.id.edtSearch)).getEditText().getText().toString());
    }
}
