package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.auth.Session;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Admin dashboard for managing flights and customers.
 */
public class AdminDashboard extends JFrame implements ActionListener {

    private FlightBookingSystem fbs;
    private JMenuBar menuBar;
    private JMenuItem addFlightItem, viewFlightsItem, deleteFlightItem;
    private JMenuItem viewCustomersItem, deleteCustomerItem;
    private JMenuItem logoutItem;

    public AdminDashboard(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    private void initialize() {
        setTitle("Admin Dashboard - Flight Booking System");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();

        JMenu flightsMenu = new JMenu("Flights");
        addFlightItem = new JMenuItem("Add Flight");
        viewFlightsItem = new JMenuItem("View All Flights");
        deleteFlightItem = new JMenuItem("Delete Flight");
        addFlightItem.addActionListener(this);
        viewFlightsItem.addActionListener(this);
        deleteFlightItem.addActionListener(this);
        flightsMenu.add(addFlightItem);
        flightsMenu.add(viewFlightsItem);
        flightsMenu.add(deleteFlightItem);

        JMenu customersMenu = new JMenu("Customers");
        viewCustomersItem = new JMenuItem("View All Customers");
        deleteCustomerItem = new JMenuItem("Delete Customer");
        viewCustomersItem.addActionListener(this);
        deleteCustomerItem.addActionListener(this);
        customersMenu.add(viewCustomersItem);
        customersMenu.add(deleteCustomerItem);

        JMenu systemMenu = new JMenu("System");
        logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(this);
        systemMenu.add(logoutItem);

        menuBar.add(flightsMenu);
        menuBar.add(customersMenu);
        menuBar.add(systemMenu);

        setJMenuBar(menuBar);

        displayWelcome();
        setVisible(true);
    }

    private void displayWelcome() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("<html><center><h1>Welcome, Admin!</h1>" +
                "<p>Use the menu above to manage flights and customers</p></center></html>");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.CENTER);

        setContentPane(panel);
        revalidate();
        repaint();
    }

    void displayFlights() {
        java.util.List<Flight> flights = fbs.getFutureFlights();
        String[] columns = {"ID", "Flight No", "Origin", "Destination", "Date", "Capacity", "Available", "Status", "Price"};
        Object[][] data = new Object[flights.size()][9];

        for (int i = 0; i < flights.size(); i++) {
            Flight f = flights.get(i);
            data[i][0] = f.getId();
            data[i][1] = f.getFlightNumber();
            data[i][2] = f.getOrigin();
            data[i][3] = f.getDestination();
            data[i][4] = f.getDepartureDate();
            data[i][5] = f.getCapacity();
            data[i][6] = f.getAvailableSeats();
            data[i][7] = f.getAvailableSeats() <= 0 ? "FULL" : "OPEN";
            data[i][8] = String.format("£%.2f", f.getPrice(fbs.getSystemDate()));
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        setContentPane(scrollPane);
        revalidate();
        repaint();
    }

    void displayCustomers() {
        java.util.List<Customer> customers = fbs.getActiveCustomers();
        String[] columns = {"ID", "Name", "Email", "Phone", "Bookings"};
        Object[][] data = new Object[customers.size()][5];

        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            data[i][0] = c.getId();
            data[i][1] = c.getName();
            data[i][2] = c.getEmail();
            data[i][3] = c.getPhone();
            data[i][4] = c.getBookings().size();
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        setContentPane(scrollPane);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addFlightItem) {
            new AddFlightWindow(this);
        } else if (e.getSource() == viewFlightsItem) {
            displayFlights();
        } else if (e.getSource() == deleteFlightItem) {
            new DeleteFlightWindow(this);
        } else if (e.getSource() == viewCustomersItem) {
            displayCustomers();
        } else if (e.getSource() == deleteCustomerItem) {
            new DeleteCustomerWindow(this);
        } else if (e.getSource() == logoutItem) {
            try {
                FlightBookingSystemData.store(fbs);
                Session.getInstance().logout();
                this.dispose();
                new LoginWindow(fbs);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
            }
        }
    }

    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }
}
