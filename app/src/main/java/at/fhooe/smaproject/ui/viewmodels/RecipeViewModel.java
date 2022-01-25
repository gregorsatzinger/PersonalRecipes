package at.fhooe.smaproject.ui.viewmodels;

import android.widget.RatingBar;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

import at.fhooe.smaproject.BR;
import at.fhooe.smaproject.models.Recipe;

public class RecipeViewModel extends BaseObservable {
    private Recipe recipe;
    private boolean isEdit;

    public RecipeViewModel(Recipe recipe) {
        this.recipe = recipe;
        this.isEdit = recipe.getId() == -1;
    }

    /*
    public static void setRating(RatingBar mboundView4, float viewModelRating) {
    }*/

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.comment);
        notifyPropertyChanged(BR.description);
        notifyPropertyChanged(BR.rating);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Bindable
    public String getTitle() {
        return recipe.getTitle();
    }

    public void setTitle(String value) {
        if (!recipe.getTitle().equals(value)) {
            recipe.setTitle(value);
            notifyPropertyChanged(BR.title);
        }
    }

    @Bindable
    public String getComment() {
        return recipe.getComment();
    }

    public void setComment(String value) {
        if (!recipe.getComment().equals(value)) {
            recipe.setComment(value);
            notifyPropertyChanged(BR.comment);
        }
    }

    @Bindable
    public String getDescription() {
        return recipe.getDescription();
    }

    public void setDescription(String value) {
        if (!recipe.getDescription().equals(value)) {
            recipe.setDescription(value);
            notifyPropertyChanged(BR.description);
        }
    }

    @Bindable
    public float getRating() {
        return recipe.getRating();
    }

    public void setRating(float value) {
        if (recipe.getRating() != value) {
            recipe.setRating(value);
            notifyPropertyChanged(BR.rating);
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
