package com.booking.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StationPricingMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationName;
    private double price;
    @Column(nullable = true)
    private boolean startStation;
    @Column(nullable = true)
    private boolean lastStation;
}
