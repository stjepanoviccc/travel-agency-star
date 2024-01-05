import {formatDate} from "./date.js";
import {getUserFromSession} from "./users.js";
import {getMessagesProperties} from "./messages.js";

// filtering on index page.
export const getFilterValues = () => {
    return {
        destination: $('#filterTravelByDestination').val(),
        destinationSort: $('#sortTravelByDestination').val(),
        travelCategory: $('#filterTravelByTravelCategory').val(),
        travelCategorySort: $('#sortTravelByTravelCategory').val(),
        travelVehicleType: $('#filterTravelByVehicleType').val(),
        travelVehicleTypeSort: $('#sortTravelByTravelVehicleType').val(),
        travelAccUnitType: $('#filterTravelByAccUnitType').val(),
        travelAccUnitTypeSort: $('#sortTravelByTravelAccUnitType').val(),
        minPrice: $('#filterTravelByMinPrice').val(),
        maxPrice: $('#filterTravelByMaxPrice').val(),
        priceSort: $('#sortTravelByPrice').val(),
        startDate: $('#filterTravelByStartDate').val(),
        endDate: $('#filterTravelByEndDate').val(),
        dateSort: $('#sortTravelByDate').val(),
        nightsFrom: $('#filterTravelByNightsFrom').val(),
        nightsTo: $('#filterTravelByNightsTo').val(),
        nightsSort: $('#sortTravelByNights').val(),
        passengersAvailability: $('#filterTravelByPassengersAvailability').val(),
        sortTravelByPassengersAvailability: $('#sortTravelByPassengersAvailability').val(),
        inputID: $('#filterTravelByID').val(),
        sortTravelByID: $('#sortTravelByID').val()
    };
}

export const getFilterValuesForTravelReservation = () => {
  return {
      startDate: $('#filterTravelForReportsByStartDate').val(),
      endDate: $('#filterTravelForReportsByEndDate').val(),
      sortDate: $('#sortTravelForReportsByDate').val(),
      sortTravelForReportsByDestination: $('#sortTravelForReportsByDestination').val(),
      sortTravelForReportsByVehicle: $('#sortTravelForReportsByVehicle').val(),
      sortTravelForReportsByTotalSpace: $('#sortTravelForReportsByTotalSpace').val(),
      sortTravelForReportsBySoldSpace: $('#sortTravelForReportsBySoldSpace').val(),
      sortTravelForReportsByTotalPrice: $('#sortTravelForReportsByTotalPrice').val()
  }
};

export const filterTravel = (destination, destinationSort, category, categorySort, vehicleType, vehicleTypeSort, accUnitType, accUnitTypeSort, minPrice, maxPrice,
                             priceSort, startDate, endDate, dateSort, nightsFrom, nightsTo, nightsSort, passengersAvailability, sortTravelByPassengersAvailability,
                             inputID, sortTravelByID)  => {

    $.ajax({
        url: '/travel/filterTravel',
        method: 'GET',
        data: { destination: destination, destinationSort: destinationSort, travelCategory: category, travelCategorySort: categorySort, travelVehicleType: vehicleType,
            travelVehicleTypeSort: vehicleTypeSort, travelAccUnitType: accUnitType, travelAccUnitTypeSort: accUnitTypeSort, minPrice: minPrice, maxPrice: maxPrice,
            priceSort: priceSort, startDate: startDate, endDate: endDate, dateSort: dateSort, nightsFrom:nightsFrom, nightsTo:nightsTo, nightsSort:nightsSort,
        passengersAvailability:passengersAvailability, sortTravelByPassengersAvailability:sortTravelByPassengersAvailability, inputID:inputID, sortTravelByID:sortTravelByID},
        dataType: 'json',
        success: data => {
            updateTravelsOnFilter(data);
        },
        error: error => {
            console.error('Fetching travels error: ', error);
        }
    });
}

export const filterTravelForReports = (startDate, endDate, sortDate, sortTravelForReportsByDestination, sortTravelForReportsByVehicle, sortTravelForReportsByTotalSpace,
                                        sortTravelForReportsBySoldSpace, sortTravelForReportsByTotalPrice) => {

    $.ajax({
        url: '/report/filterTravelReportItem',
        method: 'GET',
        data: { startDate: startDate, endDate: endDate, sortDate: sortDate, sortTravelForReportsByDestination: sortTravelForReportsByDestination,
            sortTravelForReportsByVehicle:sortTravelForReportsByVehicle, sortTravelForReportsByTotalSpace:sortTravelForReportsByTotalSpace,
            sortTravelForReportsBySoldSpace:sortTravelForReportsBySoldSpace, sortTravelForReportsByTotalPrice:sortTravelForReportsByTotalPrice},
        dataType: 'json',
        success: data => {
            updateTravelsForReportOnFilter(data);
        },
        error: error => {
            console.error('Fetching travels for report error: ', error);
        }
    });
}

