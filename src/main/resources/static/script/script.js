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
        const username = $('#filterDashboardUsersByUsername').val();
        const role = $('#filterDashboardUsersByRole').val();

        event.preventDefault();
        usersFunctions.filterDashboardUser(username, role);
    });

    $('#clearDashboardUserFilterButton').on('click', event => {
        const clearValues = "";
        $('#filterDashboardUsersByUsername').val(clearValues);
        $('#filterDashboardUsersByRole').prop('selectedIndex', 0);

        event.preventDefault();
        usersFunctions.filterDashboardUser(clearValues, clearValues);
    })

});
