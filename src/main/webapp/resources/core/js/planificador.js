document.addEventListener("DOMContentLoaded", () => {
    initFiltrosMenu();
    initTipsDinamicos();
    initCheckboxesPresupuesto();
});

function initFiltrosMenu() {
    const contenedorGrid = document.querySelector(".grilla-menu");
    if (!contenedorGrid) return;

    const barraFiltros = document.createElement("div");
    barraFiltros.className = "barra-filtros-js";
    barraFiltros.innerHTML = `
        <button class="btn-filtro active" data-tipo="TODOS">Todos</button>
        <button class="btn-filtro" data-tipo="DESAYUNO">Desayunos</button>
        <button class="btn-filtro" data-tipo="ALMUERZO">Almuerzos</button>
        <button class="btn-filtro" data-tipo="CENA">Cenas</button>
    `;
    contenedorGrid.insertBefore(barraFiltros, contenedorGrid.firstChild);

    const comidaRows = document.querySelectorAll(".comida");
    const botones = document.querySelectorAll(".btn-filtro");

    botones.forEach(boton => {
        boton.addEventListener("click", (e) => {
            botones.forEach(b => b.classList.remove("active"));
            e.target.classList.add("active");

            const tipoSeleccionado = e.target.getAttribute("data-tipo");
            comidaRows.forEach(row => {
                const tagTipoElemento = row.querySelector("strong");
                if(!tagTipoElemento) return;
                const tagTipo = tagTipoElemento.innerText.trim().toUpperCase();
                if (tipoSeleccionado === "TODOS" || tagTipo === tipoSeleccionado) {
                    row.style.display = "block";
                } else {
                    row.style.display = "none";
                }
            });
        });
    });
}

function initTipsDinamicos() {
    const comidas = document.querySelectorAll(".comida");
    comidas.forEach(comida => {
        const tipoElemento = comida.querySelector("strong");
        const nombreElemento = comida.querySelector(".nombre-alimento");
        const contenedorTip = comida.querySelector(".tip-combinacion-js");

        if (!tipoElemento || !nombreElemento || !contenedorTip) return;

        let tipo = tipoElemento.getAttribute("data-tipo") || tipoElemento.innerText;
        tipo = tipo.toUpperCase().trim();

        const nombreAlimento = nombreElemento.innerText.toLowerCase().trim();
        let textoTip = "Tip: Acompa\u00f1ar con abundante agua para mejorar la digesti\u00f3n.";
        if (nombreAlimento.includes("avena")) {
            textoTip = "Tip: Alimento alt\u00edsimo en fibra. Pod\u00e9s hidratarla con leche de almendras o agua para consumirla al toque.";
        } else if (nombreAlimento.includes("granola")) {
            textoTip = "Tip: Excelente densidad energ\u00e9tica. Va perfecto combinada con yogur firme para sumar prote\u00ednas.";
        } else if (nombreAlimento.includes("banana")) {
            textoTip = "Tip: Aporte clave de potasio e hidratos r\u00e1pidos. Consumila directo o pisada como endulzante natural.";
        } else if (nombreAlimento.includes("yogur")) {
            textoTip = "Tip: Gran aporte de probi\u00f3ticos para tu salud intestinal. Pod\u00e9s sumarle unas semillas arriba.";
        } else if (nombreAlimento.includes("lentejas") || nombreAlimento.includes("garbanzos")) {
            textoTip = "Tip: Prote\u00edna vegetal clave. Si las combin\u00e1s con arroz en el mismo d\u00eda, form\u00e1s una prote\u00edna completa de alta calidad.";
        } else if (nombreAlimento.includes("pollo") || nombreAlimento.includes("carne")) {
            textoTip = "Tip: Prote\u00edna animal de alto valor biol\u00f3gico. Ideal acompa\u00f1arlo con pur\u00e9 de calabaza o br\u00f3coli.";
        } else if (nombreAlimento.includes("huevos")) {
            textoTip = "Tip: La prote\u00edna m\u00e1s econ\u00f3mica y eficiente. Hacelos revueltos con un toque de espinaca para sumar hierro.";
        } else if (nombreAlimento.includes("merluza")) {
            textoTip = "Tip: Pescado magro de digesti\u00f3n ultra r\u00e1pida. Una cena perfecta para mantener bajo el aporte cal\u00f3rico.";
        } else if (nombreAlimento.includes("pan de molde")) {
            textoTip = "Tip: Tostalo un toque para mejorar su textura y untalo con queso descremado.";
        } else if (nombreAlimento.includes("frutos secos")) {
            textoTip = "Tip: Grasas saludables y saciantes. Control\u00e1 la porci\u00f3n (un pu\u00f1ado chico) porque son muy cal\u00f3ricos.";
        } else {
            if (tipo === "DESAYUNO") {
                textoTip = "Tip: Opci\u00f3n energ\u00e9tica para arrancar el d\u00eda. Control\u00e1 los az\u00facares agregados.";
            } else if (tipo === "ALMUERZO") {
                textoTip = "Tip: Comida central del d\u00eda. Asegur\u00e1 un buen balance de macronutrientes esenciales.";
            } else if (tipo === "CENA") {
                textoTip = "Tip: Comida liviana enfocada a mejorar la recuperaci\u00f3n muscular nocturna.";
            }
        }

        contenedorTip.innerText = textoTip;
    });
}

