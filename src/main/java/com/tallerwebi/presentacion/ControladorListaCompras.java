package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.DiaListaComprasDTO;
import com.tallerwebi.dominio.ItemCompra;
import com.tallerwebi.dominio.ServicioListaCompras;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorListaCompras {

  private ServicioListaCompras servicioListaCompras;

  @Autowired
  public ControladorListaCompras(ServicioListaCompras servicioListaCompras) {
    this.servicioListaCompras = servicioListaCompras;
  }

  @PostMapping("/lista-compras")
  public ModelAndView mostrarListaCompras(
    @RequestParam(required = false) MultiValueMap<String, String> todasLasSelecciones,
    HttpSession sesion
  ) {
    Object emailLogueado = sesion.getAttribute("usuarioLogueadoEmail");

    if (emailLogueado == null) {
      return new ModelAndView("redirect:/login");
    }

    ModelMap modelo = new ModelMap();
    if (todasLasSelecciones == null || todasLasSelecciones.isEmpty()) {
      modelo.put("error", "No seleccionaste ningún alimento de tu plan.");
      return new ModelAndView("redirect:/planificador", modelo);
    }

    String email = (String) emailLogueado;
    List<DiaListaComprasDTO> dias = servicioListaCompras.armarDiasSeleccionados(
      todasLasSelecciones
    );

    List<Comida> todasLasComidas = dias
      .stream()
      .flatMap(dia -> dia.getComidas().stream())
      .collect(Collectors.toList());

    List<ItemCompra> listaDeCompras = servicioListaCompras.generarListaCompras(todasLasComidas);
    servicioListaCompras.calcularPrecios(listaDeCompras);
    Double total = servicioListaCompras.calcularTotalListaCompras(listaDeCompras);

    modelo.put("dias", dias);
    modelo.put("listaDeCompras", listaDeCompras);
    modelo.put("totalLista", total);

    // ==========================================
    // NUEVO:guardo el modelo lista de compras en la sesion
    sesion.setAttribute("modeloLista", modelo);
    // ==========================================

    try {
      List<String> datosNutricional = servicioListaCompras.mostrarDtosTestear(email);
      modelo.put("test", datosNutricional);
    } catch (UsuarioInexistenteException e) {
      return new ModelAndView("redirect:/login");
    }

    return new ModelAndView("lista-compras", modelo);
  }
}
