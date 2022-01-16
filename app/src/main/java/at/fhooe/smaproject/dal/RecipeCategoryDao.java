package at.fhooe.smaproject.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;

public class RecipeCategoryDao {

    private DbHelper dbHelper;

    public RecipeCategoryDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public Collection<DbRecipeCategory> findByRecipeId(int recipeId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] args = new String[]{ ""+recipeId };
        try(Cursor cursor = db.rawQuery("" +
                        "select recipeid, categoryid " +
                        "from recipe_category " +
                        "where recipeid=?"
                , args)) {
            ArrayList<DbRecipeCategory> coll = new ArrayList<>();
            while(cursor.moveToNext()) {
                coll.add(new DbRecipeCategory(
                        cursor.getInt(0),
                        cursor.getInt(1))
                );
            }
            return coll;
        }
    }

    public Collection<DbRecipeCategory> findByCategoryId(int categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] args = new String[]{ ""+categoryId };
        try(Cursor cursor = db.rawQuery("" +
                        "select recipeid, categoryid " +
                        "from recipe_category " +
                        "where categoryid=?"
                , args)) {
            ArrayList<DbRecipeCategory> coll = new ArrayList<>();
            while(cursor.moveToNext()) {
                coll.add(new DbRecipeCategory(
                        cursor.getInt(0),
                        cursor.getInt(1))
                );
            }
            return coll;
        }
    }

    public DbRecipeCategory findByRecipeIdAndCategoryId(int recipeId, int categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] args = new String[]{ ""+categoryId };
        try(Cursor cursor = db.rawQuery("" +
                        "select recipeid, categoryid " +
                        "from recipe_category " +
                        "where categoryid=?"
                , args)) {
            if(cursor.moveToNext()) {
                return new DbRecipeCategory(
                        cursor.getInt(0),
                        cursor.getInt(1)
                );
            }
            return null;
        }
    }

    public int insert(DbRecipeCategory recipeCategory) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Object[] args = new Object[]{
                recipeCategory.getRecipeId(),
                recipeCategory.getCategoryId()
        };
        db.execSQL("insert into recipe_category(recipeid, categoryid)\n" +
                        "values(?,?)"
                , args);
        try(Cursor c = db.rawQuery("select last_insert_rowid()", null)) {
            if(c.moveToNext()) return c.getInt(0);
        }
        return -1;
    }

    public void delete(int recipeid, int categoryid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from recipe_category where recipeid=? and categoryid=?",
                new Object[]{ recipeid, categoryid });
    }

}
