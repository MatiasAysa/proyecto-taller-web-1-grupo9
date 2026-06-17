package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import com.tallerwebi.dominio.excepcion.perfilException.ActividadFisicaInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.AlturaInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.ObjetivoInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.PerfilAlimentarioDTONuloException;
import com.tallerwebi.dominio.excepcion.perfilException.PesoInvalidoException;
import com.tallerwebi.dominio.excepcion.perfilException.RestriccionesAlimentariasInvalidasException;
import com.tallerwebi.dominio.excepcion.perfilException.SexoInvalidoException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioRegistroPerfilAlimentarioImpl implements ServicioRegistroPerfilAlimentario {

  private final RepositorioPerfilAlimentarioUsuario repositorioPerfil;
  private final RepositorioUsuario repositorioUsuario;
  private final RepositorioRestriccionAlimentaria repositorioRestriccionAlimentaria;
  private final RepositorioPerfilRestriccion repositorioPerfilRestriccion;

  @Autowired
  public ServicioRegistroPerfilAlimentarioImpl(
    RepositorioPerfilAlimentarioUsuario repositorioPerfil,
    RepositorioUsuario repositorioUsuario,
    RepositorioRestriccionAlimentaria repositorioRestriccionAlimentaria,
    RepositorioPerfilRestriccion repositorioPerfilRestriccion
  ) {
    this.repositorioPerfil = repositorioPerfil;
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioRestriccionAlimentaria = repositorioRestriccionAlimentaria;
    this.repositorioPerfilRestriccion = repositorioPerfilRestriccion;
  }

  private <E extends Enum<E>> Boolean valorValido(Class<E> enumClase, String textoUsuario) {
    if (textoUsuario == null) return false;
    E[] valoresPosibles = enumClase.getEnumConstants();
    for (E valor : valoresPosibles) {
      if (valor.name().equalsIgnoreCase(textoUsuario.trim())) {
        return true;
      }
    }
    return false;
  }

  private void validarPeso(Double peso) {
    if (peso == null || peso < 0 || peso > 635) {
      throw new PesoInvalidoException();
    }
  }

  private void validarAltura(Double altura) {
    if (altura == null || altura < 0 || altura > 272) {
      throw new AlturaInvalidaException();
    }
  }

  private void validarEdad(Integer edad) {
    if (edad == null || edad < 0 || edad > 100) {
      throw new EdadInvalidaException();
    }
  }

  private void validarSexo(String sexo) {
    if (!valorValido(SexoTipo.class, sexo)) {
      throw new SexoInvalidoException();
    }
  }

  private void validarActividadFisica(String actividadFisica) {
    if (!valorValido(ActividadFisicaTipo.class, actividadFisica)) {
      throw new ActividadFisicaInvalidaException();
    }
  }

  private void validarRestriccionesAlimentarias(Set<String> restriccionesAlimentarias) {
    for (String restriccion : restriccionesAlimentarias) {
      if (
        !valorValido(RestriccionAlimentariaTipo.class, restriccion)
      ) throw new RestriccionesAlimentariasInvalidasException();
    }
  }

  private void validarObjetivo(String objetivo) {
    if (!valorValido(ObjetivoTipo.class, objetivo)) throw new ObjetivoInvalidaException();
  }

  @Override
  public void validarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO) {
    if (perfilAlimentarioDTO == null) {
      throw new PerfilAlimentarioDTONuloException();
    }
    validarPeso(perfilAlimentarioDTO.getPeso());
    validarAltura(perfilAlimentarioDTO.getAltura());
    validarEdad(perfilAlimentarioDTO.getEdad());
    validarSexo(perfilAlimentarioDTO.getSexo());
    validarActividadFisica(perfilAlimentarioDTO.getActividadFisica());
    validarRestriccionesAlimentarias(perfilAlimentarioDTO.getRestriccionesAlimentarias());
    validarObjetivo(perfilAlimentarioDTO.getObjetivo());
  }

  public Usuario obtenerUsuarioLogueado(String email) throws UsuarioInexistenteException {
    Usuario usuario = repositorioUsuario.buscar(email);
    if (usuario == null) {
      throw new UsuarioInexistenteException("");
    }
    return usuario;
  }

  // guardo los datos en la base de datos
  @Override
  public void guardarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO, String email)
    throws UsuarioInexistenteException {
    Usuario usuario = obtenerUsuarioLogueado(email);
    validarPerfilAlimentario(perfilAlimentarioDTO);

    // Si el usuario ya tiene un perfil, se actualiza
    if (usuario.getPerfilAlimentario() != null) {
      actualizarPerfilAlimentario(usuario.getPerfilAlimentario(), perfilAlimentarioDTO);
      return;
    }

    // se crea uno nuevo perfil
    PerfilAlimentarioUsuario nuevoPerfil = crearPerfilAlimentarioUsuarioDesdeDTO(
      perfilAlimentarioDTO
    );
    // primero se crea un perfil sin restricciones para relacionarlo
    repositorioPerfil.guardar(nuevoPerfil);
    usuario.setPerfilAlimentario(nuevoPerfil);

    // despues con las restricciones del dto creo PerfilRestriccion y lo relaciono
    // con el perfil

    Set<String> restriccionesAlimentarias = perfilAlimentarioDTO.getRestriccionesAlimentarias();
    if (restriccionesAlimentarias != null && !restriccionesAlimentarias.isEmpty()) {
      for (String restriccion : restriccionesAlimentarias) {
        RestriccionAlimentaria restriccionAlimentaria =
          repositorioRestriccionAlimentaria.buscarPorNombre(restriccion);
        PerfilRestriccion perfilRestriccion = new PerfilRestriccion();
        perfilRestriccion.setRestriccion(restriccionAlimentaria);
        perfilRestriccion.setPerfil(nuevoPerfil);
        repositorioPerfilRestriccion.guardar(perfilRestriccion);
      }
    } else {
      // Si no hay restricciones se agrega la restriccion de "NINGUNO"
      RestriccionAlimentaria restriccionAlimentaria =
        repositorioRestriccionAlimentaria.buscarPorNombre(
          RestriccionAlimentariaTipo.NINGUNO.name()
        );
      repositorioPerfilRestriccion.guardar(
        new PerfilRestriccion(nuevoPerfil, restriccionAlimentaria)
      );
    }
  }

  // @Override
  public void actualizarPerfilAlimentario(
    PerfilAlimentarioUsuario pefiAlimentarioUsuario,
    PerfilAlimentarioDTO perfilAlimentarioDTO
  ) {
    pefiAlimentarioUsuario.setPeso(perfilAlimentarioDTO.getPeso());
    pefiAlimentarioUsuario.setAltura(perfilAlimentarioDTO.getAltura());
    pefiAlimentarioUsuario.setEdad(perfilAlimentarioDTO.getEdad());
    pefiAlimentarioUsuario.setSexo(perfilAlimentarioDTO.getSexo());
    pefiAlimentarioUsuario.setActividadFisica(perfilAlimentarioDTO.getActividadFisica());
    pefiAlimentarioUsuario.setObjetivo(perfilAlimentarioDTO.getObjetivo());

    Set<String> nuevaRestriccionesAlimentarias =
      perfilAlimentarioDTO.getRestriccionesAlimentarias();
    Set<PerfilRestriccion> restriccionesActuales = pefiAlimentarioUsuario.getPerfilRestricciones();
    // si no hay restricciones se agrega "NINGUNO"
    if (nuevaRestriccionesAlimentarias.isEmpty()) {
      restriccionesActuales.clear();
      restriccionesActuales.add(
        new PerfilRestriccion(
          pefiAlimentarioUsuario,
          repositorioRestriccionAlimentaria.buscarPorNombre(
            RestriccionAlimentariaTipo.NINGUNO.name()
          )
        )
      );
      return;
    }
    // se actualizan las restricciones alimentarias
    restriccionesActuales.clear();
    for (String nuevaRestriccion : nuevaRestriccionesAlimentarias) {
      RestriccionAlimentaria restriccion = repositorioRestriccionAlimentaria.buscarPorNombre(
        nuevaRestriccion
      );
      restriccionesActuales.add(new PerfilRestriccion(pefiAlimentarioUsuario, restriccion));
    }
  }

  public PerfilAlimentarioUsuario crearPerfilAlimentarioUsuarioDesdeDTO(
    PerfilAlimentarioDTO perfilAlimentarioDTO
  ) {
    PerfilAlimentarioUsuario nuevoPerfil = new PerfilAlimentarioUsuario();
    nuevoPerfil.setPeso(perfilAlimentarioDTO.getPeso());
    nuevoPerfil.setAltura(perfilAlimentarioDTO.getAltura());
    nuevoPerfil.setEdad(perfilAlimentarioDTO.getEdad());
    nuevoPerfil.setSexo(perfilAlimentarioDTO.getSexo());
    nuevoPerfil.setActividadFisica(perfilAlimentarioDTO.getActividadFisica());
    nuevoPerfil.setObjetivo(perfilAlimentarioDTO.getObjetivo());
    return nuevoPerfil;
  }
}
