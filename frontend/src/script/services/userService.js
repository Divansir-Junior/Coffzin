export async function createUser(data) {
    const response = await fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        throw new Error(await response.text());
        window.location.href = "/index.html"
    }

    return response.json();
}