package at.fhooe.smaproject.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RecipeDao {
    private DbHelper dbHelper;

    private Bitmap blobToBitmap(byte[] blob) {
        if(blob == null) return null;
        ByteArrayInputStream imgStream = new ByteArrayInputStream(blob);
        return BitmapFactory.decodeStream(imgStream);
    }

    private byte[] bitmapToBlob(Bitmap bitmap) {
        if(bitmap == null) return null;
        ByteArrayOutputStream outStreamArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStreamArray);
        return outStreamArray.toByteArray();
    }

    public RecipeDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public DbRecipe findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] args = new String[]{ ""+id };
        try(Cursor cursor = db.rawQuery("" +
                        "select id, title, titleimage, description, comment, rating " +
                        "from recipe " +
                        "where id=?"
                , args)) {
            if(cursor.moveToNext()) {
                return new DbRecipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        blobToBitmap(cursor.getBlob(2)),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getFloat(5)
                );
            }
            return null;
        }
    }

    public Collection<DbRecipe> findAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor cursor = db.rawQuery(
                "select id, title, titleimage, description, comment, rating" +
                " from recipe", null)) {
            ArrayList<DbRecipe> coll = new ArrayList<>();
            while(cursor.moveToNext()) {
                coll.add(new DbRecipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        blobToBitmap(cursor.getBlob(2)),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getFloat(5)));
            }
            return coll;
        }
    }

    public int insert(DbRecipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Object[] args = new Object[]{
                recipe.getTitle(),
                bitmapToBlob(recipe.getTitleimage()),
                recipe.getDescription(),
                recipe.getComment(),
                recipe.getRating()
        };
        db.execSQL("insert into recipe(" +
                        "title, titleimage, description, comment, rating)\n" +
                        "values(?,?,?,?,?)"
                , args);
        try(Cursor c = db.rawQuery("select last_insert_rowid()", null)) {
            if(c.moveToNext()) return c.getInt(0);
        }
        return -1;
    }

    public void update(DbRecipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Object[] args = new Object[]{
                recipe.getTitle(),
                bitmapToBlob(recipe.getTitleimage()),
                recipe.getDescription(),
                recipe.getComment(),
                recipe.getRating(),
                recipe.getId()
        };
        db.execSQL("update recipe\n" +
                        "set title=?, titleimage=?, description=?, comment=?, rating=?\n" +
                        "where id=?"
                , args);
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from recipe where id=?",
                new String[]{ ""+id });
    }
}
