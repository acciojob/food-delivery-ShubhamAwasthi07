package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .cost(order.getCost())
                .items(order.getItems())
                .userId(order.getUserId())
                .status(order.isStatus())
                .build();

        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if(orderEntity==null){
            throw new Exception("Order doesn't exist");
        }
        OrderDto orderDto = OrderDto.builder()
                .id(orderEntity.getId())
                .orderId(orderEntity.getOrderId())
                .cost(orderEntity.getCost())
                .items(orderEntity.getItems())
                .userId(orderEntity.getUserId())
                .status(orderEntity.isStatus())
                .build();

        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if(orderEntity==null){
            throw new Exception("Order doesn't exist");
        }
        OrderEntity orderEntity1 = OrderEntity.builder()
                .id(order.getId())
                .orderId(orderId)
                .cost(order.getCost())
                .items(order.getItems())
                .userId(order.getUserId())
                .status(order.isStatus())
                .build();
        orderRepository.save(orderEntity1);
        return null;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if(orderEntity==null){
            throw new Exception("No Order with given order Id exists");
        }
        orderRepository.deleteById(orderEntity.getId());
    }

    @Override
    public List<OrderDto> getOrders() {

        List<OrderEntity> list = (List) orderRepository.findAll();
        List<OrderDto> ans = new ArrayList<>();
        for(int i=0 ; i<list.size() ; i++){
            OrderEntity orderEntity = list.get(i);
            OrderDto orderDto = OrderDto.builder()
                    .id(orderEntity.getId())
                    .orderId(orderEntity.getOrderId())
                    .items(orderEntity.getItems())
                    .userId(orderEntity.getUserId())
                    .status(orderEntity.isStatus())
                    .cost(orderEntity.getCost())
                    .build();

            ans.add(orderDto);
        }

        return ans;
    }
}