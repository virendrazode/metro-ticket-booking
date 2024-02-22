package com.booking.service;

import com.booking.dto.BookTicketDto;
import com.booking.entity.Booking;

public interface BookingService {
    Booking bookTicket(BookTicketDto bookingRequest);

    Booking ticketPrice(Long ticketNo);

    Booking checkIn(Long ticketNo);

    Booking checkOut(Long ticketNo);
}
