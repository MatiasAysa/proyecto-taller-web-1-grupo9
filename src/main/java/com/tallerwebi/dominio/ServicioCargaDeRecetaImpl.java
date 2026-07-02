package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosReceta;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServicioCargaDeRecetaImpl implements ServicioCargaDeReceta {

  @Override
  public List<String> obtenerNombresDeAlimentosExistentes() {
    return List.of("Huevo", "Harina", "Manteca");
  }

  @Override
  public void cargarReceta(DatosReceta datosReceta) {}
}
