import { initHomePage } from "./pages/home.js";
import { createAccount } from "./pages/register.js";
import { initCarousel } from "./components/carousel.js";
import { redirectToPages } from "./util/redPages.js";
import { OpenMenu } from "./components/menu.js";
document.addEventListener("DOMContentLoaded", () => {
  initHomePage();
  createAccount();
  initCarousel();
  redirectToPages();
  OpenMenu()
  if (window.lucide) lucide.createIcons();
});