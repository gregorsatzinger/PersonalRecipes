package at.fhooe.smaproject.models;

import android.graphics.Bitmap;

public class Recipe {
    private int id;
    private String title;
    private Bitmap titleImage;
    private String description;
    private String comment;
    private int rating;

    private String[] descriptionImagePaths;

    public Recipe(int id, String title, Bitmap titleImage, String description, String comment, int rating, String[] descriptionImagePaths) {
        this.id = id;
        this.title = title;
        this.titleImage = titleImage;
        this.description = description;
        this.comment = comment;
        this.rating = rating;
        this.descriptionImagePaths = descriptionImagePaths;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(Bitmap titleImage) {
        this.titleImage = titleImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String[] getDescriptionImagePaths() {
        return descriptionImagePaths;
    }

    public void setDescriptionImagePaths(String[] descriptionImagePaths) {
        this.descriptionImagePaths = descriptionImagePaths;
    }
}
