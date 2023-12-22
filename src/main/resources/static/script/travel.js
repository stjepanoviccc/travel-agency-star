export const filterUnits = (destinationId, type)  => {
    $.ajax({
        url: '/dashboard/travels/filterAccommodationUnitsByDestination',
        method: 'GET',
        data: { destinationId: destinationId },
        dataType: 'json',
        success: data => {
            if(type == 'add') {
                updateAccUnitsSelectOnAdd(data);
            } else {
                updateAccUnitsSelectOnEdit(data);
            }
        },
        error: error => {
            console.error('Fetching vehicles error: ', error);
        }
    });
}

export const filterVehicles = (destinationId, type)  => {
    $.ajax({
        url: '/dashboard/travels/filterVehiclesByDestination',
        method: 'GET',
        data: { destinationId: destinationId },
        dataType: 'json',
        success: data => {
            if(type == 'add') {
                updateVehiclesSelectOnAdd(data);
            } else {
                updateVehiclesSelectOnEdit(data);
            }
        },
        error: error => {
            console.error('Fetching vehicles error: ', error);
        }
    });
}

export const updateAccUnitsSelectOnAdd = accUnits => {
    const accUnitsSelect = $('#addNewTravelAccommodationUnit');
    accUnitsSelect.empty();
    accUnits.forEach( unit => {
        accUnitsSelect.append('<option value="' + unit.id + '">' + unit.name + '</option>');
    });
}

export const updateAccUnitsSelectOnEdit = accUnits => {
    const accUnitsSelect = $('#editTravelAccommodationUnit');
    accUnitsSelect.empty();
    accUnits.forEach( unit => {
        accUnitsSelect.append('<option value="' + unit.id + '">' + unit.name + '</option>');
    });
}

export const updateVehiclesSelectOnAdd = vehicles => {
    const vehiclesSelect = $('#addNewTravelVehicle');
    vehiclesSelect.empty();
    vehicles.forEach( vehicle => {
        vehiclesSelect.append('<option value="' + vehicle.id + '">' + vehicle.vehicleType + ' - ' + vehicle.description + '</option>');
    });
}

export const updateVehiclesSelectOnEdit = vehicles => {
    const vehiclesSelect = $('#editTravelVehicle');
    vehiclesSelect.empty();
    vehicles.forEach(vehicle => {
        vehiclesSelect.append('<option value="' + vehicle.id + '">' + vehicle.vehicleType + ' - ' + vehicle.description + '</option>');
    });
}