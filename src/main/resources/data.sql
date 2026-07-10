-- =============================================================================
-- LIMPIEZA DE TABLAS (Mismo orden secuencial por claves foráneas)
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
-- INSERTS DE ALIMENTOS BASE (Despensa completa y extendida)
-- =============================================================================
-- --- DESAYUNOS ---

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (1, 'Avena instantánea Carrefour 400g', 'Avena instantánea', 2100.00, 'DESAYUNO', true, false, false, 389, 16.9, 66.3, 6.9, 'https://www.carrefour.com.ar/avena-instantanea-carrefour-400-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (2, 'Leche entera Larga Vida Carrefour 1L', 'Leche entera',1500.00, 'DESAYUNO', true, true, true, 58, 3.0, 4.7, 3.0, 'https://www.carrefour.com.ar/leche-entera-larga-vida-carrefour-1-l/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (3, 'Yogur entero firme de Frutilla Ilolay 190g', 'Yogur entero de frutilla', 1200.00, 'DESAYUNO', true, true, true, 97, 3.1, 15.0, 2.7, 'https://www.carrefour.com.ar/yogur-entero-firme-ilolay-frutilla-190-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (4, 'Café molido clásico La Virginia 250g', 'Café molido',4500.00, 'DESAYUNO', true, true, false, 2, 0.2, 0.3, 0.0, 'https://www.carrefour.com.ar/cafe-molido-la-virginia-clasico-250-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (17, 'Queso crema clásico Casancrem 290g', 'Queso crema',3100.00, 'DESAYUNO', true, true, true, 194, 7.0, 4.5, 16.0, 'https://www.carrefour.com.ar/queso-crema-clasico-casancrem-290-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (30, 'Pan lactal sin TACC San Jacinto 300g', 'Pan lactal sin TACC',3800.00, 'DESAYUNO', true, true, false, 245, 4.2, 52.0, 2.5, 'https://www.carrefour.com.ar/pan-lactal-sin-tacc');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (31, 'Bebida de Almendras Silk Original 1L','Bebida de almendras', 2900.00, 'DESAYUNO', true, true, false, 24, 0.6, 1.7, 1.5, 'https://www.carrefour.com.ar/bebida-de-almendras-silk');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (32, 'Mermelada de Frutilla BC La Campagnola 390g','Mermelada de frutilla', 2400.00, 'DESAYUNO', true, true, false, 48, 0.2, 12.0, 0.0, 'https://www.carrefour.com.ar/mermelada-bc-frutilla');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (33, 'Huevos colorados Carrefour x6 un.', 'Huevo', 1800.00, 'DESAYUNO', true, true, false, 143, 12.6, 0.6, 9.5, 'https://www.carrefour.com.ar/huevos-colorados-x6');

-- NUEVOS ALIMENTOS DESAYUNO (IDs 40 a 43)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (40, 'Pan lactal blanco tradicional Bimbo 550g', 'Pan lactal blanco', 3200.00, 'DESAYUNO', true, false, false, 258, 8.5, 49.0, 3.2, 'https://www.carrefour.com.ar/pan-lactal-bimbo-blanco');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (41, 'Banana Cavendish por kg', 'Banana cavendish', 1900.00, 'DESAYUNO', true, true, false, 89, 1.1, 22.8, 0.3, 'https://www.carrefour.com.ar/banana-cavendish-kg');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (42, 'Crema de maní s/azúcar Le-Fit 400g', 'Crema de maní sin azúcar', 4100.00, 'DESAYUNO', true, true, false, 588, 24.0, 20.0, 50.0, 'https://www.carrefour.com.ar/crema-de-mani-le-fit');


-- --- ALMUERZOS ---
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (6, 'Fideos tallarines de sémola Carrefour 500g', 'Fideos tallarines de sémola',1300.00, 'ALMUERZO', true, false, false, 356, 12.0, 73.0, 1.2, 'https://www.carrefour.com.ar/fideos-tallarines-carrefour-500-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (7, 'Puré de tomate en caja Carrefour 520g', 'Puré de tomate',980.00, 'ALMUERZO', true, true, false, 28, 1.4, 5.0, 0.2, 'https://www.carrefour.com.ar/pure-de-tomate-carrefour-520-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (8, 'Arroz largo fino Carrefour 1kg', 'Arroz largo fino',1900.00, 'ALMUERZO', true, true, false, 358, 7.3, 79.0, 0.6, 'https://www.carrefour.com.ar/arroz-largo-fino-00000-carrefour-1-kg/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (9, 'Atún al natural en trozos Carrefour 170g', 'Atún', 2900.00, 'ALMUERZO', false, true, false, 101, 23.0, 0.0, 0.9, 'https://www.carrefour.com.ar/atun-al-natural-en-trozos-carrefour-170-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (21, 'Fideos de arroz s/TACC Matarazzo 500g', 'Fideos de arroz sin TACC', 2800.00, 'ALMUERZO', true, true, false, 348, 6.5, 78.0, 0.8, 'https://www.carrefour.com.ar/fideos-de-arroz-penne-rigate-matarazzo-sin-tacc-500-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (34, 'Pechuga de pollo fresca Suprema x1kg', 'Pechuga de pollo', 6500.00, 'ALMUERZO', false, true, false, 165, 31.0, 0.0, 3.6, 'https://www.carrefour.com.ar/suprema-de-pollo-kg');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (35, 'Espinaca congelada en cubos Carrefour 400g','Espinaca', 2200.00, 'ALMUERZO', true, true, false, 23, 2.9, 3.6, 0.4, 'https://www.carrefour.com.ar/espinaca-congelada-carrefour');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (36, 'Garbanzos secos Carrefour 400g', 'Garbanzos',1600.00, 'ALMUERZO', true, true, false, 364, 19.0, 61.0, 6.0, 'https://www.carrefour.com.ar/garbanzos-secos-400g');

-- NUEVOS ALIMENTOS ALMUERZO (IDs 44 a 46)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (44, 'Filet de merluza fresco congelado x1kg', 'Filet de merluza', 7200.00, 'ALMUERZO', false, true, false, 90, 19.0, 0.0, 1.2, 'https://www.carrefour.com.ar/filet-de-merluza-kg');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (45, 'Palta Torres selección por unidad', 'Palta', 1300.00, 'ALMUERZO', true, true, false, 160, 2.0, 8.5, 14.7, 'https://www.carrefour.com.ar/palta-unidad');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (46, 'Arvejas en lata Carrefour 300g', 'Arvejas',850.00, 'ALMUERZO', true, true, false, 69, 4.5, 12.0, 0.4, 'https://www.carrefour.com.ar/arvejas-en-lata-carrefour');


-- --- CENAS ---
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (11, 'Medallones de carne vacuna Swift 4 un.', 'Medallón de carne',3900.00, 'CENA', false, false, false, 220, 15.0, 1.0, 17.0, 'https://www.carrefour.com.ar/hamburguesas-de-carne-vacuna-swift-clasicas-4-unidades-220-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (15, 'Lentejas secas remojadas Carrefour 300g', 'Lentejas',1400.00, 'CENA', true, true, false, 116, 9.0, 20.0, 0.4, 'https://www.carrefour.com.ar/lentejas-secas-remojadas-carrefour-300-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (23, 'Medallones de lentejas y quinoa NotCo 2 un', 'Medallón de lentejas y quinoa', 3400.00, 'CENA', true, true, false, 180, 12.0, 15.0, 7.0, 'https://www.carrefour.com.ar/hamburguesa-not-burger-notco-2-unidades-200-g/p');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (37, 'Crema de leche regular Milkaut 200g', 'Crema de leche', 1950.00, 'CENA', true, true, true, 345, 2.1, 3.0, 36.0, 'https://www.carrefour.com.ar/crema-de-leche-milkaut');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (38, 'Tofu clásico Soyana 300g', 'Tofu', 2800.00, 'CENA', true, true, false, 76, 8.0, 1.9, 4.8, 'https://www.carrefour.com.ar/tofu-clasico-soyana');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (39, 'Carne picada vacuna común x1kg', 'Carne picada', 5800.00, 'CENA', false, true, false, 250, 18.0, 0.0, 20.0, 'https://www.carrefour.com.ar/carne-picada-vacuna');

-- NUEVOS ALIMENTOS CENA (IDs 47 a 49)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (47, 'Costillitas de cerdo frescas por kg', 'Costillas de cerdo', 5900.00, 'CENA', false, true, false, 240, 22.0, 0.0, 16.0, 'https://www.carrefour.com.ar/costillita-de-cerdo-kg');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (48, 'Mijo pelado nativo grano orgánico 500g', 'Mijo pelado', 2100.00, 'CENA', true, true, false, 378, 11.0, 73.0, 4.2, 'https://www.carrefour.com.ar/mijo-pelado-500g');

INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (49, 'Queso Port Salut La Serenísima 400g', 'Queso port salut', 4300.00, 'CENA', true, true, true, 280, 21.0, 1.5, 21.0, 'https://www.carrefour.com.ar/queso-port-salut-la-serenisima');


-- =============================================================================
-- ENTIDAD COMIDA (Platos armados completos)
-- =============================================================================

-- Tus 7 comidas originales
INSERT INTO Comida (id, nombre, tipo) VALUES (1, 'Café con leche y Tortilla de Avena', 'DESAYUNO');
INSERT INTO Comida (id, nombre, tipo) VALUES (2, 'Yogur con Avena y Queso Crema', 'DESAYUNO');
INSERT INTO Comida (id, nombre, tipo) VALUES (3, 'Tallarines con Atún al Pomodoro', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (4, 'Arroz con Atún Simple', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (5, 'Fideos de Arroz sin TACC con Tomate', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (6, 'Hamburguesa Swift con Lentejas', 'CENA');
INSERT INTO Comida (id, nombre, tipo) VALUES (7, 'Bowl Veggie de Lentejas y NotBurger', 'CENA');

-- Comidas agregadas anteriormente (IDs del 8 al 18)
INSERT INTO Comida (id, nombre, tipo) VALUES (8, 'Tostadas sin TACC con Mermelada y Café', 'DESAYUNO');
INSERT INTO Comida (id, nombre, tipo) VALUES (9, 'Omelette de Queso Crema y Café', 'DESAYUNO');
INSERT INTO Comida (id, nombre, tipo) VALUES (10, 'Bowl de Avena con Bebida de Almendras', 'DESAYUNO');
INSERT INTO Comida (id, nombre, tipo) VALUES (11, 'Suprema de Pollo con Arroz Blanco', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (12, 'Guiso Vegano de Garbanzos y Tomate', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (13, 'Tallarines a la Crema de Espinaca', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (14, 'Arroz con Carne Picada y Tomate', 'ALMUERZO');
INSERT INTO Comida (id, nombre, tipo) VALUES (15, 'Revuelto de Tofu y Espinaca', 'CENA');
INSERT INTO Comida (id, nombre, tipo) VALUES (16, 'Albóndigas con Puré de Tomate', 'CENA');
INSERT INTO Comida (id, nombre, tipo) VALUES (17, 'Ensalada Tibia de Lentejas y Espinaca', 'CENA');
INSERT INTO Comida (id, nombre, tipo) VALUES (18, 'Sopa de Fideos de Arroz y Pollo', 'CENA');

-- NUEVAS COMIDAS EXTRAS (IDs del 19 al 25)
INSERT INTO Comida (id, nombre, tipo) VALUES (19, 'Tostadas Comunes con Crema de Maní y Banana', 'DESAYUNO'); -- Vegano, Sin Lactosa, Con TACC
INSERT INTO Comida (id, nombre, tipo) VALUES (20, 'Filet de Merluza con Arroz y Palta', 'ALMUERZO');          -- Carnívoro (Pescado), Celíaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (21, 'Arroz con Arvejas y Huevo Frito', 'ALMUERZO');             -- Vegetariano, Celíaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (22, 'Costillitas de Cerdo con Puré Pomodoro', 'CENA');          -- Carnívoro, Celíaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (23, 'Guiso Multigrano de Mijo y Lentejas', 'CENA');             -- Vegano, Celíaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (24, 'Tortilla de Espinaca y Queso Port Salut', 'CENA');         -- Vegetariano, Celíaco, Con Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (25, 'Sándwich de Tofu, Palta y Tomate', 'ALMUERZO');            -- Vegano, Sin Lactosa, Con TACC

-- =============================================================================
-- DETALLE DE INGREDIENTES Y GRAMAJES (comida_items)
-- =============================================================================

-- Comidas 1 a 7 (Tus originales)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (1, 60.0, 1);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (1, 200.0, 2);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (1, 10.0, 4);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (2, 190.0, 3);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (2, 40.0, 1);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (2, 30.0, 17);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (3, 100.0, 6);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (3, 150.0, 7);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (3, 85.0, 9);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (4, 100.0, 8);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (4, 85.0, 9);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (5, 100.0, 21);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (5, 150.0, 7);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (6, 110.0, 11);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (6, 150.0, 15);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (7, 100.0, 23);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (7, 150.0, 15);

-- Comidas 8 a 18 (Agregadas anteriormente)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (8, 50.0, 30);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (8, 30.0, 32);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (8, 10.0, 4);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (9, 120.0, 33);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (9, 40.0, 17);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (9, 10.0, 4);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (10, 60.0, 1);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (10, 200.0, 31);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (11, 200.0, 34);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (11, 80.0, 8);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (12, 150.0, 36);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (12, 150.0, 7);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (13, 100.0, 6);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (13, 80.0, 37);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (13, 100.0, 35);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (14, 100.0, 8);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (14, 150.0, 39);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (14, 100.0, 7);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (15, 150.0, 38);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (15, 150.0, 35);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (16, 200.0, 39);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (16, 150.0, 7);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (16, 20.0, 6);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (17, 200.0, 15);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (17, 100.0, 35);

INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (18, 80.0, 21);
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (18, 150.0, 34);

-- --- DETALLE DE LAS NUEVAS COMIDAS EXTRAS ---

-- Comida 19: Tostadas Comunes con Crema de Maní y Banana
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (19, 60.0, 40);  -- Pan Bimbo (Con TACC)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (19, 30.0, 42);  -- Crema de maní
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (19, 100.0, 41); -- Banana

-- Comida 20: Filet de Merluza con Arroz y Palta
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (20, 200.0, 44); -- Merluza
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (20, 80.0, 8);   -- Arroz
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (20, 50.0, 45);  -- Palta

-- Comida 21: Arroz con Arvejas y Huevo Frito
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (21, 100.0, 8);  -- Arroz
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (21, 70.0, 46);  -- Arvejas
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (21, 60.0, 33);  -- Huevo

-- Comida 22: Costillitas de Cerdo con Puré Pomodoro
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (22, 250.0, 47); -- Cerdo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (22, 150.0, 7);  -- Puré tomate

-- Comida 23: Guiso Multigrano de Mijo y Lentejas
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (23, 80.0, 48);  -- Mijo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (23, 120.0, 15); -- Lentejas
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (23, 100.0, 7);  -- Puré tomate

-- Comida 24: Tortilla de Espinaca y Queso Port Salut
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (24, 150.0, 35); -- Espinaca
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (24, 60.0, 33);  -- Huevo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (24, 80.0, 49);  -- Queso Port Salut (Tiene Lactosa)

-- Comida 25: Sándwich de Tofu, Palta y Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (25, 60.0, 40);  -- Pan Bimbo (Con TACC)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (25, 100.0, 38); -- Tofu
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (25, 40.0, 45);  -- Palta
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (25, 50.0, 7);   -- Puré/Rodajas Tomate

