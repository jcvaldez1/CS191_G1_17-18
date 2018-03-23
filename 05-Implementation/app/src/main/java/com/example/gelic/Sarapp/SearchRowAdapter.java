/*
This is a course requirement for CS 192 Software Engineering II under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the Department of Computer Science, College of Engineering, University of the Philippines, Diliman for the AY 2017-2018.
Angelika Juliah S. Galang
*/

/* Code History:
Initial Code Authored by: Angelika Juliah S. Galang
*/

/* File Creation Date: (Sprint 4) 3/20/2018 to 3/22/2018
     Development Group: Group 1
     Client Group: CS 192 Class
     Purpose of File: Creates a custom adapter from a layout for a list item in the search list
*/

/* Variable Descriptions:

*/

package com.example.gelic.Sarapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchRowAdapter extends BaseAdapter implements Filterable {

     Context c;
     ArrayList<FoodStores> foodStores;
     CustomFilter filter;
     ArrayList<FoodStores> foodStoreList;

     /*
     Method Name: SearchRowAdapter
     Creation Date: 3/20/2018
     Purpose: Sets the local context and food store list to the ones passed
     Calling Arguments:  Context ctx - holds context activity
                         ArrayList<FoodStores> foodStores - holds the list of food stores
     Required Files: None
     Database Tables: None
     Return Value: None
      */
     public SearchRowAdapter(Context ctx,ArrayList<FoodStores> foodStores) {

          this.c=ctx;
          this.foodStores=foodStores;
          this.foodStoreList=foodStores;
     }

     /*
    Method Name: getCount
    Creation Date: 3/20/2018
    Purpose: Gets the size of the food store list
    Calling Arguments: None
    Required Files: None
    Database Tables: None
    Return Value: (int) foodStores.size()
     */
     @Override
     public int getCount() {
          return foodStores.size();
     }

     /*
    Method Name: getItem
    Creation Date: 3/20/2018
    Purpose: Gets the food store specified at given position
    Calling Arguments: int pos - holds the position of a food store
    Required Files: None
    Database Tables: None
    Return Value: (FoodStores) foodStores.get(pos)
     */
     @Override
     public FoodStores getItem(int pos) {
          return foodStores.get(pos);
     }

     /*
    Method Name: getItemId
    Creation Date: 3/20/2018
    Purpose: Gets the index of a food store specified at given position
    Calling Arguments: int pos - - holds the position of a food store
    Required Files: None
    Database Tables: None
    Return Value: (long) foodStores.indexOf(getItem(pos))
     */
     @Override
     public long getItemId(int pos) {
          return foodStores.indexOf(getItem(pos));
     }

     /*
     Method Name: getView
     Creation Date: 3/20/2018
     Purpose: Gets the drawings and events of a layout
     Calling Arguments: int pos - holds the position of the list item
                View convertView - holds the view to be converted
                ViewGroup parent - holds the view of the parent view
     Required Files: custom_list_row_search.xml
     Database Tables: FoodStore
     Return Value: (View) customView
       */
     @Override
     public View getView(int pos, View customView, ViewGroup parent) {

          LayoutInflater inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          if(customView==null) {
               customView=inflater.inflate(R.layout.custom_list_row_search, null);
          }

          TextView foodStoreNameCustom = customView.findViewById(R.id.foodStoreNameCustom);
          TextView foodStoreCuisCustom = customView.findViewById(R.id.foodStoreCuisCustom);
          TextView foodStoreLocCustom = customView.findViewById(R.id.foodStoreLocCustom);
          TextView foodStoreRatCustom = customView.findViewById(R.id.foodStoreRatCustom);
          ImageView imageView2 = customView.findViewById(R.id.imageView2);


          foodStoreNameCustom.setText(foodStores.get(pos).get_foodStoreName());
          foodStoreCuisCustom.setText(foodStores.get(pos).get_cuisineType());
          foodStoreLocCustom.setText(foodStores.get(pos).get_foodStoreLocation());
          foodStoreRatCustom.setText(String.valueOf(foodStores.get(pos).get_rating()));
          imageView2.setImageBitmap(foodStores.get(pos).get_image());

          return customView;
     }

     /*
     Method Name: getFilter
     Creation Date: 3/22/2018
     Purpose: Returns the filtered list of food stores
     Calling Arguments: None
     Required Files: None
     Database Tables: None
     Return Value: (Filter) filter
     */
     @Override
     public Filter getFilter() {
          if(filter == null) {
               filter=new CustomFilter();
          }

          return filter;
     }


     class CustomFilter extends Filter{

          /*
          Method Name: performFiltering
          Creation Date: 3/22/2018
          Purpose: Filters the list of food stores given a query/sequence of characters
          Calling Arguments: CharSequence constraint - query (the one being searched)
          Required Files: None
          Database Tables: None
          Return Value: (FilterResults) results
          */
          @Override
          protected FilterResults performFiltering(CharSequence constraint) {

               FilterResults results=new FilterResults();

               if(constraint != null && constraint.length()>0) {
                    constraint=constraint.toString().toUpperCase();

                    ArrayList<FoodStores> filters=new ArrayList<>();

                    for(int i=0;i<foodStoreList.size();i++) {
                         if(foodStoreList.get(i).get_foodStoreName().toUpperCase().contains(constraint)) {
                              FoodStores p=new FoodStores(foodStoreList.get(i).get_foodStoreName(),
                                        foodStoreList.get(i).get_foodStoreLocation(),
                                        foodStoreList.get(i).get_cuisineType(),
                                        foodStoreList.get(i).get_rating(),
                                        foodStoreList.get(i).get_image());

                              filters.add(p);
                         }
                    }

                    results.count=filters.size();
                    results.values=filters;

               }
               else {
                    results.count=foodStoreList.size();
                    results.values=foodStoreList;

               }

               return results;
          }

          /*
          Method Name: publishResults
          Creation Date: 3/22/2018
          Purpose: Returns and updates filtered list of food stores basing on the query given
          Calling Arguments:  CharSequence constraint - query (the one being searched)
                              FilterResults results - the filtered results of food stores
          Required Files: None
          Database Tables: None
          Return Value: None
          */
          @Override
          protected void publishResults(CharSequence constraint, FilterResults results) {

               foodStores=(ArrayList<FoodStores>) results.values;
               notifyDataSetChanged();
          }

     }

}
