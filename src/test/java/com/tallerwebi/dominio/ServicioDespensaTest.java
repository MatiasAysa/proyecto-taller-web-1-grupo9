package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioDespensaTest {

  private RepositorioDespensa repositorioDespensa;
  private RepositorioUsuario repositorioUsuario;
  private RepositorioAlimento repositorioAlimento;

  private ServicioDespensa servicioDespensa;

  @BeforeEach
  public void inicializarObjetos() {
    repositorioDespensa = mock(RepositorioDespensa.class);
    repositorioUsuario = mock(RepositorioUsuario.class);
    repositorioAlimento = mock(RepositorioAlimento.class);

    servicioDespensa =
      new ServicioDespensaImpl(repositorioDespensa, repositorioUsuario, repositorioAlimento);
  }

  @Test
  public void debeObtenerDespensaDeUsuario() {
    Usuario usuario = new Usuario();

    List<ItemDespensa> items = new ArrayList<>();

    items.add(new ItemDespensa());

    when(repositorioUsuario.buscar("test@test.com")).thenReturn(usuario);

    when(repositorioDespensa.obtenerDespensaDelUsuario(usuario)).thenReturn(items);

    List<ItemDespensa> resultado = servicioDespensa.obtenerDespensaDelUsuario("test@test.com");

    assertThat(resultado, hasSize(1));
  }

  @Test
  public void siUsuarioNoExisteAlObtenerDespensaLanzaError() {
    when(repositorioUsuario.buscar("test@test.com")).thenReturn(null);

    assertThrows(
      RuntimeException.class,
      () -> servicioDespensa.obtenerDespensaDelUsuario("test@test.com")
    );
  }

  @Test
  public void debeGuardarOActualizarDespensa() {
    ItemDespensa item = new ItemDespensa();

    servicioDespensa.guardarOActualizarDespensa(item);

    verify(repositorioDespensa).guardarOActualizarDespensa(item);
  }

  @Test
  public void debeAgregarItemConAlimentoExistente() {
    Usuario usuario = new Usuario();

    Alimento alimento = new Alimento();

    ItemDespensaDTO dto = new ItemDespensaDTO();

    dto.setIdAlimentoExistenteEnBaseDatos(1L);
    dto.setCantidad(5.0);

    when(repositorioUsuario.buscar("test@test.com")).thenReturn(usuario);

    when(repositorioAlimento.buscarPorId(1L)).thenReturn(alimento);

    servicioDespensa.agregarItemDespensa("test@test.com", dto);

    verify(repositorioDespensa).agregarItemDespensa(usuario, alimento, 5.0);
  }

  @Test
  public void siUsuarioNoExisteAlAgregarItemLanzaError() {
    when(repositorioUsuario.buscar("test@test.com")).thenReturn(null);

    ItemDespensaDTO dto = new ItemDespensaDTO();

    assertThrows(
      RuntimeException.class,
      () -> servicioDespensa.agregarItemDespensa("test@test.com", dto)
    );
  }

  @Test
  public void debeObtenerAlimentosDeBaseDatos() {
    List<Alimento> alimentos = new ArrayList<>();

    when(repositorioAlimento.obtenerListaAlimentos()).thenReturn(alimentos);

    List<Alimento> resultado = servicioDespensa.obtenerAlimentosBaseDatos();

    assertThat(resultado, equalTo(alimentos));
  }

  @Test
  public void debeEliminarItemDespensa() {
    servicioDespensa.eliminarItemDespensa(1L);

    verify(repositorioDespensa).eliminarItemDespensa(1L);
  }

  @Test
  public void siCantidadNuevaEsMenorIgualACeroDebeEliminar() {
    ItemDespensa item = new ItemDespensa();

    item.setCantidad(2.0);

    when(repositorioDespensa.obtenerItemDespensaPorId(1L)).thenReturn(item);

    servicioDespensa.cambiarCantidadDespensa(1L, -2.0);

    verify(repositorioDespensa).eliminarItemDespensa(1L);
  }

  @Test
  public void siCantidadEsValidaDebeActualizarCantidad() {
    ItemDespensa item = new ItemDespensa();

    item.setCantidad(5.0);

    when(repositorioDespensa.obtenerItemDespensaPorId(1L)).thenReturn(item);

    servicioDespensa.cambiarCantidadDespensa(1L, 3.0);

    assertThat(item.getCantidad(), equalTo(3.0));
  }

  @Test
  public void siItemNoExisteAlCambiarCantidadLanzaError() {
    when(repositorioDespensa.obtenerItemDespensaPorId(1L)).thenReturn(null);

    assertThrows(RuntimeException.class, () -> servicioDespensa.cambiarCantidadDespensa(1L, 5.0));
  }
}
