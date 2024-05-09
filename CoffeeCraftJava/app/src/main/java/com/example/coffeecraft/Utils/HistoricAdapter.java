package com.example.coffeecraft.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoricAdapter extends ArrayAdapter<Map.Entry<String, Integer>> {

    private Context context;
    private List<Map.Entry<String, Integer>> items;

    public HistoricAdapter(Context context, List<Map.Entry<String, Integer>> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        MaterialTextView textView = rowView.findViewById(android.R.id.text1);
        Map.Entry<String, Integer> entry = getItem(position);
        String value = entry.getKey() + " : " + entry.getValue();
        textView.setText(value);
        return rowView;
    }
}
