 async function logout() {
    try {
        const response = await fetch("http://localhost:8080/api/auth/logout", {
            method: "POST",
            credentials: "include"
        });

        if (response.ok) {
            alert("Logout...");
            window.location.href = "/login.html"; 
        }
    } catch (error) {
        console.log("Erro: " + error);
    }
}