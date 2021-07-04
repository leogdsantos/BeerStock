package com.dio.beerstock.beerstock.entity;

import com.dio.beerstock.beerstock.enums.BeerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Beer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String name;

        private String brand;

        @Column(nullable = false)
        private int max;

        @Column(nullable = false)
        private int quantity;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private BeerType type;

}
