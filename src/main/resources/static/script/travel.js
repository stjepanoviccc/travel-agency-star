import {formatDate} from "./date.js";
import {getUserFromSession} from "./users.js";

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

export const updateTravelsOnFilter = async travels => {
    const indexTravels = $('#indexTravels');

    indexTravels.empty();
    let i = 1;

    const session = {
        user: await getUserFromSession()
    }

    const textNotChosenYet = /*[[${#messages.msg('textNotChosenYet') || 'fallback'}]]*/ 'error';
    const textAccommodationUnit = /*[[${#messages.msg('textAccommodationUnit') || 'fallback'}]]*/ 'error';
    const textVehicle = /*[[${#messages.msg('textVehicle') || 'fallback'}]]*/ 'error';
    const textCategory = /*[[${#messages.msg('textCategory') || 'fallback'}]]*/ 'error';
    const textPrice = /*[[${#messages.msg('textPrice') || 'fallback'}]]*/ 'error';
    const textAddToWishlist = /*[[${#messages.msg('textAddToWishlist') || 'fallback'}]]*/ 'error';

    travels.forEach(travel => {
        const travelElement = document.createElement('div');
        travelElement.classList.add('w-full', 'rounded', 'overflow-hidden', 'shadow-lg', 'text-center', 'border-2', 'transform', 'transition-transform', 'duration-300', 'hover:scale-105');

        travelElement.innerHTML = (`
    <div class="w-full rounded overflow-hidden shadow-lg text-center border-2 transform transition-transform duration-300 hover:scale-105">
        <!-- this will probably be form and link will be redirected to exact travel page with details. -->
        <a href="'/travel?id=' + ${travel.id}">
            <img class="h-64 w-full" src="/images/${travel.destination.image}" alt="travel-img">
        </a>
        <div class="px-6 py-4">
            <a href="'/travel?id=' + ${travel.id}" class="font-bold text-xl">${travel.destination.city}</a>

            ${travel.accommodationUnit != null
            ? `<p class="text-gray-700 text-base pt-2">${textAccommodationUnit}: ${travel.accommodationUnit.accommodationType}</p>`
            : `<p class="text-gray-700 text-base pt-2">${textAccommodationUnit}: ${textNotChosenYet}</p>`
        }

            ${travel.vehicle != null
            ? `<p class="text-gray-700 text-base" >${textVehicle}: ${travel.vehicle.vehicleType}</p>`
            : `<p class="text-gray-700 text-base" >${textVehicle}: ${textNotChosenYet}</p>`
        }

            <p class="text-gray-700 text-base">${textCategory}: ${travel.travelCategory}</p>
            <p class="text-gray-700 text-xl mt-1 font-bold">${formatDate(travel.startDate, 'dd.MM.yyyy')} ---> ${formatDate(travel.endDate, 'dd.MM.yyyy')}</p>
        </div>
        <div class="px-6 py-2 flex justify-center items-center">
            <span class="inline-block bg-gray-200 rounded-full px-4 py-2 font-semibold text-gray-700 mr-2 mb-2 border-2">${textPrice}: ${travel.price}$</span>
            <form action="/profile/wishlist/addToWishlist" method="post" name="addToWishlist">
                <div class="h-[0px]">
                    <input class="hidden" type="hidden" name="idUser" value="${session.user != null ? session.user.id : '-1'}" readonly />
                </div>
                <input type="hidden" name="idTravel" value="${travel.id}" readonly />

                ${session.user != null && session.user.role === 'Passenger'
            ? `<button type="submit" class="bg-red-600 rounded-full px-4 py-2 text-white font-semibold mr-2 mb-2 border-2 border-red-600
                        transition duration-300 hover:text-red-600 hover:bg-white">a</button>`
            : ''
        }
            </form>
        </div>
    </div>
    `);

        indexTravels.append(travelElement);
        i += 1;
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