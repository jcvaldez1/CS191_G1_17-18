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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {

     ArrayList<FoodStores> foodStores = new ArrayList<>();
     ArrayList<Integer> foodStoreId = new ArrayList<>();
     ArrayList<String> foodStoreNames = new ArrayList<>();
     ArrayList<String> foodStoreCuisineTypes = new ArrayList<>();
     ArrayList<String> foodStoreLocations = new ArrayList<>();
     ArrayList<String> foodStoreRatings = new ArrayList<>();
     ArrayList<String> foodStoreImages = new ArrayList<>();
     SearchRowAdapter foodAdapter;
     ListView foodListView;
     FoodStores foodStore;
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
          setContentView(R.layout.activity_search_list);
          if (isOnline()){
               progressBar = findViewById(R.id.progressBar);
               getSupportActionBar().setDisplayHomeAsUpEnabled(true);
               progressBar.setVisibility(View.VISIBLE);
               getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
               AlertDialog alertDialog = new AlertDialog.Builder(SearchListActivity.this).create();

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


     /*
   Method Name:
   Creation Date:
   Purpose:
   Calling Arguments:
   Required Files:
   Database Tables:
   Return Value:
   */
     public void updateAdapter (String resultJson){
          Log.d("json_list",resultJson);
          try {
               JSONObject jsonObject = new JSONObject(resultJson);
               JSONArray jsonArray = jsonObject.getJSONArray("data");

               foodStores.removeAll(foodStores);
               foodStoreId.removeAll(foodStoreId);
               foodStoreNames.removeAll(foodStoreNames);
               foodStoreLocations.removeAll(foodStoreLocations);
               foodStoreCuisineTypes.removeAll(foodStoreCuisineTypes);
               foodStoreRatings.removeAll(foodStoreRatings);
               foodStoreImages.removeAll(foodStoreImages);
               int count = 0;
               while (count < jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    foodStoreId.add(JO.getInt("id"));
                    foodStoreNames.add(JO.getString("name"));
                    foodStoreLocations.add(JO.getString("location"));
                    foodStoreCuisineTypes.add(JO.getString("cuisineType"));
                    foodStoreRatings.add(JO.getString("sarapp_rating"));
                    foodStoreImages.add(JO.getString("image"));
                    Log.d("SARAPP_Name",foodStoreNames.get(count));
                    Log.d("SARAPP_Rating",foodStoreRatings.get(count));
                    foodStore = new FoodStores(foodStoreId.get(count),
                              foodStoreNames.get(count),
                              foodStoreLocations.get(count),foodStoreCuisineTypes.get(count),
                              foodStoreRatings.get(count),
                              foodStoreImages.get(count));
                    foodStores.add(foodStore);
                    count++;

               }
          } catch (JSONException e) {
               e.printStackTrace();
          }

          foodAdapter = new SearchRowAdapter(this, foodStores);
          foodListView = findViewById(R.id.foodStoreList_search);
          foodListView.setAdapter(foodAdapter);
          foodAdapter.notifyDataSetChanged();

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
                              Intent intent;

                              FoodStores temp = foodAdapter.getItem(i);
                              Log.d("on_item", String.valueOf(foodStoreNames.indexOf(temp.get_foodStoreName())));
                              Log.d("on_item2", temp.get_foodStoreName());
                              Log.d("on_item3", temp.get_foodStoreLocation());
                              Log.d("on_item2", temp.get_cuisineType());
                              Log.d("on_item2", String.valueOf(temp.get_rating()));

                              int yes = foodStoreNames.indexOf(temp.get_foodStoreName());
                              rowAttributes.add(String.valueOf(foodStoreId.get(yes)));
                              Log.d("indexof",String.valueOf(foodStoreId.get(yes)));
                              rowAttributes.add(temp.get_foodStoreName());
                              rowAttributes.add(temp.get_foodStoreLocation());
                              rowAttributes.add(temp.get_cuisineType());
                              rowAttributes.add(String.valueOf(temp.get_rating()));
                              rowAttributes.add(temp.get_image());
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
                    foodAdapter.getFilter().filter(s, new Filter.FilterListener() {
                         @Override
                         public void onFilterComplete(int count) {
                              if (count == 0){
                                   Toast.makeText(SearchListActivity.this, "Food store not found :(", Toast.LENGTH_SHORT).show();
                              }
                         }
                    });
                    return false;
               }
          });
          return super.onCreateOptionsMenu(menu);
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
          private SearchListActivity foodstore_searchlist;

          public ParseTask(SearchListActivity foodstore_searchlist) {
               this.foodstore_searchlist = foodstore_searchlist;
          }


          @Override
          protected String doInBackground(Void... params) {
               try {
                    String site_url_json = "https://rocky-retreat-95836.herokuapp.com/food_store/sarapp_rating";
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
                    Log.d("json_result_main", resultJson);
               } catch (Exception e) {
                    e.printStackTrace();
               }
               return resultJson;
          }

          protected void onPostExecute(String strJson) {
               super.onPostExecute(strJson);
               progressBar.setVisibility(View.GONE);
               foodstore_searchlist.updateAdapter(strJson);
               Log.d("FINISH", "FINISH");

          }
     }

}
