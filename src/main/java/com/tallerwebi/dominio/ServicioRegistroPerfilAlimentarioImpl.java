package com.tallerwebi.dominio;

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

  @Autowired
  public ServicioRegistroPerfilAlimentarioImpl(
    RepositorioPerfilAlimentarioUsuario repositorioPerfil,
    RepositorioUsuario repositorioUsuario,
    RepositorioRestriccionAlimentaria repositorioRestriccionAlimentaria
  ) {
    this.repositorioPerfil = repositorioPerfil;
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioRestriccionAlimentaria = repositorioRestriccionAlimentaria;
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

  private Boolean validarPeso(Double peso) {
    return peso != null && peso > 0 && peso < 635;
  }

  private Boolean validarAltura(Double altura) {
    return altura != null && altura > 0 && altura < 272;
  }

  private Boolean validarEdad(Integer edad) {
    return edad != null && edad > 0 && edad < 100;
  }

  private Boolean validarSexo(String sexo) {
    return valorValido(Sexo.class, sexo);
  }

  private Boolean validarActividadFisica(String actividadFisica) {
    return valorValido(AcividadFisica.class, actividadFisica);
  }

  private Boolean validarRestriccionesAlimentarias(Set<String> restriccionesAlimentarias) {
    if (restriccionesAlimentarias == null) return false;
    for (String restriccion : restriccionesAlimentarias) {
      if (!valorValido(RestriccionAlimentariaTipo.class, restriccion)) return false;
    }
    return true;
  }

  private Boolean validarObjetivo(String objetivo) {
    return valorValido(Objetivo.class, objetivo);
  }

  @Override
  public Boolean validarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO) {
    if (perfilAlimentarioDTO == null) {
      return false;
    }
    return (
      validarPeso(perfilAlimentarioDTO.getPeso()) &&
      validarAltura(perfilAlimentarioDTO.getAltura()) &&
      validarEdad(perfilAlimentarioDTO.getEdad()) &&
      validarSexo(perfilAlimentarioDTO.getSexo()) &&
      validarActividadFisica(perfilAlimentarioDTO.getActividadFisica()) &&
      validarRestriccionesAlimentarias(perfilAlimentarioDTO.getRestriccionesAlimentarias()) &&
      validarObjetivo(perfilAlimentarioDTO.getObjetivo())
    );
  }

  // guardo los datos en la base de datos
  @Override
  public Boolean guardarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO, String email) {
    Usuario usuario = repositorioUsuario.buscar(email);

    if (validarPerfilAlimentario(perfilAlimentarioDTO) && usuario != null) {
      if (usuario.getPerfilAlimentario() != null) {
        return actualizarPerfilAlimentario(
          usuario.getPerfilAlimentario(),
          perfilAlimentarioDTO
        );
      }
      PerfilAlimentarioUsuario nuevoPerfil = crearPerfilAlimentarioUsuarioDesdeDTO(
        perfilAlimentarioDTO
      );
      // primero se crea un perfil sin restricciones para relacionarlo
      repositorioPerfil.guardar(nuevoPerfil);
      usuario.setPerfilAlimentario(nuevoPerfil);

      // despues con las restricciones se crea un perfil de restricciones y se
      // relaciona con el perfil
      Set<String> restriccionesAlimentarias = perfilAlimentarioDTO.getRestriccionesAlimentarias();
      // guardar cada restriccion en la base de datos
      for (String restriccion : restriccionesAlimentarias) {
        RestriccionAlimentaria restriccionAlimentaria = new RestriccionAlimentaria();
        restriccionAlimentaria.setNombre(restriccion);
        restriccionAlimentaria.setPerfil(nuevoPerfil);
        repositorioRestriccionAlimentaria.guardar(restriccionAlimentaria);
      }
      if (restriccionesAlimentarias.isEmpty()) {
        RestriccionAlimentaria restriccionAlimentaria = new RestriccionAlimentaria();
        restriccionAlimentaria.setNombre(RestriccionAlimentariaTipo.NINGUNO.name());
        restriccionAlimentaria.setPerfil(nuevoPerfil);
        repositorioRestriccionAlimentaria.guardar(restriccionAlimentaria);
      }

      return true;
    }
    return false;
  }

  // @Override
  public Boolean actualizarPerfilAlimentario(
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
    if (nuevaRestriccionesAlimentarias.isEmpty()) {
      pefiAlimentarioUsuario.getRestriccionesAlimentarias().clear();
      pefiAlimentarioUsuario
        .getRestriccionesAlimentarias()
        .add(
          new RestriccionAlimentaria(
            pefiAlimentarioUsuario,
            RestriccionAlimentariaTipo.NINGUNO.name()
          )
        );
      return true;
    }

    pefiAlimentarioUsuario.getRestriccionesAlimentarias().clear();

    for (String nombreNuevaRestriccion : nuevaRestriccionesAlimentarias) {
      pefiAlimentarioUsuario
        .getRestriccionesAlimentarias()
        .add(new RestriccionAlimentaria(pefiAlimentarioUsuario, nombreNuevaRestriccion));
    }

    return true;
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
