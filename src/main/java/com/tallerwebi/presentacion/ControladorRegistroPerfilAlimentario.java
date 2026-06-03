package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PerfilAlimentarioDTO;
import com.tallerwebi.dominio.ServicioRegistroPerfilAlimentario;
import com.tallerwebi.dominio.excepcion.perfilException.ActividadFisicaInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.AlturaInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.ObjetivoInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.PerfilAlimentarioDTONuloException;
import com.tallerwebi.dominio.excepcion.perfilException.PesoInvalidoException;
import com.tallerwebi.dominio.excepcion.perfilException.RestriccionesAlimentariasInvalidasException;
import com.tallerwebi.dominio.excepcion.perfilException.SexoInvalidoException;
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

  private static final String ATT_PERFIL_ALIMENTARIO = "perfilAlimentario";
  private static final String ATT_ERROR = "error";
  private static final String ATT_USUARIO_LOGUEADO_EMAIL = "usuarioLogueadoEmail";
  private static final String VISTA_REGISTRO = "registroPerfilAlimentario";
  private static final String REDIRECT_LOGIN = "redirect:/login";
  private static final String HOME = "redirect:/";

  private final ServicioRegistroPerfilAlimentario servicioRegistroPerfilAlimentario;

  @Autowired
  public ControladorRegistroPerfilAlimentario(
    ServicioRegistroPerfilAlimentario servicioRegistroPerfilAlimentario
  ) {
    this.servicioRegistroPerfilAlimentario = servicioRegistroPerfilAlimentario;
  }

  @GetMapping("/Registro-perfil-alimentario")
  public ModelAndView mostrarFormulario(HttpSession session) {
    if (session.getAttribute(ATT_USUARIO_LOGUEADO_EMAIL) == null) {
      return new ModelAndView(REDIRECT_LOGIN);
    }
    ModelMap modelo = new ModelMap();
    modelo.put(ATT_PERFIL_ALIMENTARIO, new PerfilAlimentarioDTO());
    return new ModelAndView(VISTA_REGISTRO, modelo);
  }

  @PostMapping("/Registro-perfil-alimentario")
  public ModelAndView procesarFormulario(
    @ModelAttribute("perfilAlimentario") PerfilAlimentarioDTO perfilAlimentarioDTO,
    HttpSession session
  ) {
    String email = session.getAttribute(ATT_USUARIO_LOGUEADO_EMAIL).toString();
    if (email == null) {
      return new ModelAndView(REDIRECT_LOGIN);
    }

    ModelMap modelo = new ModelMap();
    try {
      servicioRegistroPerfilAlimentario.guardarPerfilAlimentario(perfilAlimentarioDTO, email);
      return new ModelAndView(HOME, modelo);
    } catch (Exception exception) {
      modelo.put(ATT_ERROR, obtenerMensajeDeError(exception));
      modelo.put(ATT_PERFIL_ALIMENTARIO, perfilAlimentarioDTO);
    }
    return new ModelAndView(VISTA_REGISTRO, modelo);
  }

  private String obtenerMensajeDeError(Exception exception) {
    if (exception instanceof ActividadFisicaInvalidaException) {
      return "Error: Actividad fisica invalida, vuelva a intentarlo";
    }
    if (exception instanceof AlturaInvalidaException) {
      return "Error: La altura ingresada no es valida (Debe ser entre 0 y 272 cm)";
    }
    if (exception instanceof EdadInvalidaException) {
      return "Error: La edad ingresada no es valida (Debe ser entre 0 y 100 años)";
    }
    if (exception instanceof ObjetivoInvalidaException) {
      return "Error: Objetivo invalido, vuelva a intentarlo";
    }
    if (exception instanceof PerfilAlimentarioDTONuloException) {
      return "Error: Perfil esta vacio, vuelva a intentarlo";
    }
    if (exception instanceof PesoInvalidoException) {
      return "Error: El peso ingresado no es valido (Debe ser entre 0 y 635 kg)";
    }
    if (exception instanceof RestriccionesAlimentariasInvalidasException) {
      return "Error: Una o mas restricciones alimentarias seleccionadas son invlidas";
    }
    if (exception instanceof SexoInvalidoException) {
      return "Error: Sexo invalido, vuelva a intentarlo";
    }
    return "Error inesperado, vuelva a intentarlo";
  }
}
