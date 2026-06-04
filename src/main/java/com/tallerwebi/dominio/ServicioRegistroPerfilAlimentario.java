package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;

public interface ServicioRegistroPerfilAlimentario {
  void validarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO);

  void guardarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO, String email)
    throws UsuarioInexistenteException;
}
