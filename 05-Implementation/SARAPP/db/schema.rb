# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

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

# UPDATED AT : 4/13/2018:
# =>  table food_stores -> added image column which contains URL


ActiveRecord::Schema.define(version: 20180412153259) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "food_stores", force: :cascade do |t|
    t.string "name"
    t.string "location"
    t.string "cuisineType"
    t.float "curr_sum"
    t.integer "num_of_rating"
    t.float "sarapp_rating"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "image"
  end

  create_table "users", force: :cascade do |t|
    t.integer "foodquality"
    t.integer "pricing"
    t.integer "service"
    t.integer "ambience"
    t.text "comment"
    t.float "average"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.integer "food_store_id"
    t.index ["food_store_id"], name: "index_users_on_food_store_id"
  end

end
