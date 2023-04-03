package com.example.assignment04;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OfficialActivity extends AppCompatActivity {
    private static final String TAG = "OfficialActivity";
   private TextView officename, name, party,txtlocation,address,addressinfo,phone,phoneinfo,email,emailinfo,website,websiteinfo;
   private ImageView youtube,twitter,facebook,picon,perimage;
   private ScrollView scrollView;
   private ActivityResultLauncher<Intent> activityResultLauncher;
   private Officials n;
   private String part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);
        name = findViewById(R.id.txtname);
        officename = findViewById(R.id.txtoffice);
        party = findViewById(R.id.txtxparty);
        txtlocation = findViewById(R.id.txtlocation);

        address = findViewById(R.id.txtaddress);
        addressinfo = findViewById(R.id.textView4);

        phone = findViewById(R.id.txtphone);
        phoneinfo = findViewById(R.id.textView7);

        email = findViewById(R.id.txtemail);
        emailinfo = findViewById(R.id.textView8);

        website = findViewById(R.id.txtwebsite);
        websiteinfo = findViewById(R.id.textView10);

        youtube = findViewById(R.id.ictube);
        twitter = findViewById(R.id.ictwitter);
        facebook = findViewById(R.id.icfb);
        picon = findViewById(R.id.icparty);
        perimage = findViewById(R.id.imgpeople);
        scrollView = findViewById(R.id.scrolllview);
        facebook.setVisibility(View.VISIBLE);

        /*listchannels = office.getListchannels();
        loadChannelImage(listchannels);*/

         n = (Officials) getIntent().getSerializableExtra("EDIT_NOTE");
        if (getIntent().hasExtra("EDIT_NOTE")) {
            //n = (Officials) getIntent().getSerializableExtra("EDIT_NOTE");
            txtlocation.setText(n.getDisplayFinaltext());
        }

        if (n.getPersonname()!=""){
            name.setText(n.getPersonname());
        }

        if(n.getOfficename()!=""){
            officename.setText(n.getOfficename());
        }
        if (n.getOfficialparty()!="") {
            if (n.getOfficialparty().equals("Republican Party") || n.getOfficialparty().equals("Republican")) {
                scrollView.setBackgroundColor(Color.RED);
                facebook.setBackgroundColor(Color.RED);
                twitter.setBackgroundColor(Color.RED);
                youtube.setBackgroundColor(Color.RED);
                part = "https://www.gop.com";
                if (n.getOfficialparty().equals("Republican")) {
                    party.setText(String.format("(%s Party)", n.getOfficialparty()));
                } else party.setText(String.format("(%s)", n.getOfficialparty()));
                picon.setImageResource(R.drawable.rep_logo);
                perimage.setBackgroundColor(Color.RED);
            } else if (n.getOfficialparty().equals("Democratic Party") || n.getOfficialparty().equals("Democratic")) {
                scrollView.setBackgroundColor(Color.BLUE);
                facebook.setBackgroundColor(Color.BLUE);
                twitter.setBackgroundColor(Color.BLUE);
                youtube.setBackgroundColor(Color.BLUE);
                part = "https://democrats.org";
                if (n.getOfficialparty().equals("Democratic")) {
                    party.setText(String.format("(%s Party)", n.getOfficialparty()));
                } else party.setText(String.format("(%s)", n.getOfficialparty()));
                picon.setImageResource(R.drawable.dem_logo);
                perimage.setBackgroundColor(Color.BLUE);
            } else {
                scrollView.setBackgroundColor(Color.BLACK);
                picon.setVisibility(View.INVISIBLE);
                party.setText(n.getOfficialparty());
                facebook.setBackgroundColor(Color.BLACK);
                twitter.setBackgroundColor(Color.BLACK);
                youtube.setBackgroundColor(Color.BLACK);
                perimage.setBackgroundColor(Color.BLACK);
            }
        }else {
            scrollView.setBackgroundColor(Color.BLACK);
            picon.setVisibility(View.INVISIBLE);
            facebook.setBackgroundColor(Color.BLACK);
            twitter.setBackgroundColor(Color.BLACK);
            youtube.setBackgroundColor(Color.BLACK);
            perimage.setBackgroundColor(Color.BLACK);
        }
        if (n.getAddress()!=""){
            address.setText(n.getAddress());
            Linkify.addLinks(address, Linkify.ALL);
            address.setPaintFlags(address.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            address.setTextColor(Color.WHITE);
        }
        else {
            address.setVisibility(View.INVISIBLE);
            addressinfo.setVisibility(View.INVISIBLE);
        }
        if (n.getEmail()!=""){
            email.setText(n.getEmail());
            Linkify.addLinks(email, Linkify.ALL);
            email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            email.setTextColor(Color.WHITE);
            emailinfo.setTextColor(Color.WHITE);
        }
        else {
            email.setVisibility(View.GONE);
            emailinfo.setVisibility(View.GONE);
        }

        if(n.getPhoneNumber()!=""){
            phone.setText(n.getPhoneNumber());
            Linkify.addLinks(phone, Linkify.ALL);
            phone.setPaintFlags(phone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            phone.setTextColor(Color.WHITE);
        }
        else {
            phone.setVisibility(View.INVISIBLE);
            phoneinfo.setVisibility(View.INVISIBLE);
        }
        if(n.getWebsite()!=""){
            website.setText(n.getWebsite());
            Linkify.addLinks(website, Linkify.ALL);
            website.setPaintFlags(website.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            website.setTextColor(Color.WHITE);
        }
        else {
            website.setVisibility(View.INVISIBLE);
            websiteinfo.setVisibility(View.INVISIBLE);
        }
        if (n.getFacebookUrl()==null){
            facebook.setVisibility(View.INVISIBLE);
        }
        if (n.getTwitterUrl()==null){
            twitter.setVisibility(View.INVISIBLE);
        }
        if (n.getYoutubeUrl()==null){
            youtube.setVisibility(View.INVISIBLE);
        }

        loadimage(n.getPimageurl());

    }



    public void clickFacebook(View v) {
        // You need the FB user's id for the url

            String FACEBOOK_URL = "https://www.facebook.com/" + n.getFacebookUrl();
            Intent intent;

            // Check if FB is installed, if not we'll use the browser
            if (isPackageInstalled("com.facebook.katana")) {
                String urlToUse = "fb://facewebmodal/f?href=" + n.getFacebookUrl();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToUse));
            } else {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
            }
            // Check if there is an app that can handle fb or https intents
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                makeErrorAlert("No Application found that handles ACTION_VIEW (fb/https) intents");
            }

    }

    public void clickTwitter(View v) {
        String user = n.getTwitterUrl();
        String twitterAppUrl = "twitter://user?screen_name=" + user;
        String twitterWebUrl = "https://twitter.com/" + user;

        Intent intent;
        // Check if Twitter is installed, if not we'll use the browser
        if (isPackageInstalled("com.twitter.android")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterAppUrl));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebUrl));
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (twitter/https) intents");
        }

    }


    public void youTubeClicked(View v) {
        String name = n.getYoutubeUrl();
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        }
        catch (ActivityNotFoundException e) { startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/" + name)));}
    }



    public boolean isPackageInstalled(String packageName) {
        try {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void makeErrorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void loadimage(final String imageURL){

        if(imageURL == null){
           perimage.setImageResource(R.drawable.missing);
        }
        else {
            Picasso.get().load(imageURL)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missing)
                    .into(perimage);
        }
    }

    public void onpicclick (View v){
        if(n.getPimageurl() == null)
            return;
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra("EDIT_NOTE",n);
       //activityResultLauncher.launch(intent);
        startActivity(intent);
    }

    public void clickMap(View v) {
        String address = n.getAddress();

        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));

        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);

        // Check if there is an app that can handle geo intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (geo) intents");
        }
    }

    public void clickpart(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(part));
        // Check if there is an app that can handle https intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (https) intents");
            return;
        }
    }




}