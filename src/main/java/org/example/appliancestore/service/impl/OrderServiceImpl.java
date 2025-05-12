package org.example.appliancestore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.OrderDto;
import org.example.appliancestore.dto.OrderRowDto;
import org.example.appliancestore.exception.NullEntityReferenceException;
import org.example.appliancestore.exception.PageException;
import org.example.appliancestore.mapper.ApplianceMapper;
import org.example.appliancestore.mapper.OrderMapper;
import org.example.appliancestore.model.Appliance;
import org.example.appliancestore.model.Order;
import org.example.appliancestore.model.OrderRow;
import org.example.appliancestore.repository.OrdersRepository;
import org.example.appliancestore.service.ApplianceService;
import org.example.appliancestore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.example.appliancestore.utils.PageUtils.getPageable;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderMapper orderMapper;
    private final ApplianceService applianceService;
    private final ApplianceMapper applianceMapper;

    @Loggable
    @Override
    public OrderDto add(Order order) {
        if (order != null) {
            return orderMapper.mapToDto(ordersRepository.save(order));
        }
        throw new NullEntityReferenceException("Order cannot be 'null'");
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public OrderDto readById(Long id) {
        return orderMapper.mapToDto(ordersRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order with id: " + id + " not found")));
    }

    @Loggable
    @Override
    public OrderDto update(Order order) {
        if (order != null) {
            readById(order.getId());
            return orderMapper.mapToDto(ordersRepository.save(order));
        }
        throw new NullEntityReferenceException("Order cannot be 'null'");
    }

    @Loggable
    @Override
    public OrderDto updateById(Long id, OrderDto orderToSave) {
        OrderDto order = this.readById(id);
        orderToSave.setOrderRowSetDto(order.getOrderRowSetDto());
        orderToSave.setClient(order.getClient());
        return this.update(orderMapper.mapToEntity(orderToSave));
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAll(Integer page, Integer size, String sort) {
        if (page != null && page < 0) {
            throw new PageException("Page number must not be less than 0");
        } else if (page != null && size < 1) {
            throw new PageException("Page size must not be less than one");
        }
        Pageable pageable = getPageable(page, size, sort);
        Page<Order> ordersPage = ordersRepository.findAll(pageable);
        List<OrderDto> orders = (List<OrderDto>) orderMapper.mapToDto(ordersPage.getContent());
        return orders.isEmpty() ? new ArrayList<>() : orders;
    }


    @Loggable
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAllByClientId(Long clientId, Integer page, Integer size, String sort) {
        if (page != null && page < 0) {
            throw new PageException("Page number must not be less than 0");
        } else if (page != null && size < 1) {
            throw new PageException("Page size must not be less than one");
        }
        Pageable pageable = getPageable(page, size, sort);
        Page<Order> ordersPage = ordersRepository.findAllByClientId(clientId, pageable);
        List<OrderDto> orders = (List<OrderDto>) orderMapper.mapToDto(ordersPage.getContent());
        return orders.isEmpty() ? new ArrayList<>() : orders;
    }

    @Loggable
    @Override
    public Set<OrderRowDto> getRowsByOrderId(Long id) {
        OrderDto order = readById(id);
        return order.getOrderRowSetDto();
    }

    @Loggable
    @Override
    public Order addApplianceToOrder(Long orderId, Integer applianceId, Integer numbers) {
        Order order = orderMapper.mapToEntity(readById(orderId));
        Appliance appliance = applianceMapper.mapToEntity(applianceService.readById(Long.valueOf(String.valueOf(applianceId))));
        OrderRow orderRow = new OrderRow();
        orderRow.setAppliance(appliance);
        orderRow.setNumber(Long.valueOf(numbers));
        BigDecimal orderRowAmount = appliance.getPrice().multiply(BigDecimal.valueOf(orderRow.getNumber()));
        orderRow.setAmount(orderRowAmount);
        order.getOrderRowSet().add(orderRow);
        return order;
    }

    @Loggable
    @Override
    public Order approveById(Long id) {
        Order order = orderMapper.mapToEntity(readById(id));
        order.setApproved(true);
        return order;
    }

    @Loggable
    @Override
    public Order unApproveById(Long id) {
        Order order = orderMapper.mapToEntity(readById(id));
        order.setApproved(false);
        return order;
    }

    @Override
    public Integer getPagesCount(Integer page, Integer size) {
        return ordersRepository.findAll(getPageable(page, size, null))
                .getTotalPages();
    }

    @Override
    public Integer getPagesCountByClientId(Long clientId, Integer page, Integer size) {
        return ordersRepository.findAllByClientId(clientId, getPageable(page, size, null))
                .getTotalPages();
    }

    @Loggable
    @Override
    public void delete(Long id) {
        ordersRepository.delete(orderMapper.mapToEntity(readById(id)));
    }

    @Loggable
    public boolean isOwnerOrThrow(Long orderId, Long userId) throws AccessDeniedException {
        OrderDto order = this.readById(orderId);
        if (order.getClient().getId().equals(userId)) {
            return true;
        } else {
            throw new AccessDeniedException("You are not allowed to perform this action with current order");
        }
    }

}
