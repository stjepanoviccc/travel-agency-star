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

        const username = $('#filterDashboardUsersByUsername').val();
        const role = $('#filterDashboardUsersByRole').val();
        const sortOrder = $('#userDashboardSortOrder').val();

        usersFunctions.filterDashboardUser(username, role, sortOrder);
    });

    $('#sortDashboardUserButton').on('click', event => {
        event.preventDefault();

        const username = $('#filterDashboardUsersByUsername').val();
        const role = $('#filterDashboardUsersByRole').val();
        let sortOrder = $('#userDashboardSortOrder').val();

        if(sortOrder == "asc") {
            sortOrder="desc";
        } else {
            sortOrder = "asc";
        }

        $('#userDashboardSortOrder').val(sortOrder);
        usersFunctions.filterDashboardUser(username, role, sortOrder);
    });

    $('#clearDashboardUserFilterButton').on('click', event => {
        event.preventDefault();

        const clearValues = "";
        $('#filterDashboardUsersByUsername').val(clearValues);
        $('#filterDashboardUsersByRole').prop('selectedIndex', 0);

        usersFunctions.filterDashboardUser(clearValues, clearValues, "asc");
    })

});
