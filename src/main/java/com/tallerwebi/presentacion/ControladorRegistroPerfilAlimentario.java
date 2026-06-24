package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PerfilAlimentarioDTO;
import com.tallerwebi.dominio.ServicioRegistroPerfilAlimentario;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
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
  private static final String HOME = "redirect:/home";

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

    ModelMap modelo;
    try {
      servicioRegistroPerfilAlimentario.guardarPerfilAlimentario(perfilAlimentarioDTO, email);
      return new ModelAndView("redirect:/configurar-presupuesto");
    } catch (UsuarioInexistenteException exeption) {
      modelo = new ModelMap();
      modelo.put(ATT_ERROR, "Error: Usuario inexistente");
      modelo.put(ATT_PERFIL_ALIMENTARIO, perfilAlimentarioDTO);
    } catch (Exception exception) {
      modelo = obtenerMensajeDeError(exception);
      modelo.put(ATT_PERFIL_ALIMENTARIO, perfilAlimentarioDTO);
    }
    return new ModelAndView(VISTA_REGISTRO, modelo);
  }

  private ModelMap obtenerMensajeDeError(Exception exception) {
    ModelMap modelo = new ModelMap();
    String nombreExcepcion = exception.getClass().getSimpleName();

    switch (nombreExcepcion) {
      case "ActividadFisicaInvalidaException":
        modelo.put(nombreExcepcion, "Actividad fisica invalida, vuelva a intentarlo");
        break;
      case "AlturaInvalidaException":
        modelo.put(nombreExcepcion, "La altura ingresada no es valida (Debe ser entre 0 y 272 cm)");
        break;
      case "EdadInvalidaException":
        modelo.put(nombreExcepcion, "La edad ingresada no es valida (Debe ser entre 0 y 100 años)");
        break;
      case "ObjetivoInvalidaException":
        modelo.put(nombreExcepcion, "Objetivo invalido, vuelva a intentarlo");
        break;
      case "PerfilAlimentarioDTONuloException":
        modelo.put(nombreExcepcion, "Perfil esta vacio, vuelva a intentarlo");
        break;
      case "PesoInvalidoException":
        modelo.put(nombreExcepcion, "El peso ingresado no es valido (Debe ser entre 0 y 635 kg)");
        break;
      case "RestriccionesAlimentariasInvalidasException":
        modelo.put(
          nombreExcepcion,
          "Una o mas restricciones alimentarias seleccionadas son invlidas"
        );
        break;
      case "SexoInvalidoException":
        modelo.put(nombreExcepcion, "Sexo invalido, vuelva a intentarlo");
        break;
      default:
        modelo.put(nombreExcepcion, "Error inesperado, vuelva a intentarlo");
        break;
    }
    return modelo;
  }
}
