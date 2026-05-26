package com.tallerwebi.dominio;

public interface ServicioRegistroPerfilAlimentario {
  Boolean validarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO);

  Boolean guardarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO, String email);
}
