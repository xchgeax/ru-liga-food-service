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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
    @SequenceGenerator(name = "customers_seq", sequenceName = "customers_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "coordinates", nullable = false)
    private String coordinates;
    @Column(name = "email", nullable = false)
    private String email;
}