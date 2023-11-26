package com.sr182022.travelagencystar.DAO.DestinationDAO;

import com.sr182022.travelagencystar.model.Destination;

import java.util.List;

public interface IDestinationDAO {
    List<Destination> Load();
    void Save(List<Destination> destinationsList);
}
