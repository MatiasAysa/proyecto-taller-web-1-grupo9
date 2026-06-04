package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PresupuestoNoPositivoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPresupuestoException;
import com.tallerwebi.presentacion.DatosPresupuesto;
import java.time.LocalDate;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPresupuestoImpl implements ServicioPresupuesto {

  private RepositorioPresupuesto repositorioPresupuesto;

  @Autowired
  public ServicioPresupuestoImpl(RepositorioPresupuesto repositorioPresupuesto) {
    this.repositorioPresupuesto = repositorioPresupuesto;
  }

  @Transactional
  @Override
  public Presupuesto crearPresupuesto(float monto, int intervalo, LocalDate fecha, String email) {
    if (monto <= 0) {
      throw new PresupuestoNoPositivoException();
    }

    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(monto);
    presupuesto.setIntervalo(intervalo);
    presupuesto.setFecha(fecha);

    repositorioPresupuesto.guardarPresupuesto(presupuesto, email);

    return presupuesto;
  }

  @Transactional
  @Override
  public DatosPresupuesto buscarPresupuesto(String email) {
    Presupuesto presupuesto = repositorioPresupuesto.buscarPresupuesto(email);
    if (presupuesto == null) throw new UsuarioSinPresupuestoException();
    return new DatosPresupuesto(
      presupuesto.getMonto(),
      presupuesto.getIntervalo(),
      presupuesto.getFecha()
    );
  }
}
