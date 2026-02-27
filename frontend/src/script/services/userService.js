// ======================================= USER SERVICE =======================================

export async function createUser(data) {
    const response = await fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return response.json();
}

export async function getCurrentUser() {
    try {
        const response = await fetch("http://localhost:8080/api/users/me", {
            credentials: "include"
        });

        console.log("Status /me:", response.status);

        if (response.ok) return response.json();

        console.log("Erro /me:", await response.text());
        return null;
    } catch (error) {
        console.error("Error fetching user:", error);
        return null;
    }
}