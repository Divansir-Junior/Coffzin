export function redirectToPages() {
  const btnOurProducts = document.getElementById("prodtBtn");
  const btnAboutUs = document.getElementById("contactBtn");
  const btnBuyNow = document.getElementById("buyBtn");

  btnAboutUs?.addEventListener("click", () => {
    window.location.href = "frontend/src/pages/about.html";
  });

  btnOurProducts?.addEventListener("click", () => {
    window.location.href = "frontend/src/pages/products.html";
  });

  btnBuyNow?.addEventListener("click", () => {
    window.location.href = "frontend/src/pages/products.html";
  });
}
