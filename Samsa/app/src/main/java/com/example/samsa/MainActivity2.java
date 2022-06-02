package com.example.samsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    ListView list;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    long productId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        list = (ListView) findViewById(R.id.list);
        sqlHelper = new DatabaseHelper(getApplicationContext());
        sqlHelper.create_db();
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            productId = extras.getLong("id");
//            Toast toast2 = Toast.makeText(MainActivity2.this, ":)", Toast.LENGTH_SHORT);
//            toast2.show();
//        }
//        if (productId > 0) {
//
//            db = sqlHelper.open();
//            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " + DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(productId)});
//
//            userCursor.moveToFirst();
//            String[] headers = new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_COUNT, DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_SHOP};
//            userAdapter = new SimpleCursorAdapter(this, R.layout.list_item, userCursor, headers, new int[]{R.id.text, R.id.text1, R.id.text2, R.id.text3, R.id.text4}, 0);
//            list.setAdapter(userAdapter);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            db = sqlHelper.open();
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE3, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_NAME3, DatabaseHelper.COLUMN_COUNT3, DatabaseHelper.COLUMN_PRICE3, DatabaseHelper.COLUMN_SHOP};
            userAdapter = new SimpleCursorAdapter(this, R.layout.list_item, userCursor, headers, new int[]{R.id.text, R.id.text1, R.id.text2, R.id.text3}, 0);
        } catch (SQLException ex) { }
        list.setAdapter(userAdapter);
    }


    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void thispage(View view) {
        Toast toast1 = Toast.makeText(MainActivity2.this, "вы уже находитесь на этой странице", Toast.LENGTH_SHORT);
        toast1.show();
    }

}