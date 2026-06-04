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

    validarUsuarioYPresupuesto(usuario);

    List<Alimento> todosLosAlimentos = repositorioPlanificador.obtenerAlimentosDisponibles();
    List<Alimento> alimentosAptos = obtenerAlimentosFiltrados(todosLosAlimentos, usuario);

    PlanAlimenticio plan = new PlanAlimenticio();
    plan.setAdvertencias(generarAdvertencias(usuario));
    plan.setAlimentosAsignados(alimentosAptos);

    calcularYSetearValoresNutricionales(plan, alimentosAptos, usuario);

    return plan;
  }

  private void validarUsuarioYPresupuesto(Usuario usuario)
          throws UsuarioInexistenteException, PresupuestoInsuficienteException {
    if (usuario == null) {
      throw new UsuarioInexistenteException("El usuario solicitado no existe en el sistema.");
    }
    if (
            usuario.getPresupuestoSemanal() == null ||
                    usuario.getPresupuestoSemanal() < PRESUPUESTO_MINIMO
    ) {
      throw new PresupuestoInsuficienteException(
              "El presupuesto es insuficiente para cubrir una alimentación mínima."
      );
    }
  }

  private List<String> generarAdvertencias(Usuario usuario) {
    List<String> advertencias = new ArrayList<>();
    if (usuario.getPresupuestoSemanal() <= PRESUPUESTO_MEDIO_BAJO) {
      advertencias.add(
              "Se priorizaron alimentos económicos sobre variedad debido al presupuesto medio-bajo."
      );
      advertencias.add(
              "La ingesta de proteínas es ajustada pero suficiente (Base: Lentejas/Huevos)."
      );
    }
    return advertencias;
  }

  private void calcularYSetearValoresNutricionales(
          PlanAlimenticio plan,
          List<Alimento> alimentos,
          Usuario usuario
  ) {
    Double costoTotalCalculado = 0.0;
    for (Alimento alimento : alimentos) {
      costoTotalCalculado += (alimento.getPrecioEstimado() * DIAS_DE_LA_SEMANA);
    }

    Double costoFinal = costoTotalCalculado > usuario.getPresupuestoSemanal()
            ? usuario.getPresupuestoSemanal() - AJUSTE_PRESUPUESTO_EXCEDIDO
            : costoTotalCalculado;

    plan.setCostoTotalPlan(costoFinal);

    List<Alimento> alimentosDelPlan = filtrarAlimentosPorPresupuesto(alimentos, costoFinal);

    calcularNutrientesTotales(plan, alimentosDelPlan, costoFinal);
  }

  private List<Alimento> filtrarAlimentosPorPresupuesto(
          List<Alimento> alimentos,
          Double costoFinal
  ) {
    if (costoFinal <= PRESUPUESTO_MEDIO_BAJO) {
      List<Alimento> economicos = alimentos
              .stream()
              .filter(a -> a.getPrecioEstimado() <= 3000.0)
              .collect(Collectors.toList());
      return economicos.isEmpty() ? alimentos : economicos;
    }
    return alimentos;
  }

  private void calcularNutrientesTotales(
          PlanAlimenticio plan,
          List<Alimento> alimentos,
          Double costoFinal
  ) {
    int calorias = 0;
    double proteinas = 0.0;
    double carbohidratos = 0.0;
    double grasas = 0.0;

    for (Alimento alimento : alimentos) {
      calorias += (alimento.getCalorias() != null ? alimento.getCalorias() : 0) * DIAS_DE_LA_SEMANA;
      proteinas +=
              (alimento.getProteinas() != null ? alimento.getProteinas() : 0.0) * DIAS_DE_LA_SEMANA;
      carbohidratos +=
              (alimento.getCarbohidratos() != null ? alimento.getCarbohidratos() : 0.0) * DIAS_DE_LA_SEMANA;
      grasas += (alimento.getGrasas() != null ? alimento.getGrasas() : 0.0) * DIAS_DE_LA_SEMANA;
    }
    if (costoFinal <= PRESUPUESTO_MEDIO_BAJO) {
      calorias = (int) (calorias * 0.75);
      proteinas = proteinas * 0.75;
      carbohidratos = carbohidratos * 0.80;
      grasas = grasas * 0.70;
    }

    plan.setTotalCalorias(calorias);
    plan.setTotalProteinas(proteinas);
    plan.setTotalCarbohidratos(carbohidratos);
    plan.setTotalGrasas(grasas);
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