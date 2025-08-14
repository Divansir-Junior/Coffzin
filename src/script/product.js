
async function loadProductData() {
    try {
        const response = await fetch('../script/data/product.json'); 
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Failed to load product data:', error);
        throw error;
    }
}


async function renderProducts() {
    const container = document.querySelector('.container'); 


    try {
        const products = await loadProductData();

        container.innerHTML = ''; // limpa o conteúdo antes

        products.forEach(product => {
    const productHTML = `
        <div class="card">
            <img src="${product.img}" alt="${product.name}">
            <h3>${product.name}</h3>
            <p>${product.desc}</p>
            <span>R$ ${product.price.toFixed(2)}</span>
        </div>
    `;
    container.innerHTML += productHTML;
});


    } catch (error) {
        container.innerHTML = `<p>Erro ao carregar produtos.</p>`;
    }
}

renderProducts();
