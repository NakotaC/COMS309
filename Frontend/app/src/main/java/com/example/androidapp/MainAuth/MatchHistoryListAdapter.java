package com.example.androidapp.MainAuth;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidapp.R;

import java.util.List;

public class MatchHistoryListAdapter extends ArrayAdapter<MatchItemObject> {


    public MatchHistoryListAdapter(Context context, List<MatchItemObject> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MatchItemObject item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_match, parent,
                    false);
        }

        // Lookup view for data population
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemScore = convertView.findViewById(R.id.itemMatch);

        // Populate the data into the template view using the data object
        itemName.setText(item.getName());
        itemScore.setText(item.getMatch());

        // Return the completed view to render on screen
        return convertView;
    }
}

