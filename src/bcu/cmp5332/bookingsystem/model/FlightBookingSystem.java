package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main system model.
 * OOP: ENCAPSULATION, COMPOSITION
 */
/**
 * Core system model holding flights, customers, and admins.
 */
public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2024-11-11");
    
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final Map<Integer, Admin> admins = new TreeMap<>();
    
    public LocalDate getSystemDate() { return systemDate; }
    
    public List<Flight> getFlights() {
        return new ArrayList<>(flights.values());
    }
    
    /**
     * @return list of non-deleted flights
     */
    public List<Flight> getActiveFlights() {
        return flights.values().stream()
                .filter(f -> !f.isDeleted())
                .collect(Collectors.toList());
    }
    
    /**
     * @return list of non-deleted flights that have not departed
     */
    public List<Flight> getFutureFlights() {
        return flights.values().stream()
                .filter(f -> !f.isDeleted() && !f.hasDeparted(systemDate))
                .collect(Collectors.toList());
    }
    
    public List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    /**
     * @return list of non-deleted customers
     */
    public List<Customer> getActiveCustomers() {
        return customers.values().stream()
                .filter(c -> !c.isDeleted())
                .collect(Collectors.toList());
    }
    
    public List<Admin> getAdmins() {
        return new ArrayList<>(admins.values());
    }
    
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("No flight with ID " + id);
        }
        return flights.get(id);
    }
    
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if (!customers.containsKey(id)) {
            throw new FlightBookingSystemException("No customer with ID " + id);
        }
        return customers.get(id);
    }
    
    public Admin getAdminByID(int id) throws FlightBookingSystemException {
        if (!admins.containsKey(id)) {
            throw new FlightBookingSystemException("No admin with ID " + id);
        }
        return admins.get(id);
    }
    
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new FlightBookingSystemException("Duplicate flight ID");
        }
        flights.put(flight.getId(), flight);
    }
    
    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.containsKey(customer.getId())) {
            throw new FlightBookingSystemException("Duplicate customer ID");
        }
        customers.put(customer.getId(), customer);
    }
    
    public void addAdmin(Admin admin) throws FlightBookingSystemException {
        if (admins.containsKey(admin.getId())) {
            throw new FlightBookingSystemException("Duplicate admin ID");
        }
        admins.put(admin.getId(), admin);
    }
    
    public void deleteFlight(int id) throws FlightBookingSystemException {
        getFlightByID(id).setDeleted(true);
    }
    
    public void deleteCustomer(int id) throws FlightBookingSystemException {
        getCustomerByID(id).setDeleted(true);
    }
    
    public void issueBooking(Customer customer, Flight flight, LocalDate bookingDate) 
            throws FlightBookingSystemException {
        
        if (flight.hasDeparted(systemDate)) {
            throw new FlightBookingSystemException("Flight has departed");
        }
        
        flight.addPassenger(customer);
        
        double price = flight.getPrice(bookingDate);
        
        Booking booking = new Booking(customer, flight, bookingDate);
        booking.setPrice(price);
        
        customer.addBooking(booking);
    }
    
    public void cancelBooking(int customerId, int flightId) throws FlightBookingSystemException {
        Customer customer = getCustomerByID(customerId);
        Flight flight = getFlightByID(flightId);
        
        customer.cancelBookingForFlight(flight);
        flight.removePassenger(customer);
    }
    
    public void updateBooking(int customerId, int oldFlightId, int newFlightId) 
            throws FlightBookingSystemException {
        
        Customer customer = getCustomerByID(customerId);
        Flight oldFlight = getFlightByID(oldFlightId);
        Flight newFlight = getFlightByID(newFlightId);
        
        Booking oldBooking = null;
        for (Booking b : customer.getBookings()) {
            if (b.getFlight().getId() == oldFlightId && !b.isCancelled()) {
                oldBooking = b;
                break;
            }
        }
        
        if (oldBooking == null) {
            throw new FlightBookingSystemException("No active booking found");
        }
        
        if (newFlight.getAvailableSeats() <= 0) {
            throw new FlightBookingSystemException("New flight is full");
        }
        
        oldBooking.setCancelled(true);
        oldBooking.setCancellationFee(oldBooking.getPrice() * 0.10);
        oldFlight.removePassenger(customer);
        
        issueBooking(customer, newFlight, systemDate);
    }
}
