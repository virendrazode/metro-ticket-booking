package com.booking.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long ticketNo;
    private Double price;
    private LocalDateTime bookingOn;
    private LocalDateTime expiryOn;
    @Column(nullable = true)
    private Boolean entryOn;
    @Column(nullable = true)
    private Boolean exitOn;
    private String source;
    private String destination;
}
