/*!
* Start Bootstrap - Simple Sidebar v6.0.5 (https://startbootstrap.com/template/simple-sidebar)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-simple-sidebar/blob/master/LICENSE)
*/
// 
// Scripts
// 
$(document).ready(function () {
    getAll()
    addStudent()
    validateForm()
});
function validateForm() {
    $("#add-form").validate({
        rules: {
            "CitizenIdentity": {
                required: true,
                maxlength: 12
            },
            "fullName": {
                required: true,
            },
        },
        messages: {
            "CitizenIdentity": {
                required: "Không được để trống CCCD",
                maxlength: "Hãy nhập tối đa 12 ký tự"
            },
            "fullName": {
                required: "Không được để trống Họ và tên",
            }
        }
    });
}

function getAll() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/api/diemthi',
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
                str += "<td><a onclick='editStudent(" + item.id + ")' href=\"#detailStudentModal\" class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i></a></td>";
                str += "<td><a onclick='deleteStudent(" + item.id + ")' class=\"delete\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Delete\">&#xE872;</i></a></td>";
                str += "<td><a  href=\"#detail-modal\" class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i>Chi tiết</a></td>";
                str += "</tr>";
            });
            $('#get-data').html(str);

        }
    })
}
//pagination

//get student by id
function getStudentById(id) {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/api/diemthi/' + id,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            $('#detail-citizen-identity').val(data.citizenIdentity);
            $('#full-name-detail').val(data.fullName);
            $('#email-detail').val(data.email);
            $('#address-detail').val(data.address);
            $('#gender-detail').val(data.gender);
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

function addStudent() {
    let myURL = 'http://localhost:8080/api/diemthi/register';
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

                    getAll();
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
    getStudentById(id);
    $('#save-btn').on('click', function (e) {
        let updateStudent = {};
        updateStudent.fullName = $('#editFullName').val();
        updateStudent.email = $('#editEmail4').val();
        updateStudent.address = $('#editAddress').val();
        updateStudent.dob = $('#editDOB').val();
        updateStudent.gender = $('#editGender').val();
        updateStudent.priorityPoint = $('#editpriorityPoint').val();
        let updateStudentJson = JSON.stringify(updateStudent);
        $.ajax({
            type: "PUT",
            url: 'http://localhost:8080/api/diemthi/' + id,
            data: updateStudentJson,
            contentType: "application/json; charset=utf-8",

            success: function () {
                alert("Update Student Successfull");
                //$('#name-search').val("");
            },
            error: function (error) {
                alert(error);
            }
        })

    });
}

//Delete
function deleteStudent(id) {
    $.ajax({
        type: "DELETE",
        url: 'http://localhost:8080/api/diemthi/' + id,
        success: function() {
            $('#row'+id).html("");
        },
        error: function () {
            alert("Loi roi ban oi");
        }
    });
};
function searchStudentByName() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/api/diemthi/search?key=' + $('#search-content').val(),
        dataType: 'json',

        success: function(data) {
            console.log(data)
            let str = '';
            $.each(data, function (i, item) {
                str += "<tr id='row" + item.id + "'>";
                str += "<td>" + item.id + "</td>";
                str += "<td>" + item.citizenIdentity + "</td>";
                str += "<td>" + item.fullName + "</td>";
                str += "<td>" + (item.gender == true ? "Nam" : "Nu")+ "</td>";
                str += "<td>" + (item.pass == true ? "Đậu nguyện vọng 1" : "Không đậu")+ "</td>";
                str += "<td><a onclick='editStudent(" + item.id + ")' href=\"#detailStudentModal\" class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i></a></td>";
                str += "<td><a onclick='deleteStudent(" + item.id + ")' class=\"delete\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Delete\">&#xE872;</i></a></td>";
                str += "<td><a  href=\"#detail-modal\" class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i>Chi tiết</a></td>";
                str += "</tr>";

            });
            $('#get-data').html(str);
        }
    });

}