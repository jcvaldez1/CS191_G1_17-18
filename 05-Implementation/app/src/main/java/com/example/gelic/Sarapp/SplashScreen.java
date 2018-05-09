/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Richelle Yap*/

/*Code History:
Initial Code Authored by: Richelle Yap*/

/* File Creation Date: (Sprint 1) 2/4/2018 to 2/8/2018
    Development Group: Group 1
    Client Group: CS 192 class
    Purpose of file: Handles the activity of the splash screen */


package com.example.gelic.Sarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

          /*
      Method Name:
      Creation Date:
      Purpose:
      Calling Arguments:
      Required Files:
      Database Tables:
      Return Value:
       */
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          setTheme(android.R.style.Theme_Translucent_NoTitleBar);
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_splash_screen);

          Thread myThread = new Thread() {
               @Override
               public void run() {
                    try {
                         sleep(3000);
                         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                         startActivity(intent);
                         finish();
                    } catch (InterruptedException e) {
                         e.printStackTrace();
                    }
               }
          };
          myThread.start();
     }

}
