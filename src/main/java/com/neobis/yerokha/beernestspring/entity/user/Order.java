package com.neobis.yerokha.beernestspring.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.neobis.yerokha.beernestspring.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime creationDateTime;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "contact_info_id")
    private ContactInfo contactInfo;

    @Column(name = "delivered")
    private Boolean isDelivered;

    @OneToMany(mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public Order() {
        orderItems = new ArrayList<>();
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(orderItem);
    }

    public void calculateTotalPrice() {
        BigDecimal sum = BigDecimal.valueOf(0);

        if (orderItems != null) {
            for (OrderItem orderItem : orderItems) {
                BigDecimal itemPrice = orderItem.getBeer().getSellingPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                sum = sum.add(itemPrice);
            }
        }

        this.setTotalPrice(sum);
    }
}
