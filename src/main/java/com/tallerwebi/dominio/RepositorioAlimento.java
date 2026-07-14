package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAlimento {
  Alimento buscarPorId(Long id);

  List<Alimento> obtenerListaAlimentos();
}
