package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PresupuestoNoPositivoException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPresupuestoImpl implements ServicioPresupuesto {

  private RepositorioPresupuesto repositorioPresupuesto;

  @Autowired
  public ServicioPresupuestoImpl(RepositorioPresupuesto repositorioPresupuesto) {
    this.repositorioPresupuesto = repositorioPresupuesto;
  }

  @Override
  public Presupuesto crearPresupuesto(float monto, int intervalo, LocalDate fecha) {
    if (monto <= 0) {
      throw new PresupuestoNoPositivoException();
    }

    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(monto);
    presupuesto.setIntervalo(intervalo);
    presupuesto.setFecha(fecha);

    repositorioPresupuesto.guardarPresupuesto(presupuesto);

    return presupuesto;
  }
}
