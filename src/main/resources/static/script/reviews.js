import {getMessagesProperties} from "./messages.js";

export const getFilterValues = () => {
    return {
        accommodationUnitId: $('#filterReviewsAccUnit').val()
    }
}

export const filterReview = accommodationUnitId => {
    console.log(accommodationUnitId);
    $.ajax({
        url: '/reviews/filterReviewsByAccUnit',
        method: 'GET',
        data: { accommodationUnitId: accommodationUnitId},
        dataType: 'json',
        success: data => {
            updateReviewsOnFilter(data);
        },
        error: error => {
            console.error('Fetching reviews error: ', error);
        }
    });
};

export const updateReviewsOnFilter = async reviews => {
    const container = $('#allReviewsContainer');
    container.empty();

    const messages = {
        msg: await getMessagesProperties()
    }

    if (reviews.length > 0) {
        reviews.forEach(review => {
            const reviewElement = document.createElement("div");
            reviewElement.classList.add('reviewItem', 'p-6', 'lg:p-8', 'text-center', 'lg:text-left', 'overflow-auto', 'border-2', 'border-gray-600',
                'rounded-xl', 'flex', 'flex-col', 'gap-y-4', 'mt-6');

            reviewElement.innerHTML = `
                <p class="text-lg">${messages.msg.textAccommodationUnit}: ${review.accommodationUnit.name + ' - ' + review.accommodationUnit.destination.city}</p>
                <p class="text-lg">${messages.msg.textUsername}: ${review.creator.username}</p>
                <p class="text-lg">${messages.msg.textMessage}: ${review.message}</p>
            `;

            let starWrapper = document.createElement("div");
            starWrapper.classList.add("starWrap", "flex", "flex-row");

            let starText = document.createElement("span");
            starText.classList.add("text-lg");
            starText.innerHTML = `${messages.msg.textStars}: `;
            starWrapper.append(starText);

            let starsCount = review.stars;
            for (let i = 0; i < starsCount; i++) {
                let starContainer = document.createElement("span");
                starContainer.classList.add('ml-2', 'flex', 'justify-center', 'items-center');
                starContainer.innerHTML = (
                    `<svg xmlns="http://www.w3.org/2000/svg" height="16" width="18" viewBox="0 0 576 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"/></svg>`
                )
                starWrapper.append(starContainer);
            }

            reviewElement.appendChild(starWrapper);
            container.append(reviewElement);
        })
    } else {
        container.append(`<p class="mt-4">${messages.msg.textNoContentToShow}</p>`)
    }
};

