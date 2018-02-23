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
     Purpose of File: Creates the screen of the food store list
*/

/* Variable Descriptions:
     foodStores - of type ArrayList<FoodStores>; holder of food stores from database
     foodStoreNames - of type ArrayList<String>; holder of name of food stores list
     foodStoreCuisineTypes - of type ArrayList<String>; holder of cuisine type of food stores list
     foodStoreLocations - of type ArrayList<String>; holder of location of food stores list
     foodStoreRatings - of type ArrayList<Float>; holder of rating of food stores list
     foodStoreImages - of type ArrayList<Bitmap>; holder of images of food stores list
     foodAdapter - of type ArrayAdapter<String>; holder of the custom row adapter of a list item
     foodListView - of type ListView; holder of the view of the foodAdapter
     dbHandler - of type DBHandler; holder of the database
     foodStore - of type FoodStores; iterator for foodStores
     rowAttributes - of type ArrayList<String>; holder of the attributes of a single food store
     dataBundle - of type Bundle; holder of the data to be passed on the next activity
     stream - of type ByteArrayOutputStream; holder of the food store's image stream
     byteArray - of type byte[]; holder of the array equivalent of the food store's image
     intent - of type Intent; holder of the ViewFoodStore class
*/

package com.example.gelic.Sarapp;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
     ArrayList<FoodStores> foodStores = new ArrayList<>();
     ArrayList<Integer> foodStoreId = new ArrayList<>();
     ArrayList<String> foodStoreNames = new ArrayList<>();
     ArrayList<String> foodStoreCuisineTypes = new ArrayList<>();
     ArrayList<String> foodStoreLocations = new ArrayList<>();
     ArrayList<Float> foodStoreRatings = new ArrayList<>();
     ArrayList<Bitmap> foodStoreImages = new ArrayList<>();
     ArrayAdapter<String> foodAdapter;
     ListView foodListView;
     DBHandler dbHandler;



     /*
     Method Name: onCreate
     Creation Date: 2/5/2018
     Purpose: Opens the database and store all information about the food stores
     Calling Arguments: Bundle savedInstanceState - holds the data of the saved instance of a state
     Required Files: sarapp.db, activity_list.xml, FoodStores.class, DBHandler.class
     Database Tables: FoodStore
     Return Value: None
      */
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_list);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          dbHandler = new DBHandler(this);

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



          foodStores = dbHandler.getAllFoodStores();

          for (FoodStores foodStore : foodStores) {
               foodStoreId.add(foodStore.get_id());
               foodStoreNames.add(foodStore.get_foodStoreName());
               foodStoreCuisineTypes.add(foodStore.get_cuisineType());
               foodStoreLocations.add(foodStore.get_foodStoreLocation());
               foodStoreRatings.add(foodStore.get_rating());
               foodStoreImages.add(foodStore.get_image());
          }
          foodAdapter = new CustomRowAdapter(this, foodStoreNames, foodStoreCuisineTypes,
                    foodStoreLocations, foodStoreRatings, foodStoreImages);
          foodListView = findViewById(R.id.foodStoreList);
          foodListView.setAdapter(foodAdapter);

          foodListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {

                         /*
                         Method Name: onItemClick
                         Creation Date: 2/5/2018
                         Purpose: Links the clickable list item to its corresponding activity
                         Calling Arguments: AdapterView<?> adapterView - holder of the custom row adapter
                                             View view - holder of the drawings and events of a layout
                                             int i - holder of the position of the item in the list
                                             long l - holder of the long float value of the clicked item
                         Required Files: ViewFoodStore.class
                         Database Tables: FoodStore
                         Return Value: None
                          */
                         @Override
                         public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {

                              ArrayList<String> rowAttributes = new ArrayList<>();
                              Bundle dataBundle = new Bundle();
                              ByteArrayOutputStream stream;
                              byte[] byteArray;
                              Intent intent;

                              rowAttributes.add(String.valueOf(foodStoreId.get(i)));
                              rowAttributes.add(foodStoreNames.get(i));
                              rowAttributes.add(foodStoreLocations.get(i));
                              rowAttributes.add(foodStoreCuisineTypes.get(i));
                              rowAttributes.add(String.valueOf(foodStoreRatings.get(i)));

                              stream = new ByteArrayOutputStream();
                              foodStoreImages.get(i).compress(Bitmap.CompressFormat.JPEG, 100, stream);
                              byteArray = stream.toByteArray();

                              dataBundle.putByteArray("image", byteArray);
                              dataBundle.putStringArrayList("foodstore", rowAttributes);

                              intent = new Intent(getApplicationContext(), ViewFoodStore.class);
                              intent.putExtras(dataBundle);
                              startActivity(intent);
                         }
                    }
          );


     }

}
