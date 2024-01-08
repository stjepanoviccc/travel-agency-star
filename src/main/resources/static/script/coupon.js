export const getFilterValues = () => {
    return {
        discount: $('#addCouponDiscount').val(),
        startDate: $('#addCouponStartDate').val(),
        endDate: $('#addCouponEndDate').val(),
        selectedCategories: getCheckboxValues("travelCategories"),
        selectedTravelIDs: getCheckboxValues("travels")
    };
}

export const addCoupon = (discount, startDate, endDate, selectedCategories, selectedTravelIDs) => {
    if (selectedCategories.length > 0) {
        selectedCategories = selectedCategories.join(",");
    }
    if (selectedTravelIDs.length > 0) {
        selectedTravelIDs = selectedTravelIDs.join(",");
    }

    $.ajax({
        url: '/coupon/addCoupon',
        method: 'POST',
        data: { discount: discount, startDate: startDate, endDate: endDate, selectedCategories: selectedCategories, selectedTravelIDs: selectedTravelIDs},
        dataType: 'text',
        success: data => {
            alert("Coupon added successfully!");
            updateUI();
        },
        error: error => {
            alert("Error adding coupon.");
        }
    });
};

export const resetToDefaultValues = () => {
    $('input[name="travels"]').prop('checked', false);
    $('input[name="travelCategories"]').prop('checked', false);
    $('#addCouponDiscount').val("");
    $('#addCouponStartDate').val("");
    $('#addCouponEndDate').val("");
}

export const selectAllCheckboxes = () => {
    $('input[name="travels"]').prop('checked', true);
}

// helper functions
const getCheckboxValues = checkboxName => {
    const checkboxList = document.getElementsByName(checkboxName);
    const selectedValues = [];

    for (var i = 0; i < checkboxList.length; i++) {
        if (checkboxList[i].checked) {
            selectedValues.push(checkboxList[i].value);
        }
    }

    return selectedValues;
}

const updateUI = () => {
    $.ajax({
        url: '/coupon/updateUI',
        method: 'get',
        dataType: 'json',
        success: cps => {
            // Clear the existing content
            let couponContainer = $("#couponContainerJs");
            couponContainer.empty();

            cps.forEach((coupon, index) => {
                // Create a new table row (tr) element
                const tr = document.createElement("tr");
                tr.classList.add("border-b", "dark:border-neutral-500");

                // Create and append the cells (td) one by one
                tr.appendChild(createTableCell(index + 1, "whitespace-nowrap px-6 py-4 font-medium"));
                tr.appendChild(createTableCell(coupon.id, "whitespace-nowrap px-6 py-4"));
                tr.appendChild(createTableCell(coupon.startDate, "whitespace-nowrap px-6 py-4"));
                tr.appendChild(createTableCell(coupon.endDate, "whitespace-nowrap px-6 py-4"));
                tr.appendChild(createTableCell(coupon.discount + ' %', "whitespace-nowrap px-6 py-4"));
                tr.appendChild(createDeleteButtonCell(coupon.id));

                // Append the row to the coupon container
                couponContainer.append(tr);
            });
        },
        error: error => {
            alert("Error updating UI.");
        }
    });
};

const createTableCell = (content, classes) => {
    const td = document.createElement("td");
    td.textContent = content;
    classes.split(" ").forEach(cls => td.classList.add(cls));
    return td;
};

const createDeleteButtonCell = (couponId) => {
    const td = document.createElement("td");

    const form = document.createElement("form");
    form.method = "post";
    form.action = "/coupon/deleteCoupon";

    const input = document.createElement("input");
    input.type = "hidden";
    input.value = couponId;
    input.name = "couponId";
    input.classList.add("deleteCouponId");
    input.readOnly = true;

    const button = document.createElement("button");
    button.classList.add("deleteCouponButton", "bg-red-600", "text-white", "px-4", "py-2", "text-sm", "font-medium", "border-2", "border-red-600", "hover:bg-white", "hover:text-red-600", "hover:border-red-600", "transition", "duration-300", "cursor-pointer");
    button.type = "submit";
    button.textContent = "X";

    form.appendChild(input);
    form.appendChild(button);

    td.appendChild(form);

    return td;
};

