package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.excepcion.NombreDeAlimentoInexistenteException;
import com.tallerwebi.dominio.excepcion.RecetaConNombreRepetidoException;
import com.tallerwebi.presentacion.DatosReceta;
import com.tallerwebi.presentacion.IngredienteDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

public class ServicioCargaDeRecetaTest {

  private RepositorioAlimento repositorioAlimento = mock(RepositorioAlimento.class);
  private RepositorioReceta repositorioReceta = mock(RepositorioReceta.class);
  private RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
  private ServicioCargaDeReceta servicioCargaDeReceta = new ServicioCargaDeRecetaImpl(
    repositorioAlimento,
    repositorioReceta,
    repositorioUsuario
  );

  @Test
  public void sePuedeObtenerLaListaDeAlimentosExistentes() {
    List<String> resultadoEsperado = givenExistenAlimentos(
      List.of("Huevo", "Harina", "Manteca", "Queso")
    );
    List<String> resultadoObtenido = whenObtengoListaDeAlimentosExistentes();
    thenReciboLosNombresDeLosAlimentos(resultadoEsperado, resultadoObtenido);
  }

  @Test
  public void sePuedeCargarUnaReceta() {
    Usuario usuario = givenExisteUsuario();
    List<String> listaAlimentos = givenExistenAlimentos(
      List.of("Huevo", "Harina", "Manteca", "Queso")
    );
    DatosReceta datosReceta = obtenerDatosRecetaValidos(listaAlimentos);
    whenCargoUnaReceta(usuario.getEmail(), datosReceta);
    thenSeCreaLaReceta();
  }

  @Test
  public void cargarDosVecesLaMismaRecetaProvocaUnaExcepcion() {
    Usuario usuario = givenExisteUsuario();
    List<String> listaAlimentos = givenExistenAlimentos(
      List.of("Huevo", "Harina", "Manteca", "Queso")
    );
    DatosReceta datosReceta = obtenerDatosRecetaValidos(listaAlimentos);
    givenYaExisteReceta(datosReceta, usuario);
    assertThrows(
      RecetaConNombreRepetidoException.class,
      () -> servicioCargaDeReceta.cargarReceta(datosReceta, usuario.getEmail())
    );
  }

  @Test
  public void siUnAlimentoIngresadoNoExisteEnBDSeTiraUnaExcepcion() {
    Usuario usuario = givenExisteUsuario();
    List<String> listaAlimentos = givenExistenAlimentos(
      List.of("Huevo", "Harina", "Manteca", "Queso")
    );
    DatosReceta datosReceta = obtenerDatosRecetaConIngredientesInexistentes(listaAlimentos);
    assertThrows(
      NombreDeAlimentoInexistenteException.class,
      () -> whenCargoUnaReceta(usuario.getEmail(), datosReceta)
    );
  }

  @Test
  public void sePuedeObtenerLasRecetasDeUnUsuario() {
    Usuario usuario = givenExisteUsuario();
    givenExistenRecetasDeUsuario(usuario);
    assertThat(
      servicioCargaDeReceta.obtenerRecetasDeUsuario(usuario.getEmail()),
      is(notNullValue())
    );
  }

  @Test
  public void sePuedeObtenerUnaRecetaPorId() {
    givenExisteRecetaConUsuarioYId(new Usuario(), 1L);
    assertThat(servicioCargaDeReceta.obtenerRecetaPorId(1L), is(notNullValue()));
  }

  private void givenExistenRecetasDeUsuario(Usuario usuario) {
    Comida c1 = new Comida();
    c1.setId(1L);
    c1.setAutor(usuario);
    c1.setNombre("comida rica");
    c1.setTipo(TipoDeComida.CENA);
    c1.setItems(List.of(new ItemComida(2.0, new Alimento()), new ItemComida(2.0, new Alimento())));
    List<Comida> comidas = List.of(c1);
    when(repositorioReceta.obtenerRecetasDeUsuario(usuario)).thenReturn(comidas);
  }

  private DatosReceta obtenerDatosRecetaConIngredientesInexistentes(List<String> listaAlimentos) {
    DatosReceta datosReceta = new DatosReceta();
    datosReceta.setNombre("Comida rica");
    List<IngredienteDTO> ingredienteDTOS = new ArrayList<IngredienteDTO>();
    for (String a : listaAlimentos) {
      if (a != null) {
        IngredienteDTO i = new IngredienteDTO();
        i.setNombre(a.repeat(3));
        i.setCantidad(10D);
        ingredienteDTOS.add(i);
      }
    }
    datosReceta.setIngredientes(ingredienteDTOS);
    datosReceta.setTipo("ALMUERZO");
    return datosReceta;
  }

