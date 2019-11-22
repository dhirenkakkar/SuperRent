package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.Customers;
import ca.ubc.cs304.model.FilterSearch;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.Vehicle;

import java.util.ArrayList;

public interface CustomerWindowDelegate {
    ArrayList<Vehicle> search(FilterSearch filterSearch);
    float costForReservation(FilterSearch filterSearch);
    Reservations reserve(Customers customer);
}
