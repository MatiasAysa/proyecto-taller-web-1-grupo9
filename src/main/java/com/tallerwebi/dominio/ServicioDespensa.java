package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioDespensa {
  List<ItemDespensa> obtenerDespensaDelUsuario(String email);

  void guardarOActualizarDespensa(ItemDespensa itemDespensa);

  void eliminarItemDespensa(String email, Long idAlacena);
}
