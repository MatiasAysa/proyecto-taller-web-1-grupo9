package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NombreDeAlimentoInexistenteException;
import com.tallerwebi.dominio.excepcion.RecetaConNombreRepetidoException;
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
    if (yaExisteRecetaConMismoNombreYUsuario(datosReceta.getNombre(), usuario)) {
      throw new RecetaConNombreRepetidoException();
    }
    List<Integer> alimentosInexistentes = obtenerAlimentosInexistentes(
      datosReceta.getIngredientes()
    );
    if (!alimentosInexistentes.isEmpty()) {
      throw new NombreDeAlimentoInexistenteException(alimentosInexistentes);
    }
    Comida comida = new Comida();
    List<ItemComida> ingredientes = new ArrayList<ItemComida>();

    comida.setNombre(datosReceta.getNombre());
    comida.setTipo(TipoDeComida.valueOf(datosReceta.getTipo()));
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

  @Override
  public List<DatosReceta> obtenerRecetasDeUsuario(String email) {
    return obtenerDTODeRecetas(obtenerListaDeRecetas(email));
  }

  @Override
  public void eliminarReceta(Long id, String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    Comida comida = repositorioReceta.buscarRecetaPorId(id);
    if (comida == null || comida.getAutor() == null || !comida.getAutor().equals(usuario)) return;
    repositorioReceta.eliminarReceta(id);
  }

  @Transactional
  private List<Comida> obtenerListaDeRecetas(String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    return repositorioReceta.obtenerRecetasDeUsuario(usuario);
  }

  private List<DatosReceta> obtenerDTODeRecetas(List<Comida> recetasEntity) {
    if (recetasEntity.isEmpty()) return new ArrayList<DatosReceta>();
    List<DatosReceta> datosRecetaList = new ArrayList<DatosReceta>();
    for (Comida c : recetasEntity) {
      if (c != null) {
        DatosReceta datosReceta = new DatosReceta();
        datosReceta.setId(c.getId());
        datosReceta.setNombre(c.getNombre());
        datosReceta.setTipo(c.getTipo().name());
        List<IngredienteDTO> ingredientes = new ArrayList<IngredienteDTO>();
        for (ItemComida i : c.getItems()) {
          IngredienteDTO ingredienteDTO = new IngredienteDTO();
          ingredienteDTO.setNombre(i.getAlimento().getNombreGenerico());
          ingredienteDTO.setCantidad(i.getCantidadGramos());
          ingredientes.add(ingredienteDTO);
        }
        datosReceta.setIngredientes(ingredientes);
        datosRecetaList.add(datosReceta);
      }
    }
    return datosRecetaList;
  }

  private List<Integer> obtenerAlimentosInexistentes(List<IngredienteDTO> ingredientes) {
    List<Integer> alimentosInexistentes = new ArrayList<Integer>();
    for (IngredienteDTO ingrediente : ingredientes) {
      if (repositorioAlimento.obtenerPorNombreGenerico(ingrediente.getNombre()) == null) {
        alimentosInexistentes.add(ingredientes.indexOf(ingrediente));
      }
    }
    return alimentosInexistentes;
  }

  private boolean yaExisteRecetaConMismoNombreYUsuario(String nombre, Usuario usuario) {
    return repositorioReceta.buscarRecetaPorNombreYUsuario(nombre, usuario) != null;
  }
}
