package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosReceta;
import java.util.List;

public interface ServicioCargaDeReceta {
  List<String> obtenerNombresDeAlimentosExistentes();

  void cargarReceta(DatosReceta datosReceta, String usuario);

  List<DatosReceta> obtenerRecetasDeUsuario(String email);
}
