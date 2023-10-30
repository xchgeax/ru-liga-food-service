package ru.liga.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurants_seq")
    @SequenceGenerator(name = "restaurants_seq", sequenceName = "restaurants_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "name", nullable = false)
    private String name;

}