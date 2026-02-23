export function initCarousel() {
  const el = document.querySelector('.glider');
  if (!el) return;

  window.addEventListener('load', () => {
    if (typeof Glider === 'undefined') {
      console.error('Glider não carregou');
      return;
    }
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
  });
}