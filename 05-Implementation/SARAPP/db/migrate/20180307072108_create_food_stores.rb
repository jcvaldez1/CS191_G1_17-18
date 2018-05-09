# “This is a course requirement for CS 192 Software Engineering II
# under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the
# Department of Computer Science, College of Engineering,
# University of the Philippines, Diliman for the AY 2015-2016”.

# Julian Troy C. Valdez

#  Code History:
# Initial Code Authored by: Julian Troy C. Valdez
#
#  File Creation Date: (Sprint 2) 2/17/2018 to 2/18/2018
#      Development Group: Group 1
#      Client Group: CS 192 Class
#      Purpose of File: CREATE FOOD_STORE TABLE


class CreateFoodStores < ActiveRecord::Migration[5.1]
  def change
    create_table :food_stores do |t|
      t.string :name
      t.string :location
      t.string :cuisineType
      t.float :curr_sum
      t.integer :num_of_rating
      t.float :sarapp_rating

      t.timestamps
    end
  end
end
