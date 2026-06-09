package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ItemCompra;
import com.tallerwebi.dominio.ServicioListaCompras;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorListaComprasTest {

    private ServicioListaCompras servicioListaComprasMock;
    private ControladorListaCompras controladorListaCompras;

    @BeforeEach
    public void inicializarObjetos(){
        servicioListaComprasMock = mock(ServicioListaCompras.class);
        controladorListaCompras = new ControladorListaCompras(servicioListaComprasMock);
    }


    @Test
    public void queDevuelvaLaVistaListaDeCompras(){
        //preparacion
        givenExisteUnaListaDeCompras();
        //ejecucion
        ModelAndView modelAndView = whenSolicitoListaDeCompras();
        //validacion
        thenRetornaVistaListaDeCompras(modelAndView);
    }

    private ModelAndView whenSolicitoListaDeCompras(){
        return controladorListaCompras.mostrarListaCompras();
    }

    public void thenRetornaVistaListaDeCompras(ModelAndView modelAndView){
        assertThat(modelAndView.getViewName(),equalToIgnoringCase("lista-compras"));
    }

    @Test
    public void seAgregaListaDeComprasAlModeloDeDatos(){
        //preparacion
        givenExisteUnaListaDeCompras();
        //ejecucion
        ModelAndView modelAndView = whenSolicitoListaDeCompras();
        //validacion
        thenExisteListaComprasEnModelo(modelAndView);
    }

    private void thenExisteListaComprasEnModelo(ModelAndView modelAndView){
        assertThat(modelAndView.getModel().get("listaDeCompras"),instanceOf(List.class));
    }

    @Test
    public void seAgregaPrecioTotalListaDeCompras(){

        //preparacion
        when(servicioListaComprasMock.generarListaCompras(anyList())).thenReturn(new ArrayList<>());
        when(servicioListaComprasMock.calcularTotalListaCompras(anyList())).thenReturn(5000.0);
        //ejecucion
        ModelAndView modelAndView = whenSolicitoListaDeCompras();
        //validacion
        thenExisteElPrecioTotalEnLaLista(modelAndView);
    }

    private void thenExisteElPrecioTotalEnLaLista(ModelAndView modelAndView){
        assertThat(modelAndView.getModel().get("totalLista"), equalTo(5000.0));
    }

    private void givenExisteUnaListaDeCompras(){
        when(servicioListaComprasMock.generarListaCompras(anyList())).thenReturn(new ArrayList<>());
        when(servicioListaComprasMock.calcularTotalListaCompras(anyList())).thenReturn(0.0);
    }

}
