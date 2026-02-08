// Checa se os emails são iguais
function checkEmail() { 
    const email = document.getElementById("email").value;
    const confirmEmail = document.getElementById("confirmedEmail").value;

    if (email !== confirmEmail) {
        alert("Emails do not match!");
        return false;
    }
    return true;
}

// Checa se as senhas são iguais
function checkPassword() {
    const password = document.querySelector('input[name="password"]').value;
    const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return false;
    }
    return true;
}

// Envia os dados para o backend
async function createAccount(event) {
    event.preventDefault(); // evita recarregar a página

    if (!checkEmail() || !checkPassword()) return;

    const form = document.getElementById("userForm");
    const formData = new FormData(form);

    const data = {
        name: formData.get("firstName") + " " + formData.get("lastName"),
        email: formData.get("email"),
        password: formData.get("password"),
        birthDate: formData.get("birthDate"),
        cpf: formData.get("cpf"),
        phone: formData.get("phone")
    };

    try {
        const response = await fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert("Account created successfully!");
            form.reset();
        } else {
            alert("Error creating account");
        }
    } catch (error) {
        console.error("Erro:", error);
        alert("Server error");
    }
}

// Evento do formulário (não do botão)
document.getElementById("userForm").addEventListener("submit", createAccount);
