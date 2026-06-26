package com.tallerwebi.infraestructura;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;

//import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioPlanificador;
//import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
//import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateInfraestructuraTestConfig.class })
@Transactional
public class RepositorioPlanificadorTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioPlanificador repositorioPlanificador;

  @BeforeEach
  public void init() {
    this.repositorioPlanificador = new RepositorioPlanificadorImpl(sessionFactory);
  }
  /*
  @Test
  @Rollback
  public void queSePuedaBuscarUnUsuarioPorSuIdYRetorneElUsuarioCorrecto() {
    Usuario usuarioExistente = new Usuario();
    usuarioExistente.setEmail("santiago@unlam.edu.ar");
    usuarioExistente.setPresupuestoSemanal(50000.0);
    usuarioExistente.setEsVegetariano(true);

    sessionFactory.getCurrentSession().save(usuarioExistente);
    Long idGenerado = usuarioExistente.getId();

    Usuario usuarioObtenido = repositorioPlanificador.buscarUsuarioPorId(idGenerado);

    assertThat(usuarioObtenido, notNullValue());
    assertThat(usuarioObtenido.getEmail(), equalToIgnoringCase("santiago@unlam.edu.ar"));
    assertThat(usuarioObtenido.getEsVegetariano(), is(true));
  }

  @Test
  @Rollback
  public void queAlBuscarUnUsuarioInexistenteRetorneNull() {
    Long idFalso = 999L;

    Usuario usuarioObtenido = repositorioPlanificador.buscarUsuarioPorId(idFalso);

    assertThat(usuarioObtenido, nullValue());
  }

  @Test
  @Rollback
  public void queSePuedanObtenerTodosLosAlimentosDisponiblesEnLaBaseDeDatos() {
    Alimento alimento1 = new Alimento();
    alimento1.setNombre("Fideos");
    alimento1.setPrecioPorKilo(1200.0);
    alimento1.setTipoComida("ALMUERZO");

    Alimento alimento2 = new Alimento();
    alimento2.setNombre("Leche");
    alimento2.setPrecioPorKilo(1500.0);
    alimento2.setTipoComida("DESAYUNO");

    sessionFactory.getCurrentSession().save(alimento1);
    sessionFactory.getCurrentSession().save(alimento2);

    List<Alimento> listaAlimentos = repositorioPlanificador.obtenerAlimentosDisponibles();

    assertThat(listaAlimentos, notNullValue());
    assertThat(listaAlimentos, hasSize(2));

    boolean contieneFideos = listaAlimentos.stream().anyMatch(a -> a.getNombre().equals("Fideos"));
    assertThat(contieneFideos, is(true));
  }

  @Test
  @Rollback
  public void queAlObtenerAlimentosDisponiblesNoTraigaRegistrosSiLaTablaEstaVacia() {
    List<Alimento> listaAlimentos = repositorioPlanificador.obtenerAlimentosDisponibles();

    assertThat(listaAlimentos, notNullValue());
    assertThat(listaAlimentos, hasSize(0));
  }

  @Test
  @Rollback
  public void queSePuedaActualizarElPresupuestoDeUnUsuarioYPersistaCorrectamente() {
    Usuario usuario = new Usuario();
    usuario.setEmail("santiago.update@unlam.edu.ar");
    usuario.setPresupuestoSemanal(20000.0);
    sessionFactory.getCurrentSession().save(usuario);
    Long idUsuario = usuario.getId();

    Usuario usuarioAEditar = repositorioPlanificador.buscarUsuarioPorId(idUsuario);
    usuarioAEditar.setPresupuestoSemanal(65000.0);
    usuarioAEditar.setEsVegetariano(true);

    sessionFactory.getCurrentSession().update(usuarioAEditar);
    sessionFactory.getCurrentSession().flush();
    sessionFactory.getCurrentSession().clear();

    Usuario usuarioModificado = repositorioPlanificador.buscarUsuarioPorId(idUsuario);
    assertThat(usuarioModificado, notNullValue());
    assertThat(usuarioModificado.getPresupuestoSemanal(), is(65000.0));
    assertThat(usuarioModificado.getEsVegetariano(), is(true));
  }

  @Test
  @Rollback
  public void queSePersistanYRecuperenCorrectamenteLasRestriccionesDeLosAlimentos() {
    Alimento alimentoComplejo = new Alimento();
    alimentoComplejo.setNombre("Yogur Desnatado");
    alimentoComplejo.setPrecioPorKilo(2300.0);
    alimentoComplejo.setTipoComida("DESAYUNO");
    alimentoComplejo.setEsVegetariano(true);
    alimentoComplejo.setEsCeliaco(true);
    alimentoComplejo.setContieneLactosa(true);

    sessionFactory.getCurrentSession().save(alimentoComplejo);
    sessionFactory.getCurrentSession().flush();
    sessionFactory.getCurrentSession().clear();

    List<Alimento> alimentos = repositorioPlanificador.obtenerAlimentosDisponibles();

    assertThat(alimentos, hasSize(1));
    Alimento recuperado = alimentos.get(0);
    assertThat(recuperado.getEsVegetariano(), is(true));
    assertThat(recuperado.getEsCeliaco(), is(true));
    assertThat(recuperado.getContieneLactosa(), is(true));
  }

   */
}
