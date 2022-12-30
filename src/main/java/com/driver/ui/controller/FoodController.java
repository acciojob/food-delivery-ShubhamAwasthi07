package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
	FoodService foodService;
	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDetailsResponse returnValue = new FoodDetailsResponse();
		ModelMapper modelMapper = new ModelMapper();

		FoodDto foodDto = foodService.getFoodById(id);
		returnValue = modelMapper.map(foodDto, FoodDetailsResponse.class);

		return returnValue;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDetailsResponse returnValue = new FoodDetailsResponse();
		ModelMapper modelMapper = new ModelMapper();

		FoodDto foodDto = modelMapper.map(foodDetails, FoodDto.class);
		FoodDto createFood = foodService.createFood(foodDto);

		returnValue = modelMapper.map(createFood, FoodDetailsResponse.class);
;
		return returnValue;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDetailsResponse returnValue = new FoodDetailsResponse();
		ModelMapper modelMapper = new ModelMapper();

		FoodDto foodDto = new FoodDto();
		foodDto = modelMapper.map(foodDetails, FoodDto.class);

		FoodDto updatedUser = foodService.updateFoodDetails(id, foodDto);
		returnValue = modelMapper.map(updatedUser, FoodDetailsResponse.class);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		foodService.deleteFoodItem(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

		return returnValue;
	}

	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDetailsResponse> returnValue = new ArrayList<>();

		List<FoodDto> foods = foodService.getFoods();

		for(FoodDto foodDto: foods) {
			FoodDetailsResponse response = new FoodDetailsResponse();
			BeanUtils.copyProperties(foodDto, response);
			returnValue.add(response);
		}

		return returnValue;
	}
}
