package at.fhooe.smaproject.ui.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import at.fhooe.smaproject.BR;
import at.fhooe.smaproject.models.Recipe;

public class RecipeViewModel extends BaseObservable {
    private Recipe recipe;
    public RecipeViewModel(Recipe recipe) {
        this.recipe = recipe;
        recipe.setTitle("test");
    }

    @Bindable
    public String getTitle() {
        return recipe.getTitle();
    }

    public void setTitle(String value) {
        // Avoids infinite loops.
        if (!recipe.getTitle().equals(value)) {
            recipe.setTitle(value);

            // TODO: React to the change.

            // Notify observers of a new value.
            notifyPropertyChanged(BR.title);
        }
    }
}
