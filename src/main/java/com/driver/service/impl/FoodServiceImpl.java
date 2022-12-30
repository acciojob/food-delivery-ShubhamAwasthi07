package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity=FoodEntity.builder().id(food.getId()).foodCategory(food.getFoodCategory())
                .foodId(food.getFoodId()).foodName(food.getFoodName()).foodPrice(food.getFoodPrice())
                .build();
        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity food=foodRepository.findByFoodId(foodId);
        FoodDto foodDto=FoodDto.builder().id(food.getId()).foodCategory(food.getFoodCategory())
                .foodId(food.getFoodId()).foodName(food.getFoodName()).foodPrice(food.getFoodPrice())
                .build();

        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        FoodEntity foodEntity=foodRepository.findByFoodId(foodId);
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());
        foodEntity.setFoodId(foodDetails.getFoodId());
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setId(foodDetails.getId());

        FoodDto foodDto= FoodDto.builder()
                .foodCategory(foodEntity.getFoodCategory()).foodId(foodEntity.getFoodId())
                .foodName(foodEntity.getFoodName()).foodPrice(foodEntity.getFoodPrice())
                .id(foodEntity.getId())
                .build();

        return foodDto;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity=foodRepository.findByFoodId(id);
        foodRepository.delete(foodEntity);
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodDto> list=new ArrayList<>();

        Iterable<FoodEntity> list2=foodRepository.findAll();
        for(FoodEntity food:list2){
            FoodDto foodDto=FoodDto.builder().id(food.getId()).foodId(food.getFoodId())
                    .foodName(food.getFoodName()).foodCategory(food.getFoodCategory())
                    .foodPrice(food.getFoodPrice()).build();
            list.add(foodDto);
        }
        return list;
    }
}