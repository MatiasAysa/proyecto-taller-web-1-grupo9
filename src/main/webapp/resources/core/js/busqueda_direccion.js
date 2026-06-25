//NOTA : IMPLEMENTACION DE LEAFLET --> libreria de js (api)
// Y OPENstrepMap para proveer imagenes
let map = null;

const boton = document.getElementById("btnUbicacion");



//Evento que se dispara cuanod termina de cargar la pagina --> despues refactorizar este evento



window.addEventListener("load",() => {
    //EN UN FUTURO CAMBIAR LOS <P> POR IMPUT,PORQUE LA ALTITUD Y LONGITUD NO SE DEBE MOSTRAR,SON DATOS POCO IMPORTANTES PARA EL USUARIO
    const latitud = document.getElementById("latitud").textContent;
    const longitud = document.getElementById("longitud").textContent;

    console.log("lati:",latitud);
    console.log("log : ",longitud)

    if(latitud && longitud){
        boton.style.display = "none";

        mostrarMapa(parseFloat(latitud),parseFloat(longitud))
    }

})




document.getElementById("btnUbicacion").addEventListener("click",() => {
    if(!navigator.geolocation){
        alert("Tu navegador no permite la geolocalizacion")
        return;
    }

    navigator.geolocation.getCurrentPosition(
        (position) => {
            const latitud = position.coords.latitude;
            const longitud = position.coords.longitude;
            boton.style.display = "none";
            mostrarMapa(latitud,longitud);
        },
        (error) => {
            console.error(error);
            alert("No se puede obtener la localizacion")
        }
    )

})

function mostrarMapa(lat,lon){

    if(map){
        map.remove();
    }

    map = L.map("map").setView([lat,lon],15);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {maxZoom: 19}).addTo(map);

    L.marker([lat, lon]).addTo(map).bindPopup("Estás acá 📍").openPopup();

    /*
    supermercados.forEach(supermercado => {

        L.marker([
            supermercado.cordenandas.latitud,
            supermercado.cordenandas.longitud
        ])
            .addTo(map)
            .bindPopup(supermercado.nombre);

    });

     */
}



















