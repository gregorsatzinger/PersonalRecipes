package at.fhooe.smaproject.dal;

public class DbDescriptionImage {
    private int id;
    private String imagePath;
    private int position;
    private int recipeId;

    public DbDescriptionImage(int id, String imagePath, int position, int recipeId) {
        this.id = id;
        this.imagePath = imagePath;
        this.position = position;
        this.recipeId = recipeId;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
