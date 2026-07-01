package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCargaDeReceta;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCargaDeReceta {

  private ServicioCargaDeReceta servicioCargaDeReceta;

  @Autowired
  public ControladorCargaDeReceta(ServicioCargaDeReceta servicioCargaDeReceta) {
    this.servicioCargaDeReceta = servicioCargaDeReceta;
  }

  @RequestMapping("/crear-receta")
  public ModelAndView irACrearReceta(HttpSession session) {
    if (session.getAttribute("usuarioLogueadoEmail") == null) return new ModelAndView(
      "redirect:/login"
    );
    ModelMap model = new ModelMap();
    DatosReceta datosReceta = new DatosReceta();
    List<IngredienteDTO> ingredientes = new ArrayList<IngredienteDTO>();
    ingredientes.add(new IngredienteDTO());
    ingredientes.add(new IngredienteDTO());
    datosReceta.setIngredientes(ingredientes);
    model.put("datosReceta", datosReceta);
    model.put("listaAlimentos", servicioCargaDeReceta.obtenerNombresDeAlimentosExistentes());
    return new ModelAndView("crear-receta", model);
  }

  @RequestMapping(path = "/validar-receta", method = RequestMethod.POST)
  public ModelAndView validarReceta(DatosReceta datosReceta, HttpSession session) {
    if (session.getAttribute("usuarioLogueadoEmail") == null) return new ModelAndView(
      "redirect:/login"
    );
    List<Integer> ingredientesInvalidos = buscarIngredientesInvalidos(
      datosReceta.getIngredientes()
    );
    if (!ingredientesInvalidos.isEmpty()) {
      return fallarReceta(ingredientesInvalidos, datosReceta);
    }
    return cargarReceta(datosReceta);
  }

  private ModelAndView cargarReceta(DatosReceta datosReceta) {
    servicioCargaDeReceta.cargarReceta(datosReceta);
    return new ModelAndView("redirect:/home");
  }

  private ModelAndView fallarReceta(List<Integer> ingredientesInvalidos, DatosReceta datosReceta) {
    DatosReceta datosReceta1 = datosReceta;
    List<IngredienteDTO> ingredientes = datosReceta1.getIngredientes();
    for (int i = 0; i < ingredientes.size(); i++) {
      if (ingredientesInvalidos.contains(Integer.valueOf(i))) {
        ingredientes.set(i, new IngredienteDTO());
      }
    }
    datosReceta1.setIngredientes(ingredientes);

    ModelMap model = new ModelMap();
    model.put("ingredientesInvalidos", ingredientesInvalidos);
    model.put("listaAlimentos", servicioCargaDeReceta.obtenerNombresDeAlimentosExistentes());
    model.put("datosReceta", datosReceta1);
    return new ModelAndView("crear-receta", model);
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  private List<Integer> buscarIngredientesInvalidos(List<IngredienteDTO> ingredientes) {
    if (ingredientes.isEmpty()) return new ArrayList<Integer>();
    List<Integer> ingredientesInvalidos = new ArrayList<Integer>();
    List<String> ingredientesValidados = new ArrayList<String>();
    List<String> nombresDeAlimentosExistentes =
      servicioCargaDeReceta.obtenerNombresDeAlimentosExistentes();
    for (IngredienteDTO i : ingredientes) {
      if (
        !nombresDeAlimentosExistentes.contains(i.getNombre()) ||
        i.getCantidad() <= 0D ||
        ingredientesValidados.contains(i.getNombre())
      ) ingredientesInvalidos.add(ingredientes.indexOf(i));
      ingredientesValidados.add(i.getNombre());
    }

    return ingredientesInvalidos;
  }
}
