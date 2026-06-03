package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosPresupuesto;
import java.time.LocalDate;

public interface ServicioPresupuesto {
  Presupuesto crearPresupuesto(float monto, int intervalo, LocalDate fecha, String email);

  DatosPresupuesto buscarPresupuesto(String usuarioLogueadoEmail);
}
