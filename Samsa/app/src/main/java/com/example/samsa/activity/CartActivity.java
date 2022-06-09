package com.example.samsa.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.samsa.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.adapters.CartListAdapter;
import com.example.samsa.adapters.ProductListAdapter;
import com.example.samsa.entity.Cart;
import com.example.samsa.entity.Shop;
import com.example.samsa.entity.ShopProduct;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView imageShop;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        listView = (ListView)findViewById(R.id.list_cart);
        imageShop = (ImageView)findViewById(R.id.image_shop);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

          List<Cart> cartList = new ArrayList<Cart>();

          Cursor cursorCart = sqLiteDatabase.query(Cart.TABLE_NAME, null, null, null, null, null, Cart.COLUMN_NAME_SHOP);

          if (cursorCart.moveToFirst()) {
              while (!cursorCart.isAfterLast()) {
                  Log.d("hehe", cursorCart.getInt(Cart.NUM_COLUMN_ID) + " " +
                          cursorCart.getInt(Cart.NUM_COLUMN_NAME_SHOP) + " " +
                          cursorCart.getString(Cart.NUM_COLUMN_NAME_PRODUCT) + " " +
                          cursorCart.getInt(Cart.NUM_COLUMN_PRICE) + " " +
                          cursorCart.getInt(Cart.NUM_COLUMN_COUNT));

                  Cart cart = new Cart(cursorCart.getInt(Cart.NUM_COLUMN_ID),
                          cursorCart.getString(Cart.NUM_COLUMN_NAME_SHOP),
                          cursorCart.getString(Cart.NUM_COLUMN_NAME_PRODUCT),
                          cursorCart.getInt(Cart.NUM_COLUMN_PRICE),
                          cursorCart.getInt(Cart.NUM_COLUMN_COUNT));

                  cartList.add(cart);

                  cursorCart.moveToNext();
              }
          }
          CartListAdapter cartListAdapter = new CartListAdapter(this, cartList);
          listView.setAdapter(cartListAdapter);


    }
}

