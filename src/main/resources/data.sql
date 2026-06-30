-- =============================================================================
-- LIMPIDEZA DE TABLAS (Mismo orden secuencial por claves foráneas)
-- =============================================================================
DELETE FROM comida_items;
DELETE FROM Comida;
DELETE FROM Alimento;
DELETE FROM RestriccionAlimentaria;
DELETE FROM Usuario;

-- =============================================================================
-- USUARIOS DE PRUEBA
-- =============================================================================
INSERT INTO Usuario(id, email, password, rol, activo)
VALUES(1, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

-- =============================================================================
-- RESTRICCIONES ALIMENTARIAS
-- =============================================================================
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('NINGUNO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('CELIACO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('DIABETICO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('VEGETARIANO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('VEGANO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('INTOLERANCIA_LACTOSA');

-- =============================================================================
-- INSERTS DE ALIMENTOS BASE (Despensa extendida)
-- =============================================================================

-- Desayunos (Tus originales)
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (1, 'Avena instantánea Carrefour 400g', 2100.00, 'DESAYUNO', true, false, false, 389, 16.9, 66.3, 6.9, 'https://www.carrefour.com.ar/avena-instantanea-carrefour-400-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (2, 'Leche entera Larga Vida Carrefour 1L', 1500.00, 'DESAYUNO', true, true, true, 58, 3.0, 4.7, 3.0, 'https://www.carrefour.com.ar/leche-entera-larga-vida-carrefour-1-l/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (3, 'Yogur entero firme de Frutilla Ilolay 190g', 1200.00, 'DESAYUNO', true, true, true, 97, 3.1, 15.0, 2.7, 'https://www.carrefour.com.ar/yogur-entero-firme-ilolay-frutilla-190-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (4, 'Café molido clásico La Virginia 250g', 4500.00, 'DESAYUNO', true, true, false, 2, 0.2, 0.3, 0.0, 'https://www.carrefour.com.ar/cafe-molido-la-virginia-clasico-250-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (17, 'Queso crema clásico Casancrem 290g', 3100.00, 'DESAYUNO', true, true, true, 194, 7.0, 4.5, 16.0, 'https://www.carrefour.com.ar/queso-crema-clasico-casancrem-290-g/p');

-- Desayunos (Nuevos para variables de restricción)
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (30, 'Pan lactal sin TACC San Jacinto 300g', 3800.00, 'DESAYUNO', true, true, false, 245, 4.2, 52.0, 2.5, 'https://www.carrefour.com.ar/pan-lactal-sin-tacc');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (31, 'Bebida de Almendras Silk Original 1L', 2900.00, 'DESAYUNO', true, true, false, 24, 0.6, 1.7, 1.5, 'https://www.carrefour.com.ar/bebida-de-almendras-silk');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (32, 'Mermelada de Frutilla BC La Campagnola 390g', 2400.00, 'DESAYUNO', true, true, false, 48, 0.2, 12.0, 0.0, 'https://www.carrefour.com.ar/mermelada-bc-frutilla');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (33, 'Huevos colorados Carrefour x6 un.', 1800.00, 'DESAYUNO', true, true, false, 143, 12.6, 0.6, 9.5, 'https://www.carrefour.com.ar/huevos-colorados-x6');


-- Almuerzos (Tus originales)
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (6, 'Fideos tallarines de sémola Carrefour 500g', 1300.00, 'ALMUERZO', true, false, false, 356, 12.0, 73.0, 1.2, 'https://www.carrefour.com.ar/fideos-tallarines-carrefour-500-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (7, 'Puré de tomate en caja Carrefour 520g', 980.00, 'ALMUERZO', true, true, false, 28, 1.4, 5.0, 0.2, 'https://www.carrefour.com.ar/pure-de-tomate-carrefour-520-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (8, 'Arroz largo fino Carrefour 1kg', 1900.00, 'ALMUERZO', true, true, false, 358, 7.3, 79.0, 0.6, 'https://www.carrefour.com.ar/arroz-largo-fino-00000-carrefour-1-kg/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (9, 'Atún al natural en trozos Carrefour 170g', 2900.00, 'ALMUERZO', false, true, false, 101, 23.0, 0.0, 0.9, 'https://www.carrefour.com.ar/atun-al-natural-en-trozos-carrefour-170-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (21, 'Fideos de arroz s/TACC Matarazzo 500g', 2800.00, 'ALMUERZO', true, true, false, 348, 6.5, 78.0, 0.8, 'https://www.carrefour.com.ar/fideos-de-arroz-penne-rigate-matarazzo-sin-tacc-500-g/p');

-- Almuerzos (Nuevos)
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (34, 'Pechuga de pollo fresca Suprema x1kg', 6500.00, 'ALMUERZO', false, true, false, 165, 31.0, 0.0, 3.6, 'https://www.carrefour.com.ar/suprema-de-pollo-kg');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (35, 'Espinaca congelada en cubos Carrefour 400g', 2200.00, 'ALMUERZO', true, true, false, 23, 2.9, 3.6, 0.4, 'https://www.carrefour.com.ar/espinaca-congelada-carrefour');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (36, 'Garbanzos secos Carrefour 400g', 1600.00, 'ALMUERZO', true, true, false, 364, 19.0, 61.0, 6.0, 'https://www.carrefour.com.ar/garbanzos-secos-400g');


-- Cenas (Tus originales)
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (11, 'Medallones de carne vacuna Swift 4 un.', 3900.00, 'CENA', false, false, false, 220, 15.0, 1.0, 17.0, 'https://www.carrefour.com.ar/hamburguesas-de-carne-vacuna-swift-clasicas-4-unidades-220-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (15, 'Lentejas secas remojadas Carrefour 300g', 1400.00, 'CENA', true, true, false, 116, 9.0, 20.0, 0.4, 'https://www.carrefour.com.ar/lentejas-secas-remojadas-carrefour-300-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (23, 'Medallones de lentejas y quinoa NotCo 2 un', 3400.00, 'CENA', true, true, false, 180, 12.0, 15.0, 7.0, 'https://www.carrefour.com.ar/hamburguesa-not-burger-notco-2-unidades-200-g/p');

-- Cenas (Nuevos)
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (37, 'Crema de leche regular Milkaut 200g', 1950.00, 'CENA', true, true, true, 345, 2.1, 3.0, 36.0, 'https://www.carrefour.com.ar/crema-de-leche-milkaut');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (38, 'Tofu clásico Soyana 300g', 2800.00, 'CENA', true, true, false, 76, 8.0, 1.9, 4.8, 'https://www.carrefour.com.ar/tofu-clasico-soyana');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (39, 'Carne picada vacuna común x1kg', 5800.00, 'CENA', false, true, false, 250, 18.0, 0.0, 20.0, 'https://www.carrefour.com.ar/carne-picada-vacuna');


-- =============================================================================
-- ENTIEDAD COMIDA (Platos armados completos)
-- =============================================================================

-- Tus 7 comidas originales
INSERT INTO Comida (id, nombre, tipo) VALUES (1, 'Café con leche y Tortilla de Avena', 'DESAYUNO');
INSERT INTO Comida (id, nombre, tipo) VALUES (2, 'Yogur con Avena y Queso Crema', 'DESAYUNO');
INSERT INTO Comida (id, nombre, tipo) VALUES (3, 'Tallarines con Atún al Pomodoro', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (4, 'Arroz con Atún Simple', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (5, 'Fideos de Arroz sin TACC con Tomate', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (6, 'Hamburguesa Swift con Lentejas', 'CENA');
INSERT INTO Comida (id, nombre, tipo) VALUES (7, 'Bowl Veggie de Lentejas y NotBurger', 'CENA');

-- Nuevas Comidas de Desayuno (IDs del 8 al 10)
INSERT INTO Comida (id, nombre, tipo) VALUES (8, 'Tostadas sin TACC con Mermelada y Café', 'DESAYUNO'); -- Vegano, Celiaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (9, 'Omelette de Queso Crema y Café', 'DESAYUNO');           -- Vegetariano, Con Lactosa, Celiaco
INSERT INTO Comida (id, nombre, tipo) VALUES (10, 'Bowl de Avena con Bebida de Almendras', 'DESAYUNO'); -- Vegano, Sin Lactosa, No Celiaco (Avena común)

-- Nuevas Comidas de Almuerzo (IDs del 11 al 14)
INSERT INTO Comida (id, nombre, tipo) VALUES (11, 'Suprema de Pollo con Arroz Blanco', 'ALMUERZO');     -- Carnívoro, Celiaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (12, 'Guiso Vegano de Garbanzos y Tomate', 'ALMUERZO');    -- Vegano, Vegetariano, Celiaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (13, 'Tallarines a la Crema de Espinaca', 'ALMUERZO');     -- Vegetariano, Con Lactosa, No Celiaco (Fideos comunes)
INSERT INTO Comida (id, nombre, tipo) VALUES (14, 'Arroz con Carne Picada y Tomate', 'ALMUERZO');       -- Carnívoro, Celiaco, Sin Lactosa

-- Nuevas Comidas de Cena (IDs del 15 al 18)
INSERT INTO Comida (id, nombre, tipo) VALUES (15, 'Revuelto de Tofu y Espinaca', 'CENA');               -- Vegano, Vegetariano, Celiaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (16, 'Albóndigas con Puré de Tomate', 'CENA');             -- Carnívoro, Con TACC (Usa fideo/harina común para amalgama)
INSERT INTO Comida (id, nombre, tipo) VALUES (17, 'Ensalada Tibia de Lentejas y Espinaca', 'CENA');     -- Vegano, Vegetariano, Celiaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (18, 'Sopa de Fideos de Arroz y Pollo', 'CENA');           -- Carnívoro, Celiaco, Sin Lactosa


-- =============================================================================
-- DETALLE DE INGREDIENTES Y GRAMAJES (comida_items)
-- =============================================================================

-- Comida 1 (Tus ingredientes originales)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (1, 60.0, 1);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (1, 200.0, 2);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (1, 10.0, 4);

-- Comida 2 (Tus ingredientes originales)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (2, 190.0, 3);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (2, 40.0, 1);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (2, 30.0, 17);

-- Comida 3 (Tus ingredientes originales)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (3, 100.0, 6);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (3, 150.0, 7);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (3, 85.0, 9);

-- Comida 4 (Tus ingredientes originales)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (4, 100.0, 8);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (4, 85.0, 9);

-- Comida 5 (Tus ingredientes originales)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (5, 100.0, 21);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (5, 150.0, 7);

-- Comida 6 (Tus ingredientes originales)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (6, 110.0, 11);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (6, 150.0, 15);

-- Comida 7 (Tus ingredientes originales)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (7, 100.0, 23);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (7, 150.0, 15);


-- ITEMS NUEVOS ASOCIADOS A LAS COMIDAS AGREGADAS

-- Comida 8: Tostadas sin TACC con Mermelada y Café
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (8, 50.0, 30);  -- Pan sin TACC
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (8, 30.0, 32);  -- Mermelada BC
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (8, 10.0, 4);   -- Café

-- Comida 9: Omelette de Queso Crema y Café
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (9, 120.0, 33); -- Huevos colorados
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (9, 40.0, 17);  -- Casancrem (Tiene Lactosa)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (9, 10.0, 4);   -- Café

-- Comida 10: Bowl de Avena con Bebida de Almendras
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (10, 60.0, 1);   -- Avena (Rompe Sin TACC)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (10, 200.0, 31); -- Bebida de Almendras

-- Comida 11: Suprema de Pollo con Arroz Blanco
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (11, 200.0, 34); -- Pollo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (11, 80.0, 8);   -- Arroz

-- Comida 12: Guiso Vegano de Garbanzos y Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (12, 150.0, 36); -- Garbanzos secos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (12, 150.0, 7);  -- Puré tomate

-- Comida 13: Tallarines a la Crema de Espinaca
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (13, 100.0, 6);  -- Fideos comunes (Rompe Sin TACC)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (13, 80.0, 37);  -- Crema de leche (Tiene Lactosa)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (13, 100.0, 35); -- Espinaca congelada

-- Comida 14: Arroz con Carne Picada y Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (14, 100.0, 8);  -- Arroz
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (14, 150.0, 39); -- Carne Picada
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (14, 100.0, 7);  -- Tomate en caja

-- Comida 15: Revuelto de Tofu y Espinaca
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (15, 150.0, 38); -- Tofu clásico
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (15, 150.0, 35); -- Espinaca congelada

-- Comida 16: Albóndigas con Puré de Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (16, 200.0, 39); -- Carne picada
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (16, 150.0, 7);  -- Puré de tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (16, 20.0, 6);   -- Fideos/Harina de sémola común como ligante (Rompe Sin TACC)

-- Comida 17: Ensalada Tibia de Lentejas y Espinaca
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (17, 200.0, 15); -- Lentejas secas
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (17, 100.0, 35); -- Espinaca congelada

-- Comida 18: Sopa de Fideos de Arroz y Pollo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (18, 80.0, 21);  -- Fideos de arroz sin TACC
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (18, 150.0, 34); -- Pechuga de pollo