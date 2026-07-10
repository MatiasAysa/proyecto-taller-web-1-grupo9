package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAlimento {
  Alimento buscarPorId(Long id);

  List<Alimento> obtenerTodosLosAlimentos();

  Alimento obtenerPorNombreGenerico(String nombre);

  void guardar(Alimento a1);
}
