new Glider(document.querySelector('.glider'), {
  slidesToShow: 1,
  slidesToScroll: 1,
  draggable: true,
  dots: '.dots',
  arrows: {
    prev: '.glider-prev',
    next: '.glider-next'
  },
  responsive: [
    {
      breakpoint: 768,
      settings: {
        slidesToShow: 3
      }
    }
  ]
});

// Função para mover a tela para a seção de produtos
function readHomePage() {
  const btnGoToProducts = document.getElementById("readMore");
  const target = document.getElementById("products");

  btnGoToProducts.addEventListener("click", () => {
    target.scrollIntoView({ behavior: "smooth" });
  });
}

readHomePage();
lucide.createIcons();
