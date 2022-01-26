package at.fhooe.smaproject.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PersonalRecipes.db";

    // pls don't kill me greg :')
    private static final String CREATE_SCRIPT =
            "CREATE TABLE IF NOT EXISTS Category\n" +
                    "(\n" +
                    "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\tname  VARCHAR(50)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Recipe\n" +
                    "(\n" +
                    "\tid  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\ttitle  VARCHAR(50),\n" +
                    "\tdescription  TEXT,\n" +
                    "\tcomment  TEXT,\n" +
                    "\trating  INTEGER,\n" +
                    "\ttitleImage  BLOB\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS DescriptionImage\n" +
                    "(\n" +
                    "\tid  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\timagePath  VARCHAR(100),\n" +
                    "\tposition  INTEGER,\n" +
                    "\trecipeId  INTEGER,\n" +
                    "  \tFOREIGN KEY(recipeId) REFERENCES Recipe(id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Recipe_Category\n" +
                    "(\n" +
                    "\trecipeId  INTEGER,\n" +
                    "\tcategoryId  INTEGER,\n" +
                    "  \tPRIMARY KEY (recipeId, categoryId),\n" +
                    "  \tFOREIGN KEY(recipeId) REFERENCES Recipe(id),\n" +
                    "  \tFOREIGN KEY(categoryId) REFERENCES Category(id)\n" +
                    ")\n";

    private static final String INSERT_SCRIPT =
            "insert into category(name)\n" +
            "values ('Vegetarisch'), ('Vegan'), ('Fleisch'), ('Mehlspeise'), ('Dessert'), ('Suppe'), ('Knödel'), ('Beilage'), ('Aufstrich'), ('Vorspeise'), ('Pizza');";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // execSQL can only procede single statements
        for (String command :
                CREATE_SCRIPT.split(";")) {
            db.execSQL(command);
        }
        for (String command :
                INSERT_SCRIPT.split(";")) {
            db.execSQL(command);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: not needed in this micro project
    }
}
