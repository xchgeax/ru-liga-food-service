package ru.liga.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant_menu_items")
public class RestaurantMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_menu_items_seq")
    @SequenceGenerator(name = "restaurant_menu_items_seq", sequenceName = "restaurant_menu_items_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private Long price;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "image", nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}