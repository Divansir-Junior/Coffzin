// Função para redirecionar para as páginas 
function redirectToPages() {
  const btnOurProducts = document.getElementById("prodtBtn");
  const btnAboutUs = document.getElementById("contactBtn");
  const btnBuyNow = document.getElementById("buyBtn");
  btnBuyNow.addEventListener("click", () => {
    window.location.href = "../pages/products.html";
  });
  btnAboutUs.addEventListener("click", () => {
    window.location.href = "../pages/contact.html";
  });

  btnOurProducts.addEventListener("click", () => {
    window.location.href = "../pages/products.html";
  });
}

console.log("testando");
redirectToPages();