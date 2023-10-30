package ru.liga.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_seq")
    @SequenceGenerator(name = "order_items_seq", sequenceName = "order_items_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "price", nullable = false)
    private Long price;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne
    @JoinColumn(name = "restaurant_menu_item")
    private RestaurantMenuItem restaurantMenuItem;
}