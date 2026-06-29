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
    String url =
      "https://overpass-api.de/api/interpreter?data=" +
      "[out:json];" +
      "node[shop=supermarket](around:3000," +
      latitud +
      "," +
      longitud +
      ");out;";

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
        tienda.setCordenandas(coordenadas);
        tiendas.add(tienda);
      }
      return tiendas;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
