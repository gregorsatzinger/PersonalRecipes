package at.fhooe.smaproject.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import at.fhooe.smaproject.models.Category;

public class DescriptionImageDao {

    private DbHelper dbHelper;

    public DescriptionImageDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public DbDescriptionImage findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] args = new String[]{ ""+id };
        try(Cursor cursor = db.rawQuery("" +
                "select id, imagePath, position, recipeId " +
                "from DescriptionImage " +
                "where id=?"
                , args)) {
            if(cursor.moveToNext()) {
                return new DbDescriptionImage(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)
                );
            }
            return null;
        }
    }

    public void insert(DbDescriptionImage descriptionImage) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = new String[]{
                descriptionImage.getImagePath(),
                ""+descriptionImage.getPosition(),
                ""+descriptionImage.getRecipeId()
        };
        db.execSQL("insert into descriptionimage(imagepath, position, recipeid)\n" +
                        "values(?,?,?)"
                , args);
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from descriptionimage where id=?",
                new String[]{ ""+id });
    }
}
