package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
