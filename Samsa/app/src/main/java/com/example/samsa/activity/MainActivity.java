package com.example.samsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsa.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.adapters.ShopListAdapter;
import com.example.samsa.entity.Product;
import com.example.samsa.entity.Shop;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listShops;
    private TextView textViewSearch;
    private ImageButton buttonCart;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listShops = (ListView) findViewById(R.id.list_shops);
        textViewSearch = (TextView) findViewById(R.id.textview_search);
        buttonCart = (ImageButton) findViewById(R.id.button_cart);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Shop> shopList = new ArrayList<Shop>();

        Cursor cursorShop = sqLiteDatabase.query(Shop.TABLE_NAME, null, null, null, null, null, null);
        if (cursorShop.moveToFirst()) {
            while (!cursorShop.isAfterLast()) {
                Log.d("hehe", cursorShop.getInt(Shop.NUM_COLUMN_ID) + " " +
                        cursorShop.getString(Shop.NUM_COLUMN_NAME) + " " +
                        cursorShop.getInt(Shop.NUM_COLUMN_RES));

                Shop shop = new Shop(cursorShop.getInt(Shop.NUM_COLUMN_ID),
                        cursorShop.getString(Shop.NUM_COLUMN_NAME),
                        cursorShop.getInt(Shop.NUM_COLUMN_RES));
                shopList.add(shop);

                cursorShop.moveToNext();
            }
        }

        List<Product> productList = new ArrayList<Product>();

        Cursor cursorProduct = sqLiteDatabase.query(Product.TABLE_NAME, null, null, null, null, null, null);
        if (cursorProduct.moveToFirst()) {
            while (!cursorProduct.isAfterLast()) {
                Log.d("hehe", cursorProduct.getInt(Product.NUM_COLUMN_ID) + " " +
                        cursorProduct.getString(Product.NUM_COLUMN_NAME));

                Product product = new Product(cursorProduct.getInt(Product.NUM_COLUMN_ID),
                        cursorProduct.getString(Product.NUM_COLUMN_NAME));
                productList.add(product);

                cursorProduct.moveToNext();
            }
        }


        ShopListAdapter shopListAdapter = new ShopListAdapter(this, shopList);
        listShops.setAdapter(shopListAdapter);

        listShops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id = id + 1;
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                intent.putExtra("shop_id", id);

//                Toast toast = Toast.makeText(MainActivity.this, "" + id, Toast.LENGTH_SHORT);
//                toast.show();

                startActivity(intent);
            }
        });

        textViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });

        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

//        sqLiteDatabase.close();

    }
}
