package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioBuscarSupermercado {
  Cordenandas obtenerCordenadaActual(Direccion direccion);
  List<Supermercado> buscarSupermercadosCercanos(Double latitud, Double longitud);
  void calcularDistancias(List<Supermercado> supermercados, Cordenandas ubicacion);
  Double calcularDistanciaMetros(Cordenandas origen, Cordenandas destino);
  Integer calcularTiempoCaminando(Double metros);
}
