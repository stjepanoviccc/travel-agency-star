// filtering on index page.
export const getFilterValues = () => {
    return {
        destination: $('#filterTravelByDestination').val(),
        travelCategory: $('#filterTravelByTravelCategory').val(),
        travelVehicleType: $('#travelVehicleTypeSelect').val(),
        travelAccUnitType: $('#travelAccUnitTypeSelect').val(),
        minPrice: $('#filterTravelByMinPrice').val(),
        maxPrice: $('#filterTravelByMaxPrice').val(),
        startDate: $('#filterTravelByStartDate').val(),
        endDate: $('#filterTravelByEndDate').val(),
    };
}

export const filterTravel = (destination, category, minPrice, maxPrice, vehicleType, accUnitType, startDate, endDate)  => {
    $.ajax({
        url: '/travel/filterTravel',
        method: 'GET',
        data: { destination: destination, category: category, minPrice: minPrice, maxPrice: maxPrice, vehicleType: vehicleType, accUnitType: accUnitType,
                startDate: startDate, endDate: endDate},
        dataType: 'json',
        success: data => {
            updateTravelsOnFilter(data);
        },
        error: error => {
            console.error('Fetching travels error: ', error);
        }
    });
}

export const updateTravelsOnFilter = travels => {
    const indexTravels = $('#indexTravels');
    indexTravels.empty();
    let i = 1;

    console.log(travels);


    travels.forEach( travel => {
        const travelElement = document.createElement('div');
        travelElement.classList.add('w-full', 'rounded', 'overflow-hidden', 'shadow-lg', 'text-center', 'border-2', 'transform', 'transition-transform', 'duration-300', 'hover:scale-105');

        travelElement.innerHTML = (`
        <div class="w-full rounded overflow-hidden shadow-lg text-center border-2 transform transition-transform duration-300 hover:scale-105">
            <!-- this will probably be form and link will be redirected to exact travel page with details. -->
            <a href="'/travel?id=' + ${travel.id}">
                <img class="h-64 w-full" src="/images/}+${travel.destination.image}" alt="travel-img">
            </a>
            <div class="px-6 py-4">
                <a href="'/travel?id=' + ${travel.id}" class="font-bold text-xl">${travel.destination.city}</a>
                <p class="text-gray-700 text-base pt-2" th:if="${travel.accommodationUnit != null}"
                   th:text="#{textAccommodationUnit} + ': ' + ${travel.accommodationUnit.accommodationType}"></p>
                <p class="text-gray-700 text-base pt-2" th:if="${travel.accommodationUnit == null}"
                   th:text="#{textAccommodationUnit} + ': ' + #{textNotChosenYet}"></p>
                <p class="text-gray-">
                <p class="text-gray-700 text-base" th:if="${travel.vehicle != null}" th:text="#{textVehicle} + ': ' + ${travel.vehicle.vehicleType}"></p>
                <p class="text-gray-700 text-base" th:if="${travel.vehicle == null}" th:text="#{textVehicle} + ': ' + #{textNotChosenYet}"></p>
                <p class="text-gray-700 text-base" th:text="#{textCategory} + ': ' + ${travel.travelCategory}"></p>
                <p class="text-gray-700 text-xl mt-1 font-bold" th:text="${#temporals.format(travel.startDate, 'dd.MM.yyyy')} + ' ---> '
                 + ${#temporals.format(travel.endDate, 'dd.MM.yyyy')}"></p>
            </div>
            <div class="px-6 py-2 flex justify-center items-center">
                <span class="inline-block bg-gray-200 rounded-full px-4 py-2 font-semibold text-gray-700 mr-2 mb-2 border-2"
                      th:text="#{textPrice} + ': ' + ${travel.price} + '$'">
                <form action="/profile/wishlist/addToWishlist" method="post" name="addToWishlist">
                    <!-- i will need to fix this, will handle this somehow else.(about session user id) -->
                    <div class="h-[0px]" th:if="${session.user != null}">
                        <input class="hidden" type="hidden" name="idUser" th:value="${session.user.id}" readonly />
                    </div>
                    <div class="h-[0px]" th:if="${session.user == null}">
                        <input class="hidden" type="hidden" name="idUser" th:value="'-1'" readonly />;
                    </div>
                    <input type="hidden" name="idTravel" th:value="${travel.id}" readonly />
                    <!-- pitati za ovo -->
                    <button th:if="${session.user != null and session.user.role.name() == 'Passenger'}" type="submit"
                            class="bg-red-600 rounded-full px-4 py-2 text-white font-semibold mr-2 mb-2 border-2 border-red-600
                        transition duration-300 hover:text-red-600 hover:bg-white" th:text="#{travelPageAddToWishlist}"></button>           
            </div>
        </div>
        `);

        indexTravels.appendChild(travelElement);
        i+= 1;
    });
};


// filtering for edit travel.
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