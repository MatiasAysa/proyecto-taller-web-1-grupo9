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
    ModelAndView modelo = controladorBuscarSupermercado.mostrarFormularioParaDirecciones();

    assertThat(modelo.getViewName(), equalTo("busqueda-supermercados"));
    assertThat(modelo.getModel().get("direccion"), notNullValue());
    assertThat(modelo.getModel().get("direccion"), instanceOf(Direccion.class));
  }

  @Test
  public void queBusqueDireccionYDevuelvaCoordenadas() {
    Direccion direccion = new Direccion();
    direccion.setUbicacion("Av Corrientes");
    direccion.setNumero(100);

    Cordenandas coordenadas = new Cordenandas();
    coordenadas.setLatitud(-34.1243);
    coordenadas.setLongitud(34.1243);

    when(servicioBuscarSupermercadoMock.obtenerCordenadaActual(direccion)).thenReturn(coordenadas);

    ModelAndView modelo = controladorBuscarSupermercado.mostrarSupermercados(direccion);

    assertThat(modelo.getViewName(), equalTo("busqueda-supermercados"));
    assertThat(modelo.getModel().get("latitud"), equalTo(-34.1243));
    assertThat(modelo.getModel().get("longitud"), equalTo(34.1243));

    verify(servicioBuscarSupermercadoMock).obtenerCordenadaActual(direccion);
  }

  @Test
  public void queSiNoEncuentraDireccionDevuelvaCero() {
    Direccion direccion = new Direccion();
    direccion.setUbicacion("Av Corrientes");
    direccion.setNumero(100);

    when(servicioBuscarSupermercadoMock.obtenerCordenadaActual(direccion)).thenReturn(null);
    ModelAndView modelo = controladorBuscarSupermercado.mostrarSupermercados(direccion);

    assertThat(modelo.getModel().get("latitud"), equalTo(0));
    assertThat(modelo.getModel().get("longitud"), equalTo(0));
    verify(servicioBuscarSupermercadoMock).obtenerCordenadaActual(direccion);
  }
}
