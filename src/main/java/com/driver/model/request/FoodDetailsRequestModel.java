package com.driver.model.request;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDetailsRequestModel {

	private String foodName;
	private String foodCategory;
	private float foodPrice;
	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}

	public float getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(float foodPrice) {
		this.foodPrice = foodPrice;
	}

}