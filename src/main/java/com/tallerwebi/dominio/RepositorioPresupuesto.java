package com.tallerwebi.dominio;

public interface RepositorioPresupuesto {
  void guardarPresupuesto(Presupuesto presupuesto, String email);

  Presupuesto buscarPresupuesto(String email);
}
