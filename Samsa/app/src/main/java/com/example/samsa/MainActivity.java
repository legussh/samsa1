package com.example.samsa;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static long [] cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long [] cart = new long [10000];

    }


    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void search(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void sh(View view) {
        Intent intent = new Intent(this, Shesterochka.class);
        startActivity(intent);
    }
    public void pks(View view) {
        Intent intent = new Intent(this, Piksi.class);
        startActivity(intent);
    }
    public void ash(View view) {
        Intent intent = new Intent(this, Ashot.class);
        startActivity(intent);
    }
    public void thispage (View view){
        Toast toast1 = Toast.makeText(MainActivity.this, "вы уже находитесь на этой странице", Toast.LENGTH_SHORT);
        toast1.show();
    }
}
