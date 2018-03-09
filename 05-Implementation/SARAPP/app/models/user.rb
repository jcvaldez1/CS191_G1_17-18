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
#      Purpose of File: Define data properties for the User model


 # Variable Descriptions:
 #     MIN -> minimum value for foodquality, pricing, service, and ambience
 #     MAX -> minimum value for foodquality, pricing, service, and ambience
 #     comment -> validations for comment attribute



 # Class Name: User
 #      Creation Date: 3/07/2018
 #      Purpose: Define the entry properties for the User model
 #      Calling Arguments: None
 #      Required Files: user_controller.rb, routes.rb, schema.rb
 #      Database Tables: User db table
 #      Return Value: None
class User < ApplicationRecord
	MIN = 1
    MAX = 5
    belongs_to :food_store
    validates :foodquality, :pricing, :service, :ambience, numericality: { greater_than_or_equal_to: MIN, less_than_or_equal_to: MAX }
    validates :comment, presence: true
end
