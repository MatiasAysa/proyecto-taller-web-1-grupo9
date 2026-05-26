package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;

public interface ServicioPlanificador {
  PlanAlimenticio generarPlanParaUsuario(Long usuarioId)
    throws PresupuestoInsuficienteException, UsuarioInexistenteException;
}
