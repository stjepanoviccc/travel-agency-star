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
            alert("Coupon added successfully!")
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
