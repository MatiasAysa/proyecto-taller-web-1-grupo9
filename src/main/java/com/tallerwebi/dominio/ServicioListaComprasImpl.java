package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

@Service
@Transactional
public class ServicioListaComprasImpl implements ServicioListaCompras {

  private final double CANTIDAD_KILO_A_GRAMOS = 1000.0;

  private RepositorioAlimento repositorioAlimento;
  private RepositorioListaDeCompras repositorioListaDeCompras;
  private RepositorioUsuario repositorioUsuario;

  @Autowired
  public ServicioListaComprasImpl(
    RepositorioAlimento repositorioAlimento,
    RepositorioListaDeCompras repositorioListaDeCompras,
    RepositorioUsuario repositorioUsuario
  ) {
    this.repositorioAlimento = repositorioAlimento;
    this.repositorioListaDeCompras = repositorioListaDeCompras;
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  public Alimento buscarAlimentoPorId(Long id) {
    return this.repositorioAlimento.buscarPorId(id);
  }

  @Override
  public Comida buscarComidaPorId(Long id) {
    return this.repositorioListaDeCompras.buscarComidaPorId(id);
  }

  @Override
  public List<DiaListaComprasDTO> armarDiasSeleccionados(
    MultiValueMap<String, String> seleccionados
  ) {
    List<DiaListaComprasDTO> dias = new ArrayList<>();

    for (Map.Entry<String, List<String>> entrada : seleccionados.entrySet()) {
      String key = entrada.getKey();

      if (!key.startsWith("comidasSeleccionadas")) {
        continue;
      }

      String numeroDiaStr = key.substring(key.indexOf("[") + 1, key.indexOf("]"));

      Integer numeroDia = Integer.parseInt(numeroDiaStr);

      DiaListaComprasDTO diaDTO = new DiaListaComprasDTO();
      diaDTO.setNumeroDia(numeroDia);

      List<Comida> comidas = new ArrayList<>();

      for (String idStr : entrada.getValue()) {
        Long idComida = Long.parseLong(idStr);
        Comida comida = buscarComidaPorId(idComida);
        if (comida != null) {
          comidas.add(comida);
        }
      }
      diaDTO.setComidas(comidas);
      dias.add(diaDTO);
    }

    return dias;
  }

  @Override
  public List<ItemCompra> generarListaCompras(List<Comida> Comidas) {
    List<ItemCompra> listaFinalCompra = new ArrayList<>();

    for (Comida comida : Comidas) {
      if (comida.getItems() == null) continue;
      for (ItemComida itemComida : comida.getItems()) {
        ItemCompra itemExistente = buscarItemCompra(listaFinalCompra, itemComida);

        if (itemExistente != null) {
          Double nuevaCantidad = itemExistente.getCantidadTotal() + itemComida.getCantidadGramos();
          itemExistente.setCantidadTotal(nuevaCantidad);
        } else {
          ItemCompra nuevoItem = new ItemCompra();
          nuevoItem.setAlimento(itemComida.getAlimento());
          nuevoItem.setCantidadTotal(itemComida.getCantidadGramos());

          listaFinalCompra.add(nuevoItem);
        }
      }
    }
    return listaFinalCompra;
  }

  @Override
  public ItemCompra buscarItemCompra(List<ItemCompra> itemsCompra, ItemComida itemComida) {
    for (ItemCompra itemCompra : itemsCompra) {
      if (itemCompra.getAlimento().getNombre().equals(itemComida.getAlimento().getNombre())) {
        return itemCompra;
      }
    }
    return null;
  }

  @Override
  public void calcularPrecios(List<ItemCompra> listaCompra) {
    for (ItemCompra item : listaCompra) {
      Double precioPorKilo = item.getAlimento().getPrecioEstimado();
      Double precioTotal = (item.getCantidadTotal() / CANTIDAD_KILO_A_GRAMOS) * precioPorKilo;
      item.setPrecoTotal(precioTotal);
    }
  }

  @Override
  public Double calcularTotalListaCompras(List<ItemCompra> listaCompra) {
    Double total = 0.0;
    for (ItemCompra item : listaCompra) {
      total += item.getPrecoTotal();
    }
    return total;
  }

  @Override
  public List<DiaListaComprasDTO> actualizarCantidadesYAlimentos(
    List<DiaListaComprasDTO> dias,
    String email
  ) throws UsuarioInexistenteException {
    Usuario usuario = repositorioUsuario.buscar(email);
    if (usuario == null) {
      throw new UsuarioInexistenteException("No existe un usuario con email " + email);
    }
    List<DiaListaComprasDTO> nuevaListaCompras = new ArrayList<>();

    //ObjetivoNutricional objetivoDiario = CalculadorNutricionalHelper.calcularObjetivoDiario(usuario.getPerfilAlimentario());
    //ObjetivoNutricional objetivoNutricionalDesayuno = CalculadorNutricionalHelper.calcularObjetivoComida(objetivoDiario,TipoDeComida.DESAYUNO);
    //ObjetivoNutricional objetivoNutricionalAlmuerzo = CalculadorNutricionalHelper.calcularObjetivoComida(objetivoDiario,TipoDeComida.ALMUERZO);
    //ObjetivoNutricional objetivoNutricionalCena =CalculadorNutricionalHelper.calcularObjetivoComida(objetivoDiario,TipoDeComida.CENA);

    return nuevaListaCompras;
  }

  @Override
  public List<String> mostrarDtosTestear(String email) throws UsuarioInexistenteException {
    Usuario usuario = repositorioUsuario.buscar(email);

    if (usuario == null) {
      throw new UsuarioInexistenteException("No existe un usuario con email " + email);
    }
    List<String> datos = new ArrayList<>();

    ObjetivoNutricional total = CalculadorNutricionalHelper.calcularObjetivoDiario(
      usuario.getPerfilAlimentario()
    );
    ObjetivoNutricional desayuno = CalculadorNutricionalHelper.calcularObjetivoComida(
      total,
      TipoDeComida.DESAYUNO
    );
    ObjetivoNutricional almuerzo = CalculadorNutricionalHelper.calcularObjetivoComida(
      total,
      TipoDeComida.ALMUERZO
    );
    ObjetivoNutricional cena = CalculadorNutricionalHelper.calcularObjetivoComida(
      total,
      TipoDeComida.CENA
    );

    datos.add("=== OBJETIVO DIARIO ===");
    datos.add(total.toString());

    datos.add("");

    datos.add("=== DESAYUNO ===");
    datos.add(desayuno.toString());

    datos.add("");

    datos.add("=== ALMUERZO ===");
    datos.add(almuerzo.toString());

    datos.add("");

    datos.add("=== CENA ===");
    datos.add(cena.toString());

    return datos;
  }
}
