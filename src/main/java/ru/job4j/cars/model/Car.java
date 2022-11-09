package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    private int release;
    private String shape;
    private String drive;
    private String gsb;
    private String color;
    private int mileage;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owners",
            joinColumns = { @JoinColumn(name = "car_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "driver_id", nullable = false, updatable = false)})
    private Set<Driver> owners = new HashSet<>();

    public Car() {
    }
}
