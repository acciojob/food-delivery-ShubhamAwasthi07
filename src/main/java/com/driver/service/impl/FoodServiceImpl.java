package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity = FoodEntity.builder().foodId(food.getFoodId()).foodCategory(food.getFoodCategory()).foodPrice(food.getFoodPrice()).foodName(food.getFoodName()).build();
        foodRepository.save(foodEntity);
        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        if(foodEntity == null){
            System.out.println("Food Entity doesn't exist");

            return null;
        }
        FoodDto foodDto= FoodDto.builder().
                id(foodEntity.getId()).
                foodId(foodEntity.getFoodId()).
                foodName(foodEntity.getFoodName()).
                foodPrice(foodEntity.getFoodPrice()).
                foodCategory(foodEntity.getFoodCategory()).
                build();

        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {

        return null;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {

        FoodEntity foodEntity = foodRepository.findByFoodId(id);

        if (foodEntity == null) {
            throw new Exception(id);
        }

        foodRepository.delete(foodEntity);
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodDto> returnValue = new ArrayList<>();
        Iterable<FoodEntity> iterableObjects = foodRepository.findAll();

        for (FoodEntity foodEntity : iterableObjects) {
            FoodDto foodDto = null;
            BeanUtils.copyProperties(foodEntity, foodDto);
            returnValue.add(foodDto);
        }

        return returnValue;
    }
}