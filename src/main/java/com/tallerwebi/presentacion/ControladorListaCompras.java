package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.ItemComida;
import com.tallerwebi.dominio.ItemCompra;
import com.tallerwebi.dominio.ServicioListaCompras;
import java.util.ArrayList;
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


    return new ModelAndView("lista-compras", modelo);
  }
}
