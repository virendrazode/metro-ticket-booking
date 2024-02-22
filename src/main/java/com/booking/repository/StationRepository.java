package com.booking.repository;

import com.booking.entity.StationPricingMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StationRepository extends JpaRepository<StationPricingMaster, Long> {

}
