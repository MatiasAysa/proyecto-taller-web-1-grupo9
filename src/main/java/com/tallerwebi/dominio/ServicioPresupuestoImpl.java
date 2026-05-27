package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PresupuestoNoPositivoException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class ServicioPresupuestoImpl implements ServicioPresupuesto {

  @Override
  public Presupuesto crearPresupuesto(float monto, int intervalo, LocalDate fecha) {
    if (monto <= 0) {
      throw new PresupuestoNoPositivoException();
    }
    return new Presupuesto(monto, intervalo, fecha);
  }
}
