package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cordenandas;
import com.tallerwebi.dominio.Direccion;
import com.tallerwebi.dominio.ServicioBuscarSupermercado;
import com.tallerwebi.dominio.Supermercado;
import com.tallerwebi.dominio.excepcion.MuchasPeticionesServicioMapas;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorBuscarSupermercado {

  private ServicioBuscarSupermercado servicioBuscarSupermercado;

  @Autowired
  public ControladorBuscarSupermercado(ServicioBuscarSupermercado servicioBuscarSupermercado) {
    this.servicioBuscarSupermercado = servicioBuscarSupermercado;
  }

  @RequestMapping(path = { "busqueda-supermercados" }, method = { RequestMethod.GET })
  public ModelAndView mostrarFormularioParaDirecciones() {
    ModelMap modelo = new ModelMap();
    modelo.put("direccion", new Direccion());

    return new ModelAndView("busqueda-supermercados", modelo);
  }

  @RequestMapping(path = { "ingreso-direccion" }, method = { RequestMethod.POST })
  public ModelAndView mostrarSupermercados(@ModelAttribute("direccion") Direccion direccion) {
    ModelMap modelo = new ModelMap();

    try {
      Cordenandas cordenandas = servicioBuscarSupermercado.obtenerCordenadaActual(direccion);
      if (cordenandas != null) {
        modelo.put("latitud", cordenandas.getLatitud());
        modelo.put("longitud", cordenandas.getLongitud());
      } else {
        modelo.put("latitud", 0);
        modelo.put("longitud", 0);
      }

      List<Supermercado> supermercados = servicioBuscarSupermercado.buscarSupermercadosCercanos(
        cordenandas.getLatitud(),
        cordenandas.getLongitud()
      );
      servicioBuscarSupermercado.calcularDistancias(supermercados, cordenandas);
      modelo.put("supermercados", supermercados);

      return new ModelAndView("busqueda-supermercados", modelo);
    } catch (MuchasPeticionesServicioMapas e) {
      modelo.put(
        "mensaje",
        "No fue posible consultar el servicio de mapas en este momento. " +
        "Esto puede deberse a que el servidor recibió demasiadas solicitudes " +
        "o se encuentra temporalmente fuera de servicio. Intente nuevamente en unos instantes."
      );
      return new ModelAndView("error-mapas", modelo);
    }
  }
}
