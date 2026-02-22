const API_URL = "http://localhost:8080/api/users";

export async function createUser() {
    const response = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Erro ao criar conta");
    }

    return response.json();
}
