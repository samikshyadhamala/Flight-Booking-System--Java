package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.EditBooking;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * GUI window to update an existing booking to a new flight.
 */
public class UpdateBookingWindow extends JFrame implements ActionListener {
    private MainWindow mw;
    private JTextField customerIdText = new JTextField();
    private JTextField oldFlightIdText = new JTextField();
    private JTextField newFlightIdText = new JTextField();
    private JButton updateBtn = new JButton("Update");
    private JButton cancelBtn = new JButton("Cancel");

    public UpdateBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        setTitle("Update Booking");
        setSize(350, 200);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));
        topPanel.add(new JLabel("Customer ID : "));
        topPanel.add(customerIdText);
        topPanel.add(new JLabel("Current Flight ID : "));
        topPanel.add(oldFlightIdText);
        topPanel.add(new JLabel("New Flight ID : "));
        topPanel.add(newFlightIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(updateBtn);
        bottomPanel.add(cancelBtn);

        updateBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateBtn) {
            updateBooking();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
            this.dispose();
        }
    }

    private void updateBooking() {
        try {
            int customerId = Integer.parseInt(customerIdText.getText());
            int oldFlightId = Integer.parseInt(oldFlightIdText.getText());
            int newFlightId = Integer.parseInt(newFlightIdText.getText());
            
            Command editBooking = new EditBooking(customerId, oldFlightId, newFlightId);
            editBooking.execute(mw.getFlightBookingSystem());
            
            this.setVisible(false);
            this.dispose();
            
            JOptionPane.showMessageDialog(this, 
                    "Booking updated successfully! A rebooking fee has been applied.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                    
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IDs must be numbers.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
