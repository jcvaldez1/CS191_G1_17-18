/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Richelle Yap
*/

/* Code History:
Initial Code Authored by: Richelle Yap
*/

/* File Creation Date: (Sprint 4) 3/20/2018 to 3/22/2018
     Development Group: Group 1
     Client Group: CS 192 Class
     Purpose of File: Layouts the activity that shows the searched food stores
*/

/* Variable Descriptions:
     foodStores = array of foodStores
     foodStoreId = array of food store ids
     foodStoreNames = array of food store name
     foodStoreCuisineTypes = array of food store cuisine types
     foodStoreLocations = array of food store locations
     foodStoreImages = array of food store images
     foodStoreRatings = array of food store average rating
     foodAdapter = the search row adapter responsible for showing the correct filtered food stores
     foodListView = the list view displaying the array of food stores
     dbHandler = the database handler of food stores
*/

package com.example.gelic.Sarapp;

import android.app.SearchManager;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.Menu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {

     ArrayList<FoodStores> foodStores = new ArrayList<>();
     ArrayList<Integer> foodStoreId = new ArrayList<>();
     ArrayList<String> foodStoreNames = new ArrayList<>();
     ArrayList<String> foodStoreCuisineTypes = new ArrayList<>();
     ArrayList<String> foodStoreLocations = new ArrayList<>();
     ArrayList<Float> foodStoreRatings = new ArrayList<>();
     ArrayList<Bitmap> foodStoreImages = new ArrayList<>();
     SearchRowAdapter foodAdapter;
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
          setContentView(R.layout.activity_search_list);
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
          foodAdapter = new SearchRowAdapter(this, foodStores);
          foodListView = findViewById(R.id.foodStoreList_search);
          foodListView.setAdapter(foodAdapter);

          foodListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {

                         /*
                         Method Name: onItemClick
                         Creation Date: 3/21/2018
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
                         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                              ArrayList<String> rowAttributes = new ArrayList<>();
                              Bundle dataBundle = new Bundle();
                              ByteArrayOutputStream stream;
                              byte[] byteArray;
                              Intent intent;

                              //rowAttributes.add(String.valueOf(foodStoreId.get(i)));
                              FoodStores temp = foodAdapter.getItem(i);
                              Log.d("on_item", String.valueOf(foodStoreNames.indexOf(temp.get_foodStoreName())));
                              Log.d("on_item2", temp.get_foodStoreName());
                              Log.d("on_item3", temp.get_foodStoreLocation());
                              Log.d("on_item2", temp.get_cuisineType());
                              Log.d("on_item2", String.valueOf(temp.get_rating()));


                              //rowAttributes.add(String.valueOf(temp.get_id()));
                              //mag-eerror kapag may same food store name
                              rowAttributes.add(String.valueOf(foodStoreNames.indexOf(temp.get_foodStoreName())+1));
                              rowAttributes.add(temp.get_foodStoreName());
                              rowAttributes.add(temp.get_foodStoreLocation());
                              rowAttributes.add(temp.get_cuisineType());
                              rowAttributes.add(String.valueOf(temp.get_rating()));

                              //rowAttributes.add(foodStoreNames.get(i));
                              //rowAttributes.add(foodStoreLocations.get(i));
                              //rowAttributes.add(foodStoreCuisineTypes.get(i));
                              //rowAttributes.add(String.valueOf(foodStoreRatings.get(i)));

                              stream = new ByteArrayOutputStream();
                              temp.get_image().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                              //foodStoreImages.get(i).compress(Bitmap.CompressFormat.JPEG, 100, stream);
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

     /*
     Method Name: onCreateOptionsMenu
     Creation Date: 3/22/2018
     Purpose: handles the menu which will hold the query  to be searched
     Calling Arguments: menu - contains the menu
     Required Files: sarapp.db, activity_search_list.xml, FoodStores.class, DBHandler.class
     Database Tables: FoodStore
     Return Value: boolean value of super.onCreateOptionsMenu(menu)
      */
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.search_menu, menu);
          MenuItem item = menu.findItem(R.id.search);
          SearchView searchView = (SearchView) item.getActionView();

          searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String s) {
                    return false;
               }

               @Override
               public boolean onQueryTextChange(String s) {
                    foodAdapter.getFilter().filter(s);
                    return false;
               }
          });
          return super.onCreateOptionsMenu(menu);
     }
}
