document.addEventListener("DOMContentLoaded", () => {
    initCheckboxesPresupuesto();
    initFiltrosMenuAlternativo();
    initTabsSemanas();
    initValidacionSeleccionCompleta();
});

function initCheckboxesPresupuesto() {
    const comidas = document.querySelectorAll(".comida-bloque");
    if (comidas.length === 0) return;

    const contenedorCostoBase = document.getElementById("contenedor-costo-base");
    const presupuestoTotalBruto = parseFloat(contenedorCostoBase?.getAttribute("data-presupuesto-total")) || 0;

    const txtCostoSelection = document.getElementById("txt-costo-seleccion-acumulado");
    const txtBalanceEstadoTotal = document.getElementById("txt-balance-estado-total");

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

        if (txtCostoSelection) {
            txtCostoSelection.innerText = "$" + costoAlimentosSeleccionados.toFixed(2);
        }
        if (txtBalanceEstadoTotal) {
            if (costoAlimentosSeleccionados <= presupuestoTotalBruto) {
                txtBalanceEstadoTotal.innerText = "Dentro del Presupuesto";
                txtBalanceEstadoTotal.style.backgroundColor = "#03A62C";
                txtBalanceEstadoTotal.style.color = "#ffffff";
            } else {
                txtBalanceEstadoTotal.innerText = "Presupuesto Excedido";
                txtBalanceEstadoTotal.style.backgroundColor = "#c0392b";
                txtBalanceEstadoTotal.style.color = "#ffffff";
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

function initTabsSemanas() {
    const contenedorTabs = document.getElementById("contenedor-bloques-semanas");
    const tarjetasDias = document.querySelectorAll(".tarjeta-dia-kanban");

    if (!contenedorTabs || tarjetasDias.length === 0) return;

    const diasPorSemana = 7;
    const totalDias = tarjetasDias.length;
    const totalSemanas = Math.ceil(totalDias / diasPorSemana);

    if (totalSemanas <= 1) {
        document.querySelector(".seccion-navegacion-semanas")?.remove();
        return;
    }

    for (let i = 1; i <= totalSemanas; i++) {
        const botonTab = document.createElement("button");
        botonTab.type = "button";
        botonTab.classList.add("tab-semana-btn");
        botonTab.innerText = `Semana ${i}`;
        botonTab.setAttribute("data-semana", i);

        if (i === 1) botonTab.classList.add("tab-activa");

        botonTab.addEventListener("click", () => {
            document.querySelectorAll(".tab-semana-btn").forEach(btn => btn.classList.remove("tab-activa"));
            botonTab.classList.add("tab-activa");
            filtrarDiasPorSemana(i);
        });

        contenedorTabs.appendChild(botonTab);
    }

    function filtrarDiasPorSemana(numSemana) {
        const diaInicio = ((numSemana - 1) * diasPorSemana) + 1;
        const diaFin = numSemana * diasPorSemana;

        tarjetasDias.forEach(tarjeta => {
            const numeroDiaAttr = parseInt(tarjeta.getAttribute("th:data-numero-dia") || tarjeta.getAttribute("data-numero-dia"));

            if (numeroDiaAttr >= diaInicio && numeroDiaAttr <= diaFin) {
                tarjeta.style.display = "flex";
            } else {
                tarjeta.style.display = "none";
            }
        });
    }
    filtrarDiasPorSemana(1);
}

function initFiltrosMenuAlternativo() {
    const cabeceras = document.querySelectorAll(".cabecera-tarjeta-dia");
    if(cabeceras.length === 0) return;
}

function initValidacionSeleccionCompleta() {
    const formularioPlan = document.querySelector("form.resultado-plan");
    if (!formularioPlan) return;

    formularioPlan.addEventListener("submit", (evento) => {
        const tarjetasDias = document.querySelectorAll(".tarjeta-dia-kanban");
        let planValido = true;
        let primerDiaIncompleto = null;
        let numeroPrimerDiaIncompleto = null;
        const diasIncompletos = [];

        tarjetasDias.forEach(tarjeta => {
            const numeroDia = tarjeta.getAttribute("data-numero-dia") || tarjeta.getAttribute("th:data-numero-dia");
            const momentos = tarjeta.querySelectorAll(".grupo-momento-comida");
            let diaCompleto = true;

            momentos.forEach(momento => {
                const checkboxesMarcados = momento.querySelectorAll(".check-consumo-js:checked");
                if (checkboxesMarcados.length === 0) {
                    diaCompleto = false;
                }
            });

            if (!diaCompleto) {
                planValido = false;
                diasIncompletos.push(numeroDia);
                if (!primerDiaIncompleto) {
                    primerDiaIncompleto = tarjeta;
                    numeroPrimerDiaIncompleto = parseInt(numeroDia);
                }
            }
        });
        if (!planValido) {
            evento.preventDefault();

            alert(`¡Atención! Para continuar debés seleccionar al menos un Desayuno, un Almuerzo y una Cena para todos los días.\n\nDías incompletos: ${diasIncompletos.join(", ")}`);

            if (numeroPrimerDiaIncompleto) {
                const diasPorSemana = 7;
                const semanaDelDiaIncompleto = Math.ceil(numeroPrimerDiaIncompleto / diasPorSemana);
                const botonTabSemana = document.querySelector(`.tab-semana-btn[data-semana="${semanaDelDiaIncompleto}"]`);

                if (botonTabSemana && !botonTabSemana.classList.contains("tab-activa")) {
                    botonTabSemana.click();
                }
            }
            if (primerDiaIncompleto) {
                primerDiaIncompleto.scrollIntoView({ behavior: "smooth", block: "center" });
                primerDiaIncompleto.style.outline = "2px solid #c0392b";
                setTimeout(() => {
                    primerDiaIncompleto.style.outline = "none";
                }, 3000);
            }
        }
    });
}