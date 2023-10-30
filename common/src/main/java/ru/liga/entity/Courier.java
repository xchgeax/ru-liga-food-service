package ru.liga.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Couriers")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "couriers_seq")
    @SequenceGenerator(name = "couriers_seq", sequenceName = "couriers_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "coordinates", nullable = false)
    private String coordinates;
    @Enumerated(EnumType.STRING)
    private CourierStatus status;
}