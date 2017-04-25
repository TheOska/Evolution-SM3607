package kaihcheng7_kmlee65.scm.evolution.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FormulaDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "formulas.sqlite";
    public static final String DB_PATH = "/data/data/kaihcheng7_kmlee65.scm.evolution/databases/";

    private SQLiteDatabase db;
    private Context dbContext;


    public FormulaDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION );
        this.dbContext = context;
        if (checkDataBase()) {
            openDataBase();
        } else {
            try {
                this.getReadableDatabase();
                copyDataBase();
                this.close();
                openDataBase();
            } catch (IOException e ) {
                throw new Error("Error copying database");
            }
            Toast.makeText(context, "Initial database is created", Toast.LENGTH_SHORT);
        }

    }

    private void copyDataBase() throws IOException {
        InputStream is = dbContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH+DB_NAME;
        OutputStream os = new FileOutputStream(outFileName);
        if(os == null) {
            Log.d("oska", "os is null");
        }

        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer))>0) {
            os.write(buffer, 0, length);
        }

        os.flush();
        os.close();
        is.close();
    }

    private void openDataBase() throws SQLException {
        String dbPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        boolean result = false;
        try {
            String dbPath = DB_PATH+DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.v("oska", "database doesn't exist");
        }
        if (checkDB != null) {
            result = true;
            checkDB.close();
        }else{
            Log.d("oska", "db is null");
        }
        return result;
    }

    public String getFormula(String nameA, String nameB) {
        String sql = "SELECT result FROM synthesize WHERE (elemNameA=\"" + nameA + "\" AND elemNameB=\"" + nameB +
                "\") OR (elemNameB=\"" + nameA + "\" AND elemNameA=\"" + nameB + "\")";
        if(db == null)
            Log.d("oska", "db is null");
        Cursor c = db.rawQuery(sql, null);
        String result = null;
        if (c.moveToFirst()) {
            do {
                result = c.getString(0);
            } while (c.moveToNext());
        }
        return result;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
