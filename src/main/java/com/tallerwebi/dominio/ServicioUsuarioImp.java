package com.tallerwebi.dominio;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioUsuarioImp implements ServicioUsuario {

  private final RepositorioUsuario repositorioUsuario;
  private final RepositorioPresupuesto repositorioPresupuesto;
  private final RepositorioPerfilAlimentarioUsuario repositorioPerfilAlimentarioUsuario;

  @Autowired
  public ServicioUsuarioImp(
    RepositorioUsuario repositorioUsuario,
    RepositorioPresupuesto repositorioPresupuesto,
    RepositorioPerfilAlimentarioUsuario repositorioPerfilAlimentarioUsuario
  ) {
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioPresupuesto = repositorioPresupuesto;
    this.repositorioPerfilAlimentarioUsuario = repositorioPerfilAlimentarioUsuario;
  }

  @Override
  public boolean tienePerfilAlimentario(String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    if (usuario == null) {
      return false;
    }
    return usuario.getPerfilAlimentario() != null;
  }

  @Override
  public boolean tienePresupuesto(String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    Presupuesto presupuesto = repositorioPresupuesto.buscarPresupuesto(usuario);
    if (presupuesto == null) {
      return false;
    }
    return true;
  }

  @Override
  public DatosClientePanel obtenerDatosClientePanel(String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    if (usuario == null) {
      return null;
    }

    PerfilAlimentarioUsuario perfilAlimentario = usuario.getPerfilAlimentario();
    if (perfilAlimentario == null) {
      return null;
    }
    Set<PerfilRestriccion> restricciones = perfilAlimentario.getPerfilRestricciones();
    if (restricciones != null) {
      restricciones.size();
    }
    DatosClientePanel datosClientePanel = new DatosClientePanel();

    datosClientePanel.setPeso(perfilAlimentario.getPeso());
    datosClientePanel.setAltura(perfilAlimentario.getAltura());
    datosClientePanel.setObjetivo(perfilAlimentario.getObjetivo());
    datosClientePanel.setSexo(perfilAlimentario.getSexo());
    datosClientePanel.setEdad(perfilAlimentario.getEdad());
    datosClientePanel.setActividadFisica(perfilAlimentario.getActividadFisica());
    datosClientePanel.setPerfilRestricciones(restricciones);

    Presupuesto presupuesto = repositorioPresupuesto.buscarPresupuesto(usuario);
    if (presupuesto == null) {
      presupuesto = new Presupuesto();
    }
    datosClientePanel.setPresupuestoFechaInicio(presupuesto.getFecha());
    datosClientePanel.setIntervalo(presupuesto.getIntervalo());
    datosClientePanel.setMonto(presupuesto.getMonto());
    return datosClientePanel;
  }
}
