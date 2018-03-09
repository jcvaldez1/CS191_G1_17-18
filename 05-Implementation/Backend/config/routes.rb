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
#      Purpose of File: Define the routes for the application



Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  root 'food_store#index'
  get '/food_store' => 'food_store#index'
  resources :user
end