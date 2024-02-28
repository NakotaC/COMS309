package com.example.androidapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class ListAdapter extends ArrayAdapter<ClanItemObject> {

    public ListAdapter(Context context, List<ClanItemObject> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ClanItemObject clan = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recycleview_item, parent, false);
        }

        // Lookup view for data population
        TextView clanName = convertView.findViewById(R.id.clan_name);
        TextView clanID = convertView.findViewById(R.id.clan_level);

        // Populate the data into the template view using the data object
        clanName.setText(clan.getClan_name());
        clanID.setText(clan.getClanID());

        // Return the completed view to render on screen
        return convertView;
    }
}

