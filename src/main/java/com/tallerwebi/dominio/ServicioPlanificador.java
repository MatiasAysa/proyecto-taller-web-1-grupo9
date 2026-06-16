package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.List;

public interface ServicioPlanificador {
  PlanAlimenticio generarPlanParaUsuario(String email, String tipoDuracion)
    throws PresupuestoInsuficienteException, UsuarioInexistenteException;

  void armarEstructuraNutricionalYCostosNueva(
    PlanAlimenticio plan,
    List<Alimento> alimentos,
    float montoDisponible,
    int dias,
    int caloriasObjetivo
  );
}
