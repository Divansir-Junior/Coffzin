let cart = [];

// Carrega produtos
async function loadProductData() {
    const response = await fetch('../script/data/product.json');
    return await response.json();
}

function limitProducts() {
    if (cart.length >= 5) {
        alert("You can't add more than 5 products to the cart.");
        return false;
    }
    return true;
}

// Renderiza produtos
async function renderProducts() {
    const container = document.querySelector('.container');
    const products = await loadProductData();

    container.innerHTML = '';

    products.forEach((product, index) => {
        container.innerHTML += `
            <div class="card">
                <img src="${product.img}">
                <h3>${product.name}</h3>
                <p>${product.desc}</p>
                <strong>R$ ${product.price.toFixed(2)}</strong>
                <button onclick="addToCart(${index})">Add to cart</button>
            </div>
        `;
    });
    window.products = products;
}

// Abrir / fechar carrinho
document.getElementById("openCart").addEventListener("click", () => {
    document.querySelector(".shopCart").style.display = "flex";
});

document.getElementById("closeShopCart").addEventListener("click", () => {
    document.querySelector(".shopCart").style.display = "none";
});

// Adicionar produto
function addToCart(index) {
    if (!limitProducts()) return;
    cart.push(products[index]);
    updateCart();
}

// Remover produto
function removeFromCart(index) {
    cart.splice(index, 1);
    updateCart();
}

// Atualiza carrinho
function updateCart() {
    const tbody = document.querySelector(".shopCart tbody");
    const total = document.getElementById("total");
    const count = document.getElementById("cartCount");

    tbody.innerHTML = '';
    let sum = 0;

    cart.forEach((product, index) => {
        sum += product.price;

        tbody.innerHTML += `
            <tr>
                <td>${product.name}</td>
                <td>1</td>
                <td>R$ ${product.price.toFixed(2)}</td>
                <td>
                    <button onclick="removeFromCart(${index})">✕</button>
                </td>
            </tr>
        `;
    });

    total.innerText = `Total: R$ ${sum.toFixed(2)}`;
    count.innerText = cart.length;
}

// Inicializa
renderProducts();
