import { logout } from "../services/authService.js";
import { getCurrentUser } from "../services/userService.js";

const menuBtn = document.getElementById("menu");
const menu = document.querySelector(".bbb");
const userName = document.getElementById("name");
const logoutBtn = document.getElementById("logoutBtn");

export function OpenMenu() {
    if (!menuBtn || !menu) return;

    logoutBtn?.addEventListener("click", async () => {
        try {
            await logout();
        } catch (error) {
            alert(error.message);
        }
    });

    menuBtn.addEventListener("click", async () => {
        menu.classList.toggle("open");

        if (!userName || userName.dataset.loaded) return;

        try {
            const user = await getCurrentUser();
            if (user) {
                userName.innerText = "Hello, " + user.name;
                userName.dataset.loaded = "true";
            } else {
                userName.innerText = "Hello, guest";
            }
        } catch {
            userName.innerText = "Hello, guest";
        }
    });
}
