package at.fhooe.smaproject.ui.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import at.fhooe.smaproject.BR;
import at.fhooe.smaproject.models.Recipe;
import at.fhooe.smaproject.ui.utils.Mode;

public class RecipeViewModel extends BaseObservable {
    private Recipe recipe;
    private boolean isEdit;

    public RecipeViewModel(Recipe recipe) {
        this.recipe = recipe;
        recipe.setTitle("test");
        isEdit = true;
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

    @Bindable
    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean value) {
        if (isEdit != value) {
            isEdit = value;
            notifyPropertyChanged(BR.isEdit);
        }
    }

}
