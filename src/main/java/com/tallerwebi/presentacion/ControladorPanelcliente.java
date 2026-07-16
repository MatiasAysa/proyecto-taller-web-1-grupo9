package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.Cordenandas;
import com.tallerwebi.dominio.DatosClientePanel;
import com.tallerwebi.dominio.Direccion;
import com.tallerwebi.dominio.ItemDespensa;
import com.tallerwebi.dominio.ItemDespensaDTO;
import com.tallerwebi.dominio.ServicioBuscarSupermercado;
import com.tallerwebi.dominio.ServicioDespensa;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Supermercado;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPanelcliente {

  private final String ATT_EMAIL_SESION = "usuarioLogueadoEmail";
  private final String RUTA_LOGIN = "redirect:/login";

  private final ServicioUsuario servicioUsuario;
  private final ServicioBuscarSupermercado servicioBuscarSupermercado;
  private final ServicioDespensa servicioDespensa;

  @Autowired
  public ControladorPanelcliente(
    ServicioUsuario servicioUsuario,
    ServicioBuscarSupermercado servicioBuscarSupermercado,
    ServicioDespensa servicioDespensa
  ) {
    this.servicioUsuario = servicioUsuario;
    this.servicioBuscarSupermercado = servicioBuscarSupermercado;
    this.servicioDespensa = servicioDespensa;
  }

  @GetMapping("/panel-cliente")
  public ModelAndView irAPanelCliente(HttpSession session) {
    if (session.getAttribute(ATT_EMAIL_SESION) != null) {
      return new ModelAndView("redirect:/panel-cliente/dashboard");
    }

    return new ModelAndView(RUTA_LOGIN);
  }

  @GetMapping("/panel-cliente/datos-personales")
  public ModelAndView irAPanelDatosPersonales(HttpSession session) {
    String email = obtenerEmail(session);
    if (email != null) {
      if (!servicioUsuario.tienePerfilAlimentario(email)) {
        return new ModelAndView("redirect:/Registro-perfil-alimentario");
      }
      if (!servicioUsuario.tienePresupuesto(email)) {
        ModelMap modelo = new ModelMap();
        DatosClientePanel datos = servicioUsuario.obtenerDatosClientePanel(email);
        modelo.put("datosClientePanel", datos);
        return new ModelAndView("panel__datos-personales", modelo);
      }
      ModelMap modelo = new ModelMap();
      DatosClientePanel datos = servicioUsuario.obtenerDatosClientePanel(email);
      modelo.put("datosClientePanel", datos);
      return new ModelAndView("panel__datos-personales", modelo);
    }

    return new ModelAndView(RUTA_LOGIN);
  }

//  @GetMapping("/panel-cliente/supermercados")
//  public ModelAndView irASupermercados() {
//    ModelMap modelo = new ModelMap();
//    modelo.put("direccion", new Direccion());
//    return new ModelAndView("panel__busqueda-supermercados", modelo);
//  }
//
//  @PostMapping("/panel-cliente/supermercados")
//  public ModelAndView procesarBusquedaSupermercados(
//    @ModelAttribute("direccion") Direccion direccion
//  ) {
//    ModelMap modelo = new ModelMap();
//    Cordenandas cordenandas = servicioBuscarSupermercado.obtenerCordenadaActual(direccion);
//    if (cordenandas != null) {
//      modelo.put("latitud", cordenandas.getLatitud());
//      modelo.put("longitud", cordenandas.getLongitud());
//
//      List<Supermercado> supermercados = servicioBuscarSupermercado.buscarSupermercadosCercanos(
//        cordenandas.getLatitud(),
//        cordenandas.getLongitud()
//      );
//      modelo.put("supermercados", supermercados);
//    }
//
//    modelo.put("direccion", direccion);
//    return new ModelAndView("panel__busqueda-supermercados", modelo);
//  }

  private String obtenerEmail(HttpSession session) {
    return session.getAttribute("usuarioLogueadoEmail") != null
      ? session.getAttribute("usuarioLogueadoEmail").toString()
      : null;
  }

  // ========== DESPENSA ==============
  @GetMapping("/panel-cliente/despensa")
  public ModelAndView irADespensa(HttpSession session) {
    String email = obtenerEmail(session);
    if (email == null) return new ModelAndView(RUTA_LOGIN);

    List<ItemDespensa> despensa = servicioDespensa.obtenerDespensaDelUsuario(email);
    List<Alimento> listaAlimentosBaseDatos = servicioDespensa.obtenerAlimentosBaseDatos();

    ModelMap modelo = new ModelMap();
    if (despensa != null) {
      modelo.put("despensa", despensa);
    }
    modelo.put("itemDespensaDTO", new ItemDespensaDTO());
    modelo.put("listaAlimentosBaseDatos", listaAlimentosBaseDatos);

    return new ModelAndView("panel__despensa", modelo);
  }

  @PostMapping("/panel-cliente/despensa/agregar-alimento-existente")
  public ModelAndView agregarItemDespensaExistente(
    HttpSession session,
    @ModelAttribute("itemDespensaDTO") ItemDespensaDTO itemDespensaDTO
  ) {
    String email = obtenerEmail(session);
    if (email == null) return new ModelAndView(RUTA_LOGIN);

    servicioDespensa.agregarItemDespensa(email, itemDespensaDTO);
    return new ModelAndView("redirect:/panel-cliente/despensa");
  }

  @GetMapping("/panel-cliente/despensa/eliminar/{id}")
  public ModelAndView eliminarItemDespensa(HttpSession session, @PathVariable("id") Long id) {
    String email = obtenerEmail(session);
    if (email == null) return new ModelAndView(RUTA_LOGIN);

    servicioDespensa.eliminarItemDespensa(id);
    return new ModelAndView("redirect:/panel-cliente/despensa");
  }

  @GetMapping("/panel-cliente/despensa/cambiar-cantidad")
  public ModelAndView cambiarCantidadDespensa(
    HttpSession session,
    @RequestParam("id") Long id,
    @RequestParam("cantidad") Double cantidad
  ) {
    String email = obtenerEmail(session);
    if (email == null) return new ModelAndView(RUTA_LOGIN);

    servicioDespensa.cambiarCantidadDespensa(id, cantidad);
    return new ModelAndView("redirect:/panel-cliente/despensa");
  }

  // DASHBOARD
  @GetMapping("/panel-cliente/dashboard")
  public ModelAndView irADashboard(HttpSession session) {
    String email = obtenerEmail(session);
    if (email == null) return new ModelAndView(RUTA_LOGIN);

    boolean tienePerfilAlimentario = servicioUsuario.tienePerfilAlimentario(email);
    boolean tienePresupuesto = servicioUsuario.tienePresupuesto(email);

    ModelMap modelo = new ModelMap();
    DatosClientePanel datos = servicioUsuario.obtenerDatosClientePanel(email);
    modelo.put("datosClientePanel", datos);
    modelo.put("paso1Completado", tienePerfilAlimentario);
    modelo.put("paso2Completado", tienePresupuesto);

    ModelMap modeloLista = (ModelMap) session.getAttribute("modeloLista");

    modelo.put("paso3Completado", session.getAttribute("planGenerado") != null);
    modelo.put("paso4Completado", modeloLista != null);

    modelo.put("planGenerado", session.getAttribute("planGenerado"));

    //se extrae los datos del modeloLista
    if (modeloLista != null) {
      modelo.put("listaDeCompras", modeloLista.get("listaDeCompras"));
      modelo.put("totalLista", modeloLista.get("totalLista"));
      modelo.put("dias", modeloLista.get("dias"));
    }

    return new ModelAndView("panel__dashboard", modelo);
  }
}
