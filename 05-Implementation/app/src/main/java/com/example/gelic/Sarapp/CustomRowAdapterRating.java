/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Angelika Juliah S. Galang
*/

/* Code History:
Initial Code Authored by: Angelika Juliah S. Galang
*/

/* File Creation Date: (Sprint 2) 2/21/2018
     Development Group: Group 1
     Client Group: CS 192 Class
     Purpose of File:  Creates a custom adapter from a layout for a list item in ratings
*/

/* Variable Descriptions:

*/

package com.example.gelic.Sarapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;

class CustomRowAdapterRating extends ArrayAdapter<String> {

     ArrayList<String> ratingDate;
     ArrayList<Integer> ratingQuality;
     ArrayList<Integer> ratingPricing;
     ArrayList<Integer> ratingService;
     ArrayList<Integer> ratingAmbience;
     ArrayList<String> ratingComment;
     ArrayList<Double> ratingAverage;


     CustomRowAdapterRating(Context context, ArrayList<String> ratingDate, ArrayList<Integer> ratingQuality, ArrayList<Integer> ratingPricing,
                      ArrayList<Integer> ratingService, ArrayList<Integer> ratingAmbience, ArrayList<String> ratingComment,
                      ArrayList<Double> ratingAverage) {
          super(context, R.layout.custom_list_row_rating, ratingDate);

          this.ratingDate = ratingDate;
          this.ratingQuality = ratingQuality;
          this.ratingPricing = ratingPricing;
          this.ratingService = ratingService;
          this.ratingAmbience = ratingAmbience;
          this.ratingComment = ratingComment;
          this.ratingAverage = ratingAverage;

     }
     /*
    Method Name: getView
    Creation Date: 2/21/2018
    Purpose: Gets the drawings and events of a layout
    Calling Arguments: int position - holds the position of the list item
                        View convertView - holds the view to be converted
                        ViewGroup parent - holds the view of the parent view
    Required Files: custom_list_row_rating.xml
    Database Tables: FoodStore
    Return Value: (View) customView
     */
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

          LayoutInflater listInflater = LayoutInflater.from(getContext());
          View customView = listInflater.inflate(R.layout.custom_list_row_rating, parent, false);


          RatingBar food_quality = customView.findViewById(R.id.food_quality);
          RatingBar pricing = customView.findViewById(R.id.pricing);
          RatingBar service = customView.findViewById(R.id.service);
          RatingBar ambience = customView.findViewById(R.id.ambience);
          TextView date = customView.findViewById(R.id.date);
          TextView comment = customView.findViewById(R.id.comment);
          TextView average_rating = customView.findViewById(R.id.average_rating);

          food_quality.setRating(ratingQuality.get(position));
          pricing.setRating(ratingPricing.get(position));
          service.setRating(ratingService.get(position));
          ambience.setRating(ratingAmbience.get(position));
          date.setText(ratingDate.get(position));
          comment.setText(ratingComment.get(position));
          average_rating.setText(String.valueOf(ratingAverage.get(position)));

          return customView;
     }

}
