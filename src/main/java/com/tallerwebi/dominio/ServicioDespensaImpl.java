package com.tallerwebi.dominio;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class ServicioDespensaImpl implements ServicioDespensa {

    private final RepositorioDespensa repositorioDespensa;
    private final RepositorioUsuario repositorioUsuario;
    public ServicioDespensaImpl(RepositorioDespensa repositorioDespensa, RepositorioUsuario repositorioUsuario) {
        this.repositorioDespensa = repositorioDespensa;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<ItemDespensa> obtenerDespensaDelUsuario(String email) {
        Usuario usuario = this.repositorioUsuario.buscar(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return this.repositorioDespensa.obtenerAlacenaDelUsuario(usuario);
    }

    @Override
    public void guardarOActualizarDespensa(ItemDespensa item) {
        repositorioDespensa.guardarOActualizarDespensa(item);
    }

    
    public ItemDespensa buscarItem(Usuario usuario, Alimento alimento) {

        return repositorioDespensa.obtenerDespensaDelUsuario(usuario)
                .stream()
                .filter(i -> i.getAlimento().getId().equals(alimento.getId()))
                .findFirst()
                .orElse(null);
    }

    
    public void agregarStock(Usuario usuario, Alimento alimento,
            Double gramos, Integer unidades) {

        ItemDespensa item = buscarItem(usuario, alimento);

        if (item == null) {
            item = new ItemDespensa();
            item.setUsuario(usuario);
            item.setAlimento(alimento);
            item.setGramosDisponibles(0.0);
            item.setUnidadesDisponibles(0);
        }

        if (gramos != null) {
            item.setGramosDisponibles(
                    item.getGramosDisponibles() + gramos);
        }

        if (unidades != null) {
            item.setUnidadesDisponibles(
                    item.getUnidadesDisponibles() + unidades);
        }

        guardarOActualizar(item);
    }

     
    public void descontarStock(Usuario usuario, Alimento alimento,
            Double gramos, Integer unidades) {

        ItemDespensa item = buscarItem(usuario, alimento);

        if (item == null) {
            return;
        }

        if (gramos != null) {
            double restante = item.getGramosDisponibles() - gramos;
            item.setGramosDisponibles(Math.max(restante, 0.0));
        }

        if (unidades != null) {
            int restante = item.getUnidadesDisponibles() - unidades;
            item.setUnidadesDisponibles(Math.max(restante, 0));
        }

        guardarOActualizar(item);
    }
}