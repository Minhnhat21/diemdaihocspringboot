$(document).ready(function () {
    $("table").hide();
    $("#console-rank").hide();
    $("#btn-reload").click(function(){
        removeQueryUrl()
        location.reload(true);
    });

});
function removeQueryUrl() {
    var uri = window.location.toString();
    if (uri.indexOf("?") > 0) {
        var clean_uri = uri.substring(0, uri.indexOf("?"));
        window.history.replaceState({}, document.title, clean_uri);
    }
}
function validateForm() {
    $("#main-form").validate({
        rules: {
            "CitizenIdentity": {
                required: true,
                maxlength: 12
            }
        },
        messages: {
            "CitizenIdentity": {
                required: "Không được để trống CCCD",
                maxlength: "Hãy nhập tối đa 12 ký tự"
            }
        }
    });
}
function searchStudentByName() {
    $(".error").hide()
    $(".error").empty()
    if($('#main-form').valid()) {

        $.ajax({
            type: "GET",
            url: 'http://localhost:8080/api/tracuu/search?key=' + $('#content-search').val(),
            dataType: 'json',

            success: function(data) {

                $("table").show();
                $("#console-rank").show();

                console.log(data)
                let str = '';
                str += "<tr>";
                str += "<td>" + data.citizenIdentity + "</td>";
                str += "<td>" + data.fullName + "</td>";
                str += "<td>" + data.totalScore + "</td>";
                str += "<td>" + (data.pass == true ? "Đậu" : "Trượt")+ "</td>";
                str += "</tr>";
                $('#get-data').html(str)



                getRank(data.citizenIdentity);

                $('#content-search').val("");
            },
            error: function (e) {
                $(".error").show();
                $(".error").append("Không tìm thấy CCCD của bạn, vui lòng nhập lại!")
            }
        });
    }
}
function getRank(citizenId) {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/api/tracuu/rank?key=' + citizenId,
        dataType: 'json',
        success: function(data){
            let str = data;
            $('#c-h2').append(str);
        }
    })
}