class AddColumnsToFoodStore1 < ActiveRecord::Migration[5.1]
  def change
    add_column :food_stores, :ambience_average, :float
    add_column :food_stores, :service_average, :float
    add_column :food_stores, :pricing_average, :float
    add_column :food_stores, :foodquality_average, :float
  end
end
