package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPlanificador {
  List<Alimento> obtenerAlimentosDisponibles();
  Usuario buscarUsuarioPorId(Long id);
}
