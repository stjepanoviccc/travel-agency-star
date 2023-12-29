import * as navFunctions from "./nav.js";
import * as dashboardFunctions from "./dashboard.js";
import * as profileFunctions from "./profile.js";
import * as travelFunctions from "./travel.js";
import * as usersFunctions from "./users.js";

$(document).ready(() => {

    // navbar
    $('#menuButton').on('click', navFunctions.toggleMobileMenu);
    $('.loginToggleTrigger').on('click', navFunctions.toggleLoginModal);
    $('.registerToggleTrigger').on('click', navFunctions.toggleRegisterModal);
    $("#langDropdownButton").on('click', navFunctions.toggleLangDropdown);
    $(document).on("click", navFunctions.hideLangDropdownOnClickOutside);

    // dashboard
    dashboardFunctions.linksInDashboardPageActiveState();
    $('.addNewDestinationToggleTrigger').on('click', dashboardFunctions.toggleAddNewDestinationModal);
    $('.addNewVehicleToggleTrigger').on('click', dashboardFunctions.toggleAddNewVehicleModal);
    $('.addNewAccommodationUnitToggleTrigger').on('click', dashboardFunctions.toggleAddNewAccommodationUnitModal);
    $('.addNewTravelToggleTrigger').on('click', dashboardFunctions.toggleAddNewTravelModal);

    // profile
    profileFunctions.linksInProfilePageActiveState();

    // travel filtering
    $('#applyTravelFilterSubmitButton').on('click', event => {
        event.preventDefault();

        travelFunctions.filterTravel(travelFunctions.getFilterValues().destination, travelFunctions.getFilterValues().travelCategory,
            travelFunctions.getFilterValues().travelVehicleType, travelFunctions.getFilterValues().travelAccUnitType, travelFunctions.getFilterValues().minPrice,
            travelFunctions.getFilterValues().maxPrice, travelFunctions.getFilterValues().startDate, travelFunctions.getFilterValues().endDate);
    });

    $('#sortTravelButton').on('click', event => {
        event.preventDefault();

        let sortOrder = $('#travelSortOrder').val();
        if(sortOrder == "asc") {
            sortOrder="desc";
        } else {
            sortOrder = "asc";
        }
        $('#travelSortOrder').val(sortOrder);

        travelFunctions.filterTravel(travelFunctions.getFilterValues().destination, travelFunctions.getFilterValues().travelCategory,
            travelFunctions.getFilterValues().travelVehicleType, travelFunctions.getFilterValues().travelAccUnitType, travelFunctions.getFilterValues().minPrice,
            travelFunctions.getFilterValues().maxPrice, travelFunctions.getFilterValues().startDate, travelFunctions.getFilterValues().endDate);
    });

    $('#clearTravelFilterButton').on('click', event => {
        event.preventDefault();

        const clearValues = "";
        $('#filterTravelByDestination').val(clearValues);
        $('#filterTravelByTravelCategory').prop('selectedIndex', 0);
        $('#travelVehicleTypeSelect').prop('selectedIndex', 0);
        $('#travelAccUnitTypeSelect').prop('selectedIndex', 0);
        $('#filterTravelByMinPrice').val(clearValues);
        $('#filterTravelByMaxPrice').val(clearValues);
        $('#filterTravelByStartDate').val(clearValues);
        $('#filterTravelByEndDate').val(clearValues);

        travelFunctions.filterTravel(clearValues, clearValues, clearValues, clearValues, clearValues, clearValues ,clearValues, clearValues, "asc");
    })

    // vehicles and accc units filtering
    $('#editTravelDestination').on('change', () => {
        setTimeout(() => {
            const id = $('#editTravelDestination').val();
            travelFunctions.filterVehicles(id, 'edit');
            travelFunctions.filterUnits(id, 'edit');
        }, 50)
    });

    $('#addNewTravelDestination').on('change', () => {
        setTimeout(() => {
            const id = $('#addNewTravelDestination').val();
            travelFunctions.filterVehicles(id, 'add');
            travelFunctions.filterUnits(id, 'add');
        }, 150);
    })

    // users filtering
    $('#applyDashboardUserFilterSubmitButton').on('click', event => {
        event.preventDefault();

        const sortOrder = $('#userDashboardSortOrder').val();
        usersFunctions.filterDashboardUser(usersFunctions.getFilterValues().username, usersFunctions.getFilterValues().usernameSort,
            usersFunctions.getFilterValues().role, usersFunctions.getFilterValues().roleSort);
    });

    $('#clearDashboardUserFilterButton').on('click', event => {
        event.preventDefault();

        const clearValues = "";
        $('#filterDashboardUsersByUsername').val(clearValues);
        $('#sortDashboardUsersByUsername').prop('selectedIndex', 0);
        $('#filterDashboardUsersByRole').prop('selectedIndex', 0);
        $('#sortDashboardUsersByRole').prop('selectedIndex', 0);
        let clearing = true;
        usersFunctions.filterDashboardUser(clearValues, "asc", clearValues, "asc", clearing);
    })

});
