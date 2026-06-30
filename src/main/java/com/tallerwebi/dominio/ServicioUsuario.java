package com.tallerwebi.dominio;

public interface ServicioUsuario {
  boolean tienePerfilAlimentario(String email);

  boolean tienePresupuesto(String email);
}
