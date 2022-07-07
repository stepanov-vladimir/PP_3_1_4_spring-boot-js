
// some variables
const url = "http://localhost:8080/api/users/"

let result = ''

// user
var user = {
    id: 0,
    firstName: "",
    lastName: "",
    age: "",
    email: "",
    password: "",
    roles: []
}


// модалки для обновления и удалению юзера
const editModal = new bootstrap.Modal(document.getElementById('editModal'))
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'))

$(document).ready(function () {
    userList();
});


// Запрос GET на получение всех юзеров
function userList() {
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (users) {
            userListSuccess(users);
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });
}

function userListSuccess(users) {
    // перебираем всех юзеров из ответа и строим таблицу построчно
    $.each(users, function (index, user) {
        // добавляем строку с юзером 
        userAddRow(user);
    });
}

// описание функции добавления юзера в строку в таблицу #userTable
function userAddRow(user) {
    // проверяем, если таблица пустая - то добавляем tbody
    if ($("#userTable tbody").length == 0) {
        $("#userTable").append("<tbody></tbody>");
    }

    // добавляем строку с новым юзером к существующей таблице
    $("#userTable tbody").append(
        userBuildTableRow(user)
    );
}

//строим строку с юзером (+ кнопки Edit и Delete)
function userBuildTableRow(user) {
    let result =
        "<tr id='row" + user.id + "' >" +
        "<td>" + user.id + "</td>" +
        "<td>" + user.firstName + "</td>" +
        "<td>" + user.lastName + "</td>" +
        "<td>" + user.age + "</td>" +
        "<td>" + user.email + "</td>" +
        "<td hidden>" + user.password + "</td>" +
        "<td>" + roleNames(user.roles) + "</td>" +

        "<td>" +

        "<button type='button' " +
        "onclick='userGetForEdit(this);' " +
        "class='btn btn-info text-white' " +
        "data-id='" + user.id + "'>" +
        "<span class='glyphicon glyphicon-edit' /> Edit"
        + "</button>" +

        "</td>" +

        "<td>" +

        "<button type='button' " +
        "onclick='userGetForDelete(this);' " +
        "class='btn btn-danger' " +
        "data-id='" + user.id + "'>" +
        "<span class='glyphicon glyphicon-edit' /> Delete"
        + "</button>" +

        "</td>" +

        "</tr>";

    function roleNames(roles) {
        let result = "";
        for (let i = 0; i < roles.length; i++) {
            if ((i < roles.length - 1)) {
                result += roles[i].name + ", "
            }
            else {
                result += roles[i].name;
            }
        }
        return result;
    }

    return result;
}

//обработать исключение
function handleException(request, message, error) {
    let msg = "";
    msg += "Code: " + request.status + "\n";
    msg += "Text: " + request.statusText + "\n";
    if (request.responseJSON != null) {
        msg += "Message" + request.responseJSON.Message + "\n";
    }
    alert(msg);
}


//// -------- NEW USER --------
// предотвращаем обновление страницы
formNew.addEventListener('submit', (e) => {
    e.preventDefault()
})

// строим nested JSON из values, переданных из формы 
function getRolesFromForm(array) {
    let role = {};
    let roles = [];
    for (let i = 0; i < array.length; i++) {
        role.id = array[i];
        if (role.id == 1) {
            role.name = 'USER'
        }
        if (role.id == 2) {
            role.name = 'ADMIN'
        }
        roles.push({ ...role });
    }
    return roles;
}

// описываем функцию addCLick() на кнопке из таба для добавления юзера ('New user')
function addClick() {
    // Строим объект юзер из данных в форме formNew
    const user = new Object();

    user.firstName = $("#firstNameNew").val();
    user.lastName = $("#lastNameNew").val();
    user.age = $("#ageNew").val();
    user.email = $("#emailNew").val();
    user.password = $("#passwordNew").val();
    user.roles = getRolesFromForm($("#rolesNew").val());

    // добавляем новый объект Юзер (описание функций ниже)
    userAdd(user)
}

