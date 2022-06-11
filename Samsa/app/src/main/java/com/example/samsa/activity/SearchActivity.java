package com.example.samsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.samsa.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.adapters.ProductListAdapter;
import com.example.samsa.entity.ShopProduct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private ListView listShopProducts;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listShopProducts = (ListView) findViewById(R.id.list_shop_products);
        editTextSearch = (EditText) findViewById(R.id.edit_text_search);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<ShopProduct> shopProductList = new ArrayList<ShopProduct>();

        if (editTextSearch.getText().toString().equals("")) {
            Cursor cursorShopProduct = sqLiteDatabase.query(ShopProduct.TABLE_NAME, null, null, null, null, null, null);

            if (cursorShopProduct.moveToFirst()) {
                while (!cursorShopProduct.isAfterLast()) {
//                Log.d("hehe", cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_ID) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_NAME_SHOP) + " " +
//                        cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_COUNT) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_PRICE));

//                shopNames.add(cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT));

                    ShopProduct shopProduct = new ShopProduct(cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_ID),
                            cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_SHOP),
                            cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT),
                            cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_COUNT),
                            cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_PRICE),
                            sqLiteDatabase);
                    shopProductList.add(shopProduct);

                    cursorShopProduct.moveToNext();
                }
            }
            ProductListAdapter productListAdapter = new ProductListAdapter(this, shopProductList, false);
            listShopProducts.setAdapter(productListAdapter);
        }

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Cursor cursorSearch = sqLiteDatabase.query(ShopProduct.TABLE_NAME, null, ShopProduct.COLUMN_NAME_PRODUCT + " like ?",
                        new String[]{"%" + editTextSearch.getText().toString() + "%"}, null, null, null);

                List<ShopProduct> searchList = new ArrayList<ShopProduct>();

                if (cursorSearch.moveToFirst()) {
                    while (!cursorSearch.isAfterLast()) {
//                Log.d("hehe", cursorSearch.getInt(ShopProduct.NUM_COLUMN_ID) + " " +
//                        cursorSearch.getInt(ShopProduct.NUM_COLUMN_NAME_SHOP) + " " +
//                        cursorSearch.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT) + " " +
//                        cursorSearch.getInt(ShopProduct.NUM_COLUMN_COUNT) + " " +
//                        cursorSearch.getInt(ShopProduct.NUM_COLUMN_PRICE));

                        ShopProduct shopProduct = new ShopProduct(cursorSearch.getInt(ShopProduct.NUM_COLUMN_ID),
                                cursorSearch.getString(ShopProduct.NUM_COLUMN_NAME_SHOP),
                                cursorSearch.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT),
                                cursorSearch.getInt(ShopProduct.NUM_COLUMN_COUNT),
                                (cursorSearch.getInt(ShopProduct.NUM_COLUMN_PRICE)),
                                sqLiteDatabase);
                        searchList.add(shopProduct);

                        cursorSearch.moveToNext();
                    }
                }

                ProductListAdapter searchAdapter = new ProductListAdapter(SearchActivity.this, searchList, false);
                listShopProducts.setAdapter(searchAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}