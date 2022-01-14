package at.fhooe.smaproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import at.fhooe.smaproject.R;

public class RecipeOverviewActivity extends AppCompatActivity {
    private static final String TAG = RecipeOverviewActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);
    }
}