import { initHomePage } from "./pages/home.js";
import { createAccount } from "./pages/register.js";
import { initCarousel } from "./components/carousel.js";
import { redirectToPages } from "./util/redPages.js";

document.addEventListener("DOMContentLoaded", () => {
  initHomePage();
  createAccount();
  initCarousel();
  redirectToPages();

  if (window.lucide) lucide.createIcons();
});