export function checkEmail(email, confirmEmail) {
    if (email !== confirmEmail) {
        alert("Os emails não coincidem!");
        return false;
    }
    return true;
}

export function checkPassword(password, confirmPassword) {
    if (password !== confirmPassword) {
        alert("As senhas não coincidem!");
        return false;
    }
    return true;
}
