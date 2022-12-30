package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import com.driver.shared.dto.UserDto;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity=OrderEntity.builder().id(order.getId()).orderId(order.getOrderId())
                .items(order.getItems()).cost(order.getCost()).status(order.isStatus())
                .userId(order.getUserId()).build();
        orderRepository.save(orderEntity);

        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity order= orderRepository.findByOrderId(orderId);
        OrderDto orderDto=OrderDto.builder().id(order.getId()).orderId(order.getOrderId())
                .items(order.getItems()).cost(order.getCost()).status(order.isStatus())
                .userId(order.getUserId()).build();

        return orderDto ;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setId(order.getId());
        orderEntity.setCost(order.getCost());
        orderEntity.setStatus(order.isStatus());
        orderEntity.setItems(order.getItems());
        orderEntity.setUserId(order.getUserId());


        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity= orderRepository.findByOrderId(orderId);
        orderRepository.delete(orderEntity);
    }

    @Override
    public List<OrderDto> getOrders() {

        List<OrderDto> list=new ArrayList<>();

        Iterable<OrderEntity> list2=orderRepository.findAll();
        for(OrderEntity order:list2){
            OrderDto orderDto=OrderDto.builder().id(order.getId()).orderId(order.getOrderId())
                    .cost(order.getCost()).userId(order.getUserId()).items(order.getItems())
                    .status(order.isStatus()).build();
            list.add(orderDto);
        }
        return list;

    }
}