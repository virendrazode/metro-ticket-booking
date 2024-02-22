package com.booking.controller;

import com.booking.entity.StationPricingMaster;
import com.booking.service.StationService;
import com.booking.service.impl.StationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("station-master")
public class StationController {
    private static final Logger log = LoggerFactory.getLogger(StationController.class);
    @Autowired
    StationService stationService;

    @PostMapping("create-stations")
    public List<StationPricingMaster> createStation(@RequestBody List<StationPricingMaster> stations) {
        log.info("Received a request to create stations: {}", stations);
        List<StationPricingMaster> createdStations = stationService.createStation(stations);
        log.info("Stations created successfully. Count: {}", createdStations.size());
        return createdStations;
    }

    @GetMapping("all-station-list")
    public List<StationPricingMaster> getAllStations() {
        log.info("Received a request to get all stations.");
        List<StationPricingMaster> allStations = stationService.findAllStations();
        log.info("Found {} stations.", allStations.size());
        return allStations;
    }
}
