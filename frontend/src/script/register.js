function formatDateToISO(dateString) {
    // Garante que a data está no formato yyyy-MM-dd
    const date = new Date(dateString);

    if (isNaN(date.getTime())) {
        alert("Data de nascimento inválida!");
        throw new Error("Invalid date");
    }

    return date.toISOString().split("T")[0];
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
        name: formData.get("firstName"),
        lastName: formData.get("lastName"),
        birthDate: birthDateFormatted,
        cpf: formData.get("cpf"),
        phoneNumber: formData.get("phone") || null,
        email: formData.get("email"),
        password: formData.get("password")
    };

    console.log("DATA FINAL ENVIADA:", data);

    try {
        const response = await fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert("Conta criada com sucesso!");
            form.reset();
        } else {
            const errorText = await response.text();
            console.error("ERRO BACKEND:", errorText);
            alert("Erro ao criar conta");
        }
    } catch (error) {
        console.error("Erro:", error);
        alert("Erro no servidor");
    }
}
