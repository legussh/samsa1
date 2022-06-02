package com.example.samsa;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> {

    SQLiteDatabase db;
    DatabaseHelper sqlHelper;

    public ProductAdapter(Context context, Product[] products) {
        super(context, R.layout.list, products);
        db = sqlHelper.open();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        final Product product = getItem(position);
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list, null);
        }
        ((TextView)view.findViewById(R.id.text1)).setText(product.getName());
        ((TextView)view.findViewById(R.id.text2)).setText(String.valueOf(product.getCount()));
        ((TextView)view.findViewById(R.id.text2)).setText(String.valueOf(product.getPrice()));
        View finalView = view;
        ((ImageButton)view.findViewById(R.id.plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.COLUMN_NAME3,((TextView)finalView.findViewById(R.id.text1)).getText().toString());
                cv.put(DatabaseHelper.COLUMN_COUNT,((TextView)finalView.findViewById(R.id.text2)).getText().toString());
                cv.put(DatabaseHelper.COLUMN_PRICE3,((TextView)finalView.findViewById(R.id.text3)).getText().toString());
                cv.put(DatabaseHelper.COLUMN_SHOP3,((TextView)finalView.findViewById(R.id.text4)).getText().toString());
                db.insert(DatabaseHelper.TABLE, null, cv);
            }
        });
        return view;
    }
}
