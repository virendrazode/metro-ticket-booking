package com.booking.service.impl;

import com.booking.dto.BookTicketDto;
import com.booking.dto.TotalTicketPrise;
import com.booking.entity.Booking;
import com.booking.entity.StationPricingMaster;
import com.booking.exception.ResourceNotFoundExceptionGeneric;
import com.booking.repository.BookingRepository;
import com.booking.repository.StationRepository;
import com.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    StationRepository stationRepository;
    @Value("${booking.expiryHours}")
    private int expiryHours;

    @Override
    public Booking bookTicket(BookTicketDto bookingRequest) {
        log.info("Received a request to book a ticket: {}", bookingRequest);
        Booking ticketBooking = new Booking();
        try {
            Optional<StationPricingMaster> sourceStation = stationRepository.findById(bookingRequest.getStartStationId());
            Optional<StationPricingMaster> destination = stationRepository.findById(bookingRequest.getEndStationId());
            StationPricingMaster sourceStationDetails = sourceStation.get();
            StationPricingMaster destinationDetails = destination.get();
            bookTickets(sourceStationDetails, destinationDetails, ticketBooking);
            bookingRepository.save(ticketBooking);
        } catch (Exception e) {
            log.info("Error booking ticket: {}", e.getMessage(), e);
        }
        return ticketBooking;
    }

    private Booking bookTickets(StationPricingMaster sourceStationDetails, StationPricingMaster destinationDetails, Booking ticketBooking) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        ticketBooking.setBookingOn(currentDateTime);
        ticketBooking.setDestination(destinationDetails.getStationName());
        ticketBooking.setSource(sourceStationDetails.getStationName());
        ticketBooking.setExpiryOn(currentDateTime.plusHours(expiryHours));
        ticketBooking.setEntryOn(false);
        ticketBooking.setExitOn(false);
        Double ticketPrice = destinationDetails.getPrice() - sourceStationDetails.getPrice();
        ticketBooking.setPrice(ticketPrice);
        return ticketBooking;
    }

    @Override
    public Booking ticketPrice(Long ticketNo) {
        Optional<Booking> ticketPrice = bookingRepository.findById(ticketNo);
            if(ticketPrice.isEmpty()) {
                log.error("Error getting ticket price for TicketNo = {}",ticketNo);
                throw new ResourceNotFoundExceptionGeneric("Booking details not found.");
            }else {
            log.info("Received a request to get ticket price for ID: {}", ticketNo);
            Booking priceofTicket = ticketPrice.get();
            Booking getPrice = totalPrice(priceofTicket);
            return getPrice;
        }
    }

    private Booking totalPrice(Booking ticketPrice) {
        TotalTicketPrise totalTicketPrise = new TotalTicketPrise();
        totalTicketPrise.setTotalTicketPrice(ticketPrice.getPrice());
        totalTicketPrise.setStartStationName(ticketPrice.getSource());
        totalTicketPrise.setDestinationStationName(ticketPrice.getDestination());

        Booking bookingPrice = new Booking();
        bookingPrice.setSource(totalTicketPrise.getStartStationName());
        bookingPrice.setDestination(totalTicketPrise.getDestinationStationName());
        bookingPrice.setPrice(totalTicketPrise.getTotalTicketPrice());
        return bookingPrice;
    }

    @Override
    public Booking checkIn(Long ticketNo) {
        log.info("Received a check-in/check-out request for ID: {}", ticketNo);

        Optional<Booking> bookingDetails = bookingRepository.findById(ticketNo);

        if (bookingDetails.isEmpty()) {
            throw new ResourceNotFoundExceptionGeneric("Booking details not found.");
        }
        Booking booking = bookingDetails.get();
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(booking.getExpiryOn())) {
            throw new ResourceNotFoundExceptionGeneric("Ticket has expired.");
        }
        if (booking.getEntryOn()) {
            throw new ResourceNotFoundExceptionGeneric("Check-In already done.");
        }

        booking.setEntryOn(true);
        return bookingRepository.save(booking);


    }

    @Override
    public Booking checkOut(Long ticketNo) {
        Optional<Booking> bookingDetails = bookingRepository.findById(ticketNo);
        if (bookingDetails.isEmpty()) {
            throw new ResourceNotFoundExceptionGeneric("Booking details not found.");
        }
        Booking booking = bookingDetails.get();
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(booking.getExpiryOn())) {
            throw new ResourceNotFoundExceptionGeneric("Ticket has expired.");
        }

        if (booking.getEntryOn()) {
            if (booking.getExitOn()) {
                throw new ResourceNotFoundExceptionGeneric("Check-Out already done.");
            }
            booking.setExitOn(true);
            return bookingRepository.save(booking);
        } else {
            throw new ResourceNotFoundExceptionGeneric("Not able to Check-Out before Check-In.");
        }

    }
}
