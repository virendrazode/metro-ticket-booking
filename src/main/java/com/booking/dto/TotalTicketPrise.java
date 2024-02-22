package com.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalTicketPrise {

    private String startStationName;
    private String destinationStationName;
    private Double totalTicketPrice;
}
