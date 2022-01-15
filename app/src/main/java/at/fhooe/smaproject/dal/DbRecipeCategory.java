package at.fhooe.smaproject.dal;

public class DbRecipeCategory {
    private int recipeId;
    private int categoryId;

    public DbRecipeCategory(int recipeId, int categoryId) {
        this.recipeId = recipeId;
        this.categoryId = categoryId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
