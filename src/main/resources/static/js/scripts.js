/*!
* Start Bootstrap - Simple Sidebar v6.0.5 (https://startbootstrap.com/template/simple-sidebar)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-simple-sidebar/blob/master/LICENSE)
*/
// 
// Scripts
// 
$(document).ready(function () {
    getAll(0)
    addStudent()
    validateForm()
    removeQueryUrl()
    $("#upload_csv_form").click(function (event) {
        event.preventDefault();
        upLoadCSV();
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
    $("#add-form").validate({
        rules: {
            "CitizenIdentity": {
                required: true,
                maxlength: 12,
                /* remote: {
                     type: 'POST',
                     url: 'http://localhost:8080/api/diemthi/citizenId',
                     data: JSON.stringify($('#CitizenIdentity').val()),
                     success: function (response) {
                         alert(response)
                         return response
                     }
                 },*/
            },
            "fullName": {
                required: true,
            },
            "address": {
                required: true
            },
            "email": {
                required: true,
                email: true
            },
            "priorityPoint": {
                required: true
            },
            "score1": {
                required: true,
                number: true
            },
            "score2": {
                required: true,
                number: true
            },
            "score3": {
                required: true,
                number: true
            },
        },
        messages: {
            "CitizenIdentity": {
                required: "Không được để trống CCCD",
                maxlength: "Tối đa 12 số",
                // remote: "Căn cước công dân này đã tồn tại"
            },
            "fullName": {
                required: "Không được để trống Họ và tên",
            },
            "address": {
                required: "Không được để trống đía chỉ"
            },
            "email": {
                required: "Không được để trống email",
                email: "Email không hợp lệ"
            },
            "priorityPoint": {
                required: "Không được để trống điểm ưu tiên"
            },
            "score1": {
                required: "Không được để trống điểm",
                number: "Điểm không hợp lệ"
            },
            "score2": {
                required: "Không được để trống điểm",
                number: "Điểm không hợp lệ"
            },
            "score3": {
                required: "Không được để trống điểm",
                number: "Điểm không hợp lệ"
            },
        }
    });
}

function getAll(page) {
    $.ajax({
        type: "GET",
        url: '/api/diemthi/q?page=' + page,
        dataType: 'json',
        success: function (respone) {
            console.log(respone)
            console.log(respone.data);
            let str = '';

            $.each(respone.data, function (i, item) {
                str += "<tr id='row" + item.id + "'>";
                str += "<td>" + item.id + "</td>";
                str += "<td>" + item.citizenIdentity + "</td>";
                str += "<td>" + item.fullName + "</td>";
                str += "<td>" + (item.gender == true ? "Nam" : "Nu")+ "</td>";
                str += "<td>" + (item.pass == true ? "Đậu nguyện vọng 1" : "Không đậu")+ "</td>";
                str += "<td><a onclick='editStudent(" + item.id + ")' href=\"#editStudentModal\" class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i></a></td>";
                str += "<td><a onclick='deleteStudent(" + item.id + ")' class=\"delete\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Delete\">&#xE872;</i></a></td>";
                str += "<td><a onclick='detailStudent(" + item.id +")' href=\"#detailStudentModal\" class=\"details-button\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Detail\"></i>Chi tiết</a></td>";
                str += "</tr>";
            });
            $('#get-data').html(str);
            $('#pagination').twbsPagination({
                totalPages: respone.pages,
                visiblePages: respone.limit,
                onPageClick: function (event, page) {
                    getAll(page-1);
                }
            });
        }
    })
}
//pagination

//get student by id
function getStudentById(id) {
    $.ajax({
        type: "GET",
        url: '/api/diemthi/' + id,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            $('#detail-citizen-identity').val(data.citizenIdentity);
            $('#full-name-detail').val(data.fullName);
            $('#email-detail').val(data.email);
            $('#address-detail').val(data.address);
            $('#gender-detail').val(data.gender);
            console.log($('#gender-detail').val());
            $('#dob-detail').val(data.dob);
            $('#detailPriorityPoint').val(data.priorityPoint);
            $('#detailsMajors').val(data.majorsRegister.majorsName);
        },
        error: function (error) {
            alert(error);
        }
    });
    return false;
}
function getEditFormStudentById(id) {
    $.ajax({
        type: "GET",
        url: '/api/diemthi/' + id,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            $('#edit-citizen-identity').val(data.citizenIdentity);
            $('#full-name-edit').val(data.fullName);
            $('#email-edit').val(data.email);
            $('#address-edit').val(data.address);
            $('#gender-edit').val(data.gender);
            $('#dob-edit').val(data.dob);
            $('#edit-Priority-Point').val(data.priorityPoint);
            $('#majors-edit').val(data.majorsRegister.majorsName);
        },
        error: function (error) {
            alert(error);
        }
    });
    return false;
}

function addStudent() {
    let myURL = '/api/diemthi/register';
    let student = {};
    $('#add-data-btn').on('click', function (e) {
        if($("#add-form").valid()) {
            student.citizenIdentity = $('#addCitizenIdentity').val();
            student.fullName = $('#full-name').val();
            student.email = $('#email-input').val();
            student.gender = $('#gender-input').val();
            student.dob = $('#dob-input').val();
            student.address = $('#address-text').val();
            student.priorityPoint = $('#inputPriorityPoint').val();
            student.majorsName = $('#inputMajors').val();
            student.score1 = $('#input-score-1').val();
            student.score2 = $('#input-score-2').val();
            student.score3 = $('#input-score-3').val();
            student.examSubject = [$('#input-subject-1').val(),$('#input-subject-2').val(),$('#input-subject-3').val()]

            let studentJson = JSON.stringify(student);
            $.ajax({
                type: "POST",
                url: myURL,
                data: studentJson,
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    console.log(data);
                    removeQueryUrl();
                    getAll(0);
                },
                error: function () {
                    alert("Loi roi ban oi");
                }
            });
        }

    });
    return false;
}

