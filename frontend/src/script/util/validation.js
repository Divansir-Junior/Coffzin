export function checkEmail(email, confirmEmail) {
    if (email !== confirmEmail) {
        alert("Os emails nao coincidem!");
        return false;
    }

    return true;
}

export function checkPassword(password, confirmPassword) {
    if (password !== confirmPassword) {
        alert("As senhas nao coincidem!");
        return false;
    }

    if (password.length < 8 || !/[A-Za-z]/.test(password) || !/\d/.test(password)) {
        alert("A senha precisa ter pelo menos 8 caracteres, com letras e numeros.");
        return false;
    }

    return true;
}
