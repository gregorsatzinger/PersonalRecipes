package at.fhooe.smaproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import at.fhooe.smaproject.R;
import at.fhooe.smaproject.dal.DbDescriptionImage;
import at.fhooe.smaproject.dal.DbRecipe;
import at.fhooe.smaproject.dal.DbRecipeCategory;
import at.fhooe.smaproject.dal.DescriptionImageDao;
import at.fhooe.smaproject.dal.RecipeCategoryDao;
import at.fhooe.smaproject.dal.RecipeDao;

public class RecipeOverviewActivity extends AppCompatActivity {
    private static final String TAG = RecipeOverviewActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);

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
}