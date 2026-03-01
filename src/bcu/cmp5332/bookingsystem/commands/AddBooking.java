package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * Command to add a booking for a customer on a flight.
 */
/**
 * CLI command to issue a booking.
 */
public class AddBooking implements Command {

    private final int customerId;
    private final int flightId;

    public AddBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
        Flight flight = flightBookingSystem.getFlightByID(flightId);
        
        if (customer.isDeleted()) {
            throw new FlightBookingSystemException("Cannot book for a deleted customer.");
        }
        
        if (flight.isDeleted()) {
            throw new FlightBookingSystemException("Cannot book a deleted flight.");
        }
        
        // Issue the booking
        flightBookingSystem.issueBooking(customer, flight, flightBookingSystem.getSystemDate());
        
        // Store data immediately
        try {
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save booking data: " + ex.getMessage() + 
                    ". Changes have been rolled back.");
        }
        
        System.out.println("Booking added successfully for Customer #" + customerId + 
                " on Flight #" + flightId);
    }
}
