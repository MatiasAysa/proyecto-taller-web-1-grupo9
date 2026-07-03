package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

import com.tallerwebi.presentacion.DatosReceta;
import com.tallerwebi.presentacion.IngredienteDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

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

  private void thenSeCreaLaReceta() {
    verify(repositorioReceta, times(1)).guardarReceta(any(Comida.class));
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
