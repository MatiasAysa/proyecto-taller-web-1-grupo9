package com.tallerwebi.dominio;

public interface RepositorioRestriccionAlimentaria {
  void guardar(RestriccionAlimentaria restriccion);

  RestriccionAlimentaria buscarPorNombre(String nombre);
}
