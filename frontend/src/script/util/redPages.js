// Função para redirecionar para as páginas 
function redirectToPages() {
  const btnOurProducts = document.getElementById("prodtBtn");
  const btnAboutUs = document.getElementById("contactBtn");
  const btnBuyNow = document.getElementById("buyBtn");
  
  if (btnAboutUs) {
    btnAboutUs.addEventListener("click", () => {
      window.location.href = "frontend/src/pages/about.html";
    });
  }

  if (btnOurProducts) {
    btnOurProducts.addEventListener("click", () => {
      window.location.href = "frontend/src/pages/products.html";
    });
  }

  if (btnBuyNow) {
    btnBuyNow.addEventListener("click", () => {
      window.location.href = "frontend/src/pages/products.html";
    });
  }
}

redirectToPages();
