package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioBuscarSupermercadoTest {

  private ServicioMapa servicioMapaMock;
  private ServicioBuscarSupermercado servicioBuscarSupermercado;

  @BeforeEach
  public void inicializarObjetos() {
    servicioMapaMock = mock(ServicioMapa.class);
    servicioBuscarSupermercado = new ServicioBuscarSupermercadoImp(servicioMapaMock);
  }

  @Test
  public void debeObtenerCorrectamenteLasCordenadasDeLaUbicacion() {
    //GIVEN
    Direccion direccion = crearDireccion();
    String json = "[{" + "\"lat\":\"-34.6037\"," + "\"lon\":\"-58.3816\"" + "}]";
    when(servicioMapaMock.obtenerCoordenadas("Av Corrientes 100")).thenReturn(json);

    //WHEN
    Cordenandas coordenadas = servicioBuscarSupermercado.obtenerCordenadaActual(direccion);

    //THEN
    assertThat(coordenadas.getLatitud(), equalTo(-34.6037));
    assertThat(coordenadas.getLongitud(), equalTo(-58.3816));
  }

  @Test
  public void debeRetornarNullSiLaApiNoEncuentraLaDireccion() {
    //GIVEN
    Direccion direccion = crearDireccion();
    when(servicioMapaMock.obtenerCoordenadas(anyString())).thenReturn("[]");
    //WHEN
    Cordenandas coordenadas = servicioBuscarSupermercado.obtenerCordenadaActual(direccion);
    //THEN
    Assertions.assertNull(coordenadas);
  }

  @Test
  public void debeLanzarExcepcionSiElJsonDeCoordenadasEsInvalido() {
    //GIVEN
    Direccion direccion = crearDireccion();
    when(servicioMapaMock.obtenerCoordenadas(anyString())).thenReturn("hola");
    //THEN
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> servicioBuscarSupermercado.obtenerCordenadaActual(direccion)
    );
  }

  @Test
  public void debeObtenerLosSupermercadosCorrectamente() {
    //GIVEN
    String json =
      "{" +
      "\"elements\":[" +
      "{" +
      "\"lat\":-34.60," +
      "\"lon\":-58.38," +
      "\"tags\":{" +
      "\"name\":\"Carrefour\"," +
      "\"addr:street\":\"Corrientes\"," +
      "\"addr:housenumber\":\"100\"" +
      "}" +
      "}" +
      "]" +
      "}";
    when(servicioMapaMock.obtenerSupermercados(anyDouble(), anyDouble())).thenReturn(json);
    //WHEN
    List<Supermercado> supermercados = servicioBuscarSupermercado.buscarSupermercadosCercanos(
      -34.60,
      -58.38
    );
    //THEN
    assertThat(supermercados.size(), equalTo(1));
    assertThat(supermercados.get(0).getNombre(), equalTo("Carrefour"));
    assertThat(supermercados.get(0).getDireccionName(), equalTo("Corrientes 100"));
  }

  @Test
  public void debeAsignarNombrePorDefectoCuandoNoVieneElNombre() {
    // GIVEN
    String json =
      "{" +
      "\"elements\":[" +
      "{" +
      "\"lat\":-34.60," +
      "\"lon\":-58.38," +
      "\"tags\":{" +
      "\"addr:street\":\"Corrientes\"," +
      "\"addr:housenumber\":\"100\"" +
      "}" +
      "}" +
      "]" +
      "}";
    when(servicioMapaMock.obtenerSupermercados(anyDouble(), anyDouble())).thenReturn(json);

    // WHEN
    List<Supermercado> supermercados = servicioBuscarSupermercado.buscarSupermercadosCercanos(
      -34.60,
      -58.38
    );

    // THEN
    assertThat(supermercados.get(0).getNombre(), equalTo("Supermercado"));
  }

  @Test
  public void debeAsignarDireccionNoDisponibleSiNoExistenTags() {
    // GIVEN
    String json =
      "{" + "\"elements\":[" + "{" + "\"lat\":-34.60," + "\"lon\":-58.38" + "}" + "]" + "}";
    when(servicioMapaMock.obtenerSupermercados(anyDouble(), anyDouble())).thenReturn(json);

    // WHEN
    List<Supermercado> supermercados = servicioBuscarSupermercado.buscarSupermercadosCercanos(
      -34.60,
      -58.38
    );

    // THEN
    assertThat(supermercados.get(0).getDireccionName(), equalTo("Dirección no disponible"));
  }

  @Test
  public void debeAsignarDireccionNoDisponibleSiTagsNoTieneDireccion() {
    // GIVEN
    String json =
      "{" +
      "\"elements\":[" +
      "{" +
      "\"lat\":-34.60," +
      "\"lon\":-58.38," +
      "\"tags\":{" +
      "\"name\":\"Carrefour\"" +
      "}" +
      "}" +
      "]" +
      "}";
    when(servicioMapaMock.obtenerSupermercados(anyDouble(), anyDouble())).thenReturn(json);

    // WHEN
    List<Supermercado> supermercados = servicioBuscarSupermercado.buscarSupermercadosCercanos(
      -34.60,
      -58.38
    );

    // THEN
    assertThat(supermercados.get(0).getDireccionName(), equalTo("Dirección no disponible"));
  }

  @Test
  public void debeRetornarListaVaciaSiNoHaySupermercados() {
    //GIVEN
    when(servicioMapaMock.obtenerSupermercados(anyDouble(), anyDouble())).thenReturn("{}");
    //WHEN
    List<Supermercado> supermercados = servicioBuscarSupermercado.buscarSupermercadosCercanos(
      -34.60,
      -58.38
    );
    //THEN
    assertThat(supermercados.isEmpty(), equalTo(true));
  }

  @Test
  public void debeLanzarExcepcionSiElJsonDeSupermercadosEsInvalido() {
    //GIVEN
    when(servicioMapaMock.obtenerSupermercados(anyDouble(), anyDouble())).thenReturn("hola");
    //THEN
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> servicioBuscarSupermercado.buscarSupermercadosCercanos(-34.6, -58.3)
    );
  }

  @Test
  public void debeCalcularLaDistanciaEntreDosCoordenadas() {
    //GIVEN
    Cordenandas origen = new Cordenandas();
    origen.setLatitud(-34.6037);
    origen.setLongitud(-58.3816);

    Cordenandas destino = new Cordenandas();
    destino.setLatitud(-34.6080);
    destino.setLongitud(-58.3900);
    //WHEN
    Double distancia = servicioBuscarSupermercado.calcularDistanciaMetros(origen, destino);
    //THEN
    assertThat(distancia, greaterThan(0.0));
  }

  @Test
  public void debeCalcularCorrectamenteLosMinutosCaminando() {
    //WHEN
    Integer minutos = servicioBuscarSupermercado.calcularTiempoCaminando(500.0);
    //THEN
    assertThat(minutos, equalTo(6));
  }

  @Test
  public void nuncaDebeRetornarMenosDeUnMinuto() {
    //WHEN
    Integer minutos = servicioBuscarSupermercado.calcularTiempoCaminando(5.0);
    //THEN
    assertThat(minutos, equalTo(1));
  }

  @Test
  public void debeCalcularLasDistanciasDeTodosLosSupermercados() {
    //GIVEN
    Cordenandas origen = crearCoordenadas();
    Supermercado supermercado = new Supermercado();
    Cordenandas destino = new Cordenandas();
    destino.setLatitud(-34.60);
    destino.setLongitud(-58.39);
    supermercado.setCordenadas(destino);
    List<Supermercado> supermercados = List.of(supermercado);
    //WHEN
    servicioBuscarSupermercado.calcularDistancias(supermercados, origen);
    //THEN
    assertThat(supermercado.getDistanciaMetros(), notNullValue());
    assertThat(supermercado.getMinutosCaminando(), notNullValue());
  }

  private Direccion crearDireccion() {
    Direccion direccion = new Direccion();
    direccion.setUbicacion("Av Corrientes");
    direccion.setNumero(100);
    return direccion;
  }

  private Cordenandas crearCoordenadas() {
    Cordenandas coordenadas = new Cordenandas();
    coordenadas.setLatitud(-34.6037);
    coordenadas.setLongitud(-58.3816);
    return coordenadas;
  }
}
