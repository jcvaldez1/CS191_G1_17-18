class CreateUsers < ActiveRecord::Migration[5.1]
  def change
    create_table :users do |t|
      t.integer :foodquality
      t.integer :pricing
      t.integer :service
      t.integer :ambience
      t.text :comment
      t.float :average

      t.timestamps
    end
  end
end
