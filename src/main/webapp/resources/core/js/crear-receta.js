document.addEventListener('DOMContentLoaded', function() {
    const contenedor = document.getElementById('contenedor-ingredientes');
    const btnAgregar = document.getElementById('btn-agregar-ingrediente');


    contenedor.addEventListener('click', function(e) {
        const btnBorrar = e.target.closest('.btn-remove-ingrediente');
        if (btnBorrar) {
            const filas = contenedor.querySelectorAll('.ingrediente-row');
            if (filas.length > 2) {
                btnBorrar.closest('.ingrediente-row').remove();
                reindexarIngredientes();
                actualizarEstadoBotonesBorrar();
            }
        }
    });


    btnAgregar.addEventListener('click', function() {
        const filasActuales = contenedor.querySelectorAll('.ingrediente-row');
        const nuevaFila = filasActuales[0].cloneNode(true);

        nuevaFila.querySelectorAll('input').forEach(input => {
            input.value = '';
            input.removeAttribute('name');
        });

        const btnBorrarNuevaFila = nuevaFila.querySelector('.btn-remove-ingrediente');
        if (btnBorrarNuevaFila) {
            btnBorrarNuevaFila.removeAttribute('disabled');
        }

        // Inyectamos en el DOM
        contenedor.appendChild(nuevaFila);

        // Ejecutamos la reindexación obligatoria
        reindexarIngredientes();
        actualizarEstadoBotonesBorrar();
    });

    function reindexarIngredientes() {
        const filas = contenedor.querySelectorAll('.ingrediente-row');
        filas.forEach((fila, index) => {
            // Buscamos el input de alimento por su clase específica
            const inputAlimento = fila.querySelector('.alimento-input');
            if (inputAlimento) {
                inputAlimento.name = `ingredientes[${index}].nombre`;
            }

            const inputCantidad = fila.querySelector('input[type="number"]');
            if (inputCantidad) {
                inputCantidad.name = `ingredientes[${index}].cantidad`;
            }
        });
    }

    function actualizarEstadoBotonesBorrar() {
        const filas = contenedor.querySelectorAll('.ingrediente-row');
        filas.forEach(fil => {
            const btn = fil.querySelector('.btn-remove-ingrediente');
            if (btn) {
                if (filas.length > 2) {
                    btn.removeAttribute('disabled');
                } else {
                    btn.setAttribute('disabled', 'true');
                }
            }
        });
    }
});