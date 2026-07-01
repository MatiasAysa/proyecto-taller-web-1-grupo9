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
        // Clonamos la primera fila como molde
        const nuevaFila = filasActuales[0].cloneNode(true);

        // LIMPIEZA TOTAL del clon para que no arrastre datos de Thymeleaf del servidor
        nuevaFila.querySelectorAll('input').forEach(input => {
            input.value = '';
            // Si Thymeleaf generó un atributo 'value' físico en el HTML, lo removemos
            input.removeAttribute('value');
        });

        contenedor.appendChild(nuevaFila);

        reindexarIngredientes();
        actualizarEstadoBotonesBorrar();
    });

    function reindexarIngredientes() {
        const filas = contenedor.querySelectorAll('.ingrediente-row');
        filas.forEach((fila, index) => {
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
            if (filas.length > 2) {
                btn.removeAttribute('disabled');
            } else {
                btn.setAttribute('disabled', 'true');
            }
        });
    }
});