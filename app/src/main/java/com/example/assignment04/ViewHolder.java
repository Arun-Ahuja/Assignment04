package com.example.assignment04;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
public TextView office, name,location;
public ImageView listimg;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        office = itemView.findViewById(R.id.txtpost);
        name = itemView.findViewById(R.id.txtparty);
        listimg = itemView.findViewById(R.id.imgperson);
    }
}
