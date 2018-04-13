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
     Purpose of File: Creates the first screen of the app
*/

/* Variable Descriptions:
     intent - of type Intent; holder for the ListActivity class
*/
package com.example.gelic.Sarapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
     String resultJson = "";
     /*
     Method Name: onCreate
     Creation Date: 2/4/2018
     Purpose: Sets the activity_main layout
     Calling Arguments: Bundle savedInstanceState - holds the data of the saved instance of a state
     Required Files: activity_main.xml
     Database Tables: None
     Return Value: None
      */
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          new ParseTask().execute();
     }

     public class ParseTask extends AsyncTask<Void, Void, String> {

          HttpURLConnection urlConnection = null;
          BufferedReader reader = null;


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

          }
     }

     /*
     Method Name: launchFoodStoreList
     Creation Date: 2/4/2018
     Purpose: Links the clickable image to its associated activity
     Calling Arguments: View view - contains the elements of the screen
     Required Files: ListActivity.class
     Database Tables: None
     Return Value: None
      */

     public void launchFoodStoreList(View view) {

          if (resultJson == "") {
               Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
          } else {
               Intent intent = new Intent(this, ListActivity.class);
               intent.putExtra("JSON_DATA", resultJson);
               // Log.d("resultJson afIntent",resultJson);
               startActivity(intent);
          }

     }

     public void launchSearchList(View view) {
          if (resultJson == "") {
               Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
          } else {
               Intent intent = new Intent(this, SearchListActivity.class);
               intent.putExtra("JSON_DATA", resultJson);
               startActivity(intent);
          }
     }

}