export const updateTravelsOnFilter = async travels => {
    const indexTravels = $('#indexTravels');

    indexTravels.empty();

    const session = {
        user: await getUserFromSession()
    }

    const messages = {
        msg: await getMessagesProperties()
    }

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
            ? `<p class="text-gray-700 text-base pt-2">${messages.msg.textAccommodationUnit}: ${travel.accommodationUnit.accommodationType}</p>`
            : `<p class="text-gray-700 text-base pt-2">${messages.msg.textAccommodationUnit}: ${messages.msg.textNotChosenYet}</p>`
        }

            ${travel.vehicle != null
            ? `<p class="text-gray-700 text-base" >${messages.msg.textVehicle}: ${travel.vehicle.vehicleType}</p>`
            : `<p class="text-gray-700 text-base" >${messages.msg.textVehicle}: ${messages.msg.textNotChosenYet}</p>`
        }

            <p class="text-gray-700 text-base">${messages.msg.textCategory}: ${travel.travelCategory}</p>
            <p class="text-gray-700 text-xl mt-1 font-bold">${formatDate(travel.startDate, 'dd.MM.yyyy')} ---> ${formatDate(travel.endDate, 'dd.MM.yyyy')}</p>
        </div>
        <div class="px-6 py-2 flex justify-center items-center">
            <span class="inline-block bg-gray-200 rounded-full px-4 py-2 font-semibold text-gray-700 mr-2 mb-2 border-2">${messages.msg.textPrice}: ${travel.price}$</span>
            <form action="/profile/wishlist/addToWishlist" method="post" name="addToWishlist">
                <div class="h-[0px]">
                    <input class="hidden" type="hidden" name="idUser" value="${session.user != null ? session.user.id : '-1'}" readonly />
                </div>
                <input type="hidden" name="idTravel" value="${travel.id}" readonly />

                ${session.user != null && session.user.role === 'Passenger'
            ? `<button type="submit" class="bg-red-600 rounded-full px-4 py-2 text-white font-semibold mr-2 mb-2 border-2 border-red-600
                        transition duration-300 hover:text-red-600 hover:bg-white">${messages.msg.textAddToWishlist}</button>`
            : ''
        }
            </form>
        </div>
    </div>
    `);

        indexTravels.append(travelElement);
    });

    if(travels.length == 0) {
        indexTravels.append("<p>No content to show. Try filter something else.</p>")
    }
};

export const updateTravelsForReportOnFilter = async trs => {
    let index = 1;
    let totalPriceForAll = 0;
    let totalSold = 0;
    const reportTravels = $('#reportTravels');
    reportTravels.empty();

    trs.forEach(tr => {
        let totalPrice = tr.soldSpace * tr.travel.price;
        totalPriceForAll += totalPrice;
        totalSold += tr.soldSpace;
        const trElement = document.createElement("tr");
        trElement.classList.add('border-b', 'dark:border-neutral-500');
        trElement.innerHTML =
            `
                <td class="whitespace-nowrap px-6 py-4 font-medium">${index}</td>
                <td class="whitespace-nowrap px-6 py-4">${tr.travel.destination.city}</td>
                <td class="whitespace-nowrap px-6 py-4">${tr.travel.vehicle.description}</td>
                <td class="whitespace-nowrap px-6 py-4">${tr.totalSpace}</td>
                <td class="whitespace-nowrap px-6 py-4">${tr.soldSpace}</td>
                <td class="whitespace-nowrap px-6 py-4">${totalPrice + " din"}</td>
            `;

        index++;
        reportTravels.append(trElement);
    })

    if(trs.length > 0) {
        let totalTr = document.createElement("tr");
        totalTr.innerHTML = `
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td class="whitespace-nowrap px-6 py-4 text-xl text-green-600 font-bold">${totalSold}</td>
            <td class="whitespace-nowrap px-6 py-4 text-xl text-green-600 font-bold">${totalPriceForAll}</td>
        `
        reportTravels.append(totalTr);
    } else {
        reportTravels.append("<p>No content to show. Try filter something else.</p>");
    }
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