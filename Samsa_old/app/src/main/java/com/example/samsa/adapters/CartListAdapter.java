package com.example.samsa.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsa.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.entity.Cart;
import com.example.samsa.entity.Shop;
import com.example.samsa.entity.ShopProduct;

import java.util.List;

public class CartListAdapter extends ArrayAdapter<Cart> {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private String previousShop = " ";
    public static final String changes = "com.example.samsa.adapters.action.CHANGES";

    public CartListAdapter(Context context, List<Cart> cart) {
        super(context, R.layout.listview_cart, cart);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final Cart cart = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_cart, null);
        }

        databaseHelper = new DatabaseHelper(getContext());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        ((ImageView)view.findViewById(R.id.image_shop)).setVisibility(View.GONE);

        String currentShop = cart.getName_shop();

        Log.d("hehe!!!!", currentShop + " " + previousShop);


        if(!currentShop.equals( previousShop) || DatabaseUtils.queryNumEntries(sqLiteDatabase, Cart.TABLE_NAME) == 1) {
            Cursor res =  sqLiteDatabase.rawQuery("select " + Shop.COLUMN_RES + " from " + Shop.TABLE_NAME +
                    " where " + Shop.COLUMN_NAME + " =? ", new String[]{currentShop});
            res.moveToFirst();

            ((ImageView)view.findViewById(R.id.image_shop)).setImageResource(res.getInt(0));
            ((ImageView)view.findViewById(R.id.image_shop)).setVisibility(View.VISIBLE);
        }
        previousShop = currentShop;

        ((TextView) view.findViewById(R.id.text_id)).setText(String.valueOf(cart.getId()));
        ((TextView) view.findViewById(R.id.text_product)).setText(String.valueOf(cart.getName_product()));
        ((TextView) view.findViewById(R.id.text_price)).setText(String.valueOf(cart.getPrice()  + " ₽"));
        ((TextView) view.findViewById(R.id.text_count)).setText(String.valueOf(cart.getCount() + " шт"));

        ((ImageButton)view.findViewById(R.id.delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper = new DatabaseHelper(getContext());
                sqLiteDatabase = databaseHelper.getReadableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(Cart.COLUMN_ID, cart.getId());
                contentValues.put(Cart.COLUMN_NAME_PRODUCT, cart.getName_product());
                contentValues.put(Cart.COLUMN_NAME_SHOP, cart.getName_shop());
                contentValues.put(Cart.COLUMN_COUNT, cart.getCount() - 1);
                contentValues.put(Cart.COLUMN_PRICE, cart.getPrice() - cart.getPrice()/cart.getCount());

                sqLiteDatabase.delete(Cart.TABLE_NAME,"id =?", new String[]{String.valueOf(cart.getId())});
                if(cart.getCount() > 1) {
                    sqLiteDatabase.insert(Cart.TABLE_NAME, null, contentValues);

                }
            }
        });

        ((ImageButton)view.findViewById(R.id.add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper = new DatabaseHelper(getContext());
                sqLiteDatabase = databaseHelper.getReadableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(Cart.COLUMN_ID, cart.getId());
                contentValues.put(Cart.COLUMN_NAME_PRODUCT, cart.getName_product());
                contentValues.put(Cart.COLUMN_NAME_SHOP, cart.getName_shop());
                contentValues.put(Cart.COLUMN_COUNT, cart.getCount() + 1);
                contentValues.put(Cart.COLUMN_PRICE, cart.getPrice() + cart.getPrice()/cart.getCount());

                sqLiteDatabase.delete(Cart.TABLE_NAME,"id =?", new String[]{String.valueOf(cart.getId())});
                sqLiteDatabase.insert(Cart.TABLE_NAME, null, contentValues);


            }
        });




        return view;
    }

}
