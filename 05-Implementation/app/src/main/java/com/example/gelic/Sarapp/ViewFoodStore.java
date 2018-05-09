/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Angelika Juliah S. Galang
*/

/* Code History:
Initial Code Authored by: Angelika Juliah S. Galang
Update 2/22/18: Angelika Juliah S. Galang
Update 3/9/2018: Richelle Yap
*/

/* File Creation Date: (Sprint 1) 2/4/2018 to 2/8/2018
     Development Group: Group 1
     Client Group: CS 192 Class
     Purpose of File: Creates the screen for the viewing of a food store
*/

/* Variable Descriptions:
     name - of type TextView; holder of the view of the food store's name
     location - of type TextView; holder of the view of the food store's location
     cuisineType - of type TextView; holder of the view of the food store's cuisine type
     rating - of type TextView; holder of the view of the food store's rating
     img - of type ImageView; holder of the view of the food store's image
     extras - of type Bundle; holder of the data passed from previous activity
     foodStoreList - of type ArrayString<String>; holder of name of food stores list
     byteArray - of type byte[]; holder of the byte equivalent of the food store image
     bmp - of type Bitmap; holder of the food store's image
*/

package com.example.gelic.Sarapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewFoodStore extends AppCompatActivity{
     String resultJson = "";
     String resultJsonStore = "";
     int id;
     TextView name;
     TextView location;
     TextView cuisineType;
     TextView rating;
     Button ratingButton,ratingButton2;
     ArrayList<String> foodStoreList;
     byte[] byteArray;
     Bundle extras;
     ArrayList<Integer> ratingId = new ArrayList<>();
     ArrayList<String> ratingDate = new ArrayList<>();
     ArrayList<Integer> ratingQuality = new ArrayList<>();
     ArrayList<Integer> ratingPricing = new ArrayList<>();
     ArrayList<Integer> ratingService = new ArrayList<>();
     ArrayList<Integer> ratingAmbience = new ArrayList<>();
     ArrayList<String> ratingComment = new ArrayList<>();
     ArrayList<Double> ratingAverage = new ArrayList<>();
     ArrayAdapter<String> ratingAdapter;
     ListView ratingListView;
     int user_id = 0;
     float temp_rating = 0;
     private ProgressBar progressBar;

     /*
     Method Name: onCreate
     Creation Date: 2/6/2018
     Purpose: Sets the drawings and events of a layout
     Calling Arguments: Bundle savedInstanceState - holds the data of the saved instance of a state
     Required Files: activity_view_food_store.xml
     Database Tables: FoodStore
     Return Value: None
      */
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          Log.d("hello","hello");
          overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
          ratingButton = findViewById(R.id.button_id1);
          ratingButton2 = findViewById(R.id.button_id3);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);

          setContentView(R.layout.activity_view_food_store);

          progressBar = findViewById(R.id.progressBar2);
          progressBar.setVisibility(View.VISIBLE);

          name = findViewById(R.id.foodStoreName);
          location = findViewById(R.id.foodStoreLoc);
          cuisineType = findViewById(R.id.foodStoreCuis);
          rating = findViewById(R.id.foodStoreRat);

          extras = getIntent().getExtras();

          if (extras != null) {

               foodStoreList = extras.getStringArrayList("foodstore");
               id = Integer.parseInt(foodStoreList.get(0));
               Log.d("idView",String.valueOf(id));
               ParseTask asynctask = new ParseTask(this);
               asynctask.execute();
               progressBar.setVisibility(View.GONE);
               name.setText(foodStoreList.get(1));
               location.setText(foodStoreList.get(2));
               cuisineType.setText(foodStoreList.get(3));
               rating.setText(foodStoreList.get(4));
               Glide.with(getApplicationContext()).load(foodStoreList.get(5)).into((ImageView) findViewById(R.id.imageView));


          }

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
     public void updateAdapter(String resultJson) {
          Log.d("resultJson", resultJson);
          try {
               JSONObject jsonObject = new JSONObject(resultJson);
               JSONArray jsonArray = jsonObject.getJSONArray("data");
               int count = 0;

               while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    ratingId.add(JO.getInt("id"));
                    ratingQuality.add(JO.getInt("foodquality"));
                    ratingPricing.add(JO.getInt("pricing"));
                    ratingService.add(JO.getInt("service"));
                    ratingAmbience.add(JO.getInt("ambience"));
                    ratingComment.add(JO.getString("comment"));
                    ratingAverage.add(JO.getDouble("average"));

                    String time = JO.getString("created_at");
                    String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
                    String outputPattern = "MM/dd/yyyy";
                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                    Date date;
                    String str;
                    try {
                         date = inputFormat.parse(time);
                         str = outputFormat.format(date);
                         ratingDate.add(str);
                         Log.d("new_date", str);
                    } catch (ParseException e) {
                         e.printStackTrace();
                         ratingDate.add(time);
                         Log.d("new_date_error", time);
                    }
                    count++;
               }
          } catch (JSONException e) {
               e.printStackTrace();
          }

          Collections.reverse(ratingDate);
          Collections.reverse(ratingQuality);
          Collections.reverse(ratingPricing);
          Collections.reverse(ratingService);
          Collections.reverse(ratingAmbience);
          Collections.reverse(ratingComment);
          Collections.reverse(ratingAverage);

          ratingAdapter = new CustomRowAdapterRating(this, ratingDate, ratingQuality, ratingPricing,
                  ratingService, ratingAmbience, ratingComment, ratingAverage);
          ratingListView = findViewById(R.id.ratingList);
          ratingListView.setAdapter(ratingAdapter);

          if (!ratingId.isEmpty()) {
               user_id = ratingId.get(ratingId.size() - 1);
          }
          ratingListView.invalidateViews();
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
     public class ParseTask extends AsyncTask<Void, Void, String> {

          HttpURLConnection urlConnection = null;
          BufferedReader reader = null;
          private ViewFoodStore foodstore_page;

          public ParseTask(ViewFoodStore foodstore_page) {
               this.foodstore_page = foodstore_page;
          }


          @Override
          protected String doInBackground(Void... params) {
               try {

                    String site_url_json = "https://rocky-retreat-95836.herokuapp.com/user/" + String.valueOf(id);
                    URL url = new URL(site_url_json);

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                         buffer.append(line);
                    }

                    resultJson = buffer.toString();
                    Log.d("json_result_view", resultJson);
               } catch (Exception e) {
                    e.printStackTrace();
               }
               return resultJson;
          }

          protected void onPostExecute(String strJson) {
               super.onPostExecute(strJson);
               progressBar.setVisibility(View.GONE);
               foodstore_page.updateAdapter(strJson);
          }
     }

     /*
     Method Name: launchSubmitRating
     Creation Date: 2/21/2018
     Purpose: Launches the submit rating activity
     Calling Arguments:
     Required Files:
     Database Tables:
     Return Value: None
      */
     public void launchSubmitRating(View view) {
          Bundle dataBundle = new Bundle();
          dataBundle.putInt("id", id);
          dataBundle.putInt("user_id", user_id);
          dataBundle.putStringArrayList("foodstore", foodStoreList);
          dataBundle.putByteArray("image", byteArray);
          dataBundle.putFloat("rating", temp_rating);


          Intent intent = new Intent(this, SubmitRating.class);
          intent.putExtras(dataBundle);
          startActivityForResult(intent, 1);
     }

     /*
     Method Name: onActivityResult
     Creation Date: 3/9/2018
     Purpose: refreshes View Food Store activity when Submit Rating activity returns
     Calling Arguments:
     Required Files:
     Database Tables:
     Return Value: None
      */
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          if (resultCode == RESULT_OK) {
               Intent refresh = new Intent(this, ViewFoodStore.class);
               refresh.putExtras(data.getExtras());
               startActivity(refresh);
               this.finish();
          }
     }

}

