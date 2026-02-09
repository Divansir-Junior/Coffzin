function formatDateToISO(dateString) {
    const date = new Date(dateString);

    if (isNaN(date.getTime())) {
        alert("Data de nascimento inválida!");
        throw new Error("Invalid date");
    }

    return date.toISOString().split("T")[0];
}

// Funções de validação de email e senha (adicione se não existir)
function checkEmail() {
    const email = document.getElementById("email").value;
    const confirmEmail = document.getElementById("confirmedEmail").value;
    
    if (email !== confirmEmail) {
        alert("Os emails não coincidem!");
        return false;
    }
    return true;
}

function checkPassword() {
    const password = document.querySelector('input[name="password"]').value;
    const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;
    
    if (password !== confirmPassword) {
        alert("As senhas não coincidem!");
        return false;
    }
    return true;
}

async function createAccount(event) {
    event.preventDefault();

    if (!checkEmail() || !checkPassword()) return;

    const form = document.getElementById("userForm");
    const formData = new FormData(form);

    let birthDateRaw = formData.get("birthDate");
    let birthDateFormatted;

    try {
        birthDateFormatted = formatDateToISO(birthDateRaw);
    } catch {
        return;
    }

    const data = {
        name: formData.get("name"),
        lastName: formData.get("lastName"),
        birthDate: birthDateFormatted,
        cpf: formData.get("cpf"),
        phoneNumber: formData.get("phoneNumber") || null,
        email: formData.get("email"),
        password: formData.get("password")
    };

    console.log("Dados enviados:", data);

    try {
        const response = await fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: { 
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        console.log("Status da resposta:", response.status);
        
        if (response.ok) {
            const result = await response.json();
            console.log("Resposta do servidor:", result);
            alert("Conta criada com sucesso!");
            form.reset();
        } else {
            const errorText = await response.text();
            console.error("ERRO BACKEND:", response.status, errorText);
            alert(`Erro ${response.status}: ${errorText || "Erro ao criar conta"}`);
        }
    } catch (error) {
        console.error("Erro de rede:", error);
        alert("Erro ao conectar com o servidor");
    }
}

// Adicione o event listener ao formulário
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("userForm");
    if (form) {
        form.addEventListener("submit", createAccount);
    }
});