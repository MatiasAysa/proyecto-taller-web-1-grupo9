package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPresupuesto;
import com.tallerwebi.dominio.excepcion.PresupuestoNoPositivoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPresupuestoException;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
  private ServicioPresupuesto servicioPresupuesto;
  private static final String CAMPO_MAIL_USUARIO = "usuarioLogueadoEmail";

  @Autowired
  public ControladorPresupuesto(ServicioPresupuesto servicioPresupuesto) {
    this.servicioPresupuesto = servicioPresupuesto;
  }

  @RequestMapping("/configurar-presupuesto")
  public ModelAndView irAConfigurarPresupuesto(HttpSession session) {
    if (session.getAttribute(CAMPO_MAIL_USUARIO) == null) return new ModelAndView(
      "redirect:/login"
    );
    ModelMap modelo = new ModelMap();
    modelo.put("datosPresupuesto", new DatosPresupuesto());
    return new ModelAndView("configurar-presupuesto", modelo);
  }

  @RequestMapping(path = "/mi-presupuesto", method = RequestMethod.POST)
  public ModelAndView validarPresupuesto(
    @ModelAttribute("datosPresupuesto") DatosPresupuesto datosPresupuesto,
    HttpSession session
  ) {
    if (session.getAttribute(CAMPO_MAIL_USUARIO) == null) return new ModelAndView(
      "redirect:/login"
    );
    ModelMap model = new ModelMap();
    model.put("mensaje", MENSAJE_PRESUPUESTO_EXITOSO);
    if (datosPresupuesto.getIntervalo() == 0) {
      return fallarPresupuesto(MENSAJE_INTERVALO_OBLIGATORIO);
    }
    if (datosPresupuesto.getFecha() == null) {
      return fallarPresupuesto(MENSAJE_FECHA_OBLIGATORIA);
    }
    try {
      servicioPresupuesto.crearPresupuesto(
        datosPresupuesto.getMonto(),
        datosPresupuesto.getIntervalo(),
        datosPresupuesto.getFecha(),
        session.getAttribute(CAMPO_MAIL_USUARIO).toString()
      );
      model.put("monto", datosPresupuesto.getMonto());
      model.put("intervalo", datosPresupuesto.getIntervalo());
      model.put("fecha", datosPresupuesto.getFecha());
    } catch (PresupuestoNoPositivoException e) {
      return fallarPresupuesto(MENSAJE_MONTO_OBLIGATORIO);
    }
    return new ModelAndView("mi-presupuesto", model);
  }

  private ModelAndView fallarPresupuesto(String mensaje) {
    ModelMap model = new ModelMap();
    model.put("mensaje", mensaje);
    model.put("datosPresupuesto", new DatosPresupuesto());
    return new ModelAndView("configurar-presupuesto", model);
  }

  @RequestMapping("/mi-presupuesto")
  public ModelAndView irAMiPresupuesto(HttpSession session) {
    if (session.getAttribute(CAMPO_MAIL_USUARIO) == null) return new ModelAndView(
      "redirect:/login"
    );
    try {
      DatosPresupuesto datosPresupuesto = servicioPresupuesto.buscarPresupuesto(
        session.getAttribute(CAMPO_MAIL_USUARIO).toString()
      );
      ModelMap model = new ModelMap();
      model.put("monto", datosPresupuesto.getMonto());
      model.put("intervalo", datosPresupuesto.getIntervalo());
      model.put("fecha", datosPresupuesto.getFecha().format(DateTimeFormatter.ISO_DATE));
      return new ModelAndView("mi-presupuesto", model);
    } catch (UsuarioSinPresupuestoException e) {
      return new ModelAndView("redirect:/configurar-presupuesto");
    }
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
