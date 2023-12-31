export const getFilterValues = () => {
    return {
        userId: $("#askForLoyaltyCardUserId").val()
    };
}

export const askForLoyaltyCard = userId => {
    $.ajax({
        url:"/loyaltyCard/askForLoyaltyCard",
        method:"POST",
        data: {userId: userId},
        dataType: "text",
        success: data => {
            alert("You successfuly sent request for creating loyalty card.");
            $("#askForLoyaltyCardForm").remove();
        },
        error: error => {
            alert("You either own or requested loyalty card.")
        }
    })
};

export const acceptLoyaltyCard = loyaltyCardId => {
    $.ajax({
        url:"/loyaltyCard/acceptLoyaltyCard",
        method:"POST",
        data:{loyaltyCardId: loyaltyCardId},
        dataType: "text",
        success: data => {
            alert("Loyalty Card with ID: " + loyaltyCardId + " accepted.");
        },
        error: error => {
            alert("Error accepting Loyalty Card.")
        }
    })
};

export const declineLoyaltyCard = loyaltyCardId => {
    $.ajax({
        url:"/loyaltyCard/declineLoyaltyCard",
        method:"POST",
        data:{loyaltyCardId: loyaltyCardId},
        dataType: "text",
        success: data => {
            alert("Loyalty Card with ID: " + loyaltyCardId + " declined.");
        },
        error: error => {
            alert("Error accepting Loyalty Card.")
        }
    })
};
