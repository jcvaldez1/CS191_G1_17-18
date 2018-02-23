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
#      Purpose of File: Define the entry properties for the FoodStore model


# Method Name: FoodStore
#      Creation Date: 2/17/2018
#      Purpose: Define the entry properties for the FoodStore model
#      Calling Arguments: None
#      Required Files: food_store_controller.rb, routes.rb, schema.rb
#      Database Tables: FoodStore db table
#      Return Value: None
class FoodStore < ApplicationRecord
  has_many :user
end
