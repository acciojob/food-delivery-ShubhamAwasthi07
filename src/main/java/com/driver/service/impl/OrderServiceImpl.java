package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto order) {



        ModelMapper modelMapper = new ModelMapper();
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);



        String orderId = String.valueOf(new SecureRandom());
        orderEntity.setOrderId(orderId);
        orderEntity.setStatus(false);


        OrderEntity storedOrder = orderRepository.save(orderEntity);
        OrderDto returnValue = modelMapper.map(storedOrder, OrderDto.class);


        return returnValue;
    }

    /**
     * Get order by order id method
     */
    @Override
    public OrderDto getOrderById(String orderId) throws Exception {


        OrderDto returnValue = new OrderDto();
        ModelMapper modelMapper = new ModelMapper();

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

        if (orderEntity == null) {


            throw new Exception(orderId);
        }

        returnValue = modelMapper.map(orderEntity, OrderDto.class);


        return returnValue;
    }

    /**
     * Update food method
     */
    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {


        OrderDto returnValue = new OrderDto();
        ModelMapper modelMapper = new ModelMapper();

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null) {

            throw new Exception(orderId);
        }
        orderEntity.setCost(order.getCost());
        orderEntity.setItems(order.getItems());
        orderEntity.setStatus(true);

        OrderEntity updatedOrder = orderRepository.save(orderEntity);
        returnValue = modelMapper.map(updatedOrder, OrderDto.class);

        return returnValue;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception{

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null) {
            throw new Exception(orderId);
        }

        orderRepository.delete(orderEntity);

    }
    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto> returnValue = new ArrayList<>();

        Iterable<OrderEntity> iteratableObjects = orderRepository.findAll();

        for (OrderEntity orderEntity : iteratableObjects) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderEntity, orderDto);
            returnValue.add(orderDto);
        }

        return returnValue;
    }

}