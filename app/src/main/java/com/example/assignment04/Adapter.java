package com.example.assignment04;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "Adapter";
    private final MainActivity mainActivity;
    private final ArrayList<Officials> noteList;

    public Adapter(ArrayList<Officials> officialList, MainActivity mainActivity) {
        this.noteList = officialList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: CREATING NEW");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist, parent, false);

        itemView.setOnClickListener(mainActivity);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: SETTING DATA");

        Officials selectedGov = noteList.get(position);

        holder.office.setText(selectedGov.getOfficename());
        String partyString = "(" + selectedGov.getOfficialparty() + ")";
        holder.name.setText(selectedGov.getPersonname()+" "+partyString);

        if(selectedGov.getPimageurl() == null){
            holder.listimg.setImageResource(R.drawable.missing);
        }
        else {
            Picasso.get().load(selectedGov.getPimageurl())
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missing)
                    .into(holder.listimg);
        }
    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
