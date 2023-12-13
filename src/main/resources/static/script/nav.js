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

export const updateImageValue = input => {
    const selectedFile = input.files[0];
    const previewImage = document.getElementById('previewImage');
    const destinationImageInput = document.getElementById('editDestinationImage');

    if (selectedFile) {
        // Display the selected image in the preview
        var reader = new FileReader();
        reader.onload = function (e) {
            previewImage.src = e.target.result;
        };
        reader.readAsDataURL(selectedFile);

        // Update the input field value with the selected file name
        destinationImageInput.value = selectedFile.name;
    } else {
        // If no file is selected, reset the preview and input value
        previewImage.src = "@{/images/}";
        destinationImageInput.value = "";
    }
}