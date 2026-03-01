package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to display detailed information about a specific customer.
 */
/**
 * CLI command to display a customer's details.
 */
public class ShowCustomer implements Command {

    private final int customerId;

    public ShowCustomer(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
        
        if (customer.isDeleted()) {
            System.out.println("This customer has been deleted.");
            return;
        }
        
        System.out.println(customer.getDetailsLong());
    }
}
