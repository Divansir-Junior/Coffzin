async function login() {
    const email = document.getElementById("login").value;
    const password = document.getElementById("pass").value;

    try {
        const response = await fetch("http://192.168.1.9:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        const message = await response.text();

        if (response.ok) {
            alert(message);
        } else {
            alert("Login failed: " + message); 
        }

    } catch (error) {
        console.error("Network error:", error);
        alert("Unable to connect to the server.");
    }
}