// функция добавления юзера
function userAdd(user) {
    $.ajax({
        url: url,
        type: 'POST',
        contentType:
            "application/json;charset=utf-8",
        data: JSON.stringify(user),
        success: function (user) {
            userAddSuccess(user)
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });
}

function userAddSuccess(user) {
    userAddRow(user);
    //переключаемcя на таб с таблицей юзеров ('Users table')
    $('[href="#usersTableTab"]').tab('show');
    // очищаем форму для добавления юзера
    formNewClear();
}

function formNewClear() {
    $("#firstNameNew").val("");
    $("#lastNameNew").val("");
    $("#ageNew").val("");
    $("#emailNew").val("");
    $("#passwordNew").val("");
    $("#rolesNew").val("");
}
//// -------- END of NEW USER --------

//// -------- EDIT USER --------
// получаем данные по юзеру для предзаполнения формы в модалке #editModal
function userGetForEdit(ctl) {
    // забираем id юзера из поля data-id
    let id = $(ctl).data("id");

    // сохраняем id юзера в поле "idEdit"
    $("#idEdit").val(id);

    // формируем и отправляем запрос
    $.ajax({
        url: url + id,
        type: 'GET',
        dataType: 'json',
        success: function (user) {
            userToEditToFields(user);
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });

    // функция парсинга данных о юзере из ответа на запрос GET в форму модалки
    function userToEditToFields(user) {
        $("#idEdit").val(user.id);
        $("#firstNameEdit").val(user.firstName);
        $("#lastNameEdit").val(user.lastName);
        $("#ageEdit").val(user.age);
        $("#emailEdit").val(user.email);
        $("#passwordEdit").val(user.password);
        $("#rolesEdit").val(user.roles);
    }

    // триггерим модалку для обновления юзера ('editModal')
    editModal.show()
}

formEdit.addEventListener('submit', (e) => {
    e.preventDefault()

    user = new Object();

    user.id = $("#idEdit").val();
    user.firstName = $("#firstNameEdit").val();
    user.lastName = $("#lastNameEdit").val();
    user.age = $("#ageEdit").val();
    user.email = $("#emailEdit").val();
    user.password = $("#passwordEdit").val();
    user.roles = getRolesFromForm($("#rolesEdit").val());

    userUpdate(user)

    editModal.hide();
})

function userUpdate(user) {
    $.ajax({
        url: url,
        type: 'PATCH',
        contentType:
            "application/json;charset=utf-8",
        data: JSON.stringify(user),
        success: function (user) {
            userUpdateInTable(user)
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });
}


function getUser(id) {
    $.ajax({
        url: url + id,
        type: 'GET',
        dataType: 'json',
        success: function (users) {
            return user;
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });
}


function userUpdateInTable(user) {

    // Find user in <table>
    const id = idEdit.value

    const row = document.getElementById("row" + id)

    // Добавляем измененного юзера в строку в таблице

    $(row).after(userBuildTableRow(user));

    // Удаляем строку и изначальным юзером
    $(row).remove();
};

//// -------- END of EDIT USER --------

//// -------- DELETE USER --------
function userGetForDelete(ctl) {
    // забираем id юзера из поля data-id
    let id = $(ctl).data("id");

    // сохраняем id юзера в поле "idDelete"
    $("#idDelete").val(id);

    // отправляем GET запрос для предзаполнения таблицы с данными о юзере
    $.ajax({
        url: url + id,
        type: 'GET',
        dataType: 'json',
        success: function (user) {
            userToDeleteToFields(user);
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });

    // функция парсинга данных о юзере из ответа на запрос GET в форму модалки
    function userToDeleteToFields(user) {
        $("#idDelete").val(user.id);
        $("#firstNameDelete").val(user.firstName);
        $("#lastNameDelete").val(user.lastName);
        $("#ageDelete").val(user.age);
        $("#emailDelete").val(user.email);
        $("#passwordDelete").val(user.password);
        $("#rolesDelete").val(user.roles);
    }

    // триггерим модалку для удаления юзера
    deleteModal.show()
}

// запрос DELETE на удаление юзера (без обновления всей страницы!)
formDelete.addEventListener('submit', (e) => {
    e.preventDefault()

    const id = idDelete.value
    const row = document.getElementById("row" + id)

    $.ajax({
        url: url + id,
        type: 'DELETE',
        success: function (user) {
            row.remove()
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });

    // скрываем модалку для удаления юзера
    deleteModal.hide()
});
//// -------- END of DELETE USER --------

//// -------- USER PAGE scripts --------


