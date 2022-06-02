package com.example.samsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Shesterochka extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ListView list;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shesterochka);
        list = (ListView) findViewById(R.id.list);
        sqlHelper = new DatabaseHelper(getApplicationContext());
        sqlHelper.create_db();
        search = (EditText) findViewById(R.id.search);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            db = sqlHelper.open();
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_COUNT, DatabaseHelper.COLUMN_PRICE};
            userAdapter = new SimpleCursorAdapter(this, R.layout.list,
                    userCursor, headers, new int[]{R.id.text1, R.id.text2, R.id.text3}, 0);


            search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    userAdapter.getFilter().filter(s.toString());
                }
            });

            userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {

                    if (constraint == null || constraint.length() == 0) {

                        return db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
                    } else {
                        return db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " + DatabaseHelper.COLUMN_NAME + " like ?", new String[]{"%" + constraint.toString() + "%"});
                    }
                }
            });

            list.setAdapter(userAdapter);

        } catch (SQLException ex) { }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
        userCursor.close();
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}