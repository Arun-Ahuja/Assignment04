package com.example.assignment04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {
TextView txtlocation,txtnamephoto,txtofficephoto;
ImageView imgfull,icfull;
ScrollView scrollviewpicture;
private String part;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        txtlocation = findViewById(R.id.txtlocation);
        txtnamephoto = findViewById(R.id.txtnamephoto);
        txtofficephoto = findViewById(R.id.txtofficephoto);
        imgfull = findViewById(R.id.imgfull);
        icfull = findViewById(R.id.icfull);
        scrollviewpicture = findViewById(R.id.scrollviewpicture);

        Officials n = (Officials) getIntent().getSerializableExtra("EDIT_NOTE");
        if (getIntent().hasExtra("EDIT_NOTE")) {
            //Officials n = (Officials) getIntent().getSerializableExtra("EDIT_NOTE");
            txtlocation.setText(n.getDisplayFinaltext());
            txtnamephoto.setText(n.getPersonname());
            txtofficephoto.setText(n.getOfficename());

            loadimage(n.getPimageurl());
        }

        if(n.getOfficialparty()!=null){
            if (n.getOfficialparty().equals("Republican Party") || n.getOfficialparty().equals("Republican")){
                scrollviewpicture.setBackgroundColor(Color.RED);
                icfull.setImageResource(R.drawable.rep_logo);
                imgfull.setBackgroundColor(Color.RED);
                part = "https://www.gop.com";
            }
            else if (n.getOfficialparty().equals("Democratic Party") || n.getOfficialparty().equals("Democratic")) {
                scrollviewpicture.setBackgroundColor(Color.BLUE);
                icfull.setImageResource(R.drawable.dem_logo);
                imgfull.setBackgroundColor(Color.BLUE);
                part = "https://democrats.org";
            } else {
                scrollviewpicture.setBackgroundColor(Color.BLACK);
                icfull.setVisibility(View.INVISIBLE);
            }

            }
        else {
            scrollviewpicture.setBackgroundColor(Color.BLACK);
            icfull.setVisibility(View.INVISIBLE);
            imgfull.setBackgroundColor(Color.BLACK);
        }


    }
    public void loadimage(final String imageURL){

        if(imageURL == null){
            imgfull.setImageResource(R.drawable.missing);
        }
        else {
            Picasso.get().load(imageURL)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missing)
                    .into(imgfull);
        }
    }

    public void photopart(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(part));
        // Check if there is an app that can handle https intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (https) intents");
            return;
        }
    }

    private void makeErrorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}