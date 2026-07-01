package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cordenandas;
import com.tallerwebi.dominio.Direccion;
import com.tallerwebi.dominio.ServicioBuscarSupermercado;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Supermercado;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPanelcliente {

  private final ServicioUsuario servicioUsuario;
  private final ServicioBuscarSupermercado servicioBuscarSupermercado;

  @Autowired
  public ControladorPanelcliente(
    ServicioUsuario servicioUsuario,
    ServicioBuscarSupermercado servicioBuscarSupermercado
  ) {
    this.servicioUsuario = servicioUsuario;
    this.servicioBuscarSupermercado = servicioBuscarSupermercado;
  }

  @GetMapping("/panel-cliente")
  public ModelAndView irAPanelCliente(HttpSession session) {
    // if (session.getAttribute("usuarioLogueadoEmail") != null) {
    // String email = session.getAttribute("usuarioLogueadoEmail").toString();
    // DatosPerfilUsuario datos = servicioUsuario.obtenerDatosPerfilUsuario(email);
    // ModelMap model = new ModelMap();
    // model.put("datosPerfilUsuario", datos);
    //
    // return new ModelAndView("panel-cliente", model);
    // }
    return new ModelAndView("panel-cliente");
  }

  @GetMapping("/panel-cliente/dashboard")
  public ModelAndView irADashboard(HttpSession session) {
    // if (session.getAttribute("usuarioLogueadoEmail") != null) {
    // String email = session.getAttribute("usuarioLogueadoEmail").toString();
    // DatosPerfilUsuario datos = servicioUsuario.obtenerDatosPerfilUsuario(email);
    // ModelMap model = new ModelMap();
    // model.put("datosPerfilUsuario", datos);
    //
    // return new ModelAndView("panel-cliente", model);
    // }
    return new ModelAndView("panel__dashboard");
  }

  @GetMapping("/panel-cliente/datos-personales")
  public ModelAndView irAPerfilAlimentario(HttpSession session) {
    // if (session.getAttribute("usuarioLogueadoEmail") != null) {
    // String email = session.getAttribute("usuarioLogueadoEmail").toString();
    // DatosPerfilUsuario datos = servicioUsuario.obtenerDatosPerfilUsuario(email);
    // ModelMap model = new ModelMap();
    // model.put("datosPerfilUsuario", datos);
    //
    // return new ModelAndView("panel-cliente", model);
    // }
    return new ModelAndView("panel__datos-personales");
  }

  @GetMapping("/panel-cliente/supermercados")
  public ModelAndView irASupermercados() {
    ModelMap modelo = new ModelMap();
    modelo.put("direccion", new Direccion());
    return new ModelAndView("panel__busqueda-supermercados", modelo);
  }

  @PostMapping("/panel-cliente/supermercados")
  public ModelAndView procesarBusquedaSupermercados(
    @ModelAttribute("direccion") Direccion direccion
  ) {
    ModelMap modelo = new ModelMap();
    Cordenandas cordenandas = servicioBuscarSupermercado.obtenerCordenadaActual(direccion);
    if (cordenandas != null) {
      modelo.put("latitud", cordenandas.getLatitud());
      modelo.put("longitud", cordenandas.getLongitud());

      List<Supermercado> supermercados = servicioBuscarSupermercado.buscarSupermercadosCercanos(
        cordenandas.getLatitud(),
        cordenandas.getLongitud()
      );
      modelo.put("supermercados", supermercados);
    }

    modelo.put("direccion", direccion);
    return new ModelAndView("panel__busqueda-supermercados", modelo);
  }
}