function editStudent(id) {
    getEditFormStudentById(id);
    $('#save-btn').on('click', function (e) {
        let updateStudent = {};
        updateStudent.fullName = $('#full-name-edit').val();
        updateStudent.email = $('#email-edit').val();
        updateStudent.address = $('#address-edit').val();
        updateStudent.dob = $('#dob-edit').val();
        updateStudent.gender = $('#gender-edit').val();
        updateStudent.priorityPoint = $('#edit-Priority-Point').val();
        let updateStudentJson = JSON.stringify(updateStudent);
        $.ajax({
            type: "PUT",
            url: '/api/diemthi/' + id,
            data: updateStudentJson,
            contentType: "application/json; charset=utf-8",

            success: function () {
                alert("Update Student Successfull");
                $('#name-search').val("");
            },
            error: function (error) {
                alert(error);
            }
        })

    });
}

//Detail
function detailStudent(id) {
    getStudentById(id);
}
//Delete
function deleteStudent(id) {
    $.ajax({
        type: "DELETE",
        url: '/api/diemthi/' + id,
        success: function() {
            $('#row'+id).html("");
        },
        error: function () {
            alert("Loi roi ban oi");
        }
    });
};
function searchStudentByName(page) {
    $('#btn-search').on('click', function (e) {
        $.ajax({
            type: "GET",
            url: '/api/diemthi/search?page' + page
                + '&nameKey=' + $('#search-content').val()
                + '&majorKey='  + $('#filter').val() ,
            dataType: 'json',

            success: function(respone) {
                console.log(respone)
                let str = '';
                $.each(respone.data, function (i, item) {
                    str += "<tr id='row" + item.id + "'>";
                    str += "<td>" + item.id + "</td>";
                    str += "<td>" + item.citizenIdentity + "</td>";
                    str += "<td>" + item.fullName + "</td>";
                    str += "<td>" + (item.gender == true ? "Nam" : "Nu")+ "</td>";
                    str += "<td>" + (item.pass == true ? "Đậu nguyện vọng 1" : "Không đậu")+ "</td>";
                    str += "<td><a onclick='editStudent(" + item.id + ")' href=\"#detailStudentModal\" class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i></a></td>";
                    str += "<td><a onclick='deleteStudent(" + item.id + ")' class=\"delete\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Delete\">&#xE872;</i></a></td>";
                    str += "<td><a onclick='detailStudent(" + item.id +")' href=\"#detailStudentModal\" class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\"></i></a>Chi tiết</td>";
                    str += "</tr>";

                });
                $('#get-data').html(str);
                $('#pagination').twbsPagination({
                    totalPages: respone.pages,
                    visiblePages: respone.limit,
                    onPageClick: function (event, page) {
                        // getAll(page-1);
                    }
                });
            }
        });
    });
}

function upLoadCSV() {
    // Get form
    let form = $('#fileUploadForm')[0];

    let formData = new FormData(form);


    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/api/csv/upload",
        data: formData,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert(data.message);
            console.log("SUCCESS : ", data);
            $('#pagination').twbsPagination({
                totalPages: respone.pages,
                visiblePages: respone.limit,
                onPageClick: function (event, page) {
                    getAll(page-1);
                }
            });
        },
        error: function (e) {
            alert(e);
            console.log("ERROR : ", e);

        }
    });
}