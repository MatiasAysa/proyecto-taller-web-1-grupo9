package com.tallerwebi.dominio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ServicioDespensaImpl implements ServicioDespensa {

  private final RepositorioDespensa repositorioDespensa;
  private final RepositorioUsuario repositorioUsuario;
  private final RepositorioAlimento repositorioAlimento;

  @Autowired
  public ServicioDespensaImpl(
    RepositorioDespensa repositorioDespensa,
    RepositorioUsuario repositorioUsuario,
    RepositorioAlimento repositorioAlimento
  ) {
    this.repositorioDespensa = repositorioDespensa;
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioAlimento = repositorioAlimento;
  }

  @Override
  public List<ItemDespensa> obtenerDespensaDelUsuario(String email) {
    Usuario usuario = this.repositorioUsuario.buscar(email);
    if (usuario == null) {
      throw new RuntimeException("Usuario no encontrado");
    }
    return this.repositorioDespensa.obtenerDespensaDelUsuario(usuario);
  }

  @Override
  public void guardarOActualizarDespensa(ItemDespensa item) {
    repositorioDespensa.guardarOActualizarDespensa(item);
  }

  @Override
  public void agregarItemDespensa(String email, ItemDespensaDTO itemDespensaDTO) {
    Usuario usuario = this.repositorioUsuario.buscar(email);
    if (usuario == null) {
      throw new RuntimeException("Usuario no encontrado");
    }

    // si existe alimento en la base de datos
    if (itemDespensaDTO.getIdAlimentoExistenteEnBaseDatos() != null) {
      Alimento alimento = repositorioAlimento.buscarPorId(
        itemDespensaDTO.getIdAlimentoExistenteEnBaseDatos()
      );
      this.repositorioDespensa.agregarItemDespensa(
          usuario,
          alimento,
          itemDespensaDTO.getCantidad()
        );
      return;
    }

    // no existe alimento en la base de datos
    ItemDespensa itemDespensaNuevo = new ItemDespensa();
    itemDespensaNuevo.setNombre(itemDespensaDTO.getNombre());
    itemDespensaNuevo.setTipoComida(itemDespensaDTO.getTipoComida());
    itemDespensaNuevo.setEsVegetariano(itemDespensaDTO.getEsVegetariano());
    itemDespensaNuevo.setEsCeliaco(itemDespensaDTO.getEsCeliaco());
    itemDespensaNuevo.setContieneLactosa(itemDespensaDTO.getContieneLactosa());
    itemDespensaNuevo
      .getInfoNutricional()
      .setCalorias(itemDespensaDTO.getInfoNutricional().getCalorias());
    itemDespensaNuevo
      .getInfoNutricional()
      .setProteinas(itemDespensaDTO.getInfoNutricional().getProteinas());
    itemDespensaNuevo
      .getInfoNutricional()
      .setCarbohidratos(itemDespensaDTO.getInfoNutricional().getCarbohidratos());
    itemDespensaNuevo
      .getInfoNutricional()
      .setGrasas(itemDespensaDTO.getInfoNutricional().getGrasas());
    itemDespensaNuevo.setCantidad(itemDespensaDTO.getCantidad());
    itemDespensaNuevo.setFechaVencimiento(itemDespensaDTO.getFechaVencimiento());

    // this.repositorioAlimento.guard
    this.repositorioDespensa.agregarItemDespensaNuevo(usuario, itemDespensaNuevo);
  }

  @Override
  public List<Alimento> obtenerAlimentosBaseDatos() {
    return repositorioAlimento.obtenerListaAlimentos();
  }

  @Override
  public void eliminarItemDespensa(Long id) {
    repositorioDespensa.eliminarItemDespensa(id);
  }

  @Override
  public void cambiarCantidadDespensa(Long id, Double cantidad) {
    ItemDespensa item = repositorioDespensa.obtenerItemDespensaPorId(id);
    if (item == null) {
      throw new RuntimeException("Item no encontrado");
    }
    if (item.getCantidad() + cantidad <= 0) {
      repositorioDespensa.eliminarItemDespensa(id);
    } else {
      item.setCantidad(cantidad);
    }
  }
}
