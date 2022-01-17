package at.fhooe.smaproject.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import at.fhooe.smaproject.R;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Details");
        }
        ImageView imvThumbnail = findViewById(R.id.imvThumbnail);
        imvThumbnail.setImageResource(R.drawable.ic_baseline_image_128);

    }
}