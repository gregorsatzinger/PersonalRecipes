package at.fhooe.smaproject.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;

public class Recipe {
    private int id;
    private String title;
    private Bitmap titleImage;
    private String description;
    private String comment;
    private float rating;

    private ArrayList<Category> categories;
    private ArrayList<String> descriptionImagePaths;

    public Recipe(int id, String title, Bitmap titleImage, String description, String comment, float rating, ArrayList<Category> categories, ArrayList<String> descriptionImagePaths) {
        this.id = id;
        this.title = title;
        this.titleImage = titleImage;
        this.description = description;
        this.comment = comment;
        this.rating = rating;
        this.categories = categories;
        this.descriptionImagePaths = descriptionImagePaths;
    }

    public Recipe() {
        this.id = -1;
        this.categories = new ArrayList<>();
        this.descriptionImagePaths = new ArrayList<>();
        this.title = "";
        this.description = "";
        this.comment = "";
        this.rating = 5;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getDescriptionImagePaths() {
        return descriptionImagePaths;
    }

    public void setDescriptionImagePaths(ArrayList<String> descriptionImagePaths) {
        this.descriptionImagePaths = descriptionImagePaths;
    }
}
