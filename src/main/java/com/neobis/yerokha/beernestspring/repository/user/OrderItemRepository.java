package com.neobis.yerokha.beernestspring.repository.user;

import com.neobis.yerokha.beernestspring.entity.user.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
