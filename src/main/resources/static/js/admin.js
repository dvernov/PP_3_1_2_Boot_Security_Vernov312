$(async function () {
    await getTableWithUsers();
    getNewUserForm();
    getDefaultModal();
    await addNewUser();
})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('/api/users'),
    findAllRoles: async () => await fetch('/api/users/roles'),
    findOneRole: async (id) => await fetch(`/api/users/roles/${id}`),
    findOneUser: async (id) => await fetch(`/api/users/${id}`),
    addUser: async (user) => await fetch('/api/users', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    updateUser: async (user, id) => await fetch(`/api/users/${id}`, {
        method: 'PUT',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch(`/api/users/${id}`, {method: 'DELETE', headers: userFetchService.head})
}

async function getTableWithUsers() {
    let table = $('#userTable tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td> 
                            <td>${user.email}</td> 
                            <td>${user.roles.map(role => role.name).join(', ')}</td>
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info" 
                                data-toggle="modal" data-target="#dfaultModal">Edit</button>
                            </td>
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-outline-danger" 
                                data-toggle="modal" data-target="#defaultModal">Delete</button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })

    $("#userTable").find('button').on('click', (event) => {
        let defaultModal = $('#defaultModal');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}

async function getDefaultModal() {
    $('#defaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
    $('#defaultModal').on('click', '.btn-secondary', function () {
        $(this).closest('.modal').modal('hide');
    });
}

async function getNewUserForm() {

    let form = $(`#defaultForm`)

    await userFetchService.findAllRoles()
        .then(async (response) => {
            let rolesSelect = form.find("#roles");
            let roles = await response.json();
            rolesSelect.attr("size", roles.length);
            roles.forEach(role => {
                let option = `<option value="${role.id}">${role.name}</option>`;
                rolesSelect.append(option);
            })
        })
}

async function addNewUser() {
    $('#addButton').on('click', async () => {
        let addUserForm = $('#defaultForm')
        let firstname = addUserForm.find('#firstName').val().trim();
        let lastname = addUserForm.find('#lastName').val().trim();
        let email = addUserForm.find('#email').val().trim();
        let username = addUserForm.find('#username').val().trim();
        let password = addUserForm.find('#password').val().trim();
        let age = addUserForm.find('#age').val().trim();
        let roleIds = Array.from(addUserForm.find("#roles option:selected")).map(option => parseInt(option.value));

        let roles = [];
        for (let roleId of roleIds) {
            let roleResponse = await userFetchService.findOneRole(roleId);
            let roleData = await roleResponse.json();
            roles.push(roleData);
        }
        console.log('kek')
        let data = {
            firstName: firstname,
            lastName: lastname,
            email: email,
            username: username,
            password: password,
            age: age,
            roles: roles.map(role => {
                return {id: role.id, name: role.name};
            }),
        }
        console.log(data)
        const response = await userFetchService.addUser(data);

        if (response.ok) {
            await getTableWithUsers();
            $('#nav-home-tab').tab('show');
            $('#nav-profile-tab').removeClass('active').attr('aria-selected', 'false');
            addUserForm[0].reset();
            // addUserForm.find('#firstName').val('');
            // addUserForm.find('#lastName').val('');
            // addUserForm.find('#email').val('');
            // addUserForm.find('#age').val('');
            // addUserForm.find('#username').val('');
        }
    })
}

async function editUser(modal, id) {
    let user = (await userFetchService.findOneUser(id)).json();
    modal.find('.modal-title').html('Edit user');

    let editButton = `<button  class="btn btn-outline-success" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <div class="container text-center">
                <div class="col-md-6 mx-auto">
                    <form class="form-group" id="editUser">
                        <label class="form-label">ID</label>
                        <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled>
                        <input type="hidden" id="username" name="username" value="${user.username}">
                        <br>
                        <label class="form-label">First Name</label>
                        <input class="form-control" type="text" id="firstName" value="${user.firstName}">
                        <br>
                        <label class="form-label">Last Name</label>
                        <input class="form-control" type="text" id="lastName" value="${user.lastName}">
                        <br>
                        <label class="form-label">Age</label>
                        <input class="form-control" id="age" type="number" value="${user.age}">
                        <br>
                        <label class="form-label">Email</label>
                        <input class="form-control" type="text" id="email" value="${user.email}"> 
                        <br>
                        <label class="form-label">Password</label>
                        <input class="form-control" type="password" id="password" value="">
                        <br>                 
                        <label class="form-label">Role</label>
                        <select name="role" id="roles" class="form-select" required multiple>
                        </select> 
                    </form>
                </div>
            </div>
        `;
        modal.find('.modal-body').append(bodyForm);

        userFetchService.findAllRoles()
            .then(async (response) => {
                let rolesSelect = modal.find("#roles");
                let roles = await response.json();
                rolesSelect.attr("size", roles.length);
                roles.forEach(role => {
                    let option = `<option value="${role.id}">${role.name}</option>`;
                    rolesSelect.append(option);
                })
            })
    })

    $("#editButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        let firstname = modal.find("#firstName").val().trim();
        let lastname = modal.find("#lastName").val().trim();
        let email = modal.find("#email").val().trim();
        let username = modal.find("#username").val().trim();

        let roleIds = Array.from(modal.find("#roles option:selected")).map(option => parseInt(option.value));
        let roles = [];
        for (let roleId of roleIds) {
            let roleResponse = await userFetchService.findOneRole(roleId);
            let roleData = await roleResponse.json();
            roles.push(roleData);
        }
        let password = modal.find("#password").val().trim();
        let age = modal.find("#age").val().trim();

        let data = {
            id: id,
            firstName: firstname,
            lastName: lastname,
            email: email,
            username: username,
            password: password,
            age: age,
            roles: roles.map(role => {
                return {id: role.id, name: role.name};
            }),
        }

        const response = await userFetchService.updateUser(data, id);

        if (response.ok) {
            await getTableWithUsers();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);

        }
    })
}

async function deleteUser(modal, id) {
    let preuser = await userFetchService.findOneUser(id)
    let user = preuser.json();
    modal.find('.modal-title').html('Delete user');

    let deleteButton = `<button  class="btn btn-danger" id="deleteButton">Delete</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`

    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(deleteButton);

    user.then(user => {
        let bodyForm = `
            <div class="container text-center">
                <div class="col-md-6 mx-auto">
                    <form class="form-group" id="deleteUser">
                        <label class="form-label">ID</label>
                        <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled>
                        <br>
                        <label class="form-label">First Name</label>
                        <input class="form-control" type="text" id="username" value="${user.firstName}" disabled>
                        <br>
                        <label class="form-label">Last Name</label>
                        <input class="form-control" type="text" id="lastName" value="${user.lastName}" disabled>
                        <br>
                        <label class="form-label">Age</label>
                        <input class="form-control" id="age" type="number" value="${user.age}" disabled>
                        <br>
                        <label class="form-label">Email</label>
                        <input class="form-control" type="text" id="email" value="${user.email}" disabled>
                        <br>                 
                        <label class="form-label">Role</label>
                        <select name="role" id="roles" class="form-select" size="${user.roles.length}" multiple disabled>
                        </select>
                    </form>
                </div>
            </div>
        `;
        modal.find('.modal-body').append(bodyForm);
        let rolesSelect = modal.find("#roles");
        user.roles.forEach(role => {
            let option = `<option value="${role.id}">${role.name}</option>`;
            rolesSelect.append(option);
        });
    })
    $("#deleteButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        await userFetchService.deleteUser(id);
        getTableWithUsers();
        modal.modal('hide');
    });
}

