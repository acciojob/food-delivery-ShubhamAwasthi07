package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;


    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity = FoodEntity.builder()
                .id(food.getId())
                .foodId(food.getFoodId())
                .foodCategory(food.getFoodCategory())
                .foodName(food.getFoodName())
                .foodPrice(food.getFoodPrice())
                .build();
        foodRepository.save(foodEntity);
        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        if(foodEntity==null){
            throw new Exception("No food is found with the given foodId");
        }
        FoodDto foodDto = FoodDto.builder()
                .id(foodEntity.getId())
                .foodId(foodEntity.getFoodId())
                .foodName(foodEntity.getFoodName())
                .foodCategory(foodEntity.getFoodCategory())
                .foodPrice(foodEntity.getFoodPrice())
                .build();

        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        if(foodRepository.findByFoodId(foodId)==null){
            throw new Exception("No food exists with given food id");
        }
        FoodEntity foodEntity = FoodEntity.builder()
                .id(foodDetails.getId())
                .foodId(foodId)
                .foodName(foodDetails.getFoodName())
                .foodCategory(foodDetails.getFoodCategory())
                .foodPrice(foodDetails.getFoodPrice())
                .build();
        foodRepository.save(foodEntity);
        return foodDetails;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(id);
        if(foodEntity==null){
            throw new Exception("Food doesn't exist");
        }
        foodRepository.deleteById(foodEntity.getId());
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodEntity> list = (List)foodRepository.findAll();
        List<FoodDto> ans = new ArrayList<>();
        for(int i=0 ; i<list.size() ; i++){
            FoodEntity foodEntity = list.get(i);
            FoodDto foodDto = FoodDto.builder()
                    .id(foodEntity.getId())
                    .foodId(foodEntity.getFoodId())
                    .foodName(foodEntity.getFoodName())
                    .foodCategory(foodEntity.getFoodCategory())
                    .foodPrice(foodEntity.getFoodPrice())
                    .build();
            ans.add(foodDto);
        }
        return ans;
    }
}