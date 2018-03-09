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
#      Purpose of File: Define methods for the User controller


 # Variable Descriptions:
 #     user = User objects that contains the attributes: fooquality, pricing, service, ambience, comment, and average
 #     @foodstores = FoodStore object/s

class UserController < ApplicationController

     # Method Name: create
     #      Creation Date: 3/08/2018
     #      Purpose: Creates API endpoint for new entry on the User table
     #      Calling Arguments: None
     #      Required Files: user.rb, /model/user/new.html.erb , routes.rb, schema.rb
     #      Database Tables: User db table
     #      Return Value: None

    def create
       user = User.new(user_params)
       user.average = getAverage(user.foodquality,user.pricing,user.service,user.ambience)

       if user.save
        updateFoodStoreFields(user_params[:food_store_id] , user.average)
        render json: {status: 'SUCCESS' , message:'Saved User' , data:user},status: :ok

       else
        render json: {status: 'ERROR', message:'User not Saved', data:user.errors.full_messages},status: :unprocessable_entity
       end
    end



    # Method Name: updateFoodStoreFields
    #      Creation Date: 03/08/2018
    #      Purpose: updates corresponding food_store fields upon rating added
    #      Calling Arguments: prams, average
    #      Required Files: None
    #      Database Tables: User None
    #      Return Value: None
    def updateFoodStoreFields(prams , average)
         food = FoodStore.find(prams)
         food.update(curr_sum:food.curr_sum + average , num_of_rating:food.num_of_rating + 1)
         food.update(sarapp_rating:food.curr_sum/food.num_of_rating)
    end

     # Method Name: getAverage
     #      Creation Date: 03/08/2018
     #      Purpose: Computes average of 4 integers
     #      Calling Arguments: arg1 , arg2 , arg3 , arg4
     #      Required Files: None
     #      Database Tables: User None
     #      Return Value: float average of the 4 integer arguments
     def getAverage(arg1 , arg2 , arg3, arg4)
          return (arg1+arg2+arg3+arg4)/4.0
     end



     # Method Name: show
     #      Creation Date: 3/08/2018
     #      Purpose: API endpoint for GET-ting User ratings
     #      Calling Arguments: None
     #      Required Files: user.rb, food_store.rb , /view/user/index.html.erb , routes.rb, schema.rb
     #      Database Tables: User db table , FoodStore db table
     #      Return Value: None
    def show
          food = FoodStore.find(params[:id])
          ratings = User.where(food_store:food)
          render json: {status: 'SUCCESS' , message:'Saved User' , data:ratings},status: :ok
    end


    private

 	 # Method Name: user_params
     #      Creation Date: 3/08/2018
     #      Purpose: Just declaring permitted parameters to be passed on this API endpoint
     #      Calling Arguments: None
     #      Required Files: user.rb, food_store.rb , /view/user/index.html.erb , routes.rb, schema.rb
     #      Database Tables: User db table , FoodStore db table
     #      Return Value: None

    def user_params
		params.permit(:food_store_id, :foodquality, :pricing, :service, :ambience, :comment)
	end

end
