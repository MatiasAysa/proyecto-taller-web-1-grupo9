package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.MuchasPeticionesServicioMapas;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicioMapaImpl implements ServicioMapa {

  @Override
  public String obtenerCoordenadas(String direccion) {
    String direccionCompleta = direccion.replace(" ", "+");

    String url =
      "https://nominatim.openstreetmap.org/search?q=" + direccionCompleta + "&format=jsonv2";

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "MiAplicacionTallerWebi/1.0");

    HttpEntity<String> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        entity,
        String.class
      );
      return response.getBody();
    } catch (RestClientException e) {
      throw new MuchasPeticionesServicioMapas("No fue posible consultar Nominatim", e);
    }
  }

  @Override
  public String obtenerSupermercados(Double latitud, Double longitud) {
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
      ");out;";

    String url = "https://overpass-api.de/api/interpreter?data=" + consulta;

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "MiAplicacionTallerWebi/1.0");

    HttpEntity<String> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        entity,
        String.class
      );

      return response.getBody();
    } catch (RestClientException e) {
      throw new MuchasPeticionesServicioMapas("No fue posible consultar Overpass", e);
    }
  }
}
