package at.fhooe.smaproject.logic;

import java.util.Collection;

import at.fhooe.smaproject.models.Recipe;

public interface RecipeLogic {
    // TODO: filter?
    Collection<Recipe> getAllRecipes();

    Recipe getRecipeById(int id);

    int createRecipe(Recipe recipe);

    boolean updateRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);
}
