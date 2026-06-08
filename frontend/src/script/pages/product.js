const CART_STORAGE_KEY = "coffzin.cart.v1";

let products = [];
let cart = loadCart();

const productContainer = document.querySelector(".container");
const cartDrawer = document.getElementById("shopCart");
const cartOverlay = document.getElementById("cartOverlay");
const cartItems = document.getElementById("cartItems");
const cartTotal = document.getElementById("total");
const cartCount = document.getElementById("cartCount");
const checkoutBtn = document.getElementById("checkoutBtn");
const emptyCartBtn = document.getElementById("emptyCartBtn");

document.addEventListener("DOMContentLoaded", initProductsPage);

async function initProductsPage() {
    products = await loadProductData();
    renderProducts();
    bindCartEvents();
    updateCart();

    if (window.lucide) {
        lucide.createIcons();
    }
}

async function loadProductData() {
    const response = await fetch("../script/data/product.json");

    if (!response.ok) {
        throw new Error("Unable to load products");
    }

    return response.json();
}

function renderProducts() {
    productContainer.innerHTML = "";

    products.forEach((product) => {
        const card = document.createElement("article");
        card.className = "card";

        card.innerHTML = `
            <img src="${product.img}" alt="${product.name}">
            <h3>${product.name}</h3>
            <p>${product.desc}</p>
            <strong>${formatCurrency(product.price)}</strong>
            <button type="button" class="addToCartBtn" data-product-id="${product.id}">
                Add to cart
            </button>
        `;

        productContainer.appendChild(card);
    });
}

function bindCartEvents() {
    document.getElementById("openCart")?.addEventListener("click", openCart);
    document.getElementById("closeShopCart")?.addEventListener("click", closeCart);
    cartOverlay?.addEventListener("click", closeCart);
    emptyCartBtn?.addEventListener("click", emptyCart);
    checkoutBtn?.addEventListener("click", finalizePurchase);

    productContainer?.addEventListener("click", (event) => {
        const button = event.target.closest(".addToCartBtn");
        if (!button) return;

        addToCart(Number(button.dataset.productId));
    });

    cartItems?.addEventListener("click", (event) => {
        const button = event.target.closest("[data-cart-action]");
        if (!button) return;

        const productId = Number(button.dataset.productId);
        const action = button.dataset.cartAction;

        if (action === "increase") increaseQuantity(productId);
        if (action === "decrease") decreaseQuantity(productId);
        if (action === "remove") removeFromCart(productId);
    });
}

function openCart() {
    cartDrawer.classList.add("open");
    cartOverlay.classList.add("open");
}

function closeCart() {
    cartDrawer.classList.remove("open");
    cartOverlay.classList.remove("open");
}

function addToCart(productId) {
    const product = products.find((item) => item.id === productId);
    if (!product) return;

    const existingItem = cart.find((item) => item.id === product.id);

    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({
            id: product.id,
            name: product.name,
            price: Number(product.price),
            quantity: 1
        });
    }

    saveCart();
    updateCart();
    openCart();
}

function increaseQuantity(productId) {
    const item = cart.find((cartItem) => cartItem.id === productId);
    if (!item) return;

    item.quantity += 1;
    saveCart();
    updateCart();
}

function decreaseQuantity(productId) {
    const item = cart.find((cartItem) => cartItem.id === productId);
    if (!item) return;

    item.quantity -= 1;

    if (item.quantity <= 0) {
        removeFromCart(productId);
        return;
    }

    saveCart();
    updateCart();
}

function removeFromCart(productId) {
    cart = cart.filter((item) => item.id !== productId);
    saveCart();
    updateCart();
}

function emptyCart() {
    if (cart.length === 0) return;

    cart = [];
    saveCart();
    updateCart();
}

function finalizePurchase() {
    if (cart.length === 0) {
        alert("Your cart is empty!");
        return;
    }

    alert("Purchase completed successfully!");
    cart = [];
    saveCart();
    updateCart();
    closeCart();
}

function updateCart() {
    cartItems.innerHTML = "";

    const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
    const totalPrice = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

    if (cart.length === 0) {
        cartItems.innerHTML = `
            <div class="cartEmptyState">
                <p class="cartEmptyIcon">Coffee</p>
                <p>Your cart is empty</p>
            </div>
        `;
    } else {
        cart.forEach((item) => {
            const subtotal = item.price * item.quantity;
            const cartItem = document.createElement("div");
            cartItem.className = "cartItem";
            cartItem.innerHTML = `
                <div class="cartItemInfo">
                    <div class="cartItemName">${item.name}</div>
                    <div class="cartItemPrice">${formatCurrency(subtotal)}</div>
                    <div class="cartItemMeta">${formatCurrency(item.price)} each</div>
                </div>
                <div class="cartQuantity">
                    <button type="button" data-cart-action="decrease" data-product-id="${item.id}" aria-label="Decrease ${item.name} quantity">-</button>
                    <span>${item.quantity}</span>
                    <button type="button" data-cart-action="increase" data-product-id="${item.id}" aria-label="Increase ${item.name} quantity">+</button>
                </div>
                <button type="button" class="cartRemoveBtn" data-cart-action="remove" data-product-id="${item.id}" aria-label="Remove ${item.name}">
                    x
                </button>
            `;

            cartItems.appendChild(cartItem);
        });
    }

    cartTotal.innerHTML = `Total <span>${formatCurrency(totalPrice)}</span>`;
    cartCount.innerText = totalItems;
    cartCount.classList.toggle("isEmpty", totalItems === 0);
}

function loadCart() {
    try {
        const storedCart = JSON.parse(localStorage.getItem(CART_STORAGE_KEY)) || [];

        return storedCart
            .filter((item) => item.id && item.name && Number(item.price) >= 0 && Number(item.quantity) > 0)
            .map((item) => ({
                id: Number(item.id),
                name: item.name,
                price: Number(item.price),
                quantity: Number(item.quantity)
            }));
    } catch {
        return [];
    }
}

function saveCart() {
    localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(cart));
}

function formatCurrency(value) {
    return new Intl.NumberFormat("pt-BR", {
        style: "currency",
        currency: "BRL"
    }).format(value);
}
