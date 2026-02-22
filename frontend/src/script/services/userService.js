const API_URL = 'http://localhost:8080/api/users';
 async function createUser() {
    const response = await fetch(API_URL, {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify( {
            name : document.getElementById("name").value,
            lastName : document.getElementById("lastName").value,
            birthDate : document.getElementById("data").value,
            cpf : document.getElementById("cpf").value,
            phoneNumber : document.getElementById("phoneNumber").value,
            email : document.getElementById("email").value,
            password : document.getElementById("password").value

        })
    });

    const data = await response.json();
    console.log("Usuário criado" + data);
}
