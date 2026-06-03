package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioRegistroPerfilAlimentario {
  void validarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO);

  void guardarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO, String email)
    throws UsuarioExistente;
}
