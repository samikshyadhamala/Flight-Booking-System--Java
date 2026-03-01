package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;
import java.io.*;
import java.util.Scanner;

/**
 * Persists admin data to and from text storage.
 */
public class AdminDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/admins.txt";
    
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        File file = new File(RESOURCE);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            // Create default admin
            Admin admin = new Admin(1, "admin", "admin123", "admin@fbs.com", "+44-1234-567890");
            fbs.addAdmin(admin);
            return;
        }
        
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.hasNextLine() ? sc.nextLine() : "";
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(SEPARATOR, -1);
                int id = Integer.parseInt(parts[0]);
                String username = parts[1];
                String password = parts[2];
                String email = parts[3];
                String phone = parts[4];
                String dept = parts.length > 5 ? parts[5] : "Administration";
                boolean deleted = parts.length > 6 ? Boolean.parseBoolean(parts[6]) : false;
                
                Admin admin = new Admin(id, username, password, email, phone, dept);
                admin.setDeleted(deleted);
                fbs.addAdmin(admin);
            }
        }
    }
    
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Admin admin : fbs.getAdmins()) {
                out.print(admin.getId() + SEPARATOR);
                out.print(admin.getUsername() + SEPARATOR);
                out.print(admin.getPassword() + SEPARATOR);
                out.print(admin.getEmail() + SEPARATOR);
                out.print(admin.getPhone() + SEPARATOR);
                out.print(admin.getDepartment() + SEPARATOR);
                out.print(admin.isDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
