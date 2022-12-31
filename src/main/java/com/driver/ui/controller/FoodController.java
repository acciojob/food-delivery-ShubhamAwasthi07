package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.FoodService;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodServiceImpl foodServiceImpl;


	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto = foodServiceImpl.getFoodById(id);
		if(foodDto==null){
			throw new Exception("Food doesn't exist");
		}
		FoodDetailsResponse foodDetailsResponse = FoodDetailsResponse.builder()
				.foodId(foodDto.getFoodId())
				.foodName(foodDto.getFoodName())
				.foodPrice(foodDto.getFoodPrice())
				.foodCategory(foodDto.getFoodCategory())
				.build();
		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDto foodDto = FoodDto.builder()
				.foodName(foodDetails.getFoodName())
				.foodPrice(foodDetails.getFoodPrice())
				.foodCategory(foodDetails.getFoodCategory())
				.build();
		foodServiceImpl.createFood(foodDto);

		return FoodDetailsResponse.builder()
				.foodName(foodDetails.getFoodName())
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice())
				.build();
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto = FoodDto.builder()
				.foodId(id)
				.foodName(foodDetails.getFoodName())
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice())
				.build();
		try{
			foodServiceImpl.updateFoodDetails(id , foodDto);
		}
		catch(Exception e){
			throw new Exception("Food doesn't exist");
		}
		return FoodDetailsResponse.builder()
				.foodId(id)
				.foodName(foodDetails.getFoodName())
				.foodPrice(foodDetails.getFoodPrice())
				.foodCategory(foodDetails.getFoodCategory())
				.build();
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		try{
			foodServiceImpl.deleteFoodItem(id);
		}
		catch(Exception e){
			throw new Exception("No user exist with that id");
		}

		return new OperationStatusModel("SUCCESS" , "Delete Food");
	}

	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {

		List<FoodDto> list = foodServiceImpl.getFoods();
		List<FoodDetailsResponse> ans = new ArrayList<>();

		for(int i=0 ; i<list.size() ; i++){
			FoodDto foodDto = list.get(i);
			FoodDetailsResponse foodDetailsResponse = FoodDetailsResponse.builder()
					.foodId(foodDto.getFoodId())
					.foodName(foodDto.getFoodName())
					.foodCategory(foodDto.getFoodCategory())
					.foodPrice(foodDto.getFoodPrice())
					.build();
			ans.add(foodDetailsResponse);
		}

		return ans;
	}
}