class AddColumnsToFoodStore < ActiveRecord::Migration[5.1]
  def change
    add_column :food_stores, :curr_sum_ambience, :integer
    add_column :food_stores, :curr_sum_food, :integer
    add_column :food_stores, :curr_sum_price, :integer
    add_column :food_stores, :curr_sum_service, :integer
  end
end
