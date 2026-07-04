package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioReceta {
  void guardarReceta(Comida receta);

  Comida buscarRecetaPorNombreYUsuario(String nombre, Usuario usuario);

  List<Comida> obtenerRecetasDeUsuario(Usuario usuario);
}
