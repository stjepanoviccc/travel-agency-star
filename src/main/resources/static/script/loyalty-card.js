export const askForLoyaltyCard = userId => {
    $.ajax({
        url:"/loyaltyCard/askForLoyaltyCard",
        method:"POST",
        data: {userId: userId},
        dataType: "text",
        success: data => {
            alert("You successfuly sent request for creating loyalty card.");
        },
        error: error => {
            alert("You either own or requested loyalty card.")
        }
    })
};

export const getFilterValues = () => {
    return {
        userId: $("#askForLoyaltyCardUserId").val()
    };
}