/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Angelika Juliah S. Galang
*/

/* Code History:
Initial Code Authored by: Angelika Juliah S. Galang
Update 2/21/2018: Angelika Juliah S. Galang
*/

/* File Creation Date: (Sprint 2) 2/4/2018 to 2/8/2018
     Development Group: Group 1
     Client Group: CS 192 Class
     Purpose of File: Handles all operation to be done in database
*/

/* Variable Descriptions:
     DATABASE_VERSION - of type int; holder of the database version
     DATABASE_PATH - of type String; holder of the path where the database file is
     DATABASE_NAME - of type String; holder of the database name
     db - of type SQLiteDatabase; holder of the actual database
     sarappContext - of type Context; holder of the current context/screen
     dbExist - of type boolean; holder of the value whether the database exists or not
     myPath - of type String; holder of the path to the database
     inputStream - of type InputStream; holder of the input file
     outFileName - of type String; holder of the output stream file name
     outputStream - of type OutputStream; holder of the output file
     buff - of type byte[]; holder of the buff value read from the input file
     length - of type int; holder of the length of the buf
     dbPath - of type String; holder of the path to the database
     foodStore - of type FoodStores; holder of the food store from the database
     foodStoreList - of type ArrayList<FoodStores>; holder of the foodStore list
     inStream - of type ByteArrayInputStream; holder of the food store's image stream
     blob - of type byte[]; holder of the bye equivalence of the food store's image
     bitmap - of type Bitmap; holder of the food store's image

*/

