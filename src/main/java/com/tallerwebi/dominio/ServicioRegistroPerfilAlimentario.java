package com.tallerwebi.dominio;

public interface ServicioRegistroPerfilAlimentario {
  Boolean validarPeso(Double peso);

  Boolean validarAltura(Double altura);

  Boolean validarEdad(Integer edad);

  Boolean validarSexo(String sexo);

  Boolean validarActividadFisica(String actividadFisica);

  Boolean validarRestriccionesAlimentarias(String restriccionesAlimentarias);

  Boolean validarObjetivo(String objetivo);

  Boolean validarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO);

  Boolean guardarPerfilAlimentario(PerfilAlimentarioDTO perfilAlimentarioDTO);
}
