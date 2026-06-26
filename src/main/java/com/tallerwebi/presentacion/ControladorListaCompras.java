package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.ItemCompra;
import com.tallerwebi.dominio.ServicioListaCompras;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorListaCompras {

  private ServicioListaCompras servicioListaCompras;

  @Autowired
  public ControladorListaCompras(ServicioListaCompras servicioListaCompras) {
    this.servicioListaCompras = servicioListaCompras;
  }

  @RequestMapping("/lista-compras")
  public ModelAndView mostrarListaCompras() {
    ModelMap modelo = new ModelMap();

    List<Comida> comidas = servicioListaCompras.obtenerComidas();

    List<ItemCompra> listaDeCompras = servicioListaCompras.generarListaCompras(comidas);
    servicioListaCompras.calcularPreciosParaCadaAlimento(listaDeCompras);

    Double total = servicioListaCompras.calcularTotalListaCompras(listaDeCompras);

    modelo.addAttribute("comidas", comidas);
    modelo.put("listaDeCompras", listaDeCompras);
    modelo.put("totalLista", total);
    return new ModelAndView("lista-compras", modelo);
  }
  /*
  @PostMapping("/lista-compras")
  public ModelAndView mostrarListaCompras(
    @RequestParam(required = false) Map<String, String> todasLasSelecciones
  ) {
    ModelMap modelo = new ModelMap();

    if (todasLasSelecciones == null || todasLasSelecciones.isEmpty()) {
      modelo.put("error", "No seleccionaste ningún alimento de tu plan.");
      return new ModelAndView("redirect:/planificador", modelo);
    }

    List<Comida> comidasDelPlan = new ArrayList<>();
    for (Map.Entry<String, String> entrada : todasLasSelecciones.entrySet()) {
      String key = entrada.getKey();

      if (key.startsWith("alimentosSeleccionados")) {
        String numeroDiaStr = key.substring(key.indexOf("[") + 1, key.indexOf("]"));
        Integer numeroDia = Integer.parseInt(numeroDiaStr);

        String[] idsArray = entrada.getValue().split(",");

        Comida comidaDia = new Comida();
        comidaDia.setNombre("DÍA " + numeroDia + " - Alimentos Seleccionados");

        for (String idStr : idsArray) {
          Long idAlimento = Long.parseLong(idStr);
          Alimento alimentoReal = servicioListaCompras.buscarAlimentoPorId(idAlimento);

          if (alimentoReal != null) {
            Ingrediente item = new Ingrediente(150.0, alimentoReal);
            comidaDia.getItems().add(item);
          }
        }

        comidasDelPlan.add(comidaDia);
      }
    }
    List<ItemCompra> listaDeCompras = servicioListaCompras.generarListaCompras(comidasDelPlan);
    servicioListaCompras.calcularPrecios(listaDeCompras);
    Double total = servicioListaCompras.calcularTotalListaCompras(listaDeCompras);

    modelo.put("comidas", comidasDelPlan);
    modelo.put("listaDeCompras", listaDeCompras);
    modelo.put("totalLista", total);

    return new ModelAndView("lista-compras", modelo);
  }
   */
}