package com.example.gelic.Sarapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper {

     private static final int DATABASE_VERSION = 1;
     private static final String DATABASE_PATH = "/data/data/com.example.gelic.Sarapp/databases/";
     private static final String DATABASE_NAME = "sarapp.db";

     private SQLiteDatabase db;
     private final Context sarappContext;

     public DBHandler(Context context) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
          this.sarappContext = context;
     }

     @Override
     public void onCreate(SQLiteDatabase db) {

     }

     @Override
     public void onUpgrade(SQLiteDatabase db, int i, int i1) {

     }

     /*
     Method Name: createDB
     Creation Date: 2/5/2018
     Purpose: Creates the database if it exists; throws an error otherwise
     Calling Arguments: None
     Required Files: sarapp.db
     Database Tables: None
     Return Value: None
      */
     public void createDB() throws IOException {
          boolean dbExist = checkDB();

          if (dbExist) {

          } else {
               this.getReadableDatabase();

               try {
                    copyDB();
               } catch (IOException e) {
                    throw new Error("Error copying database");
               }
          }
     }

     /*
     Method Name: checkDB
     Creation Date: 2/5/2018
     Purpose: Checks if the database specified exists
     Calling Arguments: None
     Required Files: sarapp.db
     Database Tables: All
     Return Value: (boolean) db != null
      */
     private boolean checkDB() {
          SQLiteDatabase db = null;

          try {
               String myPath = DATABASE_PATH + DATABASE_NAME;
               db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          } catch (SQLiteException e) {

          }
          if (db != null) {
               db.close();
          }
          return db != null;
     }

     /*
     Method Name: copyDB
     Creation Date: 2/5/2018
     Purpose: Copies the data from the database
     Calling Arguments: None
     Required Files: sarapp.db
     Database Tables: All
     Return Value: None
      */
     private void copyDB() throws IOException {

          InputStream inputStream = sarappContext.getAssets().open(DATABASE_NAME);
          String outFileName = DATABASE_PATH + DATABASE_NAME;
          OutputStream outputStream = new FileOutputStream(outFileName);
          byte[] buff = new byte[1024];
          int length;
          while ((length = inputStream.read(buff)) > 0) {
               outputStream.write(buff, 0, length);
          }
          inputStream.close();
          outputStream.flush();
          outputStream.close();
     }

     /*
     Method Name: openDB
     Creation Date: 2/5/2018
     Purpose: Opens the database
     Calling Arguments: None
     Required Files: sarapp.db
     Database Tables: All
     Return Value: None
      */
     public void openDB() {
          String dbPath = sarappContext.getDatabasePath(DATABASE_NAME).getPath();
          if (db != null && db.isOpen()) {
               return;
          }
          db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
     }

     /*
     Method Name: closeDB
     Creation Date: 2/5/2018
     Purpose: Closes the database
     Calling Arguments: None
     Required Files: None
     Database Tables: None
     Return Value: None
      */
     public void closeDB() {
          if (db != null) {
               db.close();
          }
     }

     /*
     Method Name: getAllFoodStore
     Creation Date: 2/5/2018
     Purpose: Gets all the food store from the database
     Calling Arguments: None
     Required Files: FoodStores.class
     Database Tables: FoodStore
     Return Value: (ArrayList<FoodStores>) foodStoreList
      */
     public ArrayList<FoodStores> getAllFoodStores() {
          FoodStores foodStore;
          ArrayList<FoodStores> foodStoreList = new ArrayList<>();
          byte[] blob;
          ByteArrayInputStream inStream;
          Bitmap bitmap;

          openDB();
          Cursor row = db.rawQuery("SELECT * FROM FoodStore", null);
          row.moveToFirst();

          while (!row.isAfterLast()) {
               blob = row.getBlob(row.getColumnIndex("image"));
               inStream = new ByteArrayInputStream(blob);
               bitmap = BitmapFactory.decodeStream(inStream);

               foodStore = new FoodStores(row.getInt(0), row.getString(1),
                         row.getString(2), row.getString(3), row.getFloat(4), bitmap);
               foodStoreList.add(foodStore);
               row.moveToNext();
          }
          row.close();
          closeDB();
          return foodStoreList;
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
     public ArrayList<Float> getFoodStore(int id) {
          ArrayList<Float> foodstore = new ArrayList<>();
          openDB();
          Cursor cursor = db.query("FoodStore",null,"id=?", new String[]{String.valueOf(id)},null,null,null);
          cursor.moveToFirst();
          foodstore.add(cursor.getFloat(6));
          foodstore.add(cursor.getFloat(7));
          cursor.close();
          closeDB();
          return foodstore;
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

     public long updateFoodStore(int id, float rating, float curr_sum, float ctr) {
          ContentValues contentValues = new ContentValues();
          contentValues.put("rating", rating);
          contentValues.put("curr_sum", curr_sum);
          contentValues.put("rating_ctr", ctr);
          String[] whereArgs = {Integer.toString(id)};
          openDB();
          long returnValue = db.update("FoodStore",contentValues, "ID=?", whereArgs);
          closeDB();
          return returnValue;
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
     public long addRating(String d, float fq, float p, float s, float a, String c,float ave){
          ContentValues contentValues = new ContentValues();
          contentValues.put("date", d);
          contentValues.put("quality",fq);
          contentValues.put("pricing",p);
          contentValues.put("service",s);
          contentValues.put("ambience",a);
          contentValues.put("comment",c);
          contentValues.put("average",ave);
          openDB();
          long returnValue = db.insert("Comment", null, contentValues);
          Log.d("returnValue",String.valueOf(returnValue));
          closeDB();
          return returnValue;
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
     public long addRatingRelation(int s_id, int u_id){
          ContentValues contentValues = new ContentValues();
          contentValues.put("store_id", s_id);
          contentValues.put("user_id",u_id);
          openDB();
          long returnValue = db.insert("FoodStoreRating", null, contentValues);
          closeDB();
          return returnValue;
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
     public ArrayList<UserRatings> getAllRatings (int id){
          UserRatings userRatings;
          ArrayList<UserRatings> userRatingList = new ArrayList<>();
          String _store_id = String.valueOf(id);
          openDB();
          Cursor row = db.rawQuery("SELECT * FROM Comment INNER JOIN FoodStoreRating ON FoodStoreRating.user_id = Comment.id WHERE store_id = " + _store_id +";", null);
          row.moveToFirst();

          while (!row.isAfterLast()) {
               userRatings = new UserRatings(row.getInt(0), row.getString(1),
                         row.getFloat(2), row.getFloat(3), row.getFloat(4),
                         row.getFloat(5), row.getString(6),row.getFloat(7));
               userRatingList.add(userRatings);
               row.moveToNext();
          }
          row.close();
          closeDB();
          return userRatingList;
     }
}
