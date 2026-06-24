package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorHome {

  private ServicioUsuario servicioUsuario;

  @Autowired
  public ControladorHome(ServicioUsuario servicioUsuario) {
    this.servicioUsuario = servicioUsuario;
  }

  @GetMapping(path = "/home")
  public ModelAndView irAHome(HttpSession session) {
    // if (session.getAttribute("usuarioLogueadoEmail") == null) {
    // // DatosUsuario datosUsuario = new DatosUsuario();
    // return new ModelAndView("redirect:/login");
    // }
    return new ModelAndView("home");
  }

  @GetMapping(path = "/planificar")
  public ModelAndView irAPlannificar(HttpSession session) {
    if (session.getAttribute("usuarioLogueadoEmail") == null) {
      // DatosUsuario datosUsuario = new DatosUsuario();
      return new ModelAndView("redirect:/login");
    }

    String email = session.getAttribute("usuarioLogueadoEmail").toString();

    boolean tienePerfilAlimentario = servicioUsuario.tienePerfilAlimentario(email);
    if (!tienePerfilAlimentario) {
      return new ModelAndView("redirect:/Registro-perfil-alimentario");
    }

    boolean tienePresupuesto = servicioUsuario.tienePresupuesto(email);
    if (!tienePresupuesto) {
      return new ModelAndView("redirect:/configurar-presupuesto");
    }
    return new ModelAndView("redirect:/lista-compras");
  }
}
