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

ActiveRecord::Schema.define(version: 20180513113556) do

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
    t.integer "curr_sum_ambience"
    t.integer "curr_sum_food"
    t.integer "curr_sum_price"
    t.integer "curr_sum_service"
    t.float "ambience_average"
    t.float "service_average"
    t.float "pricing_average"
    t.float "foodquality_average"
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
