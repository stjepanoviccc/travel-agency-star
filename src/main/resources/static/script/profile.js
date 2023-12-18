// this function is used for styling only
export const linksInProfilePageActiveState = () => {
    const url = window.location.pathname;

    switch(url) {
        case "/profile":
            $("#linkToProfileEdit").addClass('bg-gray-600 text-white');
            break;
        case "/profile/wishlist":
            $("#linkToProfileWishlist").addClass('bg-gray-600 text-white');
            break;
        case "/profile/orders":
            $("#linkToProfileOrders").addClass('bg-gray-600 text-white');
            break;
        default:
            break;
    }
}