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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
          Intent intent = new Intent(this, ListActivity.class);
          startActivity(intent);
     }


}
