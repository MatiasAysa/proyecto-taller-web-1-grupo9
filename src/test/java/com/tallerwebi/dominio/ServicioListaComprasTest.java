package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ServicioListaComprasTest {

  private RepositorioAlimento repositorioAlimentoMock;
  private RepositorioListaDeCompras repositorioListaMock;
  private RepositorioUsuario repositorioUsuarioMock;

  private ServicioListaCompras servicio;

  @BeforeEach
  public void inicializarObjetos() {
    repositorioAlimentoMock = mock(RepositorioAlimento.class);
    repositorioListaMock = mock(RepositorioListaDeCompras.class);
    repositorioUsuarioMock = mock(RepositorioUsuario.class);
    servicio =
      new ServicioListaComprasImpl(
        repositorioAlimentoMock,
        repositorioListaMock,
        repositorioUsuarioMock
      );
  }

  @Test
  public void debeBuscarAlimentoPorId() {
    // GIVEN
    Alimento alimento = new Alimento();
    when(repositorioAlimentoMock.buscarPorId(1L)).thenReturn(alimento);

    // WHEN
    Alimento obtenido = servicio.buscarAlimentoPorId(1L);

    // THEN
    assertThat(obtenido, equalTo(alimento));
  }

  @Test
  public void debeBuscarComidaPorId() {
    // GIVEN
    Comida comida = new Comida();
    when(repositorioListaMock.buscarComidaPorId(5L)).thenReturn(comida);

    // WHEN
    Comida resultado = servicio.buscarComidaPorId(5L);

    // THEN
    assertThat(resultado, equalTo(comida));
  }

  @Test
  public void debeArmarLosDiasSeleccionados() {
    // GIVEN
    MultiValueMap<String, String> mapa = new LinkedMultiValueMap<>();
    mapa.add("comidasSeleccionadas[1]", "10");
    Comida comida = new Comida();
    when(repositorioListaMock.buscarComidaPorId(10L)).thenReturn(comida);

    // WHEN
    List<DiaListaComprasDTO> dias = servicio.armarDiasSeleccionados(mapa);

    // THEN
    assertThat(dias.size(), equalTo(1));
    assertThat(dias.get(0).getNumeroDia(), equalTo(1));
    assertThat(dias.get(0).getComidas().size(), equalTo(1));
  }

  @Test
  public void debeIgnorarParametrosQueNoSeanComidasSeleccionadas() {
    // GIVEN
    MultiValueMap<String, String> mapa = new LinkedMultiValueMap<>();
    mapa.add("hola", "10");

    // WHEN
    List<DiaListaComprasDTO> dias = servicio.armarDiasSeleccionados(mapa);

    // THEN
    assertThat(dias.isEmpty(), equalTo(true));
  }

  @Test
  public void debeGenerarListaDeCompras() {
    // GIVEN
    Alimento alimento = new Alimento();
    alimento.setId(1L);

    ItemComida item = new ItemComida();
    item.setAlimento(alimento);
    item.setCantidadGramos(500.0);

    Comida comida = new Comida();
    comida.setItems(List.of(item));

    // WHEN
    List<ItemCompra> lista = servicio.generarListaCompras(List.of(comida));

    // THEN
    assertThat(lista.size(), equalTo(1));
    assertThat(lista.get(0).getCantidadTotal(), equalTo(500.0));
  }

  @Test
  public void debeSumarLasCantidadesCuandoElAlimentoSeRepite() {
    // GIVEN
    Alimento alimento = new Alimento();
    alimento.setId(1L);

    ItemComida item1 = new ItemComida();
    item1.setAlimento(alimento);
    item1.setCantidadGramos(500.0);

    ItemComida item2 = new ItemComida();
    item2.setAlimento(alimento);
    item2.setCantidadGramos(300.0);

    Comida comida = new Comida();
    comida.setItems(List.of(item1, item2));

    // WHEN
    List<ItemCompra> lista = servicio.generarListaCompras(List.of(comida));

    // THEN
    assertThat(lista.size(), equalTo(1));
    assertThat(lista.get(0).getCantidadTotal(), equalTo(800.0));
  }

  @Test
  public void debeEncontrarElItemCompra() {
    // GIVEN
    Alimento alimento = new Alimento();
    alimento.setId(1L);

    ItemCompra compra = new ItemCompra();
    compra.setAlimento(alimento);

    ItemComida comida = new ItemComida();
    comida.setAlimento(alimento);

    // WHEN
    ItemCompra resultado = servicio.buscarItemCompra(List.of(compra), comida);

    // THEN
    assertThat(resultado, equalTo(compra));
  }

  @Test
  public void debeRetornarNullSiNoEncuentraElItemCompra() {
    // GIVEN
    Alimento a1 = new Alimento();
    a1.setId(1L);

    Alimento a2 = new Alimento();
    a2.setId(2L);

    ItemCompra compra = new ItemCompra();
    compra.setAlimento(a1);

    ItemComida comida = new ItemComida();
    comida.setAlimento(a2);

    // WHEN
    ItemCompra resultado = servicio.buscarItemCompra(List.of(compra), comida);

    // THEN
    Assertions.assertNull(resultado);
  }

  @Test
  public void debeCalcularLosPrecios() {
    // GIVEN
    Alimento alimento = new Alimento();
    alimento.setPrecioEstimado(4000.0);

    ItemCompra item = new ItemCompra();
    item.setAlimento(alimento);
    item.setCantidadTotal(500.0);

    // WHEN
    servicio.calcularPrecios(List.of(item));

    // THEN
    assertThat(item.getPrecoTotal(), equalTo(2000.0));
  }

  @Test
  public void debeCalcularElTotal() {
    // GIVEN
    ItemCompra item1 = new ItemCompra();
    item1.setPrecoTotal(200.0);

    ItemCompra item2 = new ItemCompra();
    item2.setPrecoTotal(300.0);

    // WHEN
    Double total = servicio.calcularTotalListaCompras(List.of(item1, item2));

    // THEN
    assertThat(total, equalTo(500.0));
  }

  @Test
  public void debeLanzarExcepcionSiElUsuarioNoExiste() {
    // GIVEN
    when(repositorioUsuarioMock.buscar(anyString())).thenReturn(null);

    // THEN
    Assertions.assertThrows(
      UsuarioInexistenteException.class,
      () -> servicio.mostrarDtosTestear("mail")
    );
  }

  @Test
  public void debeRetornarLosDatosNutricionales() throws Exception {
    // GIVEN
    Usuario usuario = new Usuario();
    usuario.setPerfilAlimentario(new PerfilAlimentarioUsuario());

    when(repositorioUsuarioMock.buscar(anyString())).thenReturn(usuario);

    // WHEN
    List<String> datos = servicio.mostrarDtosTestear("mail");

    // THEN
    assertThat(datos.isEmpty(), equalTo(false));
  }
}
