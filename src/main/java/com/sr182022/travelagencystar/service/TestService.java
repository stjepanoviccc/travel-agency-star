package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TestService {
    // this is for testing purposes only
    Destination destination1 = new Destination(0, "Belgrade", "Serbia", "Europe", "img");
    Vehicle vehicle1 = new Vehicle(0, 4, destination1, "description", VehicleType.Bus);
    AccommodationUnit accommodation1 = new AccommodationUnit(0, "Name", 4, "Desc", null, destination1, null, AccommodationType.Apartment);
    Travel travel1 = new Travel(0, destination1, vehicle1, accommodation1, LocalDateTime.now(), LocalDateTime.now(), 5, TravelCategory.Winter);

    public Destination getDestination1() {
        return destination1;
    }

    public void setDestination1(Destination destination1) {
        this.destination1 = destination1;
    }

    public Vehicle getVehicle1() {
        return vehicle1;
    }

    public void setVehicle1(Vehicle vehicle1) {
        this.vehicle1 = vehicle1;
    }

    public AccommodationUnit getAccommodation1() {
        return accommodation1;
    }

    public void setAccommodation1(AccommodationUnit accommodation1) {
        this.accommodation1 = accommodation1;
    }

    public Travel getTravel1() {
        return travel1;
    }

    public void setTravel1(Travel travel1) {
        this.travel1 = travel1;
    }

}
