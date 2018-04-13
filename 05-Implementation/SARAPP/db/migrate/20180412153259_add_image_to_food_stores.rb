class AddImageToFoodStores < ActiveRecord::Migration[5.1]
  def change
    add_column :food_stores, :image, :string
  end
end
