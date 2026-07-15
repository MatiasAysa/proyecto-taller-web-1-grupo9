package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioComidaTest {

  private Comida comida;
  private Alimento carne;
  private Alimento papa;

  @BeforeEach
  public void setUp() {
    this.comida = new Comida();
    this.comida.setNombre("Pastel de Papa");
    this.comida.setTipo(TipoDeComida.ALMUERZO);

    this.carne = new Alimento();
    this.carne.setNombre("Carne Picada");
    this.carne.setPrecioEstimado(8000.0);
    this.carne.setCalorias(250);
    this.carne.setProteinas(20.0);
    this.carne.setCarbohidratos(0.0);
    this.carne.setGrasas(15.0);
    this.carne.setEsVegetariano(false);
    this.carne.setEsCeliaco(true);
    this.carne.setContieneLactosa(false);
    this.papa = new Alimento();
    this.papa.setNombre("Papa");
    this.papa.setPrecioEstimado(1500.0);
    this.papa.setCalorias(80);
    this.papa.setProteinas(2.0);
    this.papa.setCarbohidratos(18.0);
    this.papa.setGrasas(0.1);
    this.papa.setEsVegetariano(true);
    this.papa.setEsCeliaco(true);
    this.papa.setContieneLactosa(false);
  }

  @Test
  public void elPrecioDeLaComidaDeberiaSerLaSumaProporcionalDeSusItems() {
    ItemComida itemCarne = new ItemComida();
    itemCarne.setAlimento(carne);
    itemCarne.setCantidadGramos(250.0);
    ItemComida itemPapa = new ItemComida();
    itemPapa.setAlimento(papa);
    itemPapa.setCantidadGramos(500.0);

    comida.setItems(List.of(itemCarne, itemPapa));
    assertThat(comida.getPrecioEstimado(), is(closeTo(2750.0, 0.1)));
  }

  @Test
  public void lasCaloriasYMacrosDeberianCalcularseCorrectamente() {
    ItemComida itemCarne = new ItemComida();
    itemCarne.setAlimento(carne);
    itemCarne.setCantidadGramos(200.0);

    comida.setItems(List.of(itemCarne));

    assertThat(comida.getCalorias(), is(500));
    assertThat(comida.getProteinas(), is(closeTo(40.0, 0.1)));
    assertThat(comida.getCarbohidratos(), is(closeTo(0.0, 0.1)));
    assertThat(comida.getGrasas(), is(closeTo(30.0, 0.1)));
  }

  @Test
  public void laComidaNoDeberiaSerVegetarianaSiUnoDeSusIngredientesNoLoEs() {
    ItemComida itemCarne = new ItemComida();
    itemCarne.setAlimento(carne);
    itemCarne.setCantidadGramos(150.0);

    ItemComida itemPapa = new ItemComida();
    itemPapa.setAlimento(papa);
    itemPapa.setCantidadGramos(300.0);

    comida.setItems(List.of(itemCarne, itemPapa));
    assertThat(comida.getEsVegetariano(), is(false));
  }

  @Test
  public void laComidaDeberiaSerVegetarianaSiTodosSusIngredientesLoSon() {
    ItemComida itemPapa = new ItemComida();
    itemPapa.setAlimento(papa);
    itemPapa.setCantidadGramos(500.0);

    comida.setItems(List.of(itemPapa));

    assertThat(comida.getEsVegetariano(), is(true));
  }

  @Test
  public void siUnAlimentoTieneCamposNulosLosCalculosNoDeberianLanzarNullPointerException() {
    Alimento alimentoIncompleto = new Alimento();
    alimentoIncompleto.setNombre("Agua");
    alimentoIncompleto.setPrecioEstimado(null);
    alimentoIncompleto.setCalorias(null);
    alimentoIncompleto.setProteinas(null);

    ItemComida itemAgua = new ItemComida();
    itemAgua.setAlimento(alimentoIncompleto);
    itemAgua.setCantidadGramos(250.0);

    comida.setItems(List.of(itemAgua));
    assertThat(comida.getPrecioEstimado(), is(0.0));
    assertThat(comida.getCalorias(), is(0));
    assertThat(comida.getProteinas(), is(0.0));
  }
}
