export function deleteUserHandler(event) {
    event.preventDefault();

    const form = $(event.target);
    const { method, action } = form[0];

    $.ajax({
        type: method,
        url: action,
        data: form.serialize(),
        success: () => {
            form.closest('tr').remove();
        },
        error: error => console.error('Error deleting user:', error)
    });
}

export function openEditUserModal(button) {
    const editUserId = button.data('user-id');
    const editUserUsername = button.data('user-username');
    const editUserPassword = button.data('user-password');
    const editUserEmail = button.data('user-email');
    const editUserSurname = button.data('user-surname');
    const editUserName = button.data('user-name');
    const editUserAddress = button.data('user-address');
    const editUserPhone = button.data('user-phone');
    const editUserBirthDate = button.data('user-birthdate');

    $('#editUserId').val(editUserId);
    $('#editUserUsername').val(editUserUsername);
    $('#editUserPassword').val(editUserPassword);
    $('#editUserEmail').val(editUserEmail);
    $('#editUserSurname').val(editUserSurname);
    $('#editUserName').val(editUserName);
    $('#editUserAddress').val(editUserAddress);
    $('#editUserPhone').val(editUserPhone);
    $('#editUserBirthDate').val(editUserBirthDate);

    $('#editUserModal').removeClass('opacity-0 pointer-events-none');
}

export function closeEditUserModal() {
    $('#editUserModal').addClass('opacity-0 pointer-events-none');
}