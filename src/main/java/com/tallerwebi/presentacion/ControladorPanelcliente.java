package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Direccion;
import com.tallerwebi.dominio.ServicioUsuario;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPanelcliente {

  private final ServicioUsuario servicioUsuario;

  @Autowired
  public ControladorPanelcliente(ServicioUsuario servicioUsuario) {
    this.servicioUsuario = servicioUsuario;
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
}
