package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.DatosClientePanel;
import com.tallerwebi.dominio.ItemDespensa;
import com.tallerwebi.dominio.ItemDespensaDTO;
import com.tallerwebi.dominio.ServicioBuscarSupermercado;
import com.tallerwebi.dominio.ServicioDespensa;
import com.tallerwebi.dominio.ServicioUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPanelclienteTest {

  private static final String ATT_EMAIL_SESION = "usuarioLogueadoEmail";
  private static final String RUTA_LOGIN = "redirect:/login";
  private static final String EMAIL_VALIDO = "cliente@test.com";

  private ServicioUsuario servicioUsuarioMock;
  private ServicioBuscarSupermercado servicioBuscarSupermercadoMock;
  private ServicioDespensa servicioDespensaMock;
  private HttpSession sessionMock;
  private ControladorPanelcliente controlador;

  @BeforeEach
  public void init() {
    servicioUsuarioMock = mock(ServicioUsuario.class);
    servicioBuscarSupermercadoMock = mock(ServicioBuscarSupermercado.class);
    servicioDespensaMock = mock(ServicioDespensa.class);
    sessionMock = mock(HttpSession.class);

    controlador =
      new ControladorPanelcliente(
        servicioUsuarioMock,
        servicioBuscarSupermercadoMock,
        servicioDespensaMock
      );
  }

  // ==========================================
  // TESTS: irAPanelCliente
  // ==========================================

  @Test
  public void irAPanelClienteConUsuarioLogueadoRetornaDashboard() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);

    // When
    ModelAndView modelAndView = controlador.irAPanelCliente(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("panel__dashboard"));
  }

  @Test
  public void irAPanelClienteSinUsuarioLogueadoRedirigeALogin() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.irAPanelCliente(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(RUTA_LOGIN));
  }

  // ==========================================
  // TESTS: irAPanelDatosPersonales
  // ==========================================

  @Test
  public void irAPanelDatosPersonalesSinUsuarioLogueadoRedirigeALogin() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.irAPanelDatosPersonales(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(RUTA_LOGIN));
  }

  @Test
  public void irAPanelDatosPersonalesConUsuarioLogueadoSinPerfilAlimentarioRedirigeARegistroPerfil() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);
    when(servicioUsuarioMock.tienePerfilAlimentario(EMAIL_VALIDO)).thenReturn(false);

    // When
    ModelAndView modelAndView = controlador.irAPanelDatosPersonales(sessionMock);

    // Then
    assertThat(
      modelAndView.getViewName(),
      equalToIgnoringCase("redirect:/Registro-perfil-alimentario")
    );
  }

  @Test
  public void irAPanelDatosPersonalesConUsuarioLogueadoConPerfilPeroSinPresupuestoMuestraVistaYDatos() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);
    when(servicioUsuarioMock.tienePerfilAlimentario(EMAIL_VALIDO)).thenReturn(true);
    when(servicioUsuarioMock.tienePresupuesto(EMAIL_VALIDO)).thenReturn(false);

    DatosClientePanel datosEsperados = new DatosClientePanel();
    when(servicioUsuarioMock.obtenerDatosClientePanel(EMAIL_VALIDO)).thenReturn(datosEsperados);
    when(servicioUsuarioMock.obtenerDatosClientePanel(EMAIL_VALIDO)).thenReturn(datosEsperados);

    // When
    ModelAndView modelAndView = controlador.irAPanelDatosPersonales(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("panel__datos-personales"));
    assertThat(modelAndView.getModel().get("datosClientePanel"), is(sameInstance(datosEsperados)));
  }

  @Test
  public void irAPanelDatosPersonalesConUsuarioLogueadoConPerfilYPresupuestoMuestraVistaYDatos() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);
    when(servicioUsuarioMock.tienePerfilAlimentario(EMAIL_VALIDO)).thenReturn(true);
    when(servicioUsuarioMock.tienePresupuesto(EMAIL_VALIDO)).thenReturn(true);

    DatosClientePanel datosEsperados = new DatosClientePanel();
    when(servicioUsuarioMock.obtenerDatosClientePanel(EMAIL_VALIDO)).thenReturn(datosEsperados);

    // When
    ModelAndView modelAndView = controlador.irAPanelDatosPersonales(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("panel__datos-personales"));
    assertThat(modelAndView.getModel().get("datosClientePanel"), is(sameInstance(datosEsperados)));
  }

  // ==========================================
  // TESTS: irADespensa
  // ==========================================

  @Test
  public void irADespensaSinUsuarioLogueadoRedirigeALogin() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.irADespensa(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(RUTA_LOGIN));
  }

  @Test
  public void irADespensaConUsuarioLogueadoYDespensaConElementosMuestraVistaConModeloCorrecto() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);

    List<ItemDespensa> despensaFake = new ArrayList<>();
    despensaFake.add(new ItemDespensa());
    List<Alimento> alimentosFake = new ArrayList<>();

    when(servicioDespensaMock.obtenerDespensaDelUsuario(EMAIL_VALIDO)).thenReturn(despensaFake);
    when(servicioDespensaMock.obtenerAlimentosBaseDatos()).thenReturn(alimentosFake);

    // When
    ModelAndView modelAndView = controlador.irADespensa(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("panel__despensa"));
    assertThat(modelAndView.getModel().get("despensa"), is(sameInstance(despensaFake)));
    assertThat(
      modelAndView.getModel().get("listaAlimentosBaseDatos"),
      is(sameInstance(alimentosFake))
    );
    assertThat(
      modelAndView.getModel().get("itemDespensaDTO"),
      is(instanceOf(ItemDespensaDTO.class))
    );
  }

  @Test
  public void irADespensaConUsuarioLogueadoYDespensaNulaNoAgregaDespensaAlModelo() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);
    when(servicioDespensaMock.obtenerDespensaDelUsuario(EMAIL_VALIDO)).thenReturn(null);
    when(servicioDespensaMock.obtenerAlimentosBaseDatos()).thenReturn(new ArrayList<>());

    // When
    ModelAndView modelAndView = controlador.irADespensa(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("panel__despensa"));
    assertThat(modelAndView.getModel().containsKey("despensa"), is(false));
  }

  // ==========================================
  // TESTS: agregarItemDespensaExistente
  // ==========================================

  @Test
  public void agregarItemDespensaExistenteSinUsuarioLogueadoRedirigeALogin() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(null);
    ItemDespensaDTO dto = new ItemDespensaDTO();

    // When
    ModelAndView modelAndView = controlador.agregarItemDespensaExistente(sessionMock, dto);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(RUTA_LOGIN));
  }

  @Test
  public void agregarItemDespensaExistenteConUsuarioLogueadoGuardaYRedirigeADespensa() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);
    ItemDespensaDTO dto = new ItemDespensaDTO();

    // When
    ModelAndView modelAndView = controlador.agregarItemDespensaExistente(sessionMock, dto);

    // Then
    verify(servicioDespensaMock).agregarItemDespensa(EMAIL_VALIDO, dto);
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/panel-cliente/despensa"));
  }

  // ==========================================
  // TESTS: eliminarItemDespensa
  // ==========================================

  @Test
  public void eliminarItemDespensaSinUsuarioLogueadoRedirigeALogin() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.eliminarItemDespensa(sessionMock, 1L);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(RUTA_LOGIN));
  }

  @Test
  public void eliminarItemDespensaConUsuarioLogueadoEliminaYRedirigeADespensa() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);

    // When
    ModelAndView modelAndView = controlador.eliminarItemDespensa(sessionMock, 1L);

    // Then
    verify(servicioDespensaMock).eliminarItemDespensa(1L);
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/panel-cliente/despensa"));
  }

  // ==========================================
  // TESTS: cambiarCantidadDespensa
  // ==========================================

  @Test
  public void cambiarCantidadDespensaSinUsuarioLogueadoRedirigeALogin() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.cambiarCantidadDespensa(sessionMock, 1L, 2.5);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(RUTA_LOGIN));
  }

  @Test
  public void cambiarCantidadDespensaConUsuarioLogueadoModificaYRedirigeADespensa() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);

    // When
    ModelAndView modelAndView = controlador.cambiarCantidadDespensa(sessionMock, 1L, 2.5);

    // Then
    verify(servicioDespensaMock).cambiarCantidadDespensa(1L, 2.5);
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/panel-cliente/despensa"));
  }

  // =========================================
  // TESTS: irADashboard
  // ==========================================

  @Test
  public void irADashboardSinUsuarioLogueadoRedirigeALogin() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.irADashboard(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(RUTA_LOGIN));
  }

  @Test
  public void irADashboardConUsuarioLogueadoYVistasPorDefectoSeteaLosValoresEnElModelo() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);
    when(servicioUsuarioMock.tienePerfilAlimentario(EMAIL_VALIDO)).thenReturn(true);
    when(servicioUsuarioMock.tienePresupuesto(EMAIL_VALIDO)).thenReturn(false);

    DatosClientePanel datos = new DatosClientePanel();
    when(servicioUsuarioMock.obtenerDatosClientePanel(EMAIL_VALIDO)).thenReturn(datos);

    // Simular que no hay plan ni modelo de compras en sesión
    when(sessionMock.getAttribute("planGenerado")).thenReturn(null);
    when(sessionMock.getAttribute("modeloLista")).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.irADashboard(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("panel__dashboard"));
    assertThat(modelAndView.getModel().get("datosClientePanel"), is(sameInstance(datos)));
    assertThat(modelAndView.getModel().get("paso1Completado"), is(true));
    assertThat(modelAndView.getModel().get("paso2Completado"), is(false));
    assertThat(modelAndView.getModel().get("paso3Completado"), is(false));
    assertThat(modelAndView.getModel().get("paso4Completado"), is(false));
    assertThat(modelAndView.getModel().get("planGenerado"), is(nullValue()));
  }

  @Test
  public void irADashboardConUsuarioLogueadoPlanGeneradoYModeloListaDeComprasEnSesionMapeaTodoAlModelo() {
    // Given
    when(sessionMock.getAttribute(ATT_EMAIL_SESION)).thenReturn(EMAIL_VALIDO);
    when(servicioUsuarioMock.tienePerfilAlimentario(EMAIL_VALIDO)).thenReturn(true);
    when(servicioUsuarioMock.tienePresupuesto(EMAIL_VALIDO)).thenReturn(true);

    DatosClientePanel datos = new DatosClientePanel();
    when(servicioUsuarioMock.obtenerDatosClientePanel(EMAIL_VALIDO)).thenReturn(datos);

    Object planFake = new Object();
    when(sessionMock.getAttribute("planGenerado")).thenReturn(planFake);

    // Armamos un ModelMap fake de sesión con lista de compras
    ModelMap modeloListaFake = new ModelMap();
    List<String> listaDeComprasFake = new ArrayList<>();
    listaDeComprasFake.add("Manzana");
    modeloListaFake.put("listaDeCompras", listaDeComprasFake);
    modeloListaFake.put("totalLista", 1500.0);
    modeloListaFake.put("dias", 7);

    when(sessionMock.getAttribute("modeloLista")).thenReturn(modeloListaFake);

    // When
    ModelAndView modelAndView = controlador.irADashboard(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("panel__dashboard"));
    assertThat(modelAndView.getModel().get("datosClientePanel"), is(sameInstance(datos)));
    assertThat(modelAndView.getModel().get("paso1Completado"), is(true));
    assertThat(modelAndView.getModel().get("paso2Completado"), is(true));
    assertThat(modelAndView.getModel().get("paso3Completado"), is(true));
    assertThat(modelAndView.getModel().get("paso4Completado"), is(true));
    assertThat(modelAndView.getModel().get("planGenerado"), is(sameInstance(planFake)));

    // Extracción de variables de sesión al modelo de salida
    assertThat(modelAndView.getModel().get("listaDeCompras"), is(sameInstance(listaDeComprasFake)));
    assertThat(modelAndView.getModel().get("totalLista"), is(1500.0));
    assertThat(modelAndView.getModel().get("dias"), is(7));
  }
}
