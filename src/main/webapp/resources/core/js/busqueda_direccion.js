/* global L, latUsuario, lonUsuario */
let map = null;
const iconoUsuario = L.icon({
    iconUrl: "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png",
    shadowUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",

    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

window.addEventListener("load",() => {
    //EN UN FUTURO CAMBIAR LOS <P> POR IMPUT,PORQUE LA ALTITUD Y LONGITUD NO SE DEBE MOSTRAR,SON DATOS POCO IMPORTANTES PARA EL USUARIO
    const latitud = document.getElementById("latitud").textContent;
    const longitud = document.getElementById("longitud").textContent;

    if(latitud && longitud){

        mostrarMapa(parseFloat(latitud),parseFloat(longitud))
        dibujarSupermercados();
    }

})

function dibujarSupermercados(){
    const supermercados = document.querySelectorAll(".supermercado");
    const grupo = L.featureGroup();

    supermercados.forEach(supermercado => {

        const direccion = supermercado.dataset.direccion;
        const distancia = supermercado.dataset.distancia;
        const minutos = supermercado.dataset.minutos;
        const nombre = supermercado.dataset.nombre;
        const latitud = parseFloat(supermercado.dataset.latitud);
        const longitud = parseFloat(supermercado.dataset.longitud);

        const marker = L.marker([latitud, longitud])
            .addTo(map)
            .bindTooltip(nombre, {
                permanent: true,
                direction: "top",
                offset: [0, -10]
            })
            .bindPopup(`
                <b>${nombre}</b><br>
                📍 ${direccion}<br>
                📏 ${distancia} metros<br>
                🚶 ${minutos} min caminando
            `);
        grupo.addLayer(marker);

    });
    grupo.addLayer(
        L.marker([latUsuario, lonUsuario])
    );

    // Hace zoom para que entren todos los marcadores
    map.fitBounds(grupo.getBounds(), {
        padding: [60, 60]
    });
}

function mostrarMapa(lat,lon){

    if(map){
        map.remove();
    }

    map = L.map("map").setView([lat,lon],15);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {maxZoom: 19}).addTo(map);

    // Marcador del usuario
    L.marker([lat, lon], {
        icon: iconoUsuario
    })
        .addTo(map)
        .bindPopup("👤<b>Tu ubicación</b>")
        .openPopup();

    // Radio de búsqueda (2000 m)
    L.circle([lat, lon], {
        radius: 2000,
        color: "#03A62C",
        fillColor: "#03A62C",
        fillOpacity: 0.15
    }).addTo(map);


}



















