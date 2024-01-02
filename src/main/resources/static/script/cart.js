import {getMessagesProperties} from "./messages.js";

export const updateCart = (cartItemId, passengers) => {
    $.ajax({
        url:"/cart/updateCart",
        method:"POST",
        data: {cartItemId: cartItemId.toString(), passengers: passengers},
        dataType: "text",
        success: data => {
            alert("You have successful updated number of passengers for specific cart item");
        },
        error: error => {
            alert("Error updating cart item. You should probably check about how much space for passengers is left.")
        }
    })
};

export const updateTotalPrice = () => {
    $.ajax({
        url: "/cart/updateTotalPrice",
        method: "GET",
        dataType: "text",
        success: async newTotalPrice => {
            const messages = {
                msg: await getMessagesProperties()
            }
            const text = messages.msg.cartPageTotalPrice;
            $("#totalPrice").text(text + " " + newTotalPrice + "$");
        },
        error: error => {
            alert("Error updating total price.")
        }
    })
}