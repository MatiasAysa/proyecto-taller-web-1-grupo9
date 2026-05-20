package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPresupuesto {

  private static final String MENSAJE_PRESUPUESTO_EXITOSO = "Presupuesto creado con exito";
  private static final String MENSAJE_INTERVALO_OBLIGATORIO = "El intervalo es obligatorio";
  private static final String MENSAJE_FECHA_OBLIGATORIA = "La fecha es obligatoria";
  private static final String MENSAJE_MONTO_OBLIGATORIO = "El monto es obligatorio";
  private ModelMap model;
  private String view;

  @RequestMapping("/configurar-presupuesto")
  public ModelAndView irAConfigurarPresupuesto() {
    ModelMap modelo = new ModelMap();
    modelo.put("datosPresupuesto", new DatosPresupuesto());
    return new ModelAndView("configurar-presupuesto", modelo);
  }

  @RequestMapping(path = "/validar-presupuesto", method = RequestMethod.POST)
  public ModelAndView validarPresupuesto(
    @ModelAttribute("datosPresupuesto") DatosPresupuesto datosPresupuesto
  ) {
    model = new ModelMap();
    view = "mi-presupuesto";
    model.put("mensaje", MENSAJE_PRESUPUESTO_EXITOSO);

    if (datosPresupuesto.getIntervalo() == 0) {
      return fallarPresupuesto(MENSAJE_INTERVALO_OBLIGATORIO);
    }
    if (datosPresupuesto.getFecha() == null) {
      return fallarPresupuesto(MENSAJE_FECHA_OBLIGATORIA);
    }
    if (datosPresupuesto.getMonto() == 0) {
      return fallarPresupuesto(MENSAJE_MONTO_OBLIGATORIO);
    }
    return new ModelAndView(view, model);
  }

  private ModelAndView fallarPresupuesto(String mensaje) {
    model = new ModelMap();
    model.put("mensaje", mensaje);
    return new ModelAndView("configurar-presupuesto", model);
  }

  @RequestMapping("/mi-presupuesto")
  public ModelAndView irAMiPresupuesto() {
    return new ModelAndView("mi-presupuesto");
  }

  public String getMENSAJE_PRESUPUESTO_EXITOSO() {
    return MENSAJE_PRESUPUESTO_EXITOSO;
  }

  public String getMENSAJE_INTERVALO_OBLIGATORIO() {
    return MENSAJE_INTERVALO_OBLIGATORIO;
  }

  public String getMENSAJE_FECHA_OBLIGATORIA() {
    return MENSAJE_FECHA_OBLIGATORIA;
  }

  public String getMENSAJE_MONTO_OBLIGATORIO() {
    return MENSAJE_MONTO_OBLIGATORIO;
  }
}
