package com.driver.service;

import java.util.List;

import com.driver.shared.dto.FoodDto;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface FoodService {

	FoodDto createFood(FoodDto food);
	FoodDto getFoodById(String foodId) throws Exception;
	FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception;
	void deleteFoodItem(String id) throws Exception;
	List<FoodDto> getFoods();
}
