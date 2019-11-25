package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.*;

import java.util.ArrayList;

public interface CustomerWindowDelegate {
    ArrayList<Vehicle> search(FilterSearch filterSearch);
    float costForReservation(FilterSearch filterSearch);
    ReservationRentReciept reserve(Customers customer);
}
