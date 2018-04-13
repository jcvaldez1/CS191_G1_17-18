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
     Purpose of File: Creates a custom adapter from a layout for a list item
*/

/* Variable Descriptions:
     foodStores - of type ArrayList<String>; holder of name of food stores list
     foodStoreCuisineTypes - of type ArrayList<String>; holder of cuisine type of food stores list
     foodStoreLocations - of type ArrayList<String>; holder of location of food stores list
     foodStoreRatings - of type ArrayList<Float>; holder of rating of food stores list
     foodStoreImages - of type ArrayList<String>; holder of image of food stores list
     listInflater - of type Inflater; holder of the read XML/layout file
     customView - of type View; holder of the XML/layout's drawings and events
     foodStoreNameCustom - of type TextView; holder of the view of the food store's name
     foodStoreCuisCustom - of type TextView; holder of the view of the food store's cuisine type
     foodStoreLocCustom - of type TextView; holder of the view of the food store's location
     foodStoreRatCustom - of type TextView; holder of the view of the food store's rating
     imageView2 - of type ImageView; holder of the view of the food store's image
*/

package com.example.gelic.Sarapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class CustomRowAdapter extends ArrayAdapter<String> {

     ArrayList<String> foodStores;
     ArrayList<String> foodStoreCuisineTypes;
     ArrayList<String> foodStoreLocations;
     ArrayList<String> foodStoreRatings;
   ArrayList<String> foodStoreImages;


     CustomRowAdapter(Context context, ArrayList<String> foodStores, ArrayList<String> foodStoreCuisineTypes,
                      ArrayList<String> foodStoreLocations, ArrayList<String> foodStoreRatings,
                         ArrayList<String> foodStoreImages) {
          super(context, R.layout.custom_list_row, foodStores);

          this.foodStores = foodStores;
          this.foodStoreCuisineTypes = foodStoreCuisineTypes;
          this.foodStoreLocations = foodStoreLocations;
          this.foodStoreRatings = foodStoreRatings;
         this.foodStoreImages = foodStoreImages;

     }

     /*
     Method Name: getView
     Creation Date: 2/8/2018
     Purpose: Gets the drawings and events of a layout
     Calling Arguments: int position - holds the position of the list item
                         View convertView - holds the view to be converted
                         ViewGroup parent - holds the view of the parent view
     Required Files: custom_list_row.xml
     Database Tables: FoodStore
     Return Value: (View) customView
      */
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

          LayoutInflater listInflater = LayoutInflater.from(getContext());
          View customView = listInflater.inflate(R.layout.custom_list_row, parent, false);


          TextView foodStoreNameCustom = customView.findViewById(R.id.foodStoreNameCustom);
          TextView foodStoreCuisCustom = customView.findViewById(R.id.foodStoreCuisCustom);
          TextView foodStoreLocCustom = customView.findViewById(R.id.foodStoreLocCustom);
          TextView foodStoreRatCustom = customView.findViewById(R.id.foodStoreRatCustom);

         // ImageView imageView2 = customView.findViewById(R.id.imageView2);


          foodStoreNameCustom.setText(foodStores.get(position));
          foodStoreCuisCustom.setText(foodStoreCuisineTypes.get(position));
          foodStoreLocCustom.setText(foodStoreLocations.get(position));
          foodStoreRatCustom.setText(String.valueOf(foodStoreRatings.get(position)));
          //Picasso.with(context).load(URL).fit().centerCrop().into(imageView);
          new DownloadImageTask((ImageView) customView.findViewById(R.id.imageView2))
                    .execute(foodStoreImages.get(position));
          //imageView2.setImageBitmap(bmp);

          return customView;
     }


}
