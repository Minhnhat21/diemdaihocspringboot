$(document).ready(function () {
    $("table").hide();
    $("#console-rank").hide();

    $("#btn-reload").click(function(){
        location.reload(true);
    });
});

function searchStudentByName() {
    $(".error").hide()
    $(".error").empty()
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/api/tracuu/search?key=' + $('#content-search').val(),
        dataType: 'json',

        success: function(data) {

            console.log(data)
            $("table").show();
            $("#console-rank").show();
            console.log(data)
            window.value = data.citizenIdentity;
            let str = '';
            str += "<tr>";
            str += "<td>" + data.citizenIdentity + "</td>";
            str += "<td>" + data.fullName + "</td>";
            str += "<td>" + data.totalScore + "</td>";
            str += "<td>" + data.message + "</td>";
            str += "</tr>";
            $('#get-data').html(str);
        },
        error: function (e) {
            $(".error").show();
            $(".error").append("Không tìm thấy CCCD của bạn, vui lòng nhập lại!")
        }
    });

}
function getRank() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/api/tracuu/rank?key=' + window.value,
        dataType: 'json',
        success: function(data){
            $("#rank_button").hide();
            console.log(data);
            let str = data;
            $('#c-h2').append(str);
        }
    })
}