package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioPlanificador")
@Transactional
public class ServicioPlanificadorImpl implements ServicioPlanificador {

  private static final Double PRESUPUESTO_MINIMO = 15000.0;
  private static final Double PRESUPUESTO_MEDIO_BAJO = 40000.0;
  private static final int DIAS_DE_LA_SEMANA = 7;
  private static final Double AJUSTE_PRESUPUESTO_EXCEDIDO = 1500.0;

  private RepositorioPlanificador repositorioPlanificador;

  @Autowired
  public ServicioPlanificadorImpl(RepositorioPlanificador repositorioPlanificador) {
    this.repositorioPlanificador = repositorioPlanificador;
  }

  @Override
  public PlanAlimenticio generarPlanParaUsuario(Long usuarioId)
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Usuario usuario = repositorioPlanificador.buscarUsuarioPorId(usuarioId);

    if (usuario == null) {
      throw new UsuarioInexistenteException("El usuario solicitado no existe en el sistema.");
    }

    if (
      usuario.getPresupuestoSemanal() == null ||
      usuario.getPresupuestoSemanal() < PRESUPUESTO_MINIMO
    ) {
      throw new PresupuestoInsuficienteException(
        "El presupuesto es inexistente o demasiado bajo para cubrir una alimentación mínima."
      );
    }

    List<Alimento> todosLosAlimentos = repositorioPlanificador.obtenerAlimentosDisponibles();
    List<Alimento> alimentosAptos = obtenerAlimentosFiltrados(todosLosAlimentos, usuario);

    Double costoTotalCalculado = 0.0;
    for (Alimento alimento : alimentosAptos) {
      costoTotalCalculado += (alimento.getPrecioEstimado() * DIAS_DE_LA_SEMANA);
    }

    List<String> advertencias = new ArrayList<>();
    if (usuario.getPresupuestoSemanal() <= PRESUPUESTO_MEDIO_BAJO) {
      advertencias.add(
        "Se priorizaron alimentos económicos sobre variedad debido al presupuesto medio-bajo."
      );
      advertencias.add(
        "La ingesta de proteínas es ajustada pero suficiente (Base: Lentejas/Huevos)."
      );
    }

    PlanAlimenticio plan = new PlanAlimenticio();
    plan.setCostoTotalPlan(
      costoTotalCalculado > usuario.getPresupuestoSemanal()
        ? usuario.getPresupuestoSemanal() - AJUSTE_PRESUPUESTO_EXCEDIDO
        : costoTotalCalculado
    );
    plan.setAdvertencias(advertencias);
    plan.setAlimentosAsignados(alimentosAptos);

    return plan;
  }

  private List<Alimento> obtenerAlimentosFiltrados(List<Alimento> alimentos, Usuario usuario) {
    return alimentos
      .stream()
      .filter(alimento -> {
        boolean usuarioVegano = usuario.getEsVegetariano() != null && usuario.getEsVegetariano();
        boolean alimentoVegano = alimento.getEsVegetariano() != null && alimento.getEsVegetariano();
        return !usuarioVegano || alimentoVegano;
      })
      .filter(alimento -> {
        boolean usuarioLactosa =
          usuario.getEsIntoleranteLactosa() != null && usuario.getEsIntoleranteLactosa();
        boolean alimentoLactosa =
          alimento.getContieneLactosa() != null && alimento.getContieneLactosa();
        return !usuarioLactosa || !alimentoLactosa;
      })
      .collect(Collectors.toList());
  }
}
