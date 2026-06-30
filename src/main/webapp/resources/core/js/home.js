// Capturás el enlace de la navbar (asegurate de que tenga esta clase en el HTML)
const linkComoFunciona = document.querySelector(".link-como-funciona");

//evito que se cargue la pagina siguiente
linkComoFunciona.addEventListener("click", function (event) {
    event.preventDefault();


});