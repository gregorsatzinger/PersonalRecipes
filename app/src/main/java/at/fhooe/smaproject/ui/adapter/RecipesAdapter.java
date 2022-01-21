package at.fhooe.smaproject.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import at.fhooe.smaproject.R;
import at.fhooe.smaproject.models.Recipe;
import at.fhooe.smaproject.ui.RecipeDetailActivity;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    private final List<Recipe> recipeList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updateView(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void displayRecipes(Collection<Recipe> recipes) {
        recipeList.clear();
        if (recipes != null) {
            recipeList.addAll(recipes);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tcvTitle = itemView.findViewById(R.id.tcvTitle);
        private final RatingBar rtbAverageRating = itemView.findViewById(R.id.rtbAverageRating);

        ViewHolder(View itemView) {
            super(itemView);
        }

        void updateView(final Recipe recipe) {
            tcvTitle.setText(recipe.getTitle());
            rtbAverageRating.setRating(recipe.getRating());
            //itemView.setOnClickListener(v -> v.getContext().startActivity(RecipeDetailActivity.createIntent(v.getContext(), recipe.getId())));
        }
    }
}
