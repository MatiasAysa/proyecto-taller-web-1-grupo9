package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.List;
import org.springframework.util.MultiValueMap;

public interface ServicioListaCompras {
  List<ItemCompra> generarListaCompras(List<Comida> Comidas);
  ItemCompra buscarItemCompra(List<ItemCompra> itemsCompra, ItemComida itemComida);
  void calcularPrecios(List<ItemCompra> listaCompra);
  Double calcularTotalListaCompras(List<ItemCompra> listaCompra);
  Alimento buscarAlimentoPorId(Long id);
  Comida buscarComidaPorId(Long id);
  List<DiaListaComprasDTO> armarDiasSeleccionados(MultiValueMap<String, String> seleccionados);
  List<String> mostrarDtosTestear(String email) throws UsuarioInexistenteException;
}
