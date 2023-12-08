package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IAccommodationUnitDAO;
import com.sr182022.travelagencystar.model.AccommodationType;
import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.service.impl.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseAccommodationUnitDAO implements IAccommodationUnitDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final DestinationService destinationService;

    public DatabaseAccommodationUnitDAO(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    private class AccommodationUnitRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, AccommodationUnit> accommodationUnits = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_accommodation_unit = resultSet.getInt(index++);
            String accommodation_unit_name = resultSet.getString(index++);
            int accommodation_unit_capacity = resultSet.getInt(index++);
            String accommodation_type = resultSet.getString(index++);
            AccommodationType accommodationType = AccommodationType.valueOf(accommodation_type);
            String accommodation_unit_description = resultSet.getString(index++);
            boolean wifi = resultSet.getBoolean(index++);
            boolean bathroom = resultSet.getBoolean(index++);
            boolean tv = resultSet.getBoolean(index++);
            boolean conditioner = resultSet.getBoolean(index++);
            int id_destination = resultSet.getInt(index++);
            Destination destination = destinationService.findOne(id_destination);

            AccommodationUnit accommodationUnit = accommodationUnits.get(id_accommodation_unit);
            if (accommodationUnit == null) {
                accommodationUnit = new AccommodationUnit(id_accommodation_unit, accommodation_unit_name, accommodation_unit_capacity, accommodation_unit_description,
                        accommodationType, wifi, bathroom, tv, conditioner, destination);
                accommodationUnits.put(accommodationUnit.getId(), accommodationUnit);
            }
        }

        public List<AccommodationUnit> getAccommodationUnits() {
            return new ArrayList<>(accommodationUnits.values());
        }
    }

    @Override
    public List<AccommodationUnit> findAll() {
        return null;
    }

    @Override
    public List<String> findAllAccommodationTypes() {
        return null;
    }

    @Override
    public AccommodationUnit findOne(int accommodationUnitId) {
        return null;
    }

    @Override
    public void save(AccommodationUnit newAccommodationUnit, int destinationId) {

    }

    @Override
    public void update(AccommodationUnit editAccommodationUnit, int destinationId) {

    }

    @Override
    public void delete(int accommodationUnitId) {

    }
}
