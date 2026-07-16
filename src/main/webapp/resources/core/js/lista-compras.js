let totalEstimado = 0;

document.addEventListener("DOMContentLoaded", function() {
    const totalElemento = document.getElementById("total-estimado");
    if (totalElemento) {
        totalEstimado = parseFloat(totalElemento.getAttribute("data-total-inicial")) || 0;
    }
    const elementosMacro = document.querySelectorAll(".macro-texto");
    elementosMacro.forEach(el => {
        const textoOriginal = el.textContent;
        const textoProcesado = textoOriginal.replace(/(\d+\.\d+)/g, function(match) {
            const numero = parseFloat(match);
            return !isNaN(numero) ? numero.toFixed(2) : match;
        });
        el.textContent = textoProcesado;
    });
});

function toggleComprado(elemento) {
    const precioItem = parseFloat(elemento.getAttribute("data-precio")) || 0;
    const totalElemento = document.getElementById("total-estimado");

    if (!elemento.classList.contains("comprado")) {
        elemento.classList.add("comprado");
        totalEstimado -= precioItem;
    } else {
        elemento.classList.remove("comprado");
        totalEstimado += precioItem;
    }

    if (totalEstimado < 0) totalEstimado = 0;

    if (totalElemento) {
        totalElemento.textContent = "$" + totalEstimado.toFixed(2);
    }
}