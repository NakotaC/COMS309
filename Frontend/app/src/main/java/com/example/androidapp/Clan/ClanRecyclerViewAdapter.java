package com.example.androidapp.Clan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidapp.R;

import java.util.LinkedList;

/**
 * The following class is the adapter used to create one full list of clans from many separate
 * individual clan items.
 */
public class ClanRecyclerViewAdapter extends RecyclerView.Adapter<ClanRecyclerViewAdapter.ViewHolder> {




   // variable declaration
    private final LinkedList<ClanItemObject> clanItemList;
    private final Context context;


    /**
     * The following method is used initialize the adapter.
     * @param clanItemList
     * @param context
     */
    public ClanRecyclerViewAdapter(LinkedList<ClanItemObject> clanItemList, Context context) {
        this.clanItemList = clanItemList;
        this.context = context;

    }


    /**
     * The following method returns the complete list of clans.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public ClanRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);

    }


    /**
     * The following method is used to set clan's name and level of each individual clan item in the
     * list.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ClanRecyclerViewAdapter.ViewHolder holder, int position) {



        ClanItemObject item = clanItemList.get(position);
        holder.clanNameLabel.setText(item.getClanName());
        holder.clanAvailability.setText(item.getClanLevel());

    }


    /**
     * The following method returns the number of clans in the list.
     * @return
     */
    @Override

    public int getItemCount() {
        return clanItemList.size();

    }


    /**
     * The following class is used to initialize the layout and items of a clan item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {


        //variable declaration

        private final TextView clanNameLabel;
        private final TextView clanAvailability;
        private final TextView clanLevel;
        private final Button joinButton;



        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            clanNameLabel = itemView.findViewById(R.id.clanNameLabel);
            clanAvailability = itemView.findViewById(R.id.clanAvailability);
            clanLevel = itemView.findViewById(R.id.clanLevel);
            joinButton = itemView.findViewById(R.id.joinButton);

        }

    }

}