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
  private RepositorioUsuario repositorioUsuario;

  @Autowired
  public ServicioPresupuestoImpl(
    RepositorioPresupuesto repositorioPresupuesto,
    RepositorioUsuario repositorioUsuario
  ) {
    this.repositorioPresupuesto = repositorioPresupuesto;
    this.repositorioUsuario = repositorioUsuario;
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

    repositorioPresupuesto.guardarPresupuesto(presupuesto, repositorioUsuario.buscar(email));

    return presupuesto;
  }

  @Transactional
  @Override
  public DatosPresupuesto buscarPresupuesto(String email) {
    Presupuesto presupuesto = repositorioPresupuesto.buscarPresupuesto(
      repositorioUsuario.buscar(email)
    );
    if (presupuesto == null) throw new UsuarioSinPresupuestoException();
    DatosPresupuesto datosPresupuesto = new DatosPresupuesto();
    datosPresupuesto.setMonto(presupuesto.getMonto());
    datosPresupuesto.setIntervalo(presupuesto.getIntervalo());
    datosPresupuesto.setFecha(presupuesto.getFecha());
    return datosPresupuesto;
  }
}
