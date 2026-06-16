package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlanAlimenticio;
import com.tallerwebi.dominio.ServicioPlanificador;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPlanificador {

  private static final String VISTA_PLANIFICADOR = "planificador";
  private static final String CAMPO_MAIL_USUARIO = "usuarioLogueadoEmail";
  private static final String REDIRECT_LOGIN = "redirect:/login";

  private final ServicioPlanificador servicioPlanificador;

  @Autowired
  public ControladorPlanificador(ServicioPlanificador servicioPlanificador) {
    this.servicioPlanificador = servicioPlanificador;
  }

  @RequestMapping(path = { "/planificador" }, method = { RequestMethod.GET })
  public ModelAndView irAPlanificador(HttpSession session) {
    if (session.getAttribute(CAMPO_MAIL_USUARIO) == null) {
      return new ModelAndView(REDIRECT_LOGIN);
    }

    ModelMap modelo = new ModelMap();
    modelo.put("planGenerado", null);
    return new ModelAndView(VISTA_PLANIFICADOR, modelo);
  }

  @RequestMapping(path = { "/generar-plan" }, method = { RequestMethod.POST })
  public ModelAndView generarPlanAutomatizado(HttpSession session) {
    Object emailLogueado = session.getAttribute(CAMPO_MAIL_USUARIO);
    if (emailLogueado == null) {
      return new ModelAndView(REDIRECT_LOGIN);
    }

    ModelMap modelo = new ModelMap();

    try {
      PlanAlimenticio plan =
        this.servicioPlanificador.generarPlanParaUsuario(emailLogueado.toString(), null);
      modelo.put("planGenerado", plan);

      if (plan.getAdvertencias() != null && !plan.getAdvertencias().isEmpty()) {
        modelo.put("advertencias", plan.getAdvertencias());
      }
    } catch (UsuarioInexistenteException e) {
      modelo.put("error", "Error de perfil: " + e.getMessage());
      return new ModelAndView(VISTA_PLANIFICADOR, modelo);
    } catch (PresupuestoInsuficienteException e) {
      modelo.put("error", "No se pudo generar el plan: " + e.getMessage());
      return new ModelAndView(VISTA_PLANIFICADOR, modelo);
    } catch (Exception e) {
      modelo.put(
        "error",
        "Ocurrió un error inesperado al procesar la estructura nutricional. Intente nuevamente."
      );
      return new ModelAndView(VISTA_PLANIFICADOR, modelo);
    }

    return new ModelAndView(VISTA_PLANIFICADOR, modelo);
  }
}
