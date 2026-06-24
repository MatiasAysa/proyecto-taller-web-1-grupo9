package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioUsuarioImp implements ServicioUsuario {

  private final RepositorioUsuario repositorioUsuario;
  private final RepositorioPresupuesto repositorioPresupuesto;
  private final RepositorioPerfilAlimentarioUsuario repositorioPerfilAlimentarioUsuario;

  @Autowired
  public ServicioUsuarioImp(
    RepositorioUsuario repositorioUsuario,
    RepositorioPresupuesto repositorioPresupuesto,
    RepositorioPerfilAlimentarioUsuario repositorioPerfilAlimentarioUsuario
  ) {
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioPresupuesto = repositorioPresupuesto;
    this.repositorioPerfilAlimentarioUsuario = repositorioPerfilAlimentarioUsuario;
  }

  @Override
  public boolean tienePerfilAlimentario(String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    if (usuario == null) {
      return false;
    }
    return usuario.getPerfilAlimentario() != null;
  }

  @Override
  public boolean tienePresupuesto(String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    if (usuario == null) {
      return false;
    }
    if (usuario.getPresupuestoSemanal() != null && usuario.getPresupuestoSemanal() > 0) {
      return true;
    }
    return false;
  }
}
