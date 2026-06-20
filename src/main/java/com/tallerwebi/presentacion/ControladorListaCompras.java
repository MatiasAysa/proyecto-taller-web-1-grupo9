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
}
