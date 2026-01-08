// Checa se os campos de email são iguais
function checkEmail(email,confirmEmail) { 
    email = document.getElementById("email").value;
    confirmEmail = document.getElementById("confirmedEmail").value;

    if(email !== confirmEmail){
        alert("Emails do not match!");
        return false; 
    }
    return true;
}

// Cria a conta 
function createAccount() {
    return {
        userName: document.getElementById("userName").value,
        password: document.getElementById("password").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
        dataBirth: document.getElementById("dataBirth").value
    };
}


function sendData() { 

}