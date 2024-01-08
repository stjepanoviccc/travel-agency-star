import * as navFunctions from "./nav.js";
import * as dashboardFunctions from "./dashboard.js";
import * as profileFunctions from "./profile.js";
import * as travelFunctions from "./travel.js";
import * as usersFunctions from "./users.js";
import * as loyaltyCardFunctions from "./loyalty-card.js";
import * as cartFunctions from "./cart.js";
import * as couponFunctions from "./coupon.js";
import * as reviewFunctions from "./reviews.js";

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

        travelFunctions.filterTravel(travelFunctions.getFilterValues().destination,  travelFunctions.getFilterValues().destinationSort,
            travelFunctions.getFilterValues().travelCategory, travelFunctions.getFilterValues().travelCategorySort,
            travelFunctions.getFilterValues().travelVehicleType, travelFunctions.getFilterValues().travelVehicleTypeSort,
            travelFunctions.getFilterValues().travelAccUnitType, travelFunctions.getFilterValues().travelAccUnitTypeSort,
            travelFunctions.getFilterValues().minPrice, travelFunctions.getFilterValues().maxPrice,  travelFunctions.getFilterValues().priceSort,
            travelFunctions.getFilterValues().startDate, travelFunctions.getFilterValues().endDate, travelFunctions.getFilterValues().dateSort,
            travelFunctions.getFilterValues().nightsFrom, travelFunctions.getFilterValues().nightsTo, travelFunctions.getFilterValues().nightsSort,
            travelFunctions.getFilterValues().passengersAvailability, travelFunctions.getFilterValues().sortTravelByPassengersAvailability,
            travelFunctions.getFilterValues().inputID, travelFunctions.getFilterValues().sortTravelByID);
    });

    $('#clearTravelFilterButton').on('click', event => {
        event.preventDefault();

        const clearValues = "";
        const defaultt = "Default";
        $('#filterTravelByDestination').val(clearValues);
        $('#sortTravelByDestination').prop('selectedIndex', 0);
        $('#filterTravelByTravelCategory').prop('selectedIndex', 0);
        $('#sortTravelByTravelCategory').prop('selectedIndex', 0);
        $('#travelVehicleTypeSelect').prop('selectedIndex', 0);
        $('#sortTravelByTravelVehicleType').prop('selectedIndex', 0);
        $('#travelAccUnitTypeSelect').prop('selectedIndex', 0);
        $('#sortTravelByTravelAccUnitType').prop('selectedIndex', 0);
        $('#filterTravelByMinPrice').val(clearValues);
        $('#filterTravelByMaxPrice').val(clearValues);
        $('#sortTravelByPrice').prop('selectedIndex', 0);
        $('#filterTravelByStartDate').val(clearValues);
        $('#filterTravelByEndDate').val(clearValues);
        $('#sortTravelByDate').prop('selectedIndex', 0);
        $('#filterTravelByNightsFrom').val(clearValues);
        $('#filterTravelByNightsTo').val(clearValues);
        $('#sortTravelByNights').prop('selectedIndex', 0);
        $('#filterTravelByPassengersAvailability').val(clearValues);
        $('#sortTravelByPassengersAvailability').prop('selectedIndex', 0);
        $('#filterTravelByID').val(clearValues);
        $('#sortTravelByID').prop('selectedIndex', 0);

        travelFunctions.filterTravel(clearValues, defaultt, clearValues, defaultt, clearValues, defaultt, clearValues, defaultt,
            clearValues, clearValues, defaultt ,clearValues, clearValues, defaultt, clearValues, clearValues, defaultt, clearValues, defaultt, clearValues, defaultt);
    })

    // travel for reports filtering
    $('#applyTravelForReportsFilterSubmitButton').on('click', event => {
        event.preventDefault();

        travelFunctions.filterTravelForReports(travelFunctions.getFilterValuesForTravelReservation().startDate, travelFunctions.getFilterValuesForTravelReservation().endDate,
            travelFunctions.getFilterValuesForTravelReservation().sortDate, travelFunctions.getFilterValuesForTravelReservation().sortTravelForReportsByDestination,
            travelFunctions.getFilterValuesForTravelReservation().sortTravelForReportsByVehicle, travelFunctions.getFilterValuesForTravelReservation().sortTravelForReportsByTotalSpace,
            travelFunctions.getFilterValuesForTravelReservation().sortTravelForReportsBySoldSpace, travelFunctions.getFilterValuesForTravelReservation().sortTravelForReportsByTotalPrice);
    });

    $('#clearTravelForReportsFilterButton').on('click', event => {
        event.preventDefault();

        const clearValues = "";
        const defaultt = "Default";
        $('#filterTravelForReportsByStartDate').val(clearValues);
        $('#filterTravelForReportsByEndDate').val(clearValues);
        $('#sortTravelForReportsByDate').prop('selectedIndex', 0);
        $('#sortTravelForReportsByDestination').prop('selectedIndex', 0);
        $('#sortTravelForReportsByVehicle').prop('selectedIndex', 0);
        $('#sortTravelForReportsByTotalSpace').prop('selectedIndex', 0);
        $('#sortTravelForReportsBySoldSpace').prop('selectedIndex', 0);
        $('#sortTravelForReportsByTotalPrice').prop('selectedIndex', 0);

        travelFunctions.filterTravelForReports(clearValues, clearValues, defaultt, defaultt, defaultt, defaultt, defaultt, defaultt);
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

    // loyalty card
    $("#askForLoyaltyCardButton").on('click', event => {
        event.preventDefault();
        loyaltyCardFunctions.askForLoyaltyCard(loyaltyCardFunctions.getFilterValues().userId);
    })

    $('#acceptLoyaltyCardButton').on('click', event => {
        event.preventDefault();
        const form = $(this).closest("form");
        const itemId = form.find('input[name="loyaltyCardId"]').val();
        loyaltyCardFunctions.acceptLoyaltyCard(itemId);
    })

    $('#declineLoyaltyCardButton').on('click', event => {
        event.preventDefault();
        const form = $(this).closest("form");
        const itemId = form.find('input[name="loyaltyCardId"]').val();
        loyaltyCardFunctions.declineLoyaltyCard(itemId);
    })

    // cart functionalities
    $('.btnUpdateCartJs').on('click', event => {
        event.preventDefault();
        const clickedButton = $(event.currentTarget);
        const minPassengers = 1;
        const maxPassengers = 1000;
        const form = clickedButton.closest('form');
        const cartItemId = form.find('input[name="cartItemId"]').val();
        let passengers = form.find('input[name="cartItemPassengers"]').val();
        passengers = Math.round(passengers);

        if(passengers < minPassengers || passengers > maxPassengers) {
          alert("Check your passengers num. Update failed");
          return;
        }

        cartFunctions.updateCart(cartItemId, passengers);
        setTimeout(() => {
            cartFunctions.updateTotalPrice();
        },100);
    })

    // select all checkboxes on coupon page
    $('#btnSelectAllCheckboxesForCoupon').on('click', () => {
        couponFunctions.selectAllCheckboxes();
    })

    // add coupon submitting
    $('#btnAddCouponJs').on('click', event => {
        event.preventDefault();
        const discount = couponFunctions.getFilterValues().discount;
        const startDate = couponFunctions.getFilterValues().startDate;
        const selectedCategories = couponFunctions.getFilterValues().selectedCategories;
        const selectedTravelIDs = couponFunctions.getFilterValues().selectedTravelIDs;

        if(discount == null || discount == "" || discount < 0 || discount > 100) {
            alert("Discount can't be under 0 or over 100 and also it must be selected.");
            return;
        }
        if(startDate == null || startDate == "") {
            alert("Start date must be selected");
            return;
        }
        if(selectedCategories == "" && selectedTravelIDs == "") {
            alert("You must select category(one or more) or travel(one or more).");
            return;
        }

        couponFunctions.addCoupon(discount, startDate, couponFunctions.getFilterValues().endDate, selectedCategories, selectedTravelIDs);
        couponFunctions.resetToDefaultValues();
    });

    // reviews filtering
    $("#reviewSubmitFilterBtn").on('click', event => {
        event.preventDefault();
        const accommodationUnitId = reviewFunctions.getFilterValues().accommodationUnitId;
        reviewFunctions.filterReview(accommodationUnitId);
    })
});
