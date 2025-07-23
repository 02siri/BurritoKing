package model;

public class ItemFactory{
	Burritos burrito;
	Fries fries;
	Sodas sodas;
	public static FoodItem createFoodItem(int itemID,String itemName)
	{	String itemNameTrim = itemName.trim().toLowerCase();
		switch(itemNameTrim.toLowerCase())
		{
		case "burritos":
			return new Burritos(itemID);
		case "fries":
			return new Fries(itemID);
		case "sodas":
			return new Sodas(itemID);
		case "burgers":
			return new Burgers(itemID);
		case "shakes":
			return new Shakes(itemID);
		case "meal (1 burrito, 1 fries, 1 soda)":
			return new Meal(itemID);
		case "family meal (1 burrito, 1 fries, 1 soda, 1 burger, 1 shake)":
			return new FamilyMeal(itemID);
		default:
			throw new IllegalArgumentException("Invalid food item name :" + itemName);
		}
	}
}