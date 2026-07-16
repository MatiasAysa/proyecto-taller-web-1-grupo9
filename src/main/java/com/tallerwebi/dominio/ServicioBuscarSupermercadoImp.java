package com.tallerwebi.dominio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioBuscarSupermercadoImp implements ServicioBuscarSupermercado {

  private final Double RADIO_TIERRA = 6371000.0;
  private final Double METROS_POR_MINUTO = 83.33; //REGLA GENERAL DE UNA PERSONA PROMEDIO
  private final ServicioMapa servicioMapa;
  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public ServicioBuscarSupermercadoImp(ServicioMapa servicioMapa) {
    this.servicioMapa = servicioMapa;
  }

  @Override
  public Cordenandas obtenerCordenadaActual(Direccion direccion) {
    String direccionCompleta = direccion.getUbicacion() + " " + direccion.getNumero();
    String respuesta = servicioMapa.obtenerCoordenadas(direccionCompleta);

    try {
      JsonNode root = mapper.readTree(respuesta);

      if (root.isEmpty()) {
        return null;
      }

      JsonNode primerResultado = root.get(0);

      Double latitud = primerResultado.get("lat").asDouble();
      Double longitud = primerResultado.get("lon").asDouble();

      Cordenandas coordenadas = new Cordenandas();

      coordenadas.setLatitud(latitud);
      coordenadas.setLongitud(longitud);

      return coordenadas;
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(
        "No fue posible interpretar la respuesta del servicio de mapas.",
        e
      );
    }
  }

  @Override
  public List<Supermercado> buscarSupermercadosCercanos(Double latitud, Double longitud) {
    String respuesta = servicioMapa.obtenerSupermercados(latitud, longitud);
    return parsearSupermercados(respuesta);
  }

  private List<Supermercado> parsearSupermercados(String respuesta) {
    try {
      JsonNode root = mapper.readTree(respuesta);

      List<Supermercado> tiendas = new ArrayList<>();

      if (!root.has("elements")) {
        return tiendas;
      }

      JsonNode elementos = root.get("elements");

      for (JsonNode supermercado : elementos) {
        tiendas.add(crearSupermercado(supermercado));
      }

      return tiendas;
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(
        "No fue posible interpretar la respuesta del servicio de mapas.",
        e
      );
    }
  }

  private Supermercado crearSupermercado(JsonNode supermercado) {
    JsonNode tags = supermercado.get("tags");

    String nombre = obtenerNombre(tags);

    String direccion = obtenerDireccion(tags);

    Double lat = supermercado.get("lat").asDouble();
    Double lon = supermercado.get("lon").asDouble();

    Cordenandas coordenadas = new Cordenandas();
    coordenadas.setLatitud(lat);
    coordenadas.setLongitud(lon);

    Supermercado tienda = new Supermercado();
    tienda.setNombre(nombre);
    tienda.setDireccionName(direccion);
    tienda.setCordenadas(coordenadas);

    return tienda;
  }

  private String obtenerNombre(JsonNode tags) {
    if (tags != null && tags.has("name")) {
      return tags.get("name").asText();
    }

    return "Supermercado";
  }

  private String obtenerDireccion(JsonNode tags) {
    if (tags == null) {
      return "Dirección no disponible";
    }

    StringBuilder direccion = new StringBuilder();

    if (tags.has("addr:street")) {
      direccion.append(tags.get("addr:street").asText());
    }

    if (tags.has("addr:housenumber")) {
      if (direccion.length() > 0) {
        direccion.append(" ");
      }
      direccion.append(tags.get("addr:housenumber").asText());
    }

    if (direccion.length() == 0) {
      return "Dirección no disponible";
    }

    return direccion.toString();
  }

  @Override
  public void calcularDistancias(List<Supermercado> supermercados, Cordenandas ubicacion) {
    for (Supermercado supermercado : supermercados) {
      Double distancia = calcularDistanciaMetros(ubicacion, supermercado.getCordenadas());

      supermercado.setDistanciaMetros(distancia);

      Integer minutos = calcularTiempoCaminando(distancia);
      supermercado.setMinutosCaminando(minutos);
    }
  }

  @Override
  public Double calcularDistanciaMetros(Cordenandas origen, Cordenandas destino) {
    Double lat1 = Math.toRadians(origen.getLatitud());
    Double lon1 = Math.toRadians(origen.getLongitud());

    Double lat2 = Math.toRadians(destino.getLatitud());
    Double lon2 = Math.toRadians(destino.getLongitud());

    Double diferenciaLatitud = lat2 - lat1;
    Double diferenciaLongitud = lon2 - lon1;

    Double variable1 =
      Math.sin(diferenciaLatitud / 2) * Math.sin(diferenciaLatitud / 2) +
      Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(diferenciaLongitud / 2), 2);

    Double variable2 = 2 * Math.atan2(Math.sqrt(variable1), Math.sqrt(1 - variable1));

    return RADIO_TIERRA * variable2;
  }

  @Override
  public Integer calcularTiempoCaminando(Double metros) {
    Integer minutos = (int) Math.round(metros / METROS_POR_MINUTO);

    return Math.max(minutos, 1);
  }
}
