package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

import com.tallerwebi.dominio.ScraperService;
import org.junit.jupiter.api.Test;

public class ScraperServiceTest {

  private ScraperService scraperService = new ScraperServiceImpl();

  @Test
  public void siLaUrlEsNullDebeRetornarNull() {
    // WHEN
    Double precio = scraperService.obtenerPrecioReal(null);

    // THEN
    assertThat(precio, nullValue());
  }

  @Test
  public void siLaUrlEstaVaciaDebeRetornarNull() {
    // WHEN
    Double precio = scraperService.obtenerPrecioReal("");

    // THEN
    assertThat(precio, nullValue());
  }

  @Test
  public void siLaUrlTieneSoloEspaciosDebeRetornarNull() {
    // WHEN
    Double precio = scraperService.obtenerPrecioReal("     ");

    // THEN
    assertThat(precio, nullValue());
  }

  @Test
  public void siLaUrlEsInvalidaDebeRetornarNull() {
    // WHEN
    Double precio = scraperService.obtenerPrecioReal("https://pagina-inexistente-123456.com");

    // THEN
    assertThat(precio, nullValue());
  }

  @Test
  public void siLaPaginaNoTienePrecioDebeRetornarNull() {
    // WHEN
    Double precio = scraperService.obtenerPrecioReal("https://www.google.com");

    // THEN
    assertThat(precio, nullValue());
  }
}
