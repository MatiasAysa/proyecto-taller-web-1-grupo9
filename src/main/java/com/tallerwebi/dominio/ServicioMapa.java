package com.tallerwebi.dominio;

public interface ServicioMapa {
  String obtenerCoordenadas(String direccion);

  String obtenerSupermercados(Double latitud, Double longitud);
}
