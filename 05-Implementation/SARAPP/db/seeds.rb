# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)

5.times do
	FoodStore.create({
		name: Faker::Book.title,
		location: Faker::Address.city,
		cuisineType: Faker::Dessert.variety,
		curr_sum: 0,
		num_of_rating: 0,
		sarapp_rating: 0
    })
end
