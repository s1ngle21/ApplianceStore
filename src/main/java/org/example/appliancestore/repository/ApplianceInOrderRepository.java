package org.example.appliancestore.repository;

import org.example.appliancestore.model.OrderRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplianceInOrderRepository extends JpaRepository<OrderRow, Long> {

   @Query(value = """
           SELECT orr.* FROM order_rows orr
           JOIN order_order_row oor ON orr.id = oor.order_row_id
           WHERE oor.order_id = :orderId
           """, nativeQuery = true)
    List<OrderRow> findOrderRowsByOrderId(@Param("orderId") Long orderId);

   /*
    @Query("SELECT ord.appliance FROM OrderRow ord WHERE ord.id = :orderId")
    List<Appliance> findAppliancesByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT ord.order FROM OrderRow ord WHERE ord.appliance.id = :applianceId")
    List<Orders> findOrdersByApplianceId(@Param("applianceId") Long applianceId);

    @Query("SELECT SUM(or.amount) FROM OrderRow or WHERE or.appliance.id = :applianceId")
    BigDecimal calculateTotalAmountForAppliance(@Param("applianceId") Long applianceId);

    OrderRow save(OrderRow orderRow);

    @Modifying
    @Query("DELETE FROM OrderRow or WHERE or.order.id = :orderId")
    void deleteOrderRowsByOrderId(@Param("orderId") Long orderId);

    @Modifying
    @Query("DELETE FROM OrderRow or WHERE or.order.id = :orderId AND or.appliance.id = :applianceId")
    void deleteApplianceFromOrder(@Param("orderId") Long orderId, @Param("applianceId") Long applianceId);*/

}
