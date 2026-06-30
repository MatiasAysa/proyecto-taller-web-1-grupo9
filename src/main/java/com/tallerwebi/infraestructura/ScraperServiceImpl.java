package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ScraperService;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service("scraperService")
public class ScraperServiceImpl implements ScraperService {

  @Override
  public Double obtenerPrecioReal(String urlProducto) {
    if (urlProducto == null || urlProducto.trim().isEmpty()) {
      return null;
    }

    try {
      Document doc = Jsoup
        .connect(urlProducto)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
        .timeout(5000)
        .get();

      Element precioElemento = doc.selectFirst("[itemprop=price]");

      if (precioElemento == null) {
        precioElemento = doc.selectFirst(".val-price, .lyra-price, .precio-regular");
      }

      if (precioElemento != null) {
        return parsingPrecio(precioElemento.text());
      }
    } catch (IOException | NumberFormatException e) {
      return null;
    }

    return null;
  }

  private Double parsingPrecio(String textoOriginal) {
    if (textoOriginal == null || textoOriginal.trim().isEmpty()) {
      return null;
    }
    try {
      String precioTexto = textoOriginal.replaceAll("[^0-9,.]", "").trim();
      if (precioTexto.contains(",") && precioTexto.contains(".")) {
        if (precioTexto.lastIndexOf(',') > precioTexto.lastIndexOf('.')) {
          precioTexto = precioTexto.replace(".", "").replace(",", ".");
        } else {
          precioTexto = precioTexto.replace(",", "");
        }
      } else if (precioTexto.contains(",")) {
        precioTexto = precioTexto.replace(",", ".");
      }

      return Double.parseDouble(precioTexto);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
