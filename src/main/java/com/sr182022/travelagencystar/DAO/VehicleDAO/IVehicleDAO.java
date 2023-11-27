package com.sr182022.travelagencystar.DAO.VehicleDAO;

import com.sr182022.travelagencystar.model.Vehicle;

import java.util.List;

public interface IVehicleDAO {
    List<Vehicle> Load();
    void Save(List<Vehicle> vehiclesList);
}
