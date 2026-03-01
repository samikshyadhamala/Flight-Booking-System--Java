# ADVANCED FLIGHT BOOKING SYSTEM
## Complete Implementation with All Features

### 🎯 ALL NEW FEATURES IMPLEMENTED

#### ✅ Authentication System
- User Registration with validation
- Secure Login System
- Session Management
- **Email Uniqueness Validation** (NO DUPLICATES!)
- **Phone Uniqueness Validation** (NO DUPLICATES!)

#### ✅ Role-Based Dashboards
- **Admin Dashboard**: Manage all flights, customers, system control
- **Customer Dashboard**: Personal bookings, wallet, flight browsing

#### ✅ Wallet System
- Add funds to wallet
- Auto-deduction for bookings
- Cancellation fees handled
- Real-time balance display

#### ✅ Complete Validation
- **Email uniqueness across ALL users (Admin + Customer)**
- **Phone uniqueness across ALL users**
- Capacity overflow prevention
- Wallet balance verification
- Comprehensive error messages

### 📚 OOP PRINCIPLES (ALL DEMONSTRATED!)

#### 1. ENCAPSULATION
**Files**: User.java, Customer.java, Admin.java, Flight.java
**Example**: Private fields with public getters/setters
```java
private double walletBalance; // ENCAPSULATION
public double getWalletBalance() { return walletBalance; }
```

#### 2. INHERITANCE
**Files**: User.java (base) → Admin.java, Customer.java (derived)
**Example**: 
```java
public class Customer extends User { // INHERITANCE
    public Customer(...) {
        super(...); // Call parent constructor
    }
}
```

#### 3. POLYMORPHISM
**Files**: User.getRole(), User.getDetails()
**Example**:
```java
// In User.java
public abstract String getRole(); // POLYMORPHISM

// In Admin.java
@Override
public String getRole() { return "ADMIN"; }

// In Customer.java
@Override
public String getRole() { return "CUSTOMER"; }
```

#### 4. ABSTRACTION
**Files**: User.java, Command.java, DataManager.java
**Example**:
```java
public abstract class User { // ABSTRACTION
    public abstract String getRole();
}
```

#### 5. COMPOSITION
**Files**: Customer has Bookings, Flight has Passengers
**Example**:
```java
// COMPOSITION: Customer HAS bookings
private final List<Booking> bookings = new ArrayList<>();
```


 **Run:**
```bash
java -cp bin bcu.cmp5332.bookingsystem.main.Main
```

### 🔐 DEFAULT ACCOUNTS

**Admin:**
- Username: `admin`
- Password: `admin123`

**Test Customers:**
- Username: `john_doe`, Password: `customer123`
- Username: `sarah_j`, Password: `customer456`
- Username: `mike_brown`, Password: `customer789`

### ✅ COMPLETE CHECKLIST VERIFICATION

| Feature | Status | Details |
|---------|--------|---------|
| Add customers | ✅ | With auth, wallet, loyalty |
| List customers | ✅ | In admin dashboard |
| Issue bookings | ✅ | With visual pricing |
| Cancel bookings | ✅ | With fee handling |
| Save/Load data | ✅ | All 4 files |
| Capacity/Price | ✅ | Enhanced with dynamic pricing |
| Customer email | ✅ | With uniqueness validation |
| GUI features | ✅ | All windows enhanced |
| Delete operations | ✅ | Soft delete |
| Future flights | ✅ | Filtered |
| Fees | ✅ | 10% cancel/rebook |
| Dynamic pricing | ✅ | **VISUAL COLOR-CODED DISPLAY** |
| **Login/Register** | ✅ | **FULLY WORKING** |
| **Admin Dashboard** | ✅ | **COMPLETE** |
| **Customer Dashboard** | ✅ | **COMPLETE** |
| **Email unique** | ✅ | **VALIDATED** |
| **Phone unique** | ✅ | **VALIDATED** |
| **Wallet system** | ✅ | **FULL IMPLEMENTATION** |
| **Loyalty system** | ✅ | **4 TIERS WITH AUTO-UPGRADE** |
| **Visual pricing** | ✅ | **COLOR-CODED IN GUI** |


