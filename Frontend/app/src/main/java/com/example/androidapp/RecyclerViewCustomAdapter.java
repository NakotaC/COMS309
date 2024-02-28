package com.example.androidapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> {

    private LinkedList clanList1;
    private Context context;
    public RecyclerViewCustomAdapter(LinkedList<ClanItemObject> clanList, Context context)
    {
    this.clanList1 = clanList;
    this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType)
    {
    View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item,
            parent, false);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCustomAdapter.ViewHolder holder, int position)
    {
    ClanItemObject clanObject = (ClanItemObject) clanList1.get(position);
    holder.clanName.setText(clanObject.getClan_name());
    holder.clanID.setText(clanObject.getClanID());
    }


    @Override
    public int getItemCount() {
        return clanList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView clanName;
        private TextView clanID;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clanName = itemView.findViewById(R.id.clan_name);
            clanID = itemView.findViewById(R.id.clan_level);
        }


    }
}

