package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.*;

import java.util.ArrayList;
import java.util.List;

public interface ClerkWindowDelegate {
    Reservations searchForReservation(int confNo);
    Reservations searchForReservation(int cellphone, FilterSearch filterSearch);
    float costForRent(FilterSearch filterSearch);
    ReservationRentReciept rentWithoutReservation(Customers customer, CustomerRentInfo customerRentInfo);
    ReservationRentReciept rentWithReservation(CustomerRentInfo customerRentInfo, Reservations reservations);

    ArrayList<Rentals> generateDailyRentals(FilterSearch filterSearch);
    ArrayList<Rentals> generateDailyRentalsPerBranch(FilterSearch filterSearch);
    ArrayList<Returns> generateDailyReturns(FilterSearch filterSearch);
    ArrayList<Returns> generateDailyReturnsPerBranch(FilterSearch filterSearch);
}
