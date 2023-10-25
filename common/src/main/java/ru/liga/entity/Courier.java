package ru.liga.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Couriers")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "coordinates", nullable = false)
    private String coordinates;
    @Enumerated(EnumType.STRING)
    private CourierStatus status;
}