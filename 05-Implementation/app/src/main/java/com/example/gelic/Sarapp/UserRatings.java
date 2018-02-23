/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Angelika Juliah S. Galang
*/

/* Code History:
Initial Code Authored by: Angelika Juliah S. Galang
*/

/* File Creation Date: (Sprint 2) 2/21/18
     Development Group: Group 1
     Client Group: CS 192 Class
     Purpose of File: Creates a UserRating class that sets and gets all attributes of a user rating
*/

/* Variable Descriptions:

*/


package com.example.gelic.Sarapp;

public class UserRatings {
     private int _id;
     private String _date;
     private float _quality;
     private float _pricing;
     private float _service;
     private float _ambience;
     private String _comment;
     private float _average;

     public UserRatings(int id, String d, float q, float p, float s, float a, String c, float ave) {
          this._id = id;
          this._date = d;
          this._quality = q;
          this._pricing = p;
          this._service = s;
          this._ambience = a;
          this._comment = c;
          this._average = ave;
     }

     public int get_id() {
          return _id;
     }

     public void set_id(int _id) {
          this._id = _id;
     }

     public String get_date() {
          return _date;
     }

     public void set_date(String _date) {
          this._date = _date;
     }

     public float get_quality() {
          return _quality;
     }

     public void set_quality(float _quality) {
          this._quality = _quality;
     }

     public float get_pricing() {
          return _pricing;
     }

     public void set_pricing(float _pricing) {
          this._pricing = _pricing;
     }

     public float get_service() {
          return _service;
     }

     public void set_service(float _service) {
          this._service = _service;
     }

     public float get_ambience() {
          return _ambience;
     }

     public void set_ambience(float _ambience) {
          this._ambience = _ambience;
     }

     public String get_comment() {
          return _comment;
     }

     public void set_comment(String _comment) {
          this._comment = _comment;
     }

     public float get_average() {
          return _average;
     }

     public void set_average(float _average) {
          this._average = _average;
     }
}
