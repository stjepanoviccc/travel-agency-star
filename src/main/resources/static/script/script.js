import * as navFunctions from "./nav.js";
import * as dashboardFunctions from "./dashboard.js";

$(document).ready(() => {

    // navbar
    $('#menuButton').on('click', navFunctions.toggleMobileMenu);
    $('.loginToggleTrigger').on('click', navFunctions.toggleLoginModal);
    $('.registerToggleTrigger').on('click', navFunctions.toggleRegisterModal);
    $("#langDropdownButton").on('click', navFunctions.toggleLangDropdown);
    $(document).on("click", navFunctions.hideLangDropdownOnClickOutside);
    $('.messageDisplayToggleTrigger').on('click', navFunctions.toggleMessageDisplayModal);

    // dashboard
    dashboardFunctions.linksInDashboardPageActiveState();
    $('.addNewDestinationToggleTrigger').on('click', dashboardFunctions.toggleAddNewDestinationModal);
    $('.addNewVehicleToggleTrigger').on('click', dashboardFunctions.toggleAddNewVehicleModal);
    $('.addNewAccommodationUnitToggleTrigger').on('click', dashboardFunctions.toggleAddNewAccommodationUnitModal);

});
