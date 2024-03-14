const addDetailButtonListener = () => {
    $(".user-list .content .clickable-data").click(function(event) {
        var customerMembershipNumber = $(this).attr("data-id");

        $.ajax({
            url: `http://localhost:7050/api/customer/detail/${customerMembershipNumber}`,
            success: (response) => {
                $(".box-popup-layer").addClass("box-popup-layer-open");
                $(".box-popup-list").addClass("box-popup-list-open");
                $(".box-popup-layer .box-popup-list .membership-number").text(response.membershipNumber);
                $(".box-popup-layer .box-popup-list .full-name").text(response.fullName);
                $(".box-popup-layer .box-popup-list .birth-date").text(response.birthDate);
                $(".box-popup-layer .box-popup-list .gender").text(response.gender);
                $(".box-popup-layer .box-popup-list .phone").text(response.phone);
                $(".box-popup-layer .box-popup-list .address").text(response.address);
                console.log(response);
            }
        });
    })
};

const addCloseButtonListener = () => {
    $(".box-popup-layer .box-popup-list button").click(function() {
        $(".box-popup-layer").removeClass("box-popup-layer-open");
        $(".box-popup-list").removeClass("box-popup-list-open");
    });
};

(() => {
    addDetailButtonListener();
    addCloseButtonListener();
})();