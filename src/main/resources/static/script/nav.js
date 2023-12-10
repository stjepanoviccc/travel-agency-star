export const toggleMobileMenu = () => {
    $('#mobileMenu').toggleClass('hidden');
}

export const toggleLoginModal = () => {
    $('#loginModal').toggleClass('hidden');
}

export const toggleRegisterModal = () => {
    $('#registerModal').toggleClass('hidden');
}

export const toggleLangDropdown = () => {
    $("#langDropdown").toggleClass("hidden");
}

export const hideLangDropdownOnClickOutside = (event) => {
    if (!$(event.target).closest("#langDropdownButton, #langDropdown").length) {
        $("#langDropdown").addClass("hidden");
    }
}

// universal modal for all kind of error display messages and other information display messages.
export const toggleMessageDisplayModal = () => {
    $('#messageDisplayModal').toggleClass('hidden');
}

// LOGIN HANDLING BECAUSE OF MODAL.
// handling some more UI based forms which i couldn't do in pure controller because of redirection.

$(document).ready(function() {
    $("#loginForm").submit(function(e) {
        e.preventDefault();

        $.ajax({
            type: $(this).attr("method"),
            url: $(this).attr("action"),
            data: $(this).serialize(),
            success: response => {
                if (response.status === "success") {
                    toggleMessageDisplayModal(response.message);
                } else {
                    toggleMessageDisplayModal(response.message);
                }
            },
            error: function(error) {
                console.log(error);
            }
        });
    });
});