/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Angelika Juliah S. Galang
*/

/* Code History:
Initial Code Authored by: Angelika Juliah S. Galang
Update 3/9/2018: Richelle Yap
*/

/* File Creation Date: (Sprint 2) 2/21/2018
     Development Group: Group 1
     Client Group: CS 192 Class
     Purpose of File: Creates the screen for submitting a rating
*/

/* Variable Descriptions:

 */

package com.example.gelic.Sarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SubmitRating extends AppCompatActivity {
     private Button submit;
     EditText rating;
     TextView subRating;
     RatingBar food_quality;
     RatingBar pricing;
     RatingBar service;
     RatingBar ambience;

     TextView _name;
     TextView _location;
     TextView _cuisineType;
     TextView _rating;

     int foodStoreId;
     Bundle extras;
     ArrayList<String> foodStoreList;

     float fq;
     float p;
     float s;
     float a;
     String c;


     String resultJson;

     /*
     Method Name: onCreate
     Creation Date: 2/21/2018
     Purpose:
     Calling Arguments:
     Required Files:
     Database Tables:
     Return Value: None
      */
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_submit_rating);



          submit = findViewById(R.id.submitButt);
          subRating = findViewById(R.id.ratingText);
          food_quality = findViewById(R.id.food_quality);
          pricing = findViewById(R.id.pricing);
          service = findViewById(R.id.service);
          ambience = findViewById(R.id.ambience);

          _name = findViewById(R.id.foodStoreName);
          _location = findViewById(R.id.foodStoreLoc);
          _cuisineType = findViewById(R.id.foodStoreCuis);
          _rating = findViewById(R.id.foodStoreRat);

          extras = getIntent().getExtras();
          foodStoreId = extras.getInt("id");

          foodStoreList = extras.getStringArrayList("foodstore");

          _name.setText(foodStoreList.get(1));
          _location.setText(foodStoreList.get(2));
          _cuisineType.setText(foodStoreList.get(3));
          _rating.setText(foodStoreList.get(4));
          Glide.with(getApplicationContext()).load(foodStoreList.get(5)).into((ImageView) findViewById(R.id.imageView));

          submit.setOnClickListener(new View.OnClickListener() {

               /*
              Method Name: onClick
              Creation Date: 2/21/2018
              Purpose:
              Calling Arguments:
              Required Files:
              Database Tables:
              Return Value: None
               */
               @Override
               public void onClick(View view) {
                    rating = findViewById(R.id.submitText);

                    fq = food_quality.getRating();
                    p = pricing.getRating();
                    s = service.getRating();
                    a = ambience.getRating();
                    c = rating.getText().toString();

                    if (fq == 0 || p == 0 || s == 0 || a == 0 || c.isEmpty()){
                         Toast.makeText(SubmitRating.this, "Incomplete Rating Information", Toast.LENGTH_SHORT).show();
                    }
                    else {
                         Toast.makeText(SubmitRating.this, "Rating Submitted", Toast.LENGTH_SHORT).show();

                         sendPost();
                         Log.d("post","post task executed");



                    }
               }
          });


     }
     /*
 Method Name:
 Creation Date:
 Purpose:
 Calling Arguments:
 Required Files:
 Database Tables:
 Return Value:
  */
     public void sendPost() {
          Thread thread = new Thread(new Runnable() {

               @Override
               public void run() {
                    try {
                         HttpURLConnection urlConnection;
                         JSONObject jsonInput = new JSONObject();

                         String site_url_json = "https://rocky-retreat-95836.herokuapp.com/user/";
                         URL url = new URL(site_url_json);

                         urlConnection = (HttpURLConnection) url.openConnection();
                         urlConnection.setRequestMethod("POST");
                         urlConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                         urlConnection.setRequestProperty("Accept", "application/json");
                         urlConnection.setDoOutput(true);
                         urlConnection.setDoInput(true);
                         urlConnection.connect();

                         jsonInput.put("foodquality",fq);
                         jsonInput.put("pricing",p);
                         jsonInput.put("service",s);
                         jsonInput.put("ambience",a);
                         jsonInput.put("comment",c);
                         jsonInput.put("food_store_id",foodStoreId);

                         Log.d("JSONpost",jsonInput.toString());
                         DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                         os.writeBytes(jsonInput.toString());

                         os.flush();
                         os.close();

                         InputStream inputStream = urlConnection.getInputStream();
                         StringBuffer buffer = new StringBuffer();

                         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                         String line;
                         while ((line = reader.readLine()) != null) {
                              buffer.append(line);
                         }

                         resultJson = buffer.toString();
                         Log.d("json_result_post", resultJson);

                         JSONObject jsonObject;
                         try {
                              Log.d("resJ",resultJson);
                              jsonObject = new JSONObject(resultJson);
                              JSONArray jsonArray = jsonObject.getJSONArray("data");
                              JSONObject JO = jsonArray.getJSONObject(0);
                              foodStoreList.set(4,JO.getString("sarapp_rating"));
                              Log.d("foodStoreList",foodStoreList.get(4));
                              extras.remove("foodstore");
                              extras.putStringArrayList("foodstore",foodStoreList);
                         } catch (JSONException e) {
                              Log.d("errorFetch","errorFetch");
                              e.printStackTrace();
                         }

                         urlConnection.disconnect();
                         Intent resultIntent = new Intent();
                         resultIntent.putExtras(extras);
                         setResult(RESULT_OK, resultIntent);
                         finish();



                    } catch (Exception e) {
                         Log.d("submitExc","submit exception");
                         e.printStackTrace();
                    }
               }
          });

          thread.start();
     }

}
