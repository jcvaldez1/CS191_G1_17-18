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
     Purpose of File: Creates a FoodStore class that sets and gets all attributes of a food store
*/

/* Variable Descriptions:
     _id - of type int; holder of the food store's id
     _foodStoreName - of type String; holder of the food store's name
     _foodStoreLocation - of type String; holder of the food store's location
     _cuisineType - of type String; holder of the food store's cuisine type
     _rating - of type float; holder of the food store's rating
     _image - of type Bitmap; holder of the food store's image
*/

package com.example.gelic.Sarapp;

import android.graphics.Bitmap;

public class FoodStores {
     private int _id;
     private String _foodStoreName;
     private String _foodStoreLocation;
     private String _cuisineType;
     private float _rating;
     private Bitmap _image;



     public FoodStores(int id, String name, String loc, String cuisine, float rating, Bitmap img) {
          this._id = id;
          this._foodStoreName = name;
          this._foodStoreLocation = loc;
          this._cuisineType = cuisine;
          this._rating = rating;
          this._image = img;
     }

     public int get_id() {
          return _id;
     }

     public void set_id(int _id) {
          this._id = _id;
     }

     public String get_foodStoreName() {
          return _foodStoreName;
     }

     public void set_foodStoreName(String _foodStoreName) {
          this._foodStoreName = _foodStoreName;
     }

     public String get_foodStoreLocation() {
          return _foodStoreLocation;
     }

     public void set_foodStoreLocation(String _foodStoreLocation) {
          this._foodStoreLocation = _foodStoreLocation;
     }

     public String get_cuisineType() {
          return _cuisineType;
     }

     public void set_cuisineType(String _cuisineType) {
          this._cuisineType = _cuisineType;
     }

     public float get_rating() {
          return _rating;
     }

     public void set_rating(float _rating) {
          this._rating = _rating;
     }

     public Bitmap get_image() {
          return _image;
     }

     public void set_image(Bitmap _image) {
          this._image = _image;
     }


}
