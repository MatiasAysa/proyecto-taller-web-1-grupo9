package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.DiaListaComprasDTO;
import com.tallerwebi.dominio.ItemCompra;
import com.tallerwebi.dominio.ServicioListaCompras;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorListaCompras {

  private ServicioListaCompras servicioListaCompras;

  @Autowired
  public ControladorListaCompras(ServicioListaCompras servicioListaCompras) {
    this.servicioListaCompras = servicioListaCompras;
  }

  @PostMapping("/lista-compras")
  public ModelAndView mostrarListaCompras(
    @RequestParam(required = false) MultiValueMap<String, String> todasLasSelecciones
  ) {
    ModelMap modelo = new ModelMap();

    if (todasLasSelecciones == null || todasLasSelecciones.isEmpty()) {
      modelo.put("error", "No seleccionaste ningún alimento de tu plan.");
      return new ModelAndView("redirect:/planificador", modelo);
    }

    List<DiaListaComprasDTO> dias = servicioListaCompras.armarDiasSeleccionados(
      todasLasSelecciones
    );

    List<Comida> todasLasComidas = dias
      .stream()
      .flatMap(dia -> dia.getComidas().stream())
      .collect(Collectors.toList());

    List<ItemCompra> listaDeCompras = servicioListaCompras.generarListaCompras(todasLasComidas);
    servicioListaCompras.calcularPrecios(listaDeCompras);

    Double total = servicioListaCompras.calcularTotalListaCompras(listaDeCompras);

    modelo.put("dias", dias);
    modelo.put("listaDeCompras", listaDeCompras);
    modelo.put("totalLista", total);

    return new ModelAndView("lista-compras", modelo);
  }
}
/*

    for (Map.Entry<String, List<String>> entrada : todasLasSelecciones.entrySet()) {
      String key = entrada.getKey();

      if (key.startsWith("comidasSeleccionadas")) {
        String numeroDiaStr = key.substring(key.indexOf("[") + 1, key.indexOf("]"));
        Integer numeroDia = Integer.parseInt(numeroDiaStr);

        List<String> idsArray = entrada.getValue();

        Comida comidaDia = new Comida();
        comidaDia.setNombre("DÍA " + numeroDia + " - Alimentos Seleccionados");
        comidasPorDia.putIfAbsent(numeroDia, new ArrayList<>());

        for (String idStr : idsArray) {
          Long idComida = Long.parseLong(idStr);
          Comida comida = servicioListaCompras.buscarComidaPorId(idComida);

          if (comida != null) {
            comidasPorDia.get(numeroDia).add(comida);
          }

          if (alimentoReal != null) {
            ItemComida item = new ItemComida(150.0, alimentoReal);
            comidaDia.getItems().add(item);
          }


        }
                }
                }
 */
