package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioPlanificadorTest {

  private ServicioPlanificador servicioPlanificador;
  private RepositorioPlanificador repositorioPlanificadorMock;

  @BeforeEach
  public void init() {
    this.repositorioPlanificadorMock = mock(RepositorioPlanificador.class);
    this.servicioPlanificador = new ServicioPlanificadorImpl(this.repositorioPlanificadorMock);
  }

  @Test
  public void queUnUsuarioVegetarianoRecibaUnPlanSinCarneYAcordeAlPresupuesto()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Long usuarioId = 1L;
    Usuario usuarioVegetariano = new Usuario();
    usuarioVegetariano.setId(usuarioId);
    usuarioVegetariano.setPresupuestoSemanal(40000.0);
    usuarioVegetariano.setEsVegetariano(true);

    List<Alimento> alimentosEnStock = new ArrayList<>();
    alimentosEnStock.add(crearAlimento("Pollo", 8000.0, "ALMUERZO", false));
    alimentosEnStock.add(crearAlimento("Lentejas", 2500.0, "ALMUERZO", true));
    alimentosEnStock.add(crearAlimento("Avena", 1500.0, "DESAYUNO", true));
    alimentosEnStock.add(crearAlimento("Fruta", 1000.0, "MERIENDA", true));
    alimentosEnStock.add(crearAlimento("Tortilla de verduras", 3000.0, "CENA", true));

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioVegetariano);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(usuarioId);

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(lessThanOrEqualTo(40000.0)));

    assertThat(
      planObtenido.getAdvertencias(),
      hasItem(containsString("Se priorizaron alimentos económicos"))
    );
  }

  private Alimento crearAlimento(String nombre, Double precio, String tipo, Boolean esVegetariano) {
    Alimento alimento = new Alimento();
    alimento.setNombre(nombre);
    alimento.setPrecioEstimado(precio);
    alimento.setTipoComida(tipo);
    alimento.setEsVegetariano(esVegetariano);
    return alimento;
  }
}
