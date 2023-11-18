import * as navFunctions from "./nav.js";
import * as dashboardFunctions from "./dashboard.js";

$(document).ready(function() {
    $('#menuButton').on('click', navFunctions.toggleMobileMenu);
    $('.loginToggleTrigger').on('click', navFunctions.toggleLoginModal);
    $('.registerToggleTrigger').on('click', navFunctions.toggleRegisterModal);
    $('.editUserButton').on('click', function() {
        dashboardFunctions.openEditUserModal($(this));
    });
    $('.closeEditUserModal').on('click', dashboardFunctions.closeEditUserModal)
});
