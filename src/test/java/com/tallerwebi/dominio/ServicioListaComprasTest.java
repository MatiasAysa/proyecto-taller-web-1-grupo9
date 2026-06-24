package com.tallerwebi.dominio;

import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioListaComprasTest {

  private RepositorioAlimento repositorioAlimentoMock;
  private ServicioListaCompras servicioListaCompras;

  @BeforeEach
  public void init() {
    this.repositorioAlimentoMock = mock(RepositorioAlimento.class);
    this.servicioListaCompras = new ServicioListaComprasImpl(this.repositorioAlimentoMock);
  }

  @Test
  public void laAgrupacionDeIngredientesEsExitosa() {
    givenUsuarioNoExiste();
  }

  private void givenUsuarioNoExiste() {}
}
