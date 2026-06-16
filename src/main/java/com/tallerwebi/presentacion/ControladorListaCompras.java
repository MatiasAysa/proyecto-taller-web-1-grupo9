package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.Ingrediente;
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

    Alimento alimento1 = new Alimento();
    alimento1.setNombre("Pollo");
    alimento1.setPrecioEstimado(1000.0);
    Alimento alimento2 = new Alimento();
    alimento2.setNombre("Arroz");
    alimento2.setPrecioEstimado(2000.0);
    Alimento alimento3 = new Alimento();
    alimento3.setNombre("Tomate");
    alimento3.setPrecioEstimado(3000.0);
    Alimento alimento4 = new Alimento();
    alimento4.setNombre("Zapallo");
    alimento4.setPrecioEstimado(3000.0);
    Alimento alimento5 = new Alimento();
    alimento5.setNombre("Papa");
    alimento5.setPrecioEstimado(3000.0);
    Alimento alimento6 = new Alimento();
    alimento6.setNombre("Banana");
    alimento6.setPrecioEstimado(3000.0);

    Ingrediente ingrediente1 = new Ingrediente(100.0, alimento1);
    Ingrediente ingrediente2 = new Ingrediente(200.0, alimento2);
    Ingrediente ingrediente3 = new Ingrediente(100.0, alimento3);
    Ingrediente ingrediente4 = new Ingrediente(500.0, alimento4);
    Ingrediente ingrediente5 = new Ingrediente(240.0, alimento5);
    Ingrediente ingrediente6 = new Ingrediente(1000.0, alimento6);

    Comida comida1 = new Comida();
    comida1.setNombre("Arroz Con Pollo y Tomate");
    comida1.getItems().add(ingrediente1);
    comida1.getItems().add(ingrediente2);
    comida1.getItems().add(ingrediente3);

    Comida comida2 = new Comida();
    comida2.setNombre("Tomate Con Pollo");
    comida2.getItems().add(ingrediente1);
    comida2.getItems().add(ingrediente3);

    Comida comida3 = new Comida();
    comida3.setNombre("Tomate Con Arroz ");
    comida3.getItems().add(ingrediente2);
    comida3.getItems().add(ingrediente3);

    Comida comida4 = new Comida();
    comida4.setNombre("Pollo con Zapallo ");
    comida4.getItems().add(ingrediente1);
    comida4.getItems().add(ingrediente4);

    Comida comida5 = new Comida();
    comida5.setNombre("Pollo con tomate y papa ");
    comida5.getItems().add(ingrediente2);
    comida5.getItems().add(ingrediente5);
    comida5.getItems().add(ingrediente3);

    Comida comida6 = new Comida();
    comida6.setNombre("Banana ");
    comida6.getItems().add(ingrediente6);

    List<Comida> comidas = new ArrayList<>();
    comidas.add(comida1);
    comidas.add(comida2);
    comidas.add(comida3);
    comidas.add(comida4);
    comidas.add(comida5);
    comidas.add(comida6);

    List<ItemCompra> listaDeCompras = servicioListaCompras.generarListaCompras(comidas);
    servicioListaCompras.calcularPrecios(listaDeCompras);

    Double total = servicioListaCompras.calcularTotalListaCompras(listaDeCompras);

    modelo.put("comidas", comidas);
    modelo.put("listaDeCompras", listaDeCompras);
    modelo.put("totalLista", total);
    return new ModelAndView("lista-compras", modelo);
  }
}
