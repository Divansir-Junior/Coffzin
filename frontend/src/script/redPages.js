// Função para redirecionar para as páginas 
function redirectToPages() {
  const btnOurProducts = document.getElementById("prodtBtn");
  const btnAboutUs = document.getElementById("contactBtn");
  btnAboutUs.addEventListener("click", () => {
    window.location.href = "../pages/contact.html";
  });

  btnOurProducts.addEventListener("click", () => {
    window.location.href = "../pages/products.html";
  });
}

console.log("testando");
redirectToPages();