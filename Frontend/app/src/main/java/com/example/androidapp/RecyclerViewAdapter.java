package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.LinkedList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {





    private LinkedList<ClanItemObject> clanItemList;
    private Context context;





    public RecyclerViewAdapter(LinkedList<ClanItemObject> clanItemList, Context context) {
        this.clanItemList = clanItemList;
        this.context = context;

    }



    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);

    }



    @Override

    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {



        ClanItemObject item = clanItemList.get(position);
        holder.clanNameLabel.setText(item.getClanName());
        holder.clanAvailability.setText(item.getClanLevel());
        holder.clanLevel.setText(item.getClanAvailability());

    }



    @Override

    public int getItemCount() {
        return clanItemList.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder {



        private TextView clanNameLabel, clanAvailability, clanLevel;
        private Button joinButton;



        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            clanNameLabel = itemView.findViewById(R.id.clanNameLabel);
            clanAvailability = itemView.findViewById(R.id.clanAvailability);
            clanLevel = itemView.findViewById(R.id.clanLevel);
            joinButton = itemView.findViewById(R.id.joinButton);

        }

    }

}