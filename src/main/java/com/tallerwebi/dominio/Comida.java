package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Comida {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  @Enumerated(EnumType.STRING)
  private TipoDeComida tipo;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "comida_items", joinColumns = @JoinColumn(name = "comida_id"))
  private List<ItemComida> items = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public TipoDeComida getTipo() {
    return tipo;
  }

  public void setTipo(TipoDeComida tipo) {
    this.tipo = tipo;
  }

  public List<ItemComida> getItems() {
    return items;
  }

  public void setItems(List<ItemComida> items) {
    this.items = items;
  }

  public Double getPrecioEstimado() {
    return items
      .stream()
      .mapToDouble(i ->
        (i.getCantidadGramos() / 1000.0) *
        (i.getAlimento().getPrecioEstimado() != null ? i.getAlimento().getPrecioEstimado() : 0.0)
      )
      .sum();
  }

  public Integer getCalorias() {
    return items
      .stream()
      .mapToInt(i ->
        (int) ((i.getCantidadGramos() / 100.0) *
          (i.getAlimento().getCalorias() != null ? i.getAlimento().getCalorias() : 0))
      )
      .sum();
  }

  public Double getProteinas() {
    return items
      .stream()
      .mapToDouble(i ->
        (i.getCantidadGramos() / 100.0) *
        (i.getAlimento().getProteinas() != null ? i.getAlimento().getProteinas() : 0.0)
      )
      .sum();
  }

  public Double getCarbohidratos() {
    return items
      .stream()
      .mapToDouble(i ->
        (i.getCantidadGramos() / 100.0) *
        (i.getAlimento().getCarbohidratos() != null ? i.getAlimento().getCarbohidratos() : 0.0)
      )
      .sum();
  }

  public Double getGrasas() {
    return items
      .stream()
      .mapToDouble(i ->
        (i.getCantidadGramos() / 100.0) *
        (i.getAlimento().getGrasas() != null ? i.getAlimento().getGrasas() : 0.0)
      )
      .sum();
  }

  public Boolean getEsVegetariano() {
    return items
      .stream()
      .allMatch(i ->
        i.getAlimento().getEsVegetariano() != null && i.getAlimento().getEsVegetariano()
      );
  }

  public Boolean getEsCeliaco() {
    return items
      .stream()
      .allMatch(i -> i.getAlimento().getEsCeliaco() != null && i.getAlimento().getEsCeliaco());
  }

  public Boolean getContieneLactosa() {
    return items
      .stream()
      .anyMatch(i ->
        i.getAlimento().getContieneLactosa() != null && i.getAlimento().getContieneLactosa()
      );
  }
}
