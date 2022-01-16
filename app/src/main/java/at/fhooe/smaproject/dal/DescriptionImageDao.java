package at.fhooe.smaproject.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;

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

    // TODO: untested, but should work :)
    public Collection<DbDescriptionImage> findByRecipeId(int recipeId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] args = new String[]{ ""+recipeId };
        try(Cursor cursor = db.rawQuery("" +
                        "select id, imagePath, position, recipeId " +
                        "from DescriptionImage " +
                        "where recipeid=?" +
                        "order by position asc"
                , args)) {
            ArrayList<DbDescriptionImage> coll = new ArrayList<>();
            while(cursor.moveToNext()) {
                coll.add(new DbDescriptionImage(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3))
                );
            }
            return coll;
        }
    }

    public int insert(DbDescriptionImage descriptionImage) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = new String[]{
                descriptionImage.getImagePath(),
                ""+descriptionImage.getPosition(),
                ""+descriptionImage.getRecipeId()
        };
        db.execSQL("insert into descriptionimage(imagepath, position, recipeid)\n" +
                        "values(?,?,?)"
                , args);
        try(Cursor c = db.rawQuery("select last_insert_rowid()", null)) {
            if(c.moveToNext()) return c.getInt(0);
        }
        return -1;
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from descriptionimage where id=?",
                new String[]{ ""+id });
    }
}
