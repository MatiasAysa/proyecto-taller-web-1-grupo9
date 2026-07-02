package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorHome {

  private final String ATT_USUARIO_LOGUEADO_EMAIL = "usuarioLogueadoEmail";
  private final ServicioUsuario servicioUsuario;

  @Autowired
  public ControladorHome(ServicioUsuario servicioUsuario) {
    this.servicioUsuario = servicioUsuario;
  }

  @GetMapping(path = "/home")
  public ModelAndView irAHome(HttpSession session) {
    if (session.getAttribute(ATT_USUARIO_LOGUEADO_EMAIL) != null) {
      String email = session.getAttribute(ATT_USUARIO_LOGUEADO_EMAIL).toString();
      ModelMap model = new ModelMap();
      model.put("usuarioLogueadoEmail", email);
      return new ModelAndView("home", model);
    }
    return new ModelAndView("home");
  }

  @GetMapping(path = "/logout")
  public ModelAndView logout(HttpSession session) {
    session.removeAttribute(ATT_USUARIO_LOGUEADO_EMAIL);
    return new ModelAndView("redirect:/home");
  }

  @GetMapping(path = "/planificar")
  public ModelAndView irAPlannificar(HttpSession session) {
    if (session.getAttribute(ATT_USUARIO_LOGUEADO_EMAIL) == null) {
      return new ModelAndView("redirect:/login");
    }

    String email = session.getAttribute(ATT_USUARIO_LOGUEADO_EMAIL).toString();

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
