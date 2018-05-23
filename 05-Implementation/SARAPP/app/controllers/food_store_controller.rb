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
#      Purpose of File: Define methods for the FoodStore controller


 # Variable Descriptions:
 #     @foodstores = FoodStore object/s

class FoodStoreController < ApplicationController

  # Method Name: show
  #      Creation Date: 2/17/2018
  #      Purpose: Must exist for a /food_store/index route to also exist
  #      Calling Arguments: None
  #      Required Files: food_store.rb, /viewl/user/index.html.erb , routes.rb, schema.rb
  #      Database Tables: FoodStore db table
  #      Return Value:  None
     def show
      data = sortinate(params[:id])
      render json: {status: 'SUCCESS' , message:'Loaded articles' , data:data.reverse},status: :ok
     end

  # Method Name: sortinate
  #      Creation Date: 5/13/2018
  #      Purpose: Must exist for a /food_store/index route to also exist
  #      Calling Arguments: None
  #      Required Files: food_store.rb, /viewl/user/index.html.erb , routes.rb, schema.rb
  #      Database Tables: FoodStore db table
  #      Return Value:  foodstore list to be shown
     def sortinate(sort_param)
	foodstores = FoodStore.all	
	if sort_param == "sarapp_rating"
		return foodstores.sort_by(&:sarapp_rating)
	elsif sort_param == "ambience"
		return foodstores.sort_by(&:ambience_average)	
	elsif sort_param == "foodquality"
		return foodstores.sort_by(&:foodquality_average)	
	elsif sort_param == "service"
		return foodstores.sort_by(&:service_average)	
	elsif sort_param == "pricing"
		return foodstores.sort_by(&:pricing_average)	
	elsif sort_param == "ratings"
		return foodstores.sort_by(&:num_of_rating)	
        end
     end

end
