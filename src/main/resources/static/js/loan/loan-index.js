const addInsertButtonListener = () => {
    $(".user-list .insert-button").click(function(event) {
        $.ajax({
            url: "http://localhost:7050/api/loan/dropdown",
            success: (response) => {
                let customerDropdown = document.querySelector(".post-input .customer-dropdown");
                let bookDropdown = document.querySelector(".post-input .book-dropdown");

                for (let i = 0; i < response.customerDropdown.length; i++) {
                    customerDropdown.innerHTML +=
                    `<option value=${response.customerDropdown[i].value}>${response.customerDropdown[i].text}</option>`;
                }

                for (let i = 0; i < response.bookDropdown.length; i++) {
                    bookDropdown.innerHTML +=
                        `<option value=${response.bookDropdown[i].value}>${response.bookDropdown[i].text}</option>`;
                }

                $(".form-popup-layer").addClass("form-popup-layer-open");
                $(".form-popup-box").addClass("form-popup-box-open");
            }
        });
    });
};

const addUpdateButtonListener = () => {
    $(".user-list .edit-button").click(function(event) {
        var loanId = $(this).attr("data-id");

        $.ajax({
            url: `http://localhost:7050/api/loan/dropdown/${loanId}`,
            success: (response) => {
                let customerDropdown = document.querySelector(".post-input .customer-dropdown");
                let bookDropdown = document.querySelector(".post-input .book-dropdown");

                for (let i = 0; i < response.customerDropdown.length; i++) {
                    customerDropdown.innerHTML +=
                    `<option value=${response.customerDropdown[i].value}>${response.customerDropdown[i].text}</option>`;
                }

                for (let i = 0; i < response.bookDropdown.length; i++) {
                    bookDropdown.innerHTML +=
                        `<option value=${response.bookDropdown[i].value}>${response.bookDropdown[i].text}</option>`;
                }

                console.log(response)

                $(".form-popup-layer .loan-id").val(response.loanUpdateDTO.id);
                $(".form-popup-layer .customer-dropdown").val(response.loanUpdateDTO.customerNumber);
                $(".form-popup-layer .book-dropdown").val(response.loanUpdateDTO.bookCode);
                $(".form-popup-layer .loan-date").val(response.loanUpdateDTO.loanDate);
                $(".form-popup-layer .note").val(response.loanUpdateDTO.note);
                $(".form-popup-layer").addClass("form-popup-layer-open");
                $(".form-popup-box").addClass("form-popup-box-open");
            }
        });
    });
};

const addSubmitButtonListener = () => {
    $(".form-popup-layer .form-popup-box .popup-save-button").click(function(event) {
        event.preventDefault();
        const inputId = $('.form-popup-layer .loan-id').val();

        const loanUpsertDTO = {
            id: (inputId === '') ? null : inputId,
            customerNumber: $(".form-popup-layer .customer-dropdown").val(),
            bookCode: $(".form-popup-layer .book-dropdown").val(),
            loanDate: $(".form-popup-layer .loan-date").val(),
            note: $(".form-popup-layer .note").val()
        };

        console.log(loanUpsertDTO);
        const methodType = (loanUpsertDTO.id === null) ? "POST" : "PUT";
        console.log(methodType);

        $.ajax({
            method: methodType,
            url: "http://localhost:7050/api/loan",
            data: JSON.stringify(loanUpsertDTO),
            contentType: "application/json",
            success: (response) => {
                location.reload();
            },
            error: (response) => {
                if (response.status === 400) {
                    for (let res of response.responseJSON) {
                        $(`.form-popup-layer .form-popup-box [data-for=${res.field}]`).text(res.defaultMessage);
                    }
                }
            }
        });
    });
};

const addDetailButtonListener = () => {
    $(".user-list .content .action .detail-button").click(function() {
        var loanId = $(this).attr("data-id");

        $.ajax({
            url: `http://localhost:7050/api/loan/detail/${loanId}`,
            success: (response) => {
                $(".box-popup-layer").addClass("box-popup-layer-open");
                $(".box-popup-layer .box-popup-detail").addClass("box-popup-detail-open");
                $(".book-detail .title").text(response.title);
                $(".book-detail .category").text(response.category);
                $(".book-detail .author").text(response.author);
                $(".book-detail .floor").text(response.floor);
                $(".book-detail .isle").text(response.isle);
                $(".book-detail .bay").text(response.bay);
                $(".book-detail .membership-number").text(response.membershipNumber);
                $(".book-detail .full-name").text(response.fullName);
                $(".book-detail .phone").text(response.phone);
                $(".book-detail .expire-date").text(response.membershipExpireDate);
            }
        });
    });
}

const addCloseButtonListener = () => {
    $(".popup-close-button").click(function(event) {
    $(".form-popup-layer .category-name").removeAttr("readonly");
    $(".form-popup-layer").removeClass("form-popup-layer-open");
    $(".form-popup-box").removeClass("form-popup-box-open");
    $(".box-popup-layer").removeClass("box-popup-layer-open");
    $(".box-popup-layer .box-popup-detail").removeClass("box-popup-detail-open");
    $('.form-popup-layer .form-popup-box select').html("");
    $('.form-popup-layer .form-popup-box input').val("");
    $('.form-popup-layer .form-popup-box .error-message-popup').text("")
    });
};

(() => {
    addInsertButtonListener();
    addUpdateButtonListener();
    addSubmitButtonListener();
    addDetailButtonListener();
    addCloseButtonListener();
})();