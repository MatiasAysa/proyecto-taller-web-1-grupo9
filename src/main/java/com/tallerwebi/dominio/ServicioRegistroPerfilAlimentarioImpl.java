package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioRegistroPerfilAlimentarioImpl implements ServicioRegistroPerfilAlimentario {

  private final RepositorioPerfilAlimentarioUsuario repositorioPerfil;
  private final RepositorioUsuario repositorioUsuario;

  @Autowired
  public ServicioRegistroPerfilAlimentarioImpl(
    RepositorioPerfilAlimentarioUsuario repositorioPerfil,
    RepositorioUsuario repositorioUsuario
  ) {
    this.repositorioPerfil = repositorioPerfil;
    this.repositorioUsuario = repositorioUsuario;
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

  private Boolean validarRestriccionesAlimentarias(String restriccionesAlimentarias) {
    return valorValido(RestriccionAlimentaria.class, restriccionesAlimentarias);
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
    if (usuario == null) return false;

    if (validarPerfilAlimentario(perfilAlimentarioDTO)) {
      PerfilAlimentarioUsuario nuevoPerfil = new PerfilAlimentarioUsuario(
        perfilAlimentarioDTO.getPeso(),
        perfilAlimentarioDTO.getAltura(),
        perfilAlimentarioDTO.getEdad(),
        perfilAlimentarioDTO.getSexo(),
        perfilAlimentarioDTO.getActividadFisica(),
        perfilAlimentarioDTO.getRestriccionesAlimentarias(),
        perfilAlimentarioDTO.getObjetivo()
      );
      repositorioPerfil.guardar(nuevoPerfil);
      usuario.setPerfilAlimentario(nuevoPerfil);

      return true;
    }
    return false;
  }
}
