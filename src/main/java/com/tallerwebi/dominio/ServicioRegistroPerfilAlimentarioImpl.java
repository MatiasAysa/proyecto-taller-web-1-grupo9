package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioRegistroPerfilAlimentarioImpl implements ServicioRegistroPerfilAlimentario {

  private RepositorioPerfilAlimentarioUsuario repositorioPerfil;

  @Autowired
  public ServicioRegistroPerfilAlimentarioImpl(
    RepositorioPerfilAlimentarioUsuario repositorioPerfil
  ) {
    this.repositorioPerfil = repositorioPerfil;
  }

  // <E extends Enum<E>> -> Significa "Esta variable E representa a cualquier
  // Enum".
  // enumClase -> Recibe la clase del Enum (por ejemplo Sexo.class o
  // Objetivo.class).
  public <E extends Enum<E>> Boolean valorValido(Class<E> enumClase, String textoUsuario) {
    if (textoUsuario == null) {
      return false;
    }

    // 1. Obtenemos todos los valores del Enum (MASCULINO, FEMENINO, etc.)
    E[] valoresPosibles = enumClase.getEnumConstants();

    // 2. Recorremos esos valores
    for (E valor : valoresPosibles) {
      // 3. Comparamos ignorando mayúsculas/minúsculas
      if (valor.name().equalsIgnoreCase(textoUsuario.trim())) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Boolean validarPeso(Double peso) {
    if (peso != null && peso > 0 && peso < 635) {
      return true;
    }
    return false;
  }

  @Override
  public Boolean validarAltura(Double altura) {
    if (altura != null && altura > 0 && altura < 272) {
      return true;
    }
    return false;
  }

  @Override
  public Boolean validarEdad(Integer edad) {
    if (edad != null && edad > 0 && edad < 120) {
      return true;
    }
    return false;
  }

  @Override
  public Boolean validarSexo(String sexo) {
    return valorValido(Sexo.class, sexo);
  }

  @Override
  public Boolean validarActividadFisica(String actividadFisica) {
    return valorValido(AcividadFisica.class, actividadFisica);
  }

  @Override
  public Boolean validarRestriccionesAlimentarias(String restriccionesAlimentarias) {
    return valorValido(RestriccionAlimentaria.class, restriccionesAlimentarias);
  }

  @Override
  public Boolean validarObjetivo(String objetivo) {
    return valorValido(Objetivo.class, objetivo);
  }

  // @Override
  // public Boolean validarPerfilAlimentario(PerfilAlimentarioDTO
  // perfilAlimentario) {
  // Double peso = perfilAlimentario.getPeso();
  // Double altura = perfilAlimentario.getAltura();
  // Integer edad = perfilAlimentario.getEdad();
  // String sexo = perfilAlimentario.getSexo();
  // String actividadFisica = perfilAlimentario.getActividadFisica();
  // String restriccionesAlimentarias =
  // perfilAlimentario.getRestriccionesAlimentarias();
  // String objetivo = perfilAlimentario.getObjetivo();
  //
  // if (!validarPeso(peso)) {
  // return false;
  // }
  //
  // if (!validarAltura(altura)) {
  // return false;
  // }
  //
  // if (!validarEdad(edad)) {
  // return false;
  // }
  //
  // if (!validarSexo(sexo)) {
  // return false;
  // }
  //
  // if (!validarActividadFisica(actividadFisica)) {
  // return false;
  // }
  // if (!validarRestriccionesAlimentarias(restriccionesAlimentarias)) {
  // return false;
  // }
  // if (!validarObjetivo(objetivo)) {
  // return false;
  // }
  // return true;
  // }

  @Override
  public Boolean validarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO) {
    if (perfilAlimentarioDTO == null) {
      return false;
    }

    // Evaluamos todo directo sobre el DTO.
    // Al no declarar variables locales intermedias, la anomalía "DU" desaparece por
    // completo.
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
  public Boolean guardarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO) {
    if (validarPerfilAlimentario(perfilAlimentarioDTO)) {
      PerfilAlimentarioUsuario entidad = new PerfilAlimentarioUsuario(
        perfilAlimentarioDTO.getPeso(),
        perfilAlimentarioDTO.getAltura(),
        perfilAlimentarioDTO.getEdad(),
        perfilAlimentarioDTO.getSexo(),
        perfilAlimentarioDTO.getActividadFisica(),
        perfilAlimentarioDTO.getRestriccionesAlimentarias(),
        perfilAlimentarioDTO.getObjetivo()
      );
      repositorioPerfil.guardar(entidad);
      return true;
    }
    return false;
  }
}
