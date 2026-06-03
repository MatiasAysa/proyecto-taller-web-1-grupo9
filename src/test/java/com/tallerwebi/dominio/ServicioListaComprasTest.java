package com.tallerwebi.dominio;

import java.util.List;
import org.junit.jupiter.api.Test;

public class ServicioListaComprasTest {

  ServicioListaCompras servicioListaCompras = new ServicioListaComprasImpl();

  @Test
  public void laAgrupacionDeIngredientesEsExitosa() {
    givenUsuarioNoExiste();
  }

  private void givenUsuarioNoExiste() {}
}
