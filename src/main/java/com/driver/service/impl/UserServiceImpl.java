package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    UserEntity userEntity;

    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userEntity= UserEntity.builder().id(user.getId())
                .userId(user.getUserId()).firstName(user.getFirstName())
                .lastName(user.getLastName()).email(user.getEmail()).build();

        userRepository.save(userEntity);
        return user;
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity=userRepository.findByEmail(email);
        UserDto userDto = UserDto.builder().id(userEntity.getId()).userId(userEntity.getUserId())
                .email(userEntity.getEmail()).firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).build();
        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        UserDto userDto = UserDto.builder().id(userEntity.getId()).userId(userEntity.getUserId())
                .email(userEntity.getEmail()).firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).build();
        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        userEntity.setId(user.getId());
        userEntity.setUserId(user.getUserId());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserDto userDto= UserDto.builder().email(userEntity.getEmail()).userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName()).lastName(userEntity.getLastName()).id(userEntity.getId())
                .build();
        return userDto;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        userRepository.delete(userEntity);

    }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> list=new ArrayList<>();

        Iterable<UserEntity> list2=userRepository.findAll();
        for(UserEntity user:list2){
            UserDto userDto=UserDto.builder().id(user.getId()).userId(user.getUserId())
                    .firstName(user.getFirstName()).lastName(user.getLastName())
                    .email(user.getEmail()).build();
            list.add(userDto);
        }
        return list;
    }
}