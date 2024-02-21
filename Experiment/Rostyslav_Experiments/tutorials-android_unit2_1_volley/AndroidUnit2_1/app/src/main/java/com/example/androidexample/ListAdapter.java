package com.example.androidexample;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends ArrayAdapter<ListItemType> {

    public ListAdapter(Context context, List<ListItemType> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemType item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,
                    false);
        }

        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemEmail = convertView.findViewById(R.id.itemEmail);

        itemName.setText(item.getName());
        itemEmail.setText(item.getEmail());

        return convertView;
    }
}

