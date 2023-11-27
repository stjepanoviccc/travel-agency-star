export const toggleAddNewDestinationModal = () => {
    $('#addNewDestinationModal').toggleClass('hidden');
}

export const toggleAddNewVehicleModal = () => {
    $('#addNewVehicleModal').toggleClass('hidden');
}

// this function is used for styling only
export const linksInDashboardPageActiveState = () => {
    const url = window.location.pathname;

    switch(url) {
        case "/dashboard":
            $("#linkToUsersFromDashboard").addClass('bg-gray-600 text-white');
            break;
        case "/dashboard/destinations":
            $("#linkToDestinationsFromDashboard").addClass('bg-gray-600 text-white');
            break;
        case "/dashboard/accommodation-units":
            $("#linkToAccommodationFromDashboard").addClass('bg-gray-600 text-white');
            break;
        case "/dashboard/vehicles":
            $("#linkToVehiclesFromDashboard").addClass('bg-gray-600 text-white');
            break;
        default:
            break;
    }
}