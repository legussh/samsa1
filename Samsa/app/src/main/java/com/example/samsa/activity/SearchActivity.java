package com.example.samsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.samsa.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.adapters.ProductListAdapter;
import com.example.samsa.entity.ShopProduct;

import java.util.ArrayList;
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

        Cursor cursorShopProduct = sqLiteDatabase.query(ShopProduct.TABLE_NAME, null, null, null, null, null, null);

        if (cursorShopProduct.moveToFirst()) {
            while (!cursorShopProduct.isAfterLast()) {
                Log.d("hehe", cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_ID) + " " +
                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_NAME_SHOP) + " " +
                        cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT) + " " +
                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_COUNT) + " " +
                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_PRICE));

                ShopProduct shopProduct = new ShopProduct(cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_ID),
                        cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_SHOP),
                        cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT),
                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_COUNT),
                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_PRICE));
                shopProductList.add(shopProduct);

                cursorShopProduct.moveToNext();
            }
        }

        ProductListAdapter productListAdapter = new ProductListAdapter(this, shopProductList, false);
        listShopProducts.setAdapter(productListAdapter);

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}