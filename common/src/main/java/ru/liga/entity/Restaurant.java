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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "name", nullable = false)
    private String name;

}