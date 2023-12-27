import {formatDate} from "./date.js";

export const getUserFromSession = async () => {
    try {
        const response = await fetch('/getUserFromSession');
        if (response.ok) {
            const user = await response.json();
            return user;
        } else {
            console.error('Error fetching user from session:', response.statusText);
            return null;
        }
    } catch (error) {
        console.error('Error fetching user from session:', error.message);
        return null;
    }
};

export const getFilterValues = () => {
    return {
        username : $('#filterDashboardUsersByUsername').val(),
        role : $('#filterDashboardUsersByRole').val()
    };
}

export const filterDashboardUser = (username, role, sortOrder)  => {
    $.ajax({
        url: '/filterDashboardUser',
        method: 'GET',
        data: { username: username, role: role, sortOrder: sortOrder },
        dataType: 'json',
        success: data => {
            updateUsersOnFilter(data);
        },
        error: error => {
            console.error('Fetching users error: ', error);
        }
    });
}

export const updateUsersOnFilter = users => {
    const dashboardUsers = $('#tableDashboardUserBody');
    dashboardUsers.empty();
    let i = 1;

    console.log(users);

    users.forEach( user => {
        dashboardUsers.append(`
        <tr class="border-b dark:border-neutral-500">
        <td class="whitespace-nowrap px-6 py-4 font-medium">${i}</td>
        <td class="whitespace-nowrap px-6 py-4">${user.name}</td>
        <td class="whitespace-nowrap px-6 py-4">${user.surname}</td>
        <td class="whitespace-nowrap px-6 py-4">${user.username}</td>
        <td class="whitespace-nowrap px-6 py-4">${formatDate(user.birthDate)}</td>
        <td class="whitespace-nowrap px-6 py-4">${user.role}</td>
        <td class="whitespace-nowrap px-6 py-4">
        <!-- get edit user form -->
            <form name="GET_editUserForm" action="/editUser" method="get">
                <input type="hidden" name="userId" value="${user.id}" />
                <button type="submit" class="deleteUserButton">
                    <svg class="feather feather-edit" fill="none" height="24" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg">
                        <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                        <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                    </svg>
                </button>
            </form>
        </td>
        <td class="whitespace-nowrap px-6 py-4">
        <!-- delete user form -->
            <form name="deleteUserForm" action="/deleteUser" method="post">
                <input type="hidden" name="userId" value="${user.id}" />
                <button type="submit" class="deleteUserButton">
                    <svg class="feather feather-trash-2" fill="none" height="24" stroke="red" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg">
                        <polyline points="3 6 5 6 21 6"/>
                        <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
                        <line x1="10" x2="10" y1="11" y2="17"/>
                        <line x1="14" x2="14" y1="11" y2="17"/>
                    </svg>
                </button>
            </form>
        </td>
        </tr>
        `)

        i+= 1;
    });
};


