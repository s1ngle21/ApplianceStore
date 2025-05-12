package org.example.appliancestore.mapper;

import lombok.AllArgsConstructor;
import org.example.appliancestore.dto.OrderDto;
import org.example.appliancestore.model.Order;
import org.example.appliancestore.model.OrderRow;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderMapper implements Mapper<Order, OrderDto> {

    private final ClientMapper clientMapper;
    private final EmployeeMapper employeeMapper;
    private final OrderRowMapper orderRowMapper;


    @Override
    public OrderDto mapToDto(Order order) {
        return new OrderDto(
                order.getId(),
                employeeMapper.mapToDto(order.getEmployee()),
                clientMapper.mapToDto(order.getClient()),
                order.getOrderRowSet().stream().map(orderRowMapper::mapToDto).collect(Collectors.toSet()),
                order.getApproved()
        );
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                employeeMapper.mapToEntity(orderDto.getEmployee()),
                clientMapper.mapToEntity(orderDto.getClient()),
                (Set<OrderRow>) orderRowMapper.mapToEntity(orderDto.getOrderRowSetDto()),
                orderDto.getApproved()
        );
    }

    @Override
    public Collection<OrderDto> mapToDto(Collection<Order> orders) {
        if (orders == null) {
            return new ArrayList<>();
        }
        return orders.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public Collection<Order> mapToEntity(Collection<OrderDto> orderDtos) {
        if (orderDtos == null) {
            return new ArrayList<>();
        }
        return orderDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }


}
