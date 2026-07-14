const modal = document.getElementById('modalConfirmacion');
const formEliminar = document.getElementById('formEliminarReceta');

function abrirModal(idReceta) {
    // Le seteamos dinámicamente la ruta con el ID de la receta que se clickeó
    formEliminar.action = '/spring/mis-recetas/eliminar/' + idReceta;
    // .showModal() es nativo de HTML5, abre el diálogo en modo modal (bloquea el fondo)
    modal.showModal();
}

function cerrarModal() {
    modal.close();
}