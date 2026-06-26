package com.tallerwebi.dominio;

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

  private Boolean celiaco;
  private Boolean diabetico;
  private Boolean intoleranciaLactosa;
  private Boolean vegetariano;
  private Boolean vegano;

  @OneToMany(mappedBy = "comida")
  private List<Ingrediente> ingredientes;

  public List<Ingrediente> getIngredientes() {
    return this.ingredientes;
  }

  public void setIngredientes(List<Ingrediente> ingredientes) {
    this.ingredientes = ingredientes;
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
}