  @Test
  public void sePuedeEliminarUnaReceta() {
    Usuario usuario = givenExisteUsuario();
    givenExisteRecetaConUsuarioYId(usuario, 1L);
    whenBorroUnaReceta(usuario, 1L);
    thenSeBorraLaReceta();
  }

  @Test
  public void noSePuedeEliminarUnaRecetaAjena() {
    Usuario usuario = givenExisteUsuario();
    Usuario otro = new Usuario();
    otro.setEmail("emailDistinto@gmail.com");
    givenExisteRecetaConUsuarioYId(usuario, 1L);
    whenBorroUnaReceta(otro, 1L);
    thenNoSeBorraLaReceta();
  }

  private void thenNoSeBorraLaReceta() {
    verify(repositorioReceta, never()).eliminarReceta(any());
  }

  private void thenSeBorraLaReceta() {
    verify(repositorioReceta).eliminarReceta(any());
  }

  private void whenBorroUnaReceta(Usuario usuario, long id) {
    servicioCargaDeReceta.eliminarReceta(id, usuario.getEmail());
  }

  private void givenExisteRecetaConUsuarioYId(Usuario usuario, long id) {
    Comida comida = new Comida();
    comida.setAutor(usuario);
    comida.setId(id);
    comida.setTipo(TipoDeComida.CENA);
    when(repositorioReceta.buscarRecetaPorId(id)).thenReturn(comida);
  }

  private void givenYaExisteReceta(DatosReceta datosReceta, Usuario usuario) {
    Comida c = new Comida();
    c.setId(1L);
    when(repositorioReceta.buscarRecetaPorNombreYUsuario(datosReceta.getNombre(), usuario))
      .thenReturn(c);
  }

  private void thenSeCreaLaReceta() {
    verify(repositorioReceta, times(1)).guardarReceta(ArgumentMatchers.any(Comida.class));
  }

  private void whenCargoUnaReceta(String email, DatosReceta datosReceta) {
    servicioCargaDeReceta.cargarReceta(datosReceta, email);
  }

  private DatosReceta obtenerDatosRecetaValidos(List<String> listaAlimentos) {
    DatosReceta datosReceta = new DatosReceta();
    datosReceta.setNombre("Comida rica");
    List<IngredienteDTO> ingredienteDTOS = new ArrayList<IngredienteDTO>();
    for (String a : listaAlimentos) {
      if (a != null) {
        IngredienteDTO i = new IngredienteDTO();
        i.setNombre(a);
        i.setCantidad(10D);
        ingredienteDTOS.add(i);
      }
    }
    datosReceta.setIngredientes(ingredienteDTOS);
    datosReceta.setTipo("ALMUERZO");
    return datosReceta;
  }

  private Usuario givenExisteUsuario() {
    Usuario usuario = new Usuario();
    usuario.setEmail("a@a.com");
    when(repositorioUsuario.buscar(usuario.getEmail())).thenReturn(usuario);
    return usuario;
  }

  private void thenReciboLosNombresDeLosAlimentos(
    List<String> resultadoEsperado,
    List<String> resultadoObtenido
  ) {
    assertThat(
      resultadoEsperado.stream().collect(Collectors.joining()),
      equalTo(resultadoObtenido.stream().collect(Collectors.joining()))
    );
  }

  private List<String> whenObtengoListaDeAlimentosExistentes() {
    return servicioCargaDeReceta.obtenerNombresDeAlimentosExistentes();
  }

  private List<String> givenExistenAlimentos(List<String> listaAlimentos) {
    List<Alimento> alimentos = new ArrayList<Alimento>();
    for (String nombre : listaAlimentos) {
      if (nombre != null) alimentos.add(nuevoAlimentoConNombreGenerico(nombre));
    }
    when(repositorioAlimento.obtenerTodosLosAlimentos()).thenReturn(alimentos);
    for (Alimento alimento : alimentos) {
      when(repositorioAlimento.obtenerPorNombreGenerico(alimento.getNombreGenerico()))
        .thenReturn(alimento);
    }
    return listaAlimentos;
  }

  private Alimento nuevoAlimentoConNombreGenerico(String nombre) {
    Alimento a = new Alimento();
    a.setNombreGenerico(nombre);
    return a;
  }
}
