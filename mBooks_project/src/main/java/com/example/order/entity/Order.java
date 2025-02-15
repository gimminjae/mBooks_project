package com.example.order.entity;

import com.example.member.enitty.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "product_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @ManyToOne(fetch = LAZY)
    private Member buyer;

    private String name;

    private boolean isPaid; //결제 여부
    private boolean isCanceled; //취소 여부
    private boolean isRefunded; //환불 여부

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);

        orderItems.add(orderItem);
    }

    public int calculatePayPrice() {
        int payPrice = 0;

        for (OrderItem orderItem : orderItems) {
            payPrice += orderItem.getSalePrice();
        }

        return payPrice;
    }

    public void setPaymentDone() {
        for (OrderItem orderItem : orderItems) {
            orderItem.setPaymentDone();
        }
        isPaid =  true;
    }

    public void setRefundDone() {
        for (OrderItem orderItem : orderItems) {
            orderItem.setRefundDone();
        }
        isPaid = false;
        isRefunded = true;
    }

    public void setCanceledDone() {
        for (OrderItem orderItem : orderItems) {
            orderItem.setCanceledDone();
        }
        isCanceled = true;
    }
    public int getPayPrice() {
        int payPrice = 0;
        for (OrderItem orderItem : orderItems) {
            payPrice += orderItem.getPayPrice();
        }

        return payPrice;
    }
    public void makeName() {
        String name = orderItems.get(0).getProduct().getSubject();

        if ( orderItems.size() > 1 ) {
            name += " 외 %d권".formatted(orderItems.size() - 1);
        }

        this.name = name;
    }
    public boolean isPayable() {
        if(isPaid) return false;
        if(isCanceled) return false;
        if(isRefunded) return false;

        return true;
    }
}
