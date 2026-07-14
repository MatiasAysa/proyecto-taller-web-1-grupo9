package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioDespensa {
  List<ItemDespensa> obtenerDespensaDelUsuario(String email);

  void guardarOActualizarDespensa(ItemDespensa item);

  void agregarItemDespensa(String email, ItemDespensaDTO itemDespensaDTO);

  List<Alimento> obtenerAlimentosBaseDatos();

  void eliminarItemDespensa(Long id);

  void cambiarCantidadDespensa(Long id, Double cantidad);
}
