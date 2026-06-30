package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

import com.tallerwebi.infraestructura.RepositorioListaDeComprasImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioListaComprasTest {

    private RepositorioListaDeCompras repositorioListaDeCompras;
    private ServicioListaCompras servicioListaCompras;
    private RepositorioAlimento repositorioAlimento;

    @BeforeEach
    public void iniciazarObjetos() {
        this.repositorioListaDeCompras = mock(RepositorioListaDeCompras.class);
        this.repositorioAlimento = mock(RepositorioAlimento.class);
        servicioListaCompras = new ServicioListaComprasImpl(repositorioAlimento,repositorioListaDeCompras);
    }

    @Test
    public void buscarUnAlimentoEnUnaListaDeComprasYEncontrarlo() {
        Alimento alimento = new Alimento();
        alimento.setNombre("Arroz");

        ItemCompra item = new ItemCompra();
        item.setAlimento(alimento);

        List<ItemCompra> lista = new ArrayList<ItemCompra>();
        lista.add(item);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setAlimento(alimento);

        ItemCompra alimentoResultante = servicioListaCompras.buscarItemCompra(lista, ingrediente);

        assertThat(alimentoResultante, equalTo(item));
    }

    @Test
    public void buscarUnAlimentoYNoEncontrarlo() {
        Alimento alimento = new Alimento();
        alimento.setNombre("Arroz");
        Alimento alimento2 = new Alimento();
        alimento.setNombre("Pollo");

        ItemCompra item = new ItemCompra();
        item.setAlimento(alimento);

        List<ItemCompra> lista = new ArrayList<ItemCompra>();
        lista.add(item);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setAlimento(alimento2);

        //Busca si hay pollo en la lista,cosa que no porque solo metimos el alimento arroz
        ItemCompra alimentoResultante = servicioListaCompras.buscarItemCompra(lista, ingrediente);

        assertThat(alimentoResultante, nullValue());
    }

    @Test
    public void queAlCalcularElPrecioTotalDeLaListaDeComprasElResultadoSeaCorrecto() {
        ItemCompra item1 = new ItemCompra();
        item1.setPrecoTotal(1000.0);

        ItemCompra item2 = new ItemCompra();
        item2.setPrecoTotal(2000.0);

        List<ItemCompra> lista = new ArrayList<>();
        lista.add(item1);
        lista.add(item2);

        Double total = servicioListaCompras.calcularTotalListaCompras(lista);

        assertThat(total, equalTo(3000.0));
    }

    @Test
    public void queAlCalcularElPrecioTotalDeCadaAlimentoSeaCorrecto() {
        Alimento arroz = new Alimento();
       // arroz.setPrecioEstimado(2000.0); //ESTO ES EL PRECIO POR KILO DE ARROZ, en nuestro caso nosotros seteamos 2000

        ItemCompra item = new ItemCompra();
        item.setAlimento(arroz);
        item.setCantidadTotal(500.0);

        List<ItemCompra> lista = new ArrayList<>();
        lista.add(item);

        servicioListaCompras.calcularPreciosParaCadaAlimento(lista);

        //SI $2000 ES 1000G DE ARROZ, ENTONCES 500G ES $1000
        assertThat(item.getCantidadTotal(), equalTo(1000.0));
    }
}