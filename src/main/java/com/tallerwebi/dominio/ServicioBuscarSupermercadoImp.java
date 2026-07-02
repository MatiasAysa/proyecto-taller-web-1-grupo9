package com.tallerwebi.dominio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicioBuscarSupermercadoImp implements ServicioBuscarSupermercado {

  private static final Integer RADIO_BUSQUEDA = 800;
  private final Double RADIO_TIERRA = 6371000.0;
  private final Double METROS_POR_MINUTO = 83.33; //REGLA GENERAL DE UNA PERSONA PROMEDIO

  @Override
  public Cordenandas obtenerCordenadaActual(Direccion direccion) {
    String direccionCompleta = direccion.getUbicacion() + " " + direccion.getNumero();
    direccionCompleta = direccionCompleta.replace(" ", "+");

    String url =
      "https://nominatim.openstreetmap.org/search?q=" + direccionCompleta + "&format=jsonv2";

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "MiAplicacionTallerWebi/1.0");

    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      entity,
      String.class
    );

    String respuesta = response.getBody();

    ObjectMapper mapper = new ObjectMapper();
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
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Supermercado> buscarSupermercadosCercanos(Double latitud, Double longitud) {
    //  String url =
    //   "https://overpass-api.de/api/interpreter?data=" +
    //   "[out:json];" + "node[shop=supermarket](around:700," + latitud + "," + longitud + ");out;";
    String consulta =
      "[out:json];" +
      "(" +
      "node[shop=supermarket](around:2000," +
      latitud +
      "," +
      longitud +
      ");" +
      "node[shop=convenience](around:2000," +
      latitud +
      "," +
      longitud +
      ");" +
      "node[shop=grocery](around:2000," +
      latitud +
      "," +
      longitud +
      ");" +
      "node[shop=greengrocer](around:2000," +
      latitud +
      "," +
      longitud +
      ");" +
      ");" +
      "out;";

    String url = "https://overpass-api.de/api/interpreter?data=" + consulta;
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "MiAplicacionTallerWebi/1.0");
    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<String> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      entity,
      String.class
    );

    String respuesta = response.getBody();

    ObjectMapper mapper = new ObjectMapper();

    try {
      JsonNode root = mapper.readTree(respuesta);
      List<Supermercado> tiendas = new ArrayList<>();

      if (root.isEmpty()) {
        return tiendas;
      }

      if (!root.has("elements")) {
        return tiendas;
      }

      JsonNode elementos = root.get("elements");

      for (JsonNode supermercado : elementos) {
        /*
        String nombreSuper;
        if(supermercado.has("tags")
                && supermercado.get("tags").has("name")) {

          nombreSuper = supermercado.get("tags").get("name").asText();
        }
*/
        String nombreSuper = (supermercado.has("tags") && supermercado.get("tags").has("name"))
          ? supermercado.get("tags").get("name").asText()
          : "Supermercado";

        Double lat = supermercado.get("lat").asDouble();
        Double lon = supermercado.get("lon").asDouble();

        Supermercado tienda = new Supermercado();
        tienda.setNombre(nombreSuper);
        Cordenandas coordenadas = new Cordenandas();
        coordenadas.setLatitud(lat);
        coordenadas.setLongitud(lon);
        tienda.setCordenadas(coordenadas);
        tiendas.add(tienda);
      }
      return tiendas;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
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
