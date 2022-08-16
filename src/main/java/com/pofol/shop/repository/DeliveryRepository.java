package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.order.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {


}
