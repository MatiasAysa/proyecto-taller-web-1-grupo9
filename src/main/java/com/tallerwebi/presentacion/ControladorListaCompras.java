package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Alimento;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorListaCompras {

  @RequestMapping("/lista-compras")
  public ModelAndView mostrarListaCompras() {
    ModelMap modelo = new ModelMap();

    Alimento alimento1 = new Alimento();
    alimento1.setNombre("Pollo");
    Alimento alimento2 = new Alimento();
    alimento2.setNombre("Arroz");
    Alimento alimento3 = new Alimento();
    alimento3.setNombre("Tomate");

    List<Alimento> listaAlimentos = new ArrayList<Alimento>();
    listaAlimentos.add(alimento1);
    listaAlimentos.add(alimento2);
    listaAlimentos.add(alimento3);
    modelo.put("alimentos", listaAlimentos);
    return new ModelAndView("lista-compras", modelo);
  }
}
