package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.ServicioCargaDeReceta;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public class ControladorCargaDeRecetaTest {

  private ServicioCargaDeReceta servicioCargaDeReceta = mock(ServicioCargaDeReceta.class);
  private ControladorCargaDeReceta controlador = new ControladorCargaDeReceta(
    servicioCargaDeReceta
  );
  private HttpSession session = mock(HttpSession.class);

  @Test
  public void sePuedeIrAlaVistaMisRecetas() {
    givenExisteUsuario();
    ModelAndView mav = controlador.mostrarMisRecetas(session);
    thenVoyAVistaMisRecetas(mav);
  }

  @Test
  public void sePuedeIrAlaVistaCrearReceta() {
    givenExisteUsuario();
    ModelAndView mav = controlador.irACrearReceta(session);
    thenVoyAVistaCrearReceta(mav);
  }

  @Test
  public void siNoHayUsuarioSeVuelveAlLogin() {
    givenNoExisteUsuario();
    ModelAndView mav = controlador.irACrearReceta(session);
    thenVuelvoALogin(mav);
  }

  @Test
  public void sePuedeCrearUnaReceta() {
    givenExisteUsuario();
    givenExistenAlimentos(List.of("leche", "harina", "huevo"));
    IngredienteDTO i1 = new IngredienteDTO();
    i1.setNombre("leche");
    i1.setCantidad(250D);
    IngredienteDTO i2 = new IngredienteDTO();
    i2.setNombre("harina");
    i2.setCantidad(100D);
    IngredienteDTO i3 = new IngredienteDTO();
    i3.setNombre("huevo");
    i3.setCantidad(2D);
    DatosReceta datosReceta = new DatosReceta();
    datosReceta.setNombre("tortilla");
    datosReceta.setTipo("Desayuno");
    datosReceta.setIngredientes(new ArrayList<IngredienteDTO>(List.of(i1, i2, i3)));

    ModelAndView mav = controlador.validarReceta(datosReceta, session);

    thenSeCreaLaReceta(mav);
  }

  @Test
  public void siLaCantidadEsCeroONegativoLaRecetaFalla() {
    givenExisteUsuario();
    givenExistenAlimentos(List.of("leche", "harina", "huevo"));
    IngredienteDTO i1 = new IngredienteDTO();
    i1.setNombre("leche");
    i1.setCantidad(0D);
    IngredienteDTO i2 = new IngredienteDTO();
    i2.setNombre("harina");
    i2.setCantidad(100D);
    IngredienteDTO i3 = new IngredienteDTO();
    i3.setNombre("huevo");
    i3.setCantidad(-7D);
    DatosReceta datosReceta = new DatosReceta();
    datosReceta.setNombre("tortilla");
    datosReceta.setTipo("Desayuno");
    datosReceta.setIngredientes(new ArrayList<IngredienteDTO>(List.of(i1, i2, i3)));

    ModelAndView mav = controlador.validarReceta(datosReceta, session);

    thenNoSeCreaLaReceta(mav);
  }

  @Test
  public void siDosIngredientesSonIgualesLaRecetaFalla() {
    givenExisteUsuario();
    givenExistenAlimentos(List.of("leche", "harina", "huevo"));
    IngredienteDTO i1 = new IngredienteDTO();
    i1.setNombre("leche");
    i1.setCantidad(250D);
    IngredienteDTO i2 = new IngredienteDTO();
    i2.setNombre("harina");
    i2.setCantidad(100D);
    IngredienteDTO i3 = new IngredienteDTO();
    i3.setNombre("leche");
    i3.setCantidad(2D);

    DatosReceta datosReceta = new DatosReceta();
    datosReceta.setNombre("tortilla");
    datosReceta.setTipo("Desayuno");
    datosReceta.setIngredientes(new ArrayList<IngredienteDTO>(List.of(i1, i2, i3)));

    ModelAndView mav = controlador.validarReceta(datosReceta, session);

    thenNoSeCreaLaReceta(mav);
  }

  @Test
  public void sePuedeEliminarUnaReceta() {
    givenExisteUsuario();
    DatosReceta datosReceta = givenExisteReceta();
    ModelAndView mav = whenBorroReceta(datosReceta.getId());
    thenRedirigeAMisRecetas(mav);
  }

  private void thenRedirigeAMisRecetas(ModelAndView mav) {
    assertThat(mav.getViewName(), is(equalToIgnoringCase("redirect:/mis-recetas")));
  }

  private ModelAndView whenBorroReceta(Long id) {
    return controlador.eliminarReceta(id, session);
  }

  private DatosReceta givenExisteReceta() {
    DatosReceta datosReceta = new DatosReceta();
    datosReceta.setId(1L);
    datosReceta.setNombre("pizza");
    return datosReceta;
  }

  private void givenExistenAlimentos(List<String> nombres) {
    when(servicioCargaDeReceta.obtenerNombresDeAlimentosExistentes()).thenReturn(nombres);
  }

  private void thenNoSeCreaLaReceta(ModelAndView mav) {
    assertThat(mav.getViewName(), is(equalToIgnoringCase("crear-receta")));
  }

  private void thenSeCreaLaReceta(ModelAndView mav) {
    assertThat(mav.getViewName(), is(not(equalToIgnoringCase("crear-receta"))));
  }

  private void givenExisteUsuario() {
    when(session.getAttribute("usuarioLogueadoEmail")).thenReturn("a@a.com");
  }

  private void givenNoExisteUsuario() {
    when(session.getAttribute("usuarioLogueadoEmail")).thenReturn(null);
  }

  private void thenVuelvoALogin(ModelAndView mav) {
    assertThat(mav.getViewName(), is(equalToIgnoringCase("redirect:/login")));
  }

  private void thenVoyAVistaCrearReceta(ModelAndView mav) {
    assertThat(mav.getViewName(), is(equalToIgnoringCase("crear-receta")));
  }

  private void thenVoyAVistaMisRecetas(ModelAndView mav) {
    assertThat(mav.getViewName(), is(equalToIgnoringCase("mis-recetas")));
  }
}
