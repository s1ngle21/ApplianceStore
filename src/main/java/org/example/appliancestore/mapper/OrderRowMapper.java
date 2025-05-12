package org.example.appliancestore.mapper;

import lombok.AllArgsConstructor;
import org.example.appliancestore.dto.OrderRowDto;
import org.example.appliancestore.model.OrderRow;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderRowMapper implements Mapper<OrderRow, OrderRowDto> {

    private final ApplianceMapper applianceMapper;

    @Override
    public OrderRowDto mapToDto(OrderRow orderRow) {
        return new OrderRowDto(
                orderRow.getId(),
                applianceMapper.mapToDto(orderRow.getAppliance()),
                orderRow.getNumber(),
                orderRow.getAmount()
        );
    }

    @Override
    public OrderRow mapToEntity(OrderRowDto orderRowDto) {
        return new OrderRow(
                orderRowDto.getId(),
                applianceMapper.mapToEntity(orderRowDto.getAppliance()),
                orderRowDto.getNumber(),
                orderRowDto.getAmount()
        );
    }

    @Override
    public Collection<OrderRowDto> mapToDto(Collection<OrderRow> orderRows) {
        if (orderRows == null) {
            return new HashSet<>();
        }
        return orderRows.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<OrderRow> mapToEntity(Collection<OrderRowDto> orderRowDtos) {
        if (orderRowDtos == null) {
            return new HashSet<>();
        }
        return orderRowDtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }

}
