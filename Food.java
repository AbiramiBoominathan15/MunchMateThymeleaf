package com.chainsys.munchmate.model;

import java.util.Arrays;










public class Food {
	public int foodId;
	public int hotelId;
	public String hotelName;
	public String foodName;
    public String base64Image;

	
	public int foodPrice;
	public String foodCategories;
	public byte[] foodImage;
	public int foodQuantity;
	public String foodSession;
	public String availability;

	public int getFoodId() {
		return foodId;
	}
	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public int getFoodPrice() {
		return foodPrice;
	}
	public void setFoodPrice(int foodPrice) {
		this.foodPrice = foodPrice;
	}
	public String getFoodCategories() {
		return foodCategories;
	}
	public void setFoodCategories(String foodCategories) {
		this.foodCategories = foodCategories;
	}
	public byte[] getFoodImage() {
		return foodImage;
	}
	public void setFoodImage(byte[] foodImage) {
		this.foodImage = foodImage;
	}
	public int getFoodQuantity() {
		return foodQuantity;
	}
	public void setFoodQuantity(int foodQuantity) {
		this.foodQuantity = foodQuantity;
	}
	public String getFoodSession() {
		return foodSession;
	}
	public void setFoodSession(String foodSession) {
		this.foodSession = foodSession;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public Food(int foodId, int hotelId, String hotelName, String foodName, int foodPrice, String foodCategories,
			byte[] foodImage, int foodQuantity, String foodSession, String availability) {
		super();
		this.foodId = foodId;
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.foodCategories = foodCategories;
		this.foodImage = foodImage;
		this.foodQuantity = foodQuantity;
		this.foodSession = foodSession;
		this.availability = availability;
	}
	public Food() {
		// TODO Auto-generated constructor stub
	}
    public String getBase64Image() {
        return base64Image;
    }

	@Override
	public String toString() {
		return "Food [foodId=" + foodId + ", hotelId=" + hotelId + ", hotelName=" + hotelName + ", foodName=" + foodName
				+ ", foodPrice=" + foodPrice + ", foodCategories=" + foodCategories + ", foodImage="
				+ Arrays.toString(foodImage) + ", foodQuantity=" + foodQuantity + ", foodSession=" + foodSession
				+ ", availability=" + availability + ", getFoodId()=" + getFoodId() + ", getHotelId()=" + getHotelId()
				+ ", getHotelName()=" + getHotelName() + ", getFoodName()=" + getFoodName() + ", getFoodPrice()="
				+ getFoodPrice() + ", getFoodCategories()=" + getFoodCategories() + ", getFoodImage()="
				+ Arrays.toString(getFoodImage()) + ", getFoodQuantity()=" + getFoodQuantity() + ", getFoodSession()="
				+ getFoodSession() + ", getAvailability()=" + getAvailability() + "]";
	}
	public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
		
	}


}
