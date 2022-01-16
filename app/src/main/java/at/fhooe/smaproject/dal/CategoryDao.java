package at.fhooe.smaproject.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

public class CategoryDao {

    private DbHelper dbHelper;

    public CategoryDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public DbCategory findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] args = new String[]{ ""+id };
        try(Cursor cursor = db.rawQuery("select id, name from category where id=?", args)) {
            if(cursor.moveToNext()) {
                return new DbCategory(cursor.getInt(0), cursor.getString(1));
            }
            return null;
        }
    }

    public Collection<DbCategory> findAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor cursor = db.rawQuery("select id, name from category", null)) {
            ArrayList<DbCategory> coll = new ArrayList<>();
            while(cursor.moveToNext()) {
                coll.add(new DbCategory(cursor.getInt(0), cursor.getString(1)));
            }
            return coll;
        }
    }
}