function initCheckboxesPresupuesto() {
    const comidas = document.querySelectorAll(".comida");
    if (comidas.length === 0) return;

    const contenedorPrincipal = document.querySelector(".contenedor-principal");
    const diasPlan = parseInt(contenedorPrincipal.getAttribute("data-dias")) || 7;
    const contenedorCostoBase = document.getElementById("contenedor-costo-base");
    const presupuestoTotalBruto = parseFloat(contenedorCostoBase.getAttribute("data-presupuesto-total")) || 0;
    const presupuestoPorDiaReal = presupuestoTotalBruto / diasPlan;

    const txtPresupuestoDiarioPuro = document.getElementById("txt-presupuesto-diario-puro");
    if (txtPresupuestoDiarioPuro) {
        txtPresupuestoDiarioPuro.innerText = "$" + presupuestoPorDiaReal.toFixed(2);
    }

    const txtCostoSeleccion = document.getElementById("txt-costo-seleccion-acumulado");
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

        if(txtCostoSeleccion) txtCostoSeleccion.innerText = "$" + costoAlimentosSeleccionados.toFixed(2);

        if (txtBalanceEstadoDiario) {
            if (itemsChecked === 0) {
                txtBalanceEstadoDiario.innerText = "Sin Alimentos";
                txtBalanceEstadoDiario.style.backgroundColor = "#7f8c8d";
                txtBalanceEstadoDiario.style.color = "#ffffff";
            } else if (costoAlimentosSeleccionados <= presupuestoPorDiaReal) {
                txtBalanceEstadoDiario.innerText = "Dentro del Presupuesto";
                txtBalanceEstadoDiario.style.backgroundColor = "#03A62C";
                txtBalanceEstadoDiario.style.color = "#ffffff";
            } else {
                txtBalanceEstadoDiario.innerText = "Presupuesto Excedido";
                txtBalanceEstadoDiario.style.backgroundColor = "#c0392b";
                txtBalanceEstadoDiario.style.color = "#ffffff";
            }
        }
        if(txtCalorias) txtCalorias.innerText = caloriasAcumuladas + " kcal";
        if(txtProteinas) txtProteinas.innerText = proteinasAcumuladas.toFixed(1) + "g";
        if(txtCarbohidratos) txtCarbohidratos.innerText = carbohidratosAcumulados.toFixed(1) + "g";
        if(txtGrasas) txtGrasas.innerText = grasasAcumuladas.toFixed(1) + "g";
    }

    comidas.forEach(comida => {
        const checkbox = comida.querySelector(".check-consumo-js");
        if(checkbox) {
            checkbox.checked = false;
            checkbox.addEventListener("change", () => {
                actualizarTotalesPorSeleccion();
            });
        }
    });

    actualizarTotalesPorSeleccion();
}