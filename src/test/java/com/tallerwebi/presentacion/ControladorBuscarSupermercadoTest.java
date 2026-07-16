package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Cordenandas;
import com.tallerwebi.dominio.Direccion;
import com.tallerwebi.dominio.ServicioBuscarSupermercado;
import com.tallerwebi.dominio.Supermercado;
import com.tallerwebi.dominio.excepcion.MuchasPeticionesServicioMapas;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class ControladorBuscarSupermercadoTest {

  private ServicioBuscarSupermercado servicioBuscarSupermercadoMock;
  private ControladorBuscarSupermercado controladorBuscarSupermercado;

  @BeforeEach
  public void inicializarObjetos() {
    servicioBuscarSupermercadoMock = Mockito.mock(ServicioBuscarSupermercado.class);
    controladorBuscarSupermercado =
      new ControladorBuscarSupermercado(servicioBuscarSupermercadoMock);
  }

  @Test
  public void queMuestreFormularioDeBuscarSupermercado() {
    //WHEN
    ModelAndView modelo = controladorBuscarSupermercado.mostrarFormularioParaDirecciones();

    //THEN
    assertThat(modelo.getViewName(), equalTo("busqueda-supermercados"));
    assertThat(modelo.getModel().get("direccion"), notNullValue());
    assertThat(modelo.getModel().get("direccion"), instanceOf(Direccion.class));
  }

  @Test
  public void queBusqueDireccionYMuestreLosSupermercados() {
    // GIVEN
    Direccion direccion = crearDireccion();
    Cordenandas coordenadas = crearCoordenadas();
    List<Supermercado> supermercados = new ArrayList<>();

    when(servicioBuscarSupermercadoMock.obtenerCordenadaActual(direccion)).thenReturn(coordenadas);
    when(
      servicioBuscarSupermercadoMock.buscarSupermercadosCercanos(
        coordenadas.getLatitud(),
        coordenadas.getLongitud()
      )
    )
      .thenReturn(supermercados);

    // WHEN
    ModelAndView modelo = controladorBuscarSupermercado.mostrarSupermercados(direccion);

    // THEN
    assertThat(modelo.getViewName(), equalTo("busqueda-supermercados"));
    assertThat(modelo.getModel().get("latitud"), equalTo(coordenadas.getLatitud()));
    assertThat(modelo.getModel().get("longitud"), equalTo(coordenadas.getLongitud()));
    assertThat(modelo.getModel().get("supermercados"), equalTo(supermercados));

    verify(servicioBuscarSupermercadoMock).obtenerCordenadaActual(direccion);
    verify(servicioBuscarSupermercadoMock)
      .buscarSupermercadosCercanos(coordenadas.getLatitud(), coordenadas.getLongitud());
    verify(servicioBuscarSupermercadoMock).calcularDistancias(supermercados, coordenadas);
  }

  @Test
  public void queSiNoEncuentraLaDireccionNoBusqueSupermercados() {
    // GIVEN
    Direccion direccion = crearDireccion();
    when(servicioBuscarSupermercadoMock.obtenerCordenadaActual(direccion)).thenReturn(null);

    // WHEN
    ModelAndView modelo = controladorBuscarSupermercado.mostrarSupermercados(direccion);

    // THEN
    assertThat(modelo.getViewName(), equalTo("busqueda-supermercados"));
    assertThat(modelo.getModel().get("latitud"), equalTo(0));
    assertThat(modelo.getModel().get("longitud"), equalTo(0));
    verify(servicioBuscarSupermercadoMock).obtenerCordenadaActual(direccion);

    Mockito
      .verify(servicioBuscarSupermercadoMock, Mockito.never())
      .buscarSupermercadosCercanos(Mockito.anyDouble(), Mockito.anyDouble());
  }

  @Test
  public void queSiHayMuchasPeticionesMuestreLaVistaDeError() {
    // GIVEN
    Direccion direccion = crearDireccion();
    when(servicioBuscarSupermercadoMock.obtenerCordenadaActual(direccion))
      .thenThrow(new MuchasPeticionesServicioMapas("Error", new RuntimeException()));
    // WHEN
    ModelAndView modelo = controladorBuscarSupermercado.mostrarSupermercados(direccion);

    // THEN
    assertThat(modelo.getViewName(), equalTo("error-mapas"));
    assertThat(modelo.getModel().get("mensaje"), notNullValue());
  }

  private Direccion crearDireccion() {
    Direccion direccion = new Direccion();
    direccion.setUbicacion("Av Corrientes");
    direccion.setNumero(100);
    return direccion;
  }

  private Cordenandas crearCoordenadas() {
    Cordenandas coordenadas = new Cordenandas();
    coordenadas.setLatitud(-34.12);
    coordenadas.setLongitud(-58.38);
    return coordenadas;
  }
}
