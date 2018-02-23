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

     # Method Name: new
     #      Creation Date: 2/17/2018
     #      Purpose: Must exist for a /user/new route to also exist
     #      Calling Arguments: None
     #      Required Files: user.rb, /model/user/new.html.erb , routes.rb, schema.rb
     #      Database Tables: User db table
     #      Return Value: None
     def new
          @fid = params[:foodstoreid]
     end




     # Method Name: new
     #      Creation Date: 2/17/2018
     #      Purpose: Creates a new entry for the User table
     #      Calling Arguments: None
     #      Required Files: user.rb, /model/user/new.html.erb , routes.rb, schema.rb
     #      Database Tables: User db table
     #      Return Value: None

     def create

       user = User.new
       user.food_store = FoodStore.find(params[:user][:foodstoreid])
       user.foodquality = params[:user][:foodquality]
       user.pricing = params[:user][:pricing]
       user.service = params[:user][:service]
       user.ambience = params[:user][:ambience]
       user.comment = params[:user][:comment]
       user.average = getAverage(user.foodquality,user.pricing,user.service,user.ambience)
       if user.save
         redirect_to '/food_store'
       else
         flash[:errors] = user.errors.full_messages
         redirect_to '/user/new'
       end

     end



     # Method Name: new
     #      Creation Date: 2/17/2018
     #      Purpose: Computes average of 4 integers
     #      Calling Arguments: arg1 , arg2 , arg3 , arg4
     #      Required Files: None
     #      Database Tables: User None
     #      Return Value: float average of the 4 integer arguments
     def getAverage(arg1 , arg2 , arg3, arg4)
          return (arg1+arg2+arg3+arg4)/4.0
     end



     # Method Name: new
     #      Creation Date: 2/17/2018
     #      Purpose: Passes params to the index homepage
     #      Calling Arguments: None
     #      Required Files: user.rb, food_store.rb , /view/user/index.html.erb , routes.rb, schema.rb
     #      Database Tables: User db table , FoodStore db table
     #      Return Value: None

     def index
          # @foodstores = FoodStore.all
          @foodstores = FoodStore.find(params[:foodstoreid])
          # @foodstores = FoodStore.find(1)
          @ratings = User.where(food_store:@foodstores)
     end

end
