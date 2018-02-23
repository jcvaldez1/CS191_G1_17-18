class AddFoodStoreToUser < ActiveRecord::Migration[5.1]
  def change
    add_reference :users, :food_store, foreign_key: true
  end
end
