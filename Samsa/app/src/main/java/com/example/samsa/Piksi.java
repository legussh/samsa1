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
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Piksi extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ListView list;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piksi);
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
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE1, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_NAME1, DatabaseHelper.COLUMN_COUNT1, DatabaseHelper.COLUMN_PRICE1};
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

                        return db.rawQuery("select * from " + DatabaseHelper.TABLE1, null);
                    } else {
                        return db.rawQuery("select * from " + DatabaseHelper.TABLE1 + " where " + DatabaseHelper.COLUMN_NAME + " like ?",
                                new String[]{"%" + constraint.toString() + "%"});
                    }
                }
            });

            list.setAdapter(userAdapter);

//            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    ContentValues cv = new ContentValues();
//                    Cursor cursor = db.rawQuery("select " + DatabaseHelper.COLUMN_NAME1 + " from " + DatabaseHelper.TABLE1 + " where " + DatabaseHelper.COLUMN_ID1 + "=" + id, null);
//                            cv.put(DatabaseHelper.COLUMN_ID3, id);
//                    cv.put(DatabaseHelper.COLUMN_NAME3, cursor.toString());
//                    Toast toast1 = Toast.makeText(Piksi.this,  "успешно добавлено " + cursor.getString(2) , Toast.LENGTH_SHORT);
//                    toast1.show();
//
//                }
//            });

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