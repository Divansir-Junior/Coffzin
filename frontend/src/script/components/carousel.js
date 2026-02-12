export function initCarousel() {
  const el = document.querySelector('.glider');
  if (!el) return;

  new Glider(el, {
    slidesToShow: 1,
    slidesToScroll: 1,
    draggable: true,
    dots: '.dots',
    arrows: {
      prev: '.glider-prev',
      next: '.glider-next'
    }
  });
}
