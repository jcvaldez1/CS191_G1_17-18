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

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

     ArrayList<Integer> foodStoreId = new ArrayList<>();
     ArrayList<String> foodStoreNames = new ArrayList<>();
     ArrayList<String> foodStoreCuisineTypes = new ArrayList<>();
     ArrayList<String> foodStoreLocations = new ArrayList<>();
     ArrayList<String> foodStoreRatings = new ArrayList<>();
     ArrayList<String> foodStoreImages = new ArrayList<>();
     ArrayAdapter<String> foodAdapter;
     ListView foodListView;
     String resultJson = "";
     private ProgressBar progressBar;

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
          if (isOnline()) {
               progressBar = findViewById(R.id.progressBar);
               getSupportActionBar().setDisplayHomeAsUpEnabled(true);
               progressBar.setVisibility(View.VISIBLE);
               ParseTask asynctask = new ParseTask(this);
               asynctask.execute();
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

     public boolean isOnline() {
          ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

          if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
               AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();

               alertDialog.setTitle("OH NO :(");
               alertDialog.setMessage("No Internet Connection! Check your WiFi or Mobile Data settings and try again.");
               alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
               alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         finish();
                    }
               });

               alertDialog.show();
               return false;
          }
          return true;
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

          Log.d("json_str", resultJson);
          try {
               JSONObject jsonObject = new JSONObject(resultJson);
               JSONArray jsonArray = jsonObject.getJSONArray("data");

               foodStoreId.removeAll(foodStoreId);
               foodStoreNames.removeAll(foodStoreNames);
               foodStoreLocations.removeAll(foodStoreLocations);
               foodStoreCuisineTypes.removeAll(foodStoreCuisineTypes);
               foodStoreRatings.removeAll(foodStoreRatings);
               foodStoreImages.removeAll(foodStoreImages);
               int count = 0;

               while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    foodStoreId.add(JO.getInt("id"));
                    foodStoreNames.add(JO.getString("name"));
                    foodStoreLocations.add(JO.getString("location"));
                    foodStoreCuisineTypes.add(JO.getString("cuisineType"));
                    foodStoreRatings.add(JO.getString("sarapp_rating"));
                    foodStoreImages.add(JO.getString("image"));
                    count++;

               }
          } catch (JSONException e) {
               e.printStackTrace();
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
                         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                              ArrayList<String> rowAttributes = new ArrayList<>();
                              Bundle dataBundle = new Bundle();
                              Intent intent;

                              rowAttributes.add(String.valueOf(foodStoreId.get(i)));
                              Log.d("idFS", String.valueOf(foodStoreId.get(i)));
                              rowAttributes.add(foodStoreNames.get(i));
                              rowAttributes.add(foodStoreLocations.get(i));
                              rowAttributes.add(foodStoreCuisineTypes.get(i));
                              rowAttributes.add(foodStoreRatings.get(i));
                              rowAttributes.add(foodStoreImages.get(i));
                              dataBundle.putStringArrayList("foodstore", rowAttributes);

                              intent = new Intent(getApplicationContext(), ViewFoodStore.class);
                              intent.putExtras(dataBundle);
                              startActivity(intent);
                         }
                    }
          );
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
     public class ParseTask extends AsyncTask<Void, Integer, String> {

          HttpURLConnection urlConnection = null;
          BufferedReader reader = null;
          private ListActivity foodstore_list;

          public ParseTask(ListActivity foodstore_list) {
               this.foodstore_list = foodstore_list;
          }


          @Override
          protected String doInBackground(Void... params) {
               try {

                    String site_url_json = "https://rocky-retreat-95836.herokuapp.com/food_store";
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
                         Log.d("BUFFER", line);
                    }

                    resultJson = buffer.toString();
                    Log.d("json_result_main", resultJson);
               } catch (Exception e) {
                    e.printStackTrace();
               }
               return resultJson;
          }

          protected void onPostExecute(String strJson) {
               super.onPostExecute(strJson);
               progressBar.setVisibility(View.GONE);
               foodstore_list.updateAdapter(strJson);

          }
     }

     @Override
     public void onResume() {
          super.onResume();
          //When BACK BUTTON is pressed, the activity on the stack is restarted
          //Do what you want on the refresh procedure here
          Log.d("RESUME", "theresume");
          progressBar = findViewById(R.id.progressBar);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          progressBar.setVisibility(View.VISIBLE);
          ParseTask asynctask = new ParseTask(this);
          asynctask.execute();
     }
}