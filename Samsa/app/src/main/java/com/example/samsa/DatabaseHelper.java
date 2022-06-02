package com.example.samsa;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH;
    private static final String DB_NAME = "shops.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "shesterochka";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_COUNT = "count";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_SHOP = "shop";

    static final String TABLE1 = "piksy";
    static final String COLUMN_ID1 = "_id";
    static final String COLUMN_NAME1 = "name";
    static final String COLUMN_COUNT1 = "count";
    static final String COLUMN_PRICE1 = "price";
    static final String COLUMN_SHOP1 = "shop";

    static final String TABLE2 = "ashot";
    static final String COLUMN_ID2 = "_id";
    static final String COLUMN_NAME2 = "name";
    static final String COLUMN_COUNT2 = "count";
    static final String COLUMN_PRICE2 = "price";
    static final String COLUMN_SHOP2 = "shop";

    private static String DB_PATH1;
    private static final String DB_NAME1 = "cart.db";
    private static final int SCHEMA1 = 1;
    static final String TABLE3 = "cart";
    static final String COLUMN_ID3 = "_id";
    static final String COLUMN_NAME3 = "name";
    static final String COLUMN_COUNT3 = "count";
    static final String COLUMN_PRICE3 = "price";
    static final String COLUMN_SHOP3 = "shop";

    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DB_NAME;
        DB_PATH1 =context.getFilesDir().getPath() + DB_NAME1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    void create_db(){

        File file = new File(DB_PATH);
        if (!file.exists()) {
            try(InputStream myInput = myContext.getAssets().open(DB_NAME);
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }


    public SQLiteDatabase open()throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
