package com.booking.controller;

import com.booking.dto.BookTicketDto;
import com.booking.entity.Booking;
import com.booking.service.BookingService;
import com.booking.service.impl.BookingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket-booking")
public class BookingController {
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingService bookingService;

    @PostMapping("generate-ticket")
    public Booking bookTicket(@RequestBody BookTicketDto bookingRequest) {
        log.info("Received a request to book a ticket: {}", bookingRequest);
        Booking bookTicket = bookingService.bookTicket(bookingRequest);
        log.info("Ticket booked successfully. Ticket details: {}", bookTicket);
        return bookTicket;
    }

    @GetMapping("get-ticket-prising/{ticketNo}")
    public Booking totalTicketPrice(@PathVariable("ticketNo") Long ticketNo) {
        log.info("Received a request to get ticket pricing for ID: {}", ticketNo);
        Booking booking = bookingService.ticketPrice(ticketNo);
        log.info("Ticket pricing retrieved successfully. Ticket details: {}", booking);
        return booking;
    }

    @PostMapping("checkin/{ticketNo}")
    public Booking checkIn(@PathVariable("ticketNo") Long ticketNo) {
        log.info("Received a request for check-in/check-out: {}", ticketNo);
        Booking booking = bookingService.checkIn(ticketNo);
        log.info("Check-in/Check-out processed successfully. Result: {}", booking);
        return booking;
    }

    @PostMapping("checkout/{ticketNo}")
    public Booking checkout(@PathVariable("ticketNo") Long ticketNo) {
        log.info("Received a request for check-in/check-out: {}", ticketNo);
        Booking booking = bookingService.checkOut(ticketNo);
        log.info("Check-in/Check-out processed successfully. Result: {}", booking);
        return booking;
    }
}
