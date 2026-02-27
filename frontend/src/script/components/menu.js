const menuBtn = document.getElementById("menu");
const menu = document.querySelector(".bbb");
const userName = document.getElementById("name");

export function OpenMenu() {
    menuBtn.addEventListener("click", async () => {
        menu.classList.toggle("open");

        // Busca o usuário só uma vez
        if (!userName.dataset.loaded) {
            const user = await getCurrentUser();
            if (user) {
                userName.innerText = "Hello, " + user.name;
                userName.dataset.loaded = "true";
            }
        }
    });
}

async function getCurrentUser() {
    try {
        const response = await fetch("http://localhost:8080/api/users/me", {
            credentials: "include"
        });

        console.log("Status:", response.status); 
        
        if (response.ok) return response.json();
        
        console.log("Erro:", await response.text()); 
        return null;
    } catch (error) {
        console.error("Error fetching user:", error);
        return null;
    }
}

