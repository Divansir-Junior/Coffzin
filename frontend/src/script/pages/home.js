export function initHomePage() {
  const btn = document.getElementById("readMore");
  const target = document.getElementById("products");

  if (btn && target) {
    btn.addEventListener("click", () => {
      target.scrollIntoView({ behavior: "smooth" });
    });
  }
}
