package org.example.appliancestore.service;

import org.example.appliancestore.dto.OrderDto;
import org.example.appliancestore.dto.OrderRowDto;
import org.example.appliancestore.model.Order;

import java.util.List;
import java.util.Set;

public interface OrderService {

    OrderDto add(Order order);

    OrderDto readById(Long id);

    OrderDto update(Order order);
    OrderDto updateById(Long id, OrderDto orderToSave);

    List<OrderDto> getAll(Integer page, Integer size, String sort);
    List<OrderDto> getAllByClientId(Long clientId, Integer page, Integer size, String sort);

    void delete(Long id);

    Set<OrderRowDto> getRowsByOrderId(Long id);

    Order addApplianceToOrder(Long orderId, Integer applianceId, Integer numbers);

    Order approveById(Long id);
    Order unApproveById(Long id);

    Integer getPagesCount(Integer page, Integer size);
    Integer getPagesCountByClientId(Long clientId, Integer page, Integer size);

}
