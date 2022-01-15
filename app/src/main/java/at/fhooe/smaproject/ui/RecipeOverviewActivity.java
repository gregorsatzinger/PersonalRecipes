package at.fhooe.smaproject.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

import at.fhooe.smaproject.R;

public class RecipeOverviewActivity extends AppCompatActivity {
    private static final String TAG = RecipeOverviewActivity.class.toString();
    private BottomSheetBehavior<View> sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);

        ImageView filterIcon = findViewById(R.id.filterIcon);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setElevation(0);
        }

        sheetBehavior = BottomSheetBehavior.from(contentLayout);
        sheetBehavior.setFitToContents(false);
        sheetBehavior.setHideable(false);//prevents the bottom sheet from completely hiding off the screen
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//initially state to fully expanded

        filterIcon.setOnClickListener(v -> {
            toggleFilters();
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
        dao.insert(newRecipe);
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
    }


    private void toggleFilters(){
        if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        }
        else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}