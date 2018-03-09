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

import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.view.View;

public class ViewFoodStore extends AppCompatActivity {

     int id;
     TextView name;
     TextView location;
     TextView cuisineType;
     TextView rating;
     ImageView img;
     Button ratingButton, googleMapButton, submitRating;
     ArrayList<String> foodStoreList;
     byte[] byteArray;
     Bundle extras;
     DBHandler dbHandler;
     ArrayList<UserRatings> ratings;
     ArrayList<Integer> ratingId = new ArrayList<>();
     ArrayList<String> ratingDate = new ArrayList<>();
     ArrayList<Float> ratingQuality = new ArrayList<>();
     ArrayList<Float> ratingPricing = new ArrayList<>();
     ArrayList<Float> ratingService = new ArrayList<>();
     ArrayList<Float> ratingAmbience = new ArrayList<>();
     ArrayList<String> ratingComment = new ArrayList<>();
     ArrayList<Float> ratingAverage = new ArrayList<>();
     ArrayAdapter<String> ratingAdapter;
     ListView ratingListView;
     int user_id = 0;
     float temp_rating = 0;

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
          overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
          ratingButton = findViewById(R.id.button_id1);
          googleMapButton = findViewById(R.id.button_id2);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);

          setContentView(R.layout.activity_view_food_store);

          name = findViewById(R.id.foodStoreName);
          location = findViewById(R.id.foodStoreLoc);
          cuisineType = findViewById(R.id.foodStoreCuis);
          rating = findViewById(R.id.foodStoreRat);
          img = findViewById(R.id.imageView);

          dbHandler = new DBHandler(this);
          extras = getIntent().getExtras();
          if (extras != null) {

               foodStoreList = extras.getStringArrayList("foodstore");
               byteArray = extras.getByteArray("image");
               Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

               id = Integer.parseInt(foodStoreList.get(0));
               Log.d("id", String.valueOf(id));
               name.setText(foodStoreList.get(1));
               location.setText(foodStoreList.get(2));
               cuisineType.setText(foodStoreList.get(3));
               //rating.setText(foodStoreList.get(4));


               img.setImageBitmap(bmp);

          }

          try {
               dbHandler.createDB();
          } catch (IOException ioe) {
               throw new Error("Unable to create database");
          }
          try {
               dbHandler.openDB();
          } catch (SQLiteException sqle) {
               throw sqle;
          }


          ratings = dbHandler.getAllRatings(id);

          temp_rating = dbHandler.getNewRating(id);
          String temp = String.valueOf(temp_rating);
          rating.setText(temp);

          for (UserRatings userRating : ratings) {
               ratingId.add(userRating.get_id());
               ratingDate.add(userRating.get_date());
               ratingQuality.add(userRating.get_quality());
               ratingPricing.add(userRating.get_pricing());
               ratingService.add(userRating.get_service());
               ratingAmbience.add(userRating.get_ambience());
               ratingComment.add(userRating.get_comment());
               ratingAverage.add(userRating.get_average());
          }
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


