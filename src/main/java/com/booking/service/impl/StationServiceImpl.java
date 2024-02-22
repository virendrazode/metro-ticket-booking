package com.booking.service.impl;

import com.booking.entity.StationPricingMaster;
import com.booking.repository.StationRepository;
import com.booking.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    StationRepository stationRepository;
    private static final Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    @Override
    public List<StationPricingMaster> createStation(List<StationPricingMaster> stations) {
        try {
            log.info("Received a request to create stations: {}", stations);
            List<StationPricingMaster> createdStations = stationRepository.saveAll(stations);
            log.info("Stations created successfully. Count: {}", createdStations.size());
            return createdStations;
        } catch (Exception e) {
            log.error("Error creating stations: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating stations. Please try again.");
        }
    }

    @Override
    public List<StationPricingMaster> findAllStations() {
        try {
            log.info("Received a request to get all stations.");
            List<StationPricingMaster> allStationList = stationRepository.findAll();
            if (allStationList != null && !allStationList.isEmpty()) {
                log.info("Found {} stations.", allStationList.size());
            } else {
                log.warn("No stations found.");
            }
            return allStationList;
        } catch (Exception e) {
            log.error("Error getting all stations: {}", e.getMessage(), e);
            throw new RuntimeException("Error getting all stations. Please try again.");
        }
    }
}
