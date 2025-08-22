let cart = []; // Armazena os produtos do carrinho

// Carrega os dados dos produtos do arquivo JSON
async function loadProductData() {
    try {
        const response = await fetch('../script/data/product.json'); 
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        console.error('Failed to load product data:', error);
        throw error;
    }
}

// Renderiza os produtos e já adiciona eventos
async function renderProducts() {
    const container = document.querySelector('.container'); 
    try {
        const products = await loadProductData();

        container.innerHTML = ''; // limpa o conteúdo antes

        products.forEach((product, index) => {
            const productHTML = `
                <div class="card">
                    <img src="${product.img}" alt="${product.name}">
                    <h3>${product.name}</h3>
                    <p>${product.desc}</p>
                    <span>R$ ${product.price.toFixed(2)}</span>
                    <button class="btnAdd" data-index="${index}">${product.btnAdd}</button>
                    <button class="btnRemove" data-index="${index}">${product.btnRemove}</button>
                </div>
            `;
            container.innerHTML += productHTML;
        });

        // adiciona eventos após renderizar
        const btnsAdd = document.querySelectorAll('.btnAdd');
        const btnsRemove = document.querySelectorAll('.btnRemove');

        btnsAdd.forEach(btn => {
            btn.addEventListener('click', () => {
                const index = btn.getAttribute('data-index');
                addToCart(products[index]);
            });
        });

        btnsRemove.forEach(btn => {
            btn.addEventListener('click', () => {
                const index = btn.getAttribute('data-index');
                removeFromCart(products[index]);
            });
        });

    } catch (error) {
        container.innerHTML = `<p>Erro ao carregar produtos.</p>`;
    }
}

// Adiciona ao carrinho de compras 
function addToCart(product) {
    cart.push(product);
    console.log("Product added:", product.name);
    console.log("Carrinho:", cart);
    totalPrice();
    createdCartContainer();
}

// Remove do carrinho
function removeFromCart(product) {
    const i = cart.indexOf(product);
    if (i > -1) {
        cart.splice(i, 1);
        console.log("Product removed:", product.name);
    }
    console.log("Carrinho:", cart);
    createdCartContainer();
    totalPrice();
}

// Cria o container do carrinho de compras
function createdCartContainer() {
    const cartContainer = document.createElement('div');
    if(cart.length > 0  ) {
         cartContainer.classList.add('cart-container');
         document.body.appendChild(cartContainer);
         console.log("Carrinho de compras criado com sucesso!");
    }
   else {
        console.log("Carrinho de compras vazio.sasas");
    }
}

// Mostra o carrinho de compras
function showCartContainer(container) {
}

// Fecha o carrinho de compras 
function closeCart() {
    const cart = document.querySelector(".shopCart");
    const closeBtn = document.getElementById("closeShopCart").addEventListener("click", () => {
        cart.style.display = "none";
    });
    

}
// Calcula o preço total da compra 
function totalPrice() {
    let total = cart.reduce((sum, product) => sum + product.price, 0);
    console.log("Total: R$ " + total.toFixed(2));
}

// inicia
renderProducts();
closeCart();