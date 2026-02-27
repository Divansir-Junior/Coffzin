async function handleLogin() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("pass").value; // ← adicione isso

    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify({ email, password })
        });

        const message = await response.text();

        if (response.ok) {
            window.location.href = "/index.html";
        } else {
            alert("Login failed: " + message);
        }

    } catch (error) {
        console.error("Network error:", error);
        alert("Unable to connect to the server.");
    }
}