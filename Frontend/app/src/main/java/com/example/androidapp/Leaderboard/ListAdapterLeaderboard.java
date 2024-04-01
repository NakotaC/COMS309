package com.example.androidapp.Leaderboard;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidapp.R;

import java.util.List;

/**
 * The following class is an adapter used to create a complete list of users from each individual
 * user object.
 */
public class ListAdapterLeaderboard extends ArrayAdapter<LeaderboardItemObject> {

    /**
     * The following method initializes the adapter
     * @param context
     * @param items
     */
    public ListAdapterLeaderboard(Context context, List<LeaderboardItemObject> items) {
        super(context, 0, items);
    }

    /**
     * The following method returns the complete list of users for the stats screen.
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LeaderboardItemObject item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_leaderboard, parent,
                    false);
        }

        // Lookup view for data population
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemScore = convertView.findViewById(R.id.itemScore);

        // Populate the data into the template view using the data object
        itemName.setText(item.getName());
        itemScore.setText(item.getScore());

        // Return the completed view to render on screen
        return convertView;
    }
}

