package com.example.androidapp.ShopInventory;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidapp.R;

import java.util.List;

/**
 * Class to handle the List Adatper for the Shop elements
 */
public class ListAdapterInventory extends ArrayAdapter<ListItemObjectInventory> {
    /**
     * Creates a List Adapter for the Shop given context and a List
     * @param context this is the context used for the List Adapter
     * @param items a list of items to placce in the List Adapter
     */
    public ListAdapterInventory(Context context, List<ListItemObjectInventory> items) {
        super(context, 0, items);

    }

    /**
     * Gets the view from a position, parent, and a view to convert
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return Returns the View in the position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ListItemObjectInventory item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_shop, parent, false);
        }

        // Lookup view for data population
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemPrice = convertView.findViewById(R.id.itemDescription);

        // Populate the data into the template view using the data object
        itemName.setText(item.getName());
        itemPrice.setText(item.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }
}

