INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

DELETE FROM Alimento;

--================== DESAYUNOS ===================

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (1, 'Avena instantánea Carrefour 400g', 2100.00, 'DESAYUNO', true, false, false, 389, 16.9, 66.3, 6.9, 'https://www.carrefour.com.ar/avena-instantanea-carrefour-400-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (2, 'Leche entera Larga Vida Carrefour 1L', 1500.00, 'DESAYUNO', true, true, true, 58, 3.0, 4.7, 3.0, 'https://www.carrefour.com.ar/leche-entera-larga-vida-carrefour-1-l/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (3, 'Yogur entero firme de Frutilla Ilolay 190g', 1200.00, 'DESAYUNO', true, true, true, 97, 3.1, 15.0, 2.7, 'https://www.carrefour.com.ar/yogur-entero-firme-ilolay-frutilla-190-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (4, 'Café molido clásico La Virginia 250g', 4500.00, 'DESAYUNO', true, true, false, 2, 0.2, 0.3, 0.0, 'https://www.carrefour.com.ar/cafe-molido-la-virginia-clasico-250-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (5, 'Galletitas de agua Express 312g', 1800.00, 'DESAYUNO', true, false, false, 421, 10.0, 68.0, 11.0, 'https://www.carrefour.com.ar/galletitas-de-agua-express-312-g/p');


--================== ALMUERZOS ===================

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (6, 'Fideos tallarines de sémola Carrefour 500g', 1300.00, 'ALMUERZO', true, false, false, 356, 12.0, 73.0, 1.2, 'https://www.carrefour.com.ar/fideos-tallarines-carrefour-500-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (7, 'Puré de tomate en caja Carrefour 520g', 980.00, 'ALMUERZO', true, true, false, 28, 1.4, 5.0, 0.2, 'https://www.carrefour.com.ar/pure-de-tomate-carrefour-520-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (8, 'Arroz largo fino Carrefour 1kg', 1900.00, 'ALMUERZO', true, true, false, 358, 7.3, 79.0, 0.6, 'https://www.carrefour.com.ar/arroz-largo-fino-00000-carrefour-1-kg/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (9, 'Atún al natural en trozos Carrefour 170g', 2900.00, 'ALMUERZO', false, true, false, 101, 23.0, 0.0, 0.9, 'https://www.carrefour.com.ar/atun-al-natural-en-trozos-carrefour-170-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (10, 'Aceite de girasol Cañuelas 900ml', 2200.00, 'ALMUERZO', true, true, false, 884, 0.0, 0.0, 100.0, 'https://www.carrefour.com.ar/aceite-de-girasol-canuelas-900-ml/p');


--================== CENAS ===================

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (11, 'Medallones de carne vacuna Swift 4 un.', 3900.00, 'CENA', false, false, false, 220, 15.0, 1.0, 17.0, 'https://www.carrefour.com.ar/hamburguesas-de-carne-vacuna-swift-clasicas-4-unidades-220-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (12, 'Arvejas secas remojadas Carrefour 300g', 1100.00, 'CENA', true, true, false, 79, 5.2, 13.0, 0.4, 'https://www.carrefour.com.ar/arvejas-secas-remojadas-carrefour-300-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (13, 'Jardinera de verduras Carrefour 300g', 1450.00, 'CENA', true, true, false, 65, 3.1, 12.0, 0.3, 'https://www.carrefour.com.ar/jardinera-de-vegetales-carrefour-300-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (14, 'Garbanzos secos remojados Carrefour 300g', 1350.00, 'CENA', true, true, false, 140, 7.5, 23.0, 2.1, 'https://www.carrefour.com.ar/garbanzos-secas-remojadas-carrefour-300-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (15, 'Lentejas secas remojadas Carrefour 300g', 1400.00, 'CENA', true, true, false, 116, 9.0, 20.0, 0.4, 'https://www.carrefour.com.ar/lentejas-secas-remojadas-carrefour-300-g/p');


--================== RESTRICCIONES ALIMENTARIAS ===================
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('NINGUNO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('CELIACO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('DIABETICO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('VEGETARIANO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('VEGANO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('INTOLERANCIA_LACTOSA');