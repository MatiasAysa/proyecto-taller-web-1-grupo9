package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCargaDeReceta;
import com.tallerwebi.dominio.excepcion.NombreDeAlimentoInexistenteException;
import com.tallerwebi.dominio.excepcion.RecetaConNombreRepetidoException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCargaDeReceta {

  private ServicioCargaDeReceta servicioCargaDeReceta;
  private final String CAMPO_DATOS_RECETA = "datosReceta";
  private final String VISTA_CREAR_RECETA = "crear-receta";
  private final String CAMPO_MAIL_USUARIO = "usuarioLogueadoEmail";
  private final String REDIRECT_LOGIN = "redirect:/login";

  @Autowired
  public ControladorCargaDeReceta(ServicioCargaDeReceta servicioCargaDeReceta) {
    this.servicioCargaDeReceta = servicioCargaDeReceta;
  }

  @RequestMapping("/crear-receta")
  public ModelAndView irACrearReceta(HttpSession session) {
    if (session.getAttribute(CAMPO_MAIL_USUARIO) == null) return new ModelAndView(REDIRECT_LOGIN);
    ModelMap model = new ModelMap();
    DatosReceta datosReceta = new DatosReceta();
    List<IngredienteDTO> ingredientes = new ArrayList<IngredienteDTO>();
    ingredientes.add(new IngredienteDTO());
    ingredientes.add(new IngredienteDTO());
    datosReceta.setIngredientes(ingredientes);
    model.put(CAMPO_DATOS_RECETA, datosReceta);
    model.put("listaAlimentos", servicioCargaDeReceta.obtenerNombresDeAlimentosExistentes());
    return new ModelAndView(VISTA_CREAR_RECETA, model);
  }

  @RequestMapping(path = "/validar-receta", method = RequestMethod.POST)
  public ModelAndView validarReceta(DatosReceta datosReceta, HttpSession session) {
    if (session.getAttribute(CAMPO_MAIL_USUARIO) == null) return new ModelAndView(REDIRECT_LOGIN);
    if (
      datosReceta.getNombre() == null || datosReceta.getNombre().isEmpty()
    ) return fallarRecetaPorNombre(datosReceta, "Por favor, introduzca un nombre.");

    if (datosReceta.getTipo() == null) return fallarRecetaPorTipo(
      datosReceta,
      "Por favor, seleccione un tipo."
    );

    List<Integer> ingredientesInvalidos = buscarIngredientesInvalidos(
      datosReceta.getIngredientes()
    );
    if (!ingredientesInvalidos.isEmpty()) {
      return fallarReceta(ingredientesInvalidos, datosReceta);
    }
    return cargarReceta(datosReceta, session.getAttribute(CAMPO_MAIL_USUARIO).toString());
  }

  @RequestMapping("/mis-recetas")
  public ModelAndView mostrarMisRecetas(HttpSession session) {
    if (session.getAttribute(CAMPO_MAIL_USUARIO) == null) return new ModelAndView(REDIRECT_LOGIN);
    String email = session.getAttribute(CAMPO_MAIL_USUARIO).toString();
    ModelMap model = new ModelMap();
    model.put("recetas", servicioCargaDeReceta.obtenerRecetasDeUsuario(email));
    return new ModelAndView("mis-recetas", model);
  }

  @RequestMapping(path = "mis-recetas/eliminar/{id}")
  public ModelAndView eliminarReceta(@PathVariable("id") Long id, HttpSession session) {
    if (session.getAttribute(CAMPO_MAIL_USUARIO) == null) return new ModelAndView(REDIRECT_LOGIN);
    servicioCargaDeReceta.eliminarReceta(id, session.getAttribute(CAMPO_MAIL_USUARIO).toString());
    return new ModelAndView("redirect:/mis-recetas");
  }

  private ModelAndView fallarRecetaPorTipo(DatosReceta datosReceta, String mensaje) {
    datosReceta.setTipo("");
    ModelMap model = new ModelMap();
    model.put("tipoInvalido", mensaje);
    model.put(CAMPO_DATOS_RECETA, datosReceta);
    return new ModelAndView(VISTA_CREAR_RECETA, model);
  }

  private ModelAndView fallarRecetaPorNombre(DatosReceta datosReceta, String mensaje) {
    DatosReceta datosReceta1 = datosReceta;
    datosReceta.setNombre("");
    ModelMap model = new ModelMap();
    model.put(CAMPO_DATOS_RECETA, datosReceta1);
    model.put("nombreInvalido", mensaje);
    return new ModelAndView(VISTA_CREAR_RECETA, model);
  }

  private ModelAndView cargarReceta(DatosReceta datosReceta, String email) {
    try {
      servicioCargaDeReceta.cargarReceta(datosReceta, email);
      return new ModelAndView("redirect:/mis-recetas");
    } catch (RecetaConNombreRepetidoException e) {
      return fallarRecetaPorNombre(datosReceta, "Ya has creado una receta con ese nombre.");
    } catch (NombreDeAlimentoInexistenteException e) {
      return fallarReceta(e.getAlimentosInexistentes(), datosReceta);
    }
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
    model.put(CAMPO_DATOS_RECETA, datosReceta1);
    return new ModelAndView(VISTA_CREAR_RECETA, model);
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  private List<Integer> buscarIngredientesInvalidos(List<IngredienteDTO> ingredientes) {
    if (ingredientes == null || ingredientes.isEmpty()) return new ArrayList<Integer>();
    List<Integer> ingredientesInvalidos = new ArrayList<Integer>();
    List<String> ingredientesValidados = new ArrayList<String>();
    for (IngredienteDTO ingrediente : ingredientes) {
      if (ingredienteEsInvalido(ingrediente, ingredientesValidados)) ingredientesInvalidos.add(
        ingredientes.indexOf(ingrediente)
      );
      ingredientesValidados.add(ingrediente.getNombre());
    }

    return ingredientesInvalidos;
  }

  private boolean ingredienteEsInvalido(
    IngredienteDTO ingrediente,
    List<String> ingredientesValidados
  ) {
    if (ingrediente == null || ingrediente.getNombre() == null) return true;

    return (
      ingrediente.getCantidad() <= 0D || ingredientesValidados.contains(ingrediente.getNombre())
    );
  }
}
