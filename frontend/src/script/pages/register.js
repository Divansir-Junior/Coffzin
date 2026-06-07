import { createUser } from "../services/userService.js";
import { formatDateToISO } from "../util/date.js";
import { checkEmail, checkPassword } from "../util/validation.js";

export async function createAccount(event) {
    event.preventDefault();

    const form = document.getElementById("userForm");
    const formData = new FormData(form);

    const email = formData.get("email").trim().toLowerCase();
    const confirmEmail = formData.get("confirmEmail").trim().toLowerCase();
    const password = formData.get("password");
    const confirmPassword = formData.get("confirmPassword");

    if (!checkEmail(email, confirmEmail)) return;
    if (!checkPassword(password, confirmPassword)) return;

    let birthDateFormatted;
    try {
        birthDateFormatted = formatDateToISO(formData.get("birthDate"));
    } catch (error) {
        alert(error.message);
        return;
    }

    const data = {
        name: formData.get("name").trim(),
        lastName: formData.get("lastName").trim(),
        birthDate: birthDateFormatted,
        cpf: onlyDigits(formData.get("cpf")),
        phoneNumber: onlyDigits(formData.get("phoneNumber")) || null,
        email,
        password
    };

    try {
        await createUser(data);
        alert("Conta criada com sucesso!");
        form.reset();
        window.location.href = "login.html";
    } catch (error) {
        alert(`Erro: ${error.message}`);
    }
}

function onlyDigits(value) {
    return (value || "").replace(/\D/g, "");
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("userForm");
    form?.addEventListener("submit", createAccount);
});
