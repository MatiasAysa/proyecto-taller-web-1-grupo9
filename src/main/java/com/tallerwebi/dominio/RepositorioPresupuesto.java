package com.tallerwebi.dominio;

public interface RepositorioPresupuesto {
  void guardarPresupuesto(Presupuesto presupuesto, Usuario usuario);

  Presupuesto buscarPresupuesto(Usuario usuario);
}
