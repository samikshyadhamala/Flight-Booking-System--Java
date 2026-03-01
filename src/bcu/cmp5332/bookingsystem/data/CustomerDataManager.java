package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;
import java.io.*;
import java.util.Scanner;

/**
 * Persists customer data to and from text storage.
 */
public class CustomerDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/customers.txt";
    
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        File file = new File(RESOURCE);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return;
        }
        
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(SEPARATOR, -1);
                int id = Integer.parseInt(parts[0]);
                String username = parts[1];
                String password = parts.length > 2 ? parts[2] : "password";
                String email = parts.length > 3 ? parts[3] : "";
                String phone = parts.length > 4 ? parts[4] : "";
                boolean deleted = parts.length > 7 ? Boolean.parseBoolean(parts[7])
                        : (parts.length > 5 ? Boolean.parseBoolean(parts[5]) : false);
                
                Customer customer = new Customer(id, username, password, email, phone);
                customer.setDeleted(deleted);
                fbs.addCustomer(customer);
            }
        }
    }
    
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getUsername() + SEPARATOR);
                out.print(customer.getPassword() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.isDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
