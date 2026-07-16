package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.DiaListaComprasDTO;
import com.tallerwebi.dominio.ItemCompra;
import com.tallerwebi.dominio.ServicioListaCompras;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

public class ControladorListaComprasTest {

  private ServicioListaCompras servicioListaComprasMock;
  private ControladorListaCompras controladorListaCompras;
  private HttpSession sesionMock;

  @BeforeEach
  public void inicializarObjetos() {
    servicioListaComprasMock = mock(ServicioListaCompras.class);
    sesionMock = mock(HttpSession.class);
    controladorListaCompras = new ControladorListaCompras(servicioListaComprasMock);
  }

  @Test
  public void debeRedirigirAlLoginSiNoHayUsuarioLogueado() {
    // GIVEN
    when(sesionMock.getAttribute("usuarioLogueadoEmail")).thenReturn(null);

    // WHEN
    ModelAndView vista = controladorListaCompras.mostrarListaCompras(null, sesionMock);

    // THEN
    assertThat(vista.getViewName(), equalTo("redirect:/login"));
  }

  @Test
  public void debeRedirigirAlPlanificadorSiNoSeleccionoComidas() {
    // GIVEN
    when(sesionMock.getAttribute("usuarioLogueadoEmail")).thenReturn("matias@gmail.com");

    // WHEN
    ModelAndView vista = controladorListaCompras.mostrarListaCompras(null, sesionMock);

    // THEN
    assertThat(vista.getViewName(), equalTo("redirect:/planificador"));
  }

  @Test
  public void debeRedirigirAlPlanificadorSiLasSeleccionesEstanVacias() {
    // GIVEN
    MultiValueMap<String, String> selecciones = new LinkedMultiValueMap<>();

    when(sesionMock.getAttribute("usuarioLogueadoEmail")).thenReturn("matias@gmail.com");

    // WHEN
    ModelAndView vista = controladorListaCompras.mostrarListaCompras(selecciones, sesionMock);

    // THEN
    assertThat(vista.getViewName(), equalTo("redirect:/planificador"));
  }

  @Test
  public void debeMostrarCorrectamenteLaListaDeCompras() throws UsuarioInexistenteException {
    // GIVEN
    when(sesionMock.getAttribute("usuarioLogueadoEmail")).thenReturn("matias@gmail.com");
    MultiValueMap<String, String> selecciones = crearSelecciones();
    DiaListaComprasDTO dia = crearDia();
    List<DiaListaComprasDTO> dias = List.of(dia);
    List<ItemCompra> items = crearItems();

    when(servicioListaComprasMock.armarDiasSeleccionados(selecciones)).thenReturn(dias);
    when(servicioListaComprasMock.generarListaCompras(anyList())).thenReturn(items);
    when(servicioListaComprasMock.calcularTotalListaCompras(items)).thenReturn(25000.0);
    when(servicioListaComprasMock.mostrarDtosTestear(anyString())).thenReturn(List.of("Dato"));

    // WHEN
    ModelAndView vista = controladorListaCompras.mostrarListaCompras(selecciones, sesionMock);

    // THEN
    assertThat(vista.getViewName(), equalTo("lista-compras"));
    verify(servicioListaComprasMock).calcularPrecios(items);
    verify(sesionMock).setAttribute(eq("modeloLista"), any(ModelMap.class));
  }

  @Test
  public void debeRedirigirAlLoginSiElUsuarioNoExiste() throws UsuarioInexistenteException {
    // GIVEN
    when(sesionMock.getAttribute("usuarioLogueadoEmail")).thenReturn("matias@gmail.com");
    MultiValueMap<String, String> selecciones = crearSelecciones();
    DiaListaComprasDTO dia = crearDia();
    List<ItemCompra> items = crearItems();
    when(servicioListaComprasMock.armarDiasSeleccionados(any())).thenReturn(List.of(dia));
    when(servicioListaComprasMock.generarListaCompras(anyList())).thenReturn(items);
    when(servicioListaComprasMock.calcularTotalListaCompras(anyList())).thenReturn(1000.0);
    doNothing().when(servicioListaComprasMock).calcularPrecios(anyList());

    when(servicioListaComprasMock.mostrarDtosTestear(anyString()))
      .thenThrow(new UsuarioInexistenteException("Usuario Inexistente"));

    // WHEN
    ModelAndView vista = controladorListaCompras.mostrarListaCompras(selecciones, sesionMock);

    // THEN
    assertThat(vista.getViewName(), equalTo("redirect:/login"));
  }

  @Test
  public void debeInvocarTodosLosMetodosDelServicio() throws UsuarioInexistenteException {
    // GIVEN
    when(sesionMock.getAttribute("usuarioLogueadoEmail")).thenReturn("matias@gmail.com");
    MultiValueMap<String, String> selecciones = crearSelecciones();
    DiaListaComprasDTO dia = crearDia();
    List<ItemCompra> items = crearItems();

    when(servicioListaComprasMock.armarDiasSeleccionados(any())).thenReturn(List.of(dia));
    when(servicioListaComprasMock.generarListaCompras(anyList())).thenReturn(items);
    when(servicioListaComprasMock.calcularTotalListaCompras(anyList())).thenReturn(1000.0);
    when(servicioListaComprasMock.mostrarDtosTestear(anyString())).thenReturn(List.of("dato"));

    // WHEN
    controladorListaCompras.mostrarListaCompras(selecciones, sesionMock);

    // THEN
    verify(servicioListaComprasMock).armarDiasSeleccionados(any());
    verify(servicioListaComprasMock).generarListaCompras(anyList());
    verify(servicioListaComprasMock).calcularPrecios(anyList());
    verify(servicioListaComprasMock).calcularTotalListaCompras(anyList());
    verify(servicioListaComprasMock).mostrarDtosTestear(anyString());
  }

  private DiaListaComprasDTO crearDia() {
    Comida comida = new Comida();

    DiaListaComprasDTO dia = new DiaListaComprasDTO();
    dia.setComidas(List.of(comida));

    return dia;
  }

  private MultiValueMap<String, String> crearSelecciones() {
    MultiValueMap<String, String> selecciones = new LinkedMultiValueMap<>();

    selecciones.add("Lunes", "Pollo");

    return selecciones;
  }

  private List<ItemCompra> crearItems() {
    return List.of(new ItemCompra());
  }
}
