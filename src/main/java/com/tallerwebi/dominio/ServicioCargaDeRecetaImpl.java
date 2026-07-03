package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosReceta;
import com.tallerwebi.presentacion.IngredienteDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioCargaDeReceta")
@Transactional
public class ServicioCargaDeRecetaImpl implements ServicioCargaDeReceta {

  private RepositorioAlimento repositorioAlimento;
  private RepositorioReceta repositorioReceta;
  private RepositorioUsuario repositorioUsuario;

  @Autowired
  public ServicioCargaDeRecetaImpl(
    RepositorioAlimento repositorioAlimento,
    RepositorioReceta repositorioReceta,
    RepositorioUsuario repositorioUsuario
  ) {
    this.repositorioAlimento = repositorioAlimento;
    this.repositorioReceta = repositorioReceta;
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  public List<String> obtenerNombresDeAlimentosExistentes() {
    List<Alimento> alimentos = repositorioAlimento.obtenerTodosLosAlimentos();
    return alimentos.stream().map(Alimento::getNombreGenerico).collect(Collectors.toList());
  }

  @Override
  public void cargarReceta(DatosReceta datosReceta, String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    Comida comida = new Comida();
    List<ItemComida> ingredientes = new ArrayList<ItemComida>();
    comida.setNombre(datosReceta.getNombre());
    for (IngredienteDTO i : datosReceta.getIngredientes()) {
      Alimento alimento = repositorioAlimento.obtenerPorNombreGenerico(i.getNombre());
      ItemComida ingrediente = new ItemComida();
      ingrediente.setAlimento(alimento);
      ingrediente.setCantidadGramos(i.getCantidad());
      ingredientes.add(ingrediente);
    }
    comida.setItems(ingredientes);
    comida.setAutor(usuario);
    repositorioReceta.guardarReceta(comida);
  }
}
