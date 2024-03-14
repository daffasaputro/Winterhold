const addSummaryButtonListener = () => {
    $(".user-list .content .action .summary-button").click(function(event) {
        var bookCode = $(this).attr("data-id");

        $.ajax({
            url: `http://localhost:7050/api/category/book/summary/${bookCode}`,
            success: (response) => {
                $(".box-popup-layer").addClass("box-popup-layer-open");
                $(".box-popup-box").addClass("box-popup-box-open");
                $(".box-popup-layer .box-popup-box p").text(response.summary);
            }
        });
    })
};

const addCloseButtonListener = () => {
    $(".box-popup-layer .box-popup-box .close-button").click(function(event) {
        $(".box-popup-layer").removeClass("box-popup-layer-open");
        $(".box-popup-box").removeClass("box-popup-box-open");
    });
};

(() => {
    addSummaryButtonListener();
    addCloseButtonListener();
})();