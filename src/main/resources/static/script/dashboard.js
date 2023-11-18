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
    const userId = button.data('user-id');
    $('#editUserModal').removeClass('opacity-0 pointer-events-none');
}

export function closeEditUserModal() {
    $('#editUserModal').addClass('opacity-0 pointer-events-none');
}