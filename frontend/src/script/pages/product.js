let cart = [];

// ─── CARREGA PRODUTOS ───
async function loadProductData() {
    const response = await fetch('../script/data/product.json');
    return await response.json();
}

// ─── RENDERIZA PRODUTOS ───
async function renderProducts() {
    const container = document.querySelector('.container');
    const products = await loadProductData();
    container.innerHTML = '';

    products.forEach((product, index) => {
        container.innerHTML += `
            <div class="card">
                <img src="${product.img}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>${product.desc}</p>
                <strong>R$ ${product.price.toFixed(2)}</strong>
                <button onclick="addToCart(${index})">Add to cart</button>
            </div>
        `;
    });

    window.products = products;
}

// ─── ABRIR / FECHAR CARRINHO ───
document.getElementById("openCart").addEventListener("click", () => {
    document.getElementById("shopCart").classList.add("open");
    document.getElementById("cartOverlay").style.display = "block";
});

function closeCart() {
    document.getElementById("shopCart").classList.remove("open");
    document.getElementById("cartOverlay").style.display = "none";
}

document.getElementById("closeShopCart").addEventListener("click", closeCart);
document.getElementById("cartOverlay").addEventListener("click", closeCart);

// ─── ADICIONAR PRODUTO ───
function addToCart(index) {
   
    cart.push(products[index]);
    updateCart();
}

// ─── REMOVER PRODUTO ───
function removeFromCart(index) {
    cart.splice(index, 1);
    updateCart();
}

// ─── FINALIZAR COMPRA ───
function finalizePurchase() {
    if (cart.length === 0) {
        alert("Your cart is empty!");
        return;
    }
    alert("Purchase completed successfully!");
    cart = [];
    updateCart();
    closeCart();
}

// ─── ATUALIZA CARRINHO ───
function updateCart() {
    const cartItems = document.getElementById("cartItems");
    const total = document.getElementById("total");
    const count = document.getElementById("cartCount");

    cartItems.innerHTML = '';
    let sum = 0;

    if (cart.length === 0) {
        cartItems.innerHTML = `
            <div style="text-align:center; color:rgba(255,255,255,0.3); margin-top: 60px;">
                <p style="font-size:2rem;">☕</p>
                <p style="margin-top:12px; font-size:0.9rem;">Your cart is empty</p>
            </div>
        `;
    } else {
        cart.forEach((product, index) => {
            sum += product.price;
            cartItems.innerHTML += `
                <div class="cartItem">
                    <div class="cartItemInfo">
                        <div class="cartItemName">${product.name}</div>
                        <div class="cartItemQty">Qty: 1</div>
                    </div>
                    <div class="cartItemPrice">R$ ${product.price.toFixed(2)}</div>
                    <button class="cartRemoveBtn" onclick="removeFromCart(${index})">✕</button>
                </div>
            `;
        });
    }

    total.innerHTML = `Total <span>R$ ${sum.toFixed(2)}</span>`;
    count.innerText = cart.length;
}

// ─── INICIALIZA ───
renderProducts();