const menuBtn = document.getElementById("menu");
const menu = document.querySelector(".bbb");

export function OpenMenu() {
    menuBtn.addEventListener("click", () => {
        menu.classList.toggle("open");
    });
}