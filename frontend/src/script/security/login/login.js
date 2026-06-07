import { login } from "../../services/authService.js";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".login-form");

    form?.addEventListener("submit", async (event) => {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("pass").value;

        try {
            await login(email, password);
            window.location.href = "/index.html";
        } catch (error) {
            alert(error.message);
        }
    });
});
