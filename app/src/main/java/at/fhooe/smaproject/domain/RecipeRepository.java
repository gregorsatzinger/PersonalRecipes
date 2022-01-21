package at.fhooe.smaproject.domain;

import java.util.Collection;

import at.fhooe.smaproject.models.Category;
import at.fhooe.smaproject.models.Recipe;

public interface RecipeRepository {
    // TODO: filter?
    Collection<Recipe> findAllRecipes();

    Recipe findRecipeById(int recipeId);

    int createRecipe(Recipe recipe);

    void updateRecipe(Recipe recipe);

    void deleteRecipe(int recipeId);

    Collection<Category> findAllCategories();

}
