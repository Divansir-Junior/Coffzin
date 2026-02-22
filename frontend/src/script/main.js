import { initHomePage } from "./pages/home.js";
import { initRegisterPage } from "./pages/register.js";
import { initCarousel } from "./components/carousel.js";
import { redirectToPages } from "./util/redPages.js";
import { createUser } from "./services/userService.js";

document.addEventListener("DOMContentLoaded", () => {
  initHomePage();
  initRegisterPage();
  initCarousel();
  redirectToPages();

  if (window.lucide) lucide.createIcons();
});

  
document.getElementById("userForm").addEventListener("submit", async function(e) {
    e.preventDefault(); // impede o form de recarregar a página
    await createUser();
});
  