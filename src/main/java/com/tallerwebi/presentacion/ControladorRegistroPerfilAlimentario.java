package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PerfilAlimentarioDTO;
import com.tallerwebi.dominio.ServicioRegistroPerfilAlimentario;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistroPerfilAlimentario {

  private final ServicioRegistroPerfilAlimentario servicioRegistroPerfilAlimentario;

  @Autowired
  public ControladorRegistroPerfilAlimentario(
    ServicioRegistroPerfilAlimentario servicioRegistroPerfilAlimentario
  ) {
    this.servicioRegistroPerfilAlimentario = servicioRegistroPerfilAlimentario;
  }

  @GetMapping("/Registro-perfil-alimentario")
  public ModelAndView mostrarFormulario(HttpSession session) {
    if (session.getAttribute("usuarioLogueadoEmail") == null) {
      return new ModelAndView("redirect:/login");
    }
    ModelMap modelo = new ModelMap();
    modelo.put("perfilAlimentario", new PerfilAlimentarioDTO());
    return new ModelAndView("registroPerfilAlimentario", modelo);
  }

  @PostMapping("/Registro-perfil-alimentario")
  public ModelAndView procesarFormulario(
    @ModelAttribute("perfilAlimentario") PerfilAlimentarioDTO perfilAlimentarioDTO,
    HttpSession session
  ) {
    String email = session.getAttribute("usuarioLogueadoEmail").toString();
    if (email == null) {
      return new ModelAndView("redirect:/login");
    }
    if (servicioRegistroPerfilAlimentario.guardarPerfilAlimentario(perfilAlimentarioDTO, email)) {
      return new ModelAndView("redirect:/");
    }
    ModelMap modelo = new ModelMap();
    modelo.put(
      "error",
      "Los datos ingresados son invalidos o estan fuera de rango. Por favor, verifiquelos."
    );
    modelo.put("perfilAlimentario", perfilAlimentarioDTO);
    return new ModelAndView("registroPerfilAlimentario", modelo);
  }
}
