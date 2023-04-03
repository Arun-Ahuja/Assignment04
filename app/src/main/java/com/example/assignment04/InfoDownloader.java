package com.example.assignment04;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.LongDef;

public class InfoDownloader {

    private static final String url1 = "https://www.googleapis.com/civicinfo/v2/representatives";
    private static final String url2 = "?key=AIzaSyDAQwDQi8ZEa1MyQZxhPguSNcxhlFgcWuI&address=";
    private final MainActivity mainActivity;
    private final String find;
    private static final String TAG = "InfoDownloader";
    String name, party,phone,url,email,finalPhoneNumber,fb, tw,yt,photourl,personname,address,textf;

    public InfoDownloader(MainActivity mainActivity, String find) {
        this.mainActivity = mainActivity;
        this.find=find;
    }
    public  void alldownload(MainActivity mainActivity, String sname) {
        Log.d(TAG, "alldownload: ");
        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        Uri.Builder buildURL = Uri.parse(url1).buildUpon();
        String urlToUse = buildURL.build().toString();
        urlToUse = urlToUse + url2+ sname;
        Log.d(TAG, "alldownload: " + urlToUse);

        Response.Listener<JSONObject> listener =
                response -> parsfinace(mainActivity, response.toString());


        Response.ErrorListener error = error1 -> {
            Log.d(TAG, "dodownload: ");
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(new String(error1.networkResponse.data));
                Log.d(TAG, "getSourceData: " + jsonObject);
                parsfinace(mainActivity, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error);

        queue.add(jsonArrayRequest);

    }

    private void parsfinace(MainActivity mainActivity, String s) {
        try {
            JSONObject jObjMain = new JSONObject(s);

            JSONObject jNormalInput = jObjMain.getJSONObject("normalizedInput");
            JSONArray jOfficesArray = jObjMain.getJSONArray("offices");
            JSONArray jOfficialsArray = jObjMain.getJSONArray("officials");


            String city =  (String)jNormalInput.get("city");
            String state = (String)jNormalInput.get("state");
            String zip = (String)jNormalInput.get("zip");

            if(zip.isEmpty())
            {
                textf = city +","+state;
            }
            else
            {
                textf = city +","+state+","+zip;
            }

            for (int i = 0; i < jOfficesArray.length(); i++) {
                JSONObject rec = (JSONObject) jOfficesArray.get(i);
                name = rec.getString("name");  //put in array list of Government

                JSONArray officialIndices = rec.getJSONArray("officialIndices");


                for (int j = 0; j < officialIndices.length(); j++) {
                    int pos = Integer.parseInt(officialIndices.getString(j));
                   // Official official = new Official(officeName);
                    JSONObject jOfficial = jOfficialsArray.getJSONObject(pos);

                    personname = jOfficial.getString("name");

                    if (jOfficial.has("address")) {
                        JSONArray jAddresses = jOfficial.getJSONArray("address");
                        JSONObject jAddress = jAddresses.getJSONObject(0);
                       // String add1=null,add2=null,add3=null,cty=null,ste=null,zp=null;
                        String add1="",add2="",cty="",ste="",zp="";


                        if (jAddress.has("line1"))
                            add1 += jAddress.getString("line1");
                        if (jAddress.has("line2"))
                            add2 += jAddress.getString("line2");
                        if (jAddress.has("city"))
                            cty += jAddress.getString("city");
                        if (jAddress.has("state"))
                            ste += jAddress.getString("state");
                        if (jAddress.has("zip"))
                            zp += jAddress.getString("zip");

                           String address1 = add1 + add2;
                          String  address2 =  cty + ", " + ste+" "+zp;
                          address = address1 + ", "+address2;
                       // official.setOfficeAddress(address);
                    }

                    if (jOfficial.has("party"))
                       party = jOfficial.getString("party");
                    if (jOfficial.has("phones")) {

                        phone = jOfficial.getJSONArray("phones").getString(0);
                        String first = "(" + phone.subSequence(1, 4).toString() + ")";
                        String rest = phone.substring(6, phone.length());
                        finalPhoneNumber = first + " " + rest;
                    }
                    else {
                        finalPhoneNumber = "";
                    }

                    if (jOfficial.has("urls")){
                        url=jOfficial.getJSONArray("urls").getString(0);}
                    else{
                        url = "";
                    }
                    if (jOfficial.has("emails")){
                        email= jOfficial.getJSONArray("emails").getString(0);}
                    else {
                        email="";
                    }


                    if (jOfficial.has("channels")) {

                        JSONArray jChannels = jOfficial.getJSONArray("channels");
                        Log.d(TAG, "parsfinace: "+ jChannels);
                        for (int k = 0; k < jChannels.length(); k++) {
                            JSONObject jChannel = jChannels.getJSONObject(k);
                            if (jChannel.getString("type").equals("Facebook"))
                                fb=jChannel.getString("id");
                            if (jChannel.getString("type").equals("Twitter"))
                                tw=jChannel.getString("id");
                            if (jChannel.getString("type").equals("YouTube"))
                               yt=jChannel.getString("id");
                        }

                    }
                    if (jOfficial.has("photoUrl"))
                        photourl= jOfficial.getString("photoUrl");
                    //Log.d(TAG, "process1: " + official.toString());

                    Officials obj = new Officials(name,personname,party,photourl,address,finalPhoneNumber,email,url,textf,fb,tw,yt);
                    Log.d(TAG, "parsfinace: "+obj.getFacebookUrl());
                    mainActivity.add(obj);
                    photourl = null;

                }
            }
                } catch (Exception e) {
            Log.d(TAG, "parsfinace: " + e.getMessage());
        }
    }
}