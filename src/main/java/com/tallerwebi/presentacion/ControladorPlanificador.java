package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlanAlimenticio;
import com.tallerwebi.dominio.ServicioPlanificador;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPlanificador {

  private static final String VISTA_PLANIFICADOR = "planificador";
  private final ServicioPlanificador servicioPlanificador;

  @Autowired
  public ControladorPlanificador(ServicioPlanificador servicioPlanificador) {
    this.servicioPlanificador = servicioPlanificador;
  }

  @RequestMapping(path = { "/planificador" }, method = { RequestMethod.GET })
  public ModelAndView irAPlanificador() {
    ModelMap modelo = new ModelMap();
    modelo.put("planGenerado", null);
    return new ModelAndView(VISTA_PLANIFICADOR, modelo);
  }

  @RequestMapping(path = { "/generar-plan" }, method = { RequestMethod.POST })
  public ModelAndView generarPlanAutomatizado(@RequestParam("usuarioId") Long usuarioId) {
    ModelMap modelo = new ModelMap();

    try {
      PlanAlimenticio plan = this.servicioPlanificador.generarPlanParaUsuario(usuarioId);
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
      modelo.put("error", "Error inesperado del sistema: " + e.getMessage());
      return new ModelAndView(VISTA_PLANIFICADOR, modelo);
    }

    return new ModelAndView(VISTA_PLANIFICADOR, modelo);
  }
}
