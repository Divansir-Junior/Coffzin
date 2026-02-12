import { formatDateToISO } from "../util/date.js";
import { checkEmail, checkPassword } from "../util/validation.js";
import { createUser } from "../services/userService.js";

async function createAccount(event) {
    event.preventDefault();

    const form = document.getElementById("userForm");
    const formData = new FormData(form);

    const email = formData.get("email");
    const confirmEmail = formData.get("confirmedEmail");
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
        name: formData.get("name"),
        lastName: formData.get("lastName"),
        birthDate: birthDateFormatted,
        cpf: formData.get("cpf"),
        phoneNumber: formData.get("phoneNumber") || null,
        email,
        password
    };

    try {
        await createUser(data);
        alert("Conta criada com sucesso!");
        form.reset();
    } catch (error) {
        alert(`Erro: ${error.message}`);
    }
}

export function initRegisterPage() {
    const form = document.getElementById("userForm");
    if (form) {
        form.addEventListener("submit", createAccount);
    }
}
