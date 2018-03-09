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
