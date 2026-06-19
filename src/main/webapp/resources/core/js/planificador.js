document.addEventListener("DOMContentLoaded", () => {
    initCheckboxesPresupuesto();
    initFiltrosMenuAlternativo();
});

function initCheckboxesPresupuesto() {
    const comidas = document.querySelectorAll(".comida-bloque");
    if (comidas.length === 0) return;

    const contenedorPrincipal = document.querySelector(".contenedor-principal");
    const contenedorCostoBase = document.getElementById("contenedor-costo-base");
    const diasPlan = parseInt(contenedorPrincipal?.getAttribute("data-dias")) || 7;
    const presupuestoTotalBruto = parseFloat(contenedorCostoBase?.getAttribute("data-presupuesto-total")) || 0;
    const presupuestoPorDiaReal = presupuestoTotalBruto / diasPlan;

    const txtPresupuestoDiarioPuro = document.getElementById("txt-presupuesto-diario-puro");
    if (txtPresupuestoDiarioPuro && presupuestoPorDiaReal > 0) {
        txtPresupuestoDiarioPuro.innerText = "$" + presupuestoPorDiaReal.toFixed(2);
    }

    const txtCostoSelection = document.getElementById("txt-costo-seleccion-acumulado");
    const txtBalanceEstadoDiario = document.getElementById("txt-balance-estado-diario");

    const txtCalorias = document.getElementById("txt-calorias-dinamicas");
    const txtProteinas = document.getElementById("txt-proteinas-dinamicas");
    const txtCarbohidratos = document.getElementById("txt-carbohidratos-dinamicos");
    const txtGrasas = document.getElementById("txt-grasas-dinamicas");

    function actualizarTotalesPorSeleccion() {
        let costoAlimentosSeleccionados = 0;
        let caloriasAcumuladas = 0;
        let proteinasAcumuladas = 0;
        let carbohidratosAcumulados = 0;
        let grasasAcumuladas = 0;
        let itemsChecked = 0;

        comidas.forEach(comida => {
            const cb = comida.querySelector(".check-consumo-js");
            if (cb && cb.checked) {
                costoAlimentosSeleccionados += parseFloat(comida.getAttribute("data-precio")) || 0;
                caloriasAcumuladas += parseInt(comida.getAttribute("data-calorias")) || 0;
                proteinasAcumuladas += parseFloat(comida.getAttribute("data-proteinas")) || 0;
                carbohidratosAcumulados += parseFloat(comida.getAttribute("data-carbohidratos")) || 0;
                grasasAcumuladas += parseFloat(comida.getAttribute("data-grasas")) || 0;

                comida.style.opacity = "1";
                itemsChecked++;
            } else {
                comida.style.opacity = "0.65";
            }
        });

        if (txtCostoSelection) txtCostoSelection.innerText = "$" + costoAlimentosSeleccionados.toFixed(2);

        if (txtBalanceEstadoDiario) {
            if (itemsChecked === 0) {
                txtBalanceEstadoDiario.innerText = "Sin Alimentos";
                txtBalanceEstadoDiario.style.backgroundColor = "#7f8c8d";
                txtBalanceEstadoDiario.style.color = "#ffffff";
            } else if (costoAlimentosSeleccionados <= presupuestoTotalBruto) {
                txtBalanceEstadoDiario.innerText = "Dentro del Presupuesto";
                txtBalanceEstadoDiario.style.backgroundColor = "#03A62C";
                txtBalanceEstadoDiario.style.color = "#ffffff";
            } else {
                txtBalanceEstadoDiario.innerText = "Presupuesto Excedido";
                txtBalanceEstadoDiario.style.backgroundColor = "#c0392b";
                txtBalanceEstadoDiario.style.color = "#ffffff";
            }
        }

        if (txtCalorias) txtCalorias.innerText = caloriasAcumuladas + " kcal";
        if (txtProteinas) txtProteinas.innerText = proteinasAcumuladas.toFixed(1) + "g";
        if (txtCarbohidratos) txtCarbohidratos.innerText = carbohidratosAcumulados.toFixed(1) + "g";
        if (txtGrasas) txtGrasas.innerText = grasasAcumuladas.toFixed(1) + "g";
    }
    comidas.forEach(comida => {
        const checkbox = comida.querySelector(".check-consumo-js");
        if (checkbox) {
            checkbox.addEventListener("change", () => {
                actualizarTotalesPorSeleccion();
            });
        }
    });
    actualizarTotalesPorSeleccion();
}

function initFiltrosMenuAlternativo() {
    const cabeceras = document.querySelectorAll(".cabecera-tarjeta-dia");
    if(cabeceras.length === 0) return;
}