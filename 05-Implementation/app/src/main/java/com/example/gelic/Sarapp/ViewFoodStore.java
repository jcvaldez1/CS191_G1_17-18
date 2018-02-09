/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Angelika Juliah S. Galang
*/

/* Code History:
Initial Code Authored by: Angelika Juliah S. Galang
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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import java.util.ArrayList;
import android.view.View;

public class ViewFoodStore extends AppCompatActivity {

     TextView name;
     TextView location;
     TextView cuisineType;
     TextView rating;
     ImageView img;
     Button ratingButton, googleMapButton;

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

          Bundle extras = getIntent().getExtras();
          if (extras != null) {

               ArrayList<String> foodStoreList = extras.getStringArrayList("foodstore");
               byte[] byteArray = extras.getByteArray("image");
               Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
               name.setText(foodStoreList.get(0));
               location.setText(foodStoreList.get(1));
               cuisineType.setText(foodStoreList.get(2));
               rating.setText(foodStoreList.get(3));
               img.setImageBitmap(bmp);

          }
     }


}
