let map = null;

const boton = document.getElementById("btnUbicacion");


window.addEventListener("load",() => {
    //EN UN FUTURO CAMBIAR LOS <P> POR IMPUT,PORQUE LA ALTITUD Y LONGITUD NO SE DEBE MOSTRAR,SON DATOS POCO IMPORTANTES PARA EL USUARIO
    const latitud = document.getElementById("latitud").textContent;
    const longitud = document.getElementById("longitud").textContent;

    console.log("lati:",latitud);
    console.log("log : ",longitud)

    if(latitud && longitud){
        boton.style.display = "none";

        mostrarMapa(parseFloat(latitud),parseFloat(longitud))
        dibujarSupermercados();
    }

})

function dibujarSupermercados(){
    const supermercados = document.querySelectorAll(".supermercado");

    supermercados.forEach(supermercado => {

        const nombre = supermercado.dataset.nombre;
        const latitud = parseFloat(supermercado.dataset.latitud);
        const longitud = parseFloat(supermercado.dataset.longitud);

        L.marker([latitud, longitud])
            .addTo(map)
            .bindTooltip(nombre);

    });
}


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
            console.log(L);
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

}



















