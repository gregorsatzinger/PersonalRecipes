package at.fhooe.smaproject.dal;

import android.graphics.Bitmap;

public class DbRecipe {
    private int id;
    private String title;
    private Bitmap titleimage;
    private String description;
    private String comment;
    private int rating;

    public DbRecipe(int id, String title, Bitmap titleimage, String description, String comment, int rating) {
        this.id = id;
        this.title = title;
        this.titleimage = titleimage;
        this.description = description;
        this.comment = comment;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getTitleimage() {
        return titleimage;
    }

    public void setTitleimage(Bitmap titleimage) {
        this.titleimage = titleimage;
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
}
