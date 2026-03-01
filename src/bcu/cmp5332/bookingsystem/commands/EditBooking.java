package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * Command to update/edit a booking to a different flight.
 */
/**
 * CLI command to update a booking to a different flight.
 */
public class EditBooking implements Command {

    private final int customerId;
    private final int oldFlightId;
    private final int newFlightId;

    public EditBooking(int customerId, int oldFlightId, int newFlightId) {
        this.customerId = customerId;
        this.oldFlightId = oldFlightId;
        this.newFlightId = newFlightId;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Update the booking
        flightBookingSystem.updateBooking(customerId, oldFlightId, newFlightId);
        
        // Store data immediately
        try {
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save updated booking data: " + 
                    ex.getMessage() + ". Changes have been rolled back.");
        }
        
        System.out.println("Booking updated successfully. A rebooking fee has been applied.");
    }
}
