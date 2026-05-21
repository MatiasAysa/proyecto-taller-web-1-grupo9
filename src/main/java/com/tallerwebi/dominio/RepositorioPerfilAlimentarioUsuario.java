package com.tallerwebi.dominio;

public interface RepositorioPerfilAlimentarioUsuario {
  void guardar(PerfilAlimentarioUsuario perfilAlimentario);

  PerfilAlimentarioUsuario buscarPorId(Long id);
}
