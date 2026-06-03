package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.ItemComida;
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

    Alimento alimento1 = new Alimento();
    alimento1.setNombre("Pollo");
    alimento1.setPrecioPorKg(1000.0);
    Alimento alimento2 = new Alimento();
    alimento2.setNombre("Arroz");
    alimento2.setPrecioPorKg(2000.0);
    Alimento alimento3 = new Alimento();
    alimento3.setNombre("Tomate");
    alimento3.setPrecioPorKg(3000.0);

    ItemComida itemComida1 = new ItemComida(100.0, alimento1);
    ItemComida itemComida2 = new ItemComida(200.0, alimento2);
    ItemComida itemComida3 = new ItemComida(100.0, alimento3);

    Comida comida1 = new Comida();
    comida1.setNombre("Arroz Con Pollo y Tomate");
    comida1.getItems().add(itemComida1);
    comida1.getItems().add(itemComida2);
    comida1.getItems().add(itemComida3);

    Comida comida2 = new Comida();
    comida2.setNombre("Tomate Con Pollo");
    comida2.getItems().add(itemComida1);
    comida2.getItems().add(itemComida3);

    Comida comida3 = new Comida();
    comida3.setNombre("Tomate Con Arroz ");
    comida3.getItems().add(itemComida2);
    comida3.getItems().add(itemComida3);

    List<Comida> comidas = new ArrayList<>();
    comidas.add(comida1);
    comidas.add(comida2);
    comidas.add(comida3);

    modelo.put("comidas", comidas);
    return new ModelAndView("lista-compras", modelo);
  }
}
