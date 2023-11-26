export function toggleMobileMenu() {
    $('#mobileMenu').toggleClass('hidden');
}

export function toggleLoginModal() {
    $('#loginModal').toggleClass('hidden');
}

export function toggleRegisterModal() {
    $('#registerModal').toggleClass('hidden');
}

export function toggleLangDropdown() {
    $("#langDropdown").toggleClass("hidden");
}

export function hideLangDropdownOnClickOutside(event) {
    if (!$(event.target).closest("#langDropdownButton, #langDropdown").length) {
        $("#langDropdown").addClass("hidden");
    }
}