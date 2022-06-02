package com.example.samsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.widget.Toast;


public class Search extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ListView list;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        list = (ListView) findViewById(R.id.list);
        search = (EditText) findViewById(R.id.search);
        sqlHelper = new DatabaseHelper(getApplicationContext());
        sqlHelper.create_db();

    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            db = sqlHelper.open();
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " union select * from " + DatabaseHelper.TABLE1 + " union select * from "
                    + DatabaseHelper.TABLE2 + " order by " + DatabaseHelper.COLUMN_SHOP, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_COUNT, DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_SHOP};
            userAdapter = new SimpleCursorAdapter(this, R.layout.list_item, userCursor, headers, new int[]{R.id.text, R.id.text1, R.id.text2, R.id.text3, R.id.text4}, 0);

//            if (!search.getText().toString().isEmpty())
//                userAdapter.getFilter().filter(search.getText().toString());

            search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) { }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    userAdapter.getFilter().filter(s.toString());
                }
            });

            userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {

                    if (constraint == null || constraint.length() == 0) {

                        return db.rawQuery("select * from " + DatabaseHelper.TABLE + " union select * from " + DatabaseHelper.TABLE1 + " union select * from "
                                + DatabaseHelper.TABLE2 + " order by " + DatabaseHelper.COLUMN_SHOP, null);
                    } else {
                        return db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " + DatabaseHelper.COLUMN_NAME + " like ?" + " or " + DatabaseHelper.COLUMN_SHOP + " like ?"
                                + " union select * from " + DatabaseHelper.TABLE1 + " where " + DatabaseHelper.COLUMN_NAME1 + " like ?" + " or " + DatabaseHelper.COLUMN_SHOP + " like ?"
                                + " union select * from " + DatabaseHelper.TABLE2 + " where " + DatabaseHelper.COLUMN_NAME2 + " like ?" + " or " + DatabaseHelper.COLUMN_SHOP + " like ?"
                                + " order by " + DatabaseHelper.COLUMN_SHOP, new String[]{"%" + constraint.toString() + "%", "%" + constraint.toString() + "%",
                                "%" + constraint.toString() + "%", "%" + constraint.toString() + "%", "%" + constraint.toString() + "%", "%" + constraint.toString() + "%"});
                    }
                }
            });

            list.setAdapter(userAdapter);





        } catch (SQLException ex) { }
    }


    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
        userCursor.close();
    }
}