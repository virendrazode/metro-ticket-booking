package com.booking.service;

import com.booking.entity.StationPricingMaster;

import java.util.List;

public interface StationService {
    List<StationPricingMaster> createStation(List<StationPricingMaster> stations);

    List<StationPricingMaster> findAllStations();
}
