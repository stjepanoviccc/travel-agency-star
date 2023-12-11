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