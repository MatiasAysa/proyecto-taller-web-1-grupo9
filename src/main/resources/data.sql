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
-- INSERTS DE ALIMENTOS BASE (Normalizados por Kilo/Litro)
-- =============================================================================

-- --- DESAYUNOS (Precios normalizados a 1000g o 1000ml) ---

-- Avena instantánea Carrefour 400g a $2100 -> Kilo = $5250.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (1, 'Avena instantánea Carrefour 400g', 'Avena instantánea', 5250.00, 'DESAYUNO', true, false, false, 389, 16.9, 66.3, 6.9, 'https://www.carrefour.com.ar/avena-instantanea-carrefour-400-g/p');

-- Leche entera Larga Vida Carrefour 1L -> Ya está por Litro = $1500.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (2, 'Leche entera Larga Vida Carrefour 1L', 'Leche entera', 1500.00, 'DESAYUNO', true, true, true, 58, 3.0, 4.7, 3.0, 'https://www.carrefour.com.ar/leche-entera-larga-vida-carrefour-1-l/p');

-- Yogur entero firme de Frutilla Ilolay 190g a $1200 -> Kilo = $6315.79
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (3, 'Yogur entero firme de Frutilla Ilolay 190g', 'Yogur entero de frutilla', 6315.79, 'DESAYUNO', true, true, true, 97, 3.1, 15.0, 2.7, 'https://www.carrefour.com.ar/yogur-entero-firme-ilolay-frutilla-190-g/p');

-- Café molido clásico La Virginia 250g a $4500 -> Kilo = $18000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (4, 'Café molido clásico La Virginia 250g', 'Café molido', 18000.00, 'DESAYUNO', true, true, false, 2, 0.2, 0.3, 0.0, 'https://www.carrefour.com.ar/cafe-molido-la-virginia-clasico-250-g/p');

-- Queso crema clásico Casancrem 290g a $3100 -> Kilo = $10689.66
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (17, 'Queso crema clásico Casancrem 290g', 'Queso crema', 10689.66, 'DESAYUNO', true, true, true, 194, 7.0, 4.5, 16.0, 'https://www.carrefour.com.ar/queso-crema-clasico-casancrem-290-g/p');

-- Pan lactal sin TACC San Jacinto 300g a $3800 -> Kilo = $12666.67
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (30, 'Pan lactal sin TACC San Jacinto 300g', 'Pan lactal sin TACC', 12666.67, 'DESAYUNO', true, true, false, 245, 4.2, 52.0, 2.5, 'https://www.carrefour.com.ar/pan-lactal-sin-tacc');

-- Bebida de Almendras Silk Original 1L -> Ya está por Litro = $2900.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (31, 'Bebida de Almendras Silk Original 1L','Bebida de almendras', 2900.00, 'DESAYUNO', true, true, false, 24, 0.6, 1.7, 1.5, 'https://www.carrefour.com.ar/bebida-de-almendras-silk');

-- Mermelada de Frutilla BC La Campagnola 390g a $2400 -> Kilo = $6153.85
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (32, 'Mermelada de Frutilla BC La Campagnola 390g','Mermelada de frutilla', 6153.85, 'DESAYUNO', true, true, false, 48, 0.2, 12.0, 0.0, 'https://www.carrefour.com.ar/mermelada-bc-frutilla');

-- Huevos colorados Carrefour x6 un (promedio 360g el pack) a $1800 -> Kilo = $5000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (33, 'Huevos colorados Carrefour x6 un.', 'Huevo', 5000.00, 'DESAYUNO', true, true, false, 143, 12.6, 0.6, 9.5, 'https://www.carrefour.com.ar/huevos-colorados-x6');

-- Pan lactal blanco tradicional Bimbo 550g a $3200 -> Kilo = $5818.18
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (40, 'Pan lactal blanco tradicional Bimbo 550g', 'Pan lactal blanco', 5818.18, 'DESAYUNO', true, false, false, 258, 8.5, 49.0, 3.2, 'https://www.carrefour.com.ar/pan-lactal-bimbo-blanco');

-- Banana Cavendish por kg -> Ya está por Kilo = $1900.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (41, 'Banana Cavendish por kg', 'Banana cavendish', 1900.00, 'DESAYUNO', true, true, false, 89, 1.1, 22.8, 0.3, 'https://www.carrefour.com.ar/banana-cavendish-kg');

-- Crema de maní s/azúcar Le-Fit 400g a $4100 -> Kilo = $10250.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (42, 'Crema de maní s/azúcar Le-Fit 400g', 'Crema de maní sin azúcar', 10250.00, 'DESAYUNO', true, true, false, 588, 24.0, 20.0, 50.0, 'https://www.carrefour.com.ar/crema-de-mani-le-fit');


-- --- ALMUERZOS (Precios normalizados a 1000g o 1000ml) ---

-- Fideos tallarines de sémola Carrefour 500g a $1300 -> Kilo = $2600.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (6, 'Fideos tallarines de sémola Carrefour 500g', 'Fideos tallarines de sémola', 2600.00, 'ALMUERZO', true, false, false, 356, 12.0, 73.0, 1.2, 'https://www.carrefour.com.ar/fideos-tallarines-carrefour-500-g/p');

-- Puré de tomate en caja Carrefour 520g a $980 -> Kilo = $1884.62
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (7, 'Puré de tomate en caja Carrefour 520g', 'Puré de tomate', 1884.62, 'ALMUERZO', true, true, false, 28, 1.4, 5.0, 0.2, 'https://www.carrefour.com.ar/pure-de-tomate-carrefour-520-g/p');

-- Arroz largo fino Carrefour 1kg -> Ya está por Kilo = $1900.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (8, 'Arroz largo fino Carrefour 1kg', 'Arroz largo fino', 1900.00, 'ALMUERZO', true, true, false, 358, 7.3, 79.0, 0.6, 'https://www.carrefour.com.ar/arroz-largo-fino-00000-carrefour-1-kg/p');

-- Atún al natural en trozos Carrefour 170g a $2900 -> Kilo = $17058.82
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (9, 'Atún al natural en trozos Carrefour 170g', 'Atún', 17058.82, 'ALMUERZO', false, true, false, 101, 23.0, 0.0, 0.9, 'https://www.carrefour.com.ar/atun-al-natural-en-trozos-carrefour-170-g/p');

-- Fideos de arroz s/TACC Matarazzo 500g a $2800 -> Kilo = $5600.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (21, 'Fideos de arroz s/TACC Matarazzo 500g', 'Fideos de arroz sin TACC', 5600.00, 'ALMUERZO', true, true, false, 348, 6.5, 78.0, 0.8, 'https://www.carrefour.com.ar/fideos-de-arroz-penne-rigate-matarazzo-sin-tacc-500-g/p');

-- Pechuga de pollo fresca Suprema x1kg -> Ya está por Kilo = $6500.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (34, 'Pechuga de pollo fresca Suprema x1kg', 'Pechuga de pollo', 6500.00, 'ALMUERZO', false, true, false, 165, 31.0, 0.0, 3.6, 'https://www.carrefour.com.ar/suprema-de-pollo-kg');

-- Espinaca congelada en cubos Carrefour 400g a $2200 -> Kilo = $5500.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (35, 'Espinaca congelada en cubos Carrefour 400g','Espinaca', 5500.00, 'ALMUERZO', true, true, false, 23, 2.9, 3.6, 0.4, 'https://www.carrefour.com.ar/espinaca-congelada-carrefour');

-- Garbanzos secos Carrefour 400g a $1600 -> Kilo = $4000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (36, 'Garbanzos secos Carrefour 400g', 'Garbanzos', 4000.00, 'ALMUERZO', true, true, false, 364, 19.0, 61.0, 6.0, 'https://www.carrefour.com.ar/garbanzos-secos-400g');

-- Filet de merluza fresco congelado x1kg -> Ya está por Kilo = $7200.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (44, 'Filet de merluza fresco congelado x1kg', 'Filet de merluza', 7200.00, 'ALMUERZO', false, true, false, 90, 19.0, 0.0, 1.2, 'https://www.carrefour.com.ar/filet-de-merluza-kg');

-- Palta Torres selección por unidad (peso estimado de pulpa/unidad útil ~150g) a $1300 -> Kilo = $8666.67
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (45, 'Palta Torres selección por unidad', 'Palta', 8666.67, 'ALMUERZO', true, true, false, 160, 2.0, 8.5, 14.7, 'https://www.carrefour.com.ar/palta-unidad');

-- Arvejas en lata Carrefour 300g (peso escurrido ~170g para ser exactos en alimento útil) a $850 -> Kilo = $5000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (46, 'Arvejas en lata Carrefour 300g', 'Arvejas', 5000.00, 'ALMUERZO', true, true, false, 69, 4.5, 12.0, 0.4, 'https://www.carrefour.com.ar/arvejas-en-lata-carrefour');


-- --- CENAS (Precios normalizados a 1000g o 1000ml) ---

-- Medallones de carne vacuna Swift 4 un. 220g a $3900 -> Kilo = $17727.27
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (11, 'Medallones de carne vacuna Swift 4 un.', 'Medallón de carne', 17727.27, 'CENA', false, false, false, 220, 15.0, 1.0, 17.0, 'https://www.carrefour.com.ar/hamburguesas-de-carne-vacuna-swift-clasicas-4-unidades-220-g/p');

-- Lentejas secas remojadas Carrefour 300g a $1400 -> Kilo = $4666.67
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (15, 'Lentejas secas remojadas Carrefour 300g', 'Lentejas', 4666.67, 'CENA', true, true, false, 116, 9.0, 20.0, 0.4, 'https://www.carrefour.com.ar/lentejas-secas-remojadas-carrefour-300-g/p');

-- Medallones de lentejas y quinoa NotCo 2 un. 200g a $3400 -> Kilo = $17000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (23, 'Medallones de lentejas y quinoa NotCo 2 un', 'Medallón de lentejas y quinoa', 17000.00, 'CENA', true, true, false, 180, 12.0, 15.0, 7.0, 'https://www.carrefour.com.ar/hamburguesa-not-burger-notco-2-unidades-200-g/p');

-- Crema de leche regular Milkaut 200g a $1950 -> Kilo = $9750.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (37, 'Crema de leche regular Milkaut 200g', 'Crema de leche', 9750.00, 'CENA', true, true, true, 345, 2.1, 3.0, 36.0, 'https://www.carrefour.com.ar/crema-de-leche-milkaut');

-- Tofu clásico Soyana 300g a $2800 -> Kilo = $9333.33
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (38, 'Tofu clásico Soyana 300g', 'Tofu', 9333.33, 'CENA', true, true, false, 76, 8.0, 1.9, 4.8, 'https://www.carrefour.com.ar/tofu-clasico-soyana');

-- Carne picada vacuna común x1kg -> Ya está por Kilo = $5800.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (39, 'Carne picada vacuna común x1kg', 'Carne picada', 5800.00, 'CENA', false, true, false, 250, 18.0, 0.0, 20.0, 'https://www.carrefour.com.ar/carne-picada-vacuna');

-- Costillitas de cerdo frescas por kg -> Ya está por Kilo = $5900.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (47, 'Costillitas de cerdo frescas por kg', 'Costillas de cerdo', 5900.00, 'CENA', false, true, false, 240, 22.0, 0.0, 16.0, 'https://www.carrefour.com.ar/costillita-de-cerdo-kg');

-- Mijo pelado nativo grano orgánico 500g a $2100 -> Kilo = $4200.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (48, 'Mijo pelado nativo grano orgánico 500g', 'Mijo pelado', 4200.00, 'CENA', true, true, false, 378, 11.0, 73.0, 4.2, 'https://www.carrefour.com.ar/mijo-pelado-500g');

-- Queso Port Salut La Serenísima 400g a $4300 -> Kilo = $10750.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (49, 'Queso Port Salut La Serenísima 400g', 'Queso port salut', 10750.00, 'CENA', true, true, true, 280, 21.0, 1.5, 21.0, 'https://www.carrefour.com.ar/queso-port-salut-la-serenisima');
-- =============================================================================
-- ENTIDAD COMIDA (Platos armados completos - Nombres Corregidos)
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

-- NUEVAS COMIDAS EXTRAS (IDs del 19 al 25 - Nombres Reestructurados)
INSERT INTO Comida (id, nombre, tipo) VALUES (19, 'Tostadas Comunes con Crema de Maní y Banana', 'DESAYUNO'); -- Vegano, Sin Lactosa, Con TACC
INSERT INTO Comida (id, nombre, tipo) VALUES (20, 'Filet de Merluza con Arroz y Palta', 'ALMUERZO');          -- Carnívoro (Pescado), Celíaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (21, 'Arroz con Arvejas y Huevo Frito', 'ALMUERZO');             -- Vegetariano, Celíaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (22, 'Costillitas de Cerdo al Pomodoro', 'CENA');                -- Carnívoro, Celíaco, Sin Lactosa (Corregido)
INSERT INTO Comida (id, nombre, tipo) VALUES (23, 'Guiso Multigrano de Mijo y Lentejas', 'CENA');             -- Vegano, Celíaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (24, 'Tortilla de Espinaca y Queso Port Salut', 'CENA');         -- Vegetariano, Celíaco, Con Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (25, 'Sándwich de Tofu, Palta y Aderezo de Tomate', 'ALMUERZO');  -- Vegano, Sin Lactosa, Con TACC (Corregido)

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

-- Comida 22: Costillitas de Cerdo al Pomodoro
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

-- Comida 25: Sándwich de Tofu, Palta y Aderezo de Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (25, 60.0, 40);  -- Pan Bimbo (Con TACC)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (25, 100.0, 38); -- Tofu
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (25, 40.0, 45);  -- Palta
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (25, 50.0, 7);   -- Puré/Aderezo Tomate

-- =============================================================================
-- NUEVOS ALIMENTOS BASE (IDs del 50 en adelante - Precios normalizados por Kg/L)
-- =============================================================================

-- --- DESAYUNOS ---

-- Yogur descremado natural Carrefour 190g a $1100 -> Kilo = $5789.47
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (50, 'Yogur descremado natural Carrefour 190g', 'Yogur descremado', 5789.47, 'DESAYUNO', true, true, true, 47, 4.2, 6.0, 0.1, 'https://www.carrefour.com.ar/yogur-descremado-natural-carrefour');

-- Granola con almendras y nueces Carrefour 300g a $2900 -> Kilo = $9666.67
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (51, 'Granola con almendras y nueces Carrefour 300g', 'Granola', 9666.67, 'DESAYUNO', true, false, false, 413, 10.0, 62.0, 13.0, 'https://www.carrefour.com.ar/granola-almendras-nueces-300g');

-- Semillas de Chía Carrefour 150g a $1800 -> Kilo = $12000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (52, 'Semillas de Chía Carrefour 150g', 'Semillas de chía', 12000.00, 'DESAYUNO', true, true, false, 486, 16.5, 42.1, 30.7, 'https://www.carrefour.com.ar/semillas-chia-150g');


-- --- ALMUERZOS ---

-- Carne vacuna Nalga por kg (Para milanesas o salteados) -> Ya está por Kilo = $8200.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (53, 'Carne de Nalga vacuna fresca por kg', 'Carne de nalga', 8200.00, 'ALMUERZO', false, true, false, 128, 21.5, 0.0, 4.0, 'https://www.carrefour.com.ar/nalga-vacuna-kg');

-- Rebozador sin TACC Kapac 300g a $2100 -> Kilo = $7000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (54, 'Rebozador sin TACC Kapac 300g', 'Rebozador sin TACC', 7000.00, 'ALMUERZO', true, true, false, 350, 6.0, 78.0, 1.0, 'https://www.carrefour.com.ar/rebozador-sin-tacc-kapac');

-- Tomate redondo primicia por kg -> Ya está por Kilo = $2300.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (55, 'Tomate redondo por kg', 'Tomate fresco', 2300.00, 'ALMUERZO', true, true, false, 18, 0.9, 3.9, 0.2, 'https://www.carrefour.com.ar/tomate-redondo-kg');

-- Choclo amarillo en granos Carrefour 300g a $1100 -> Kilo = $3666.67
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (56, 'Choclo amarillo en grano Carrefour 300g', 'Choclo en granos', 3666.67, 'ALMUERZO', true, true, false, 86, 3.2, 19.0, 1.2, 'https://www.carrefour.com.ar/choclo-grano-300g');


-- --- CENAS ---

-- Calabaza Anco por kg -> Ya está por Kilo = $1200.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (57, 'Calabaza Anco fresca por kg', 'Calabaza', 1200.00, 'CENA', true, true, false, 45, 1.0, 11.0, 0.1, 'https://www.carrefour.com.ar/calabaza-anco-kg');

-- Lentejas Turcas Rojas secas 500g a $2400 -> Kilo = $4800.00 (Ideales para sopas/guisados rápidos)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (58, 'Lentejas Turcas Rojas 500g', 'Lentejas rojas', 4800.00, 'CENA', true, true, false, 340, 24.0, 58.0, 1.5, 'https://www.carrefour.com.ar/lentejas-rojas-500g');

-- Bondiola de cerdo fresca por kg -> Ya está por Kilo = $7500.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (59, 'Bondiola de cerdo fresca por kg', 'Bondiola de cerdo', 7500.00, 'CENA', false, true, false, 200, 19.0, 0.0, 14.0, 'https://www.carrefour.com.ar/bondiola-cerdo-kg');

-- Brócoli congelado Carrefour 400g a $2600 -> Kilo = $6500.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (60, 'Brócoli congelado en flores Carrefour 400g', 'Brócoli', 6500.00, 'CENA', true, true, false, 34, 2.8, 7.0, 0.4, 'https://www.carrefour.com.ar/brocoli-congelado-400g');

-- Queso Tybo en fetas Carrefour 200g a $2200 -> Kilo = $11000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (61, 'Queso Tybo en fetas Carrefour 200g', 'Queso tybo', 11000.00, 'CENA', true, true, true, 340, 25.0, 1.0, 26.0, 'https://www.carrefour.com.ar/queso-tybo-fetas-200g');


-- =============================================================================
-- ENTIDAD COMIDA (Nuevas comidas agregadas: IDs 26 al 35)
-- =============================================================================
INSERT INTO Comida (id, nombre, tipo) VALUES (26, 'Yogur Natural con Granola y Semillas de Chía', 'DESAYUNO'); -- Vegetariano, Alto en fibra
INSERT INTO Comida (id, nombre, tipo) VALUES (27, 'Tostadas Celíacas con Huevo Revuelto', 'DESAYUNO');          -- Celíaco, Proteico
INSERT INTO Comida (id, nombre, tipo) VALUES (28, 'Suprema de Pollo con Puré de Calabaza', 'ALMUERZO');         -- Celíaco, Clásico Hogareño
INSERT INTO Comida (id, nombre, tipo) VALUES (29, 'Salteado de Cerdo, Fideos de Arroz y Brócoli', 'ALMUERZO'); -- Celíaco, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (30, 'Ensalada Fresca de Tomate, Choclo, Arvejas y Palta', 'ALMUERZO'); -- Vegano, Fresco y Rápido
INSERT INTO Comida (id, nombre, tipo) VALUES (31, 'Calabaza Rellena con Queso Port Salut y Espinaca', 'CENA'); -- Vegetariano, Celíaco
INSERT INTO Comida (id, nombre, tipo) VALUES (32, 'Milanesa de Nalga al Horno con Ensalada de Tomate', 'CENA'); -- Tradicional Argentino
INSERT INTO Comida (id, nombre, tipo) VALUES (33, 'Tofu Salteado con Brócoli y Arroz Largo Fino', 'CENA');     -- Vegano, Fit
INSERT INTO Comida (id, nombre, tipo) VALUES (34, 'Omelette de Espinaca, Choclo y Queso Tybo', 'CENA');       -- Vegetariano, Celíaco, Proteico
INSERT INTO Comida (id, nombre, tipo) VALUES (35, 'Crema Express de Lentejas Rojas y Puré de Tomate', 'CENA');  -- Vegano, Reconfortante


-- =============================================================================
-- DETALLE DE INGREDIENTES Y GRAMAJES (comida_items para IDs 26 al 35)
-- =============================================================================

-- Comida 26: Yogur Natural con Granola y Semillas de Chía
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (26, 190.0, 50); -- Yogur natural descremado
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (26, 40.0, 51);  -- Granola
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (26, 15.0, 52);  -- Semillas de chía

-- Comida 27: Tostadas Celíacas con Huevo Revuelto
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (27, 60.0, 30);  -- Pan lactal sin TACC
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (27, 120.0, 33); -- 2 Huevos medianos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (27, 10.0, 4);   -- Café de acompañamiento

-- Comida 28: Suprema de Pollo con Puré de Calabaza
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (28, 200.0, 34); -- Suprema de Pollo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (28, 250.0, 57); -- Calabaza Anco (puré)

-- Comida 29: Salteado de Cerdo, Fideos de Arroz y Brócoli
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (29, 150.0, 59); -- Bondiola de cerdo en tiras
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (29, 80.0, 21);  -- Fideos de arroz sin TACC
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (29, 100.0, 60); -- Brócoli

-- Comida 30: Ensalada Fresca de Tomate, Choclo, Arvejas y Palta
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (30, 150.0, 55); -- Tomate redondo fresco
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (30, 80.0, 56);  -- Choclo amarillo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (30, 70.0, 46);  -- Arvejas
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (30, 60.0, 45);  -- Palta fresca

-- Comida 31: Calabaza Rellena con Queso Port Salut y Espinaca
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (31, 300.0, 57); -- Mitad de calabaza
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (31, 100.0, 35); -- Espinaca cocida
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (31, 80.0, 49);  -- Queso Port Salut (Derrite espectacular)

-- Comida 32: Milanesa de Nalga al Horno con Ensalada de Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (32, 150.0, 53); -- Carne de nalga
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (32, 40.0, 54);  -- Rebozador sin TACC (La hace apta celíacos)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (32, 120.0, 55); -- Tomate fresco
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (32, 60.0, 33);  -- Huevo (para ligar la milanesa)

-- Comida 33: Tofu Salteado con Brócoli y Arroz Largo Fino
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (33, 150.0, 38); -- Tofu clásico
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (33, 100.0, 60); -- Brócoli en arbolitos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (33, 80.0, 8);   -- Arroz largo fino

-- Comida 34: Omelette de Espinaca, Choclo y Queso Tybo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (34, 120.0, 33); -- 2 Huevos medianos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (34, 80.0, 35);  -- Espinaca
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (34, 50.0, 56);  -- Choclo en grano
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (34, 60.0, 61);  -- Queso Tybo en fetas

-- Comida 35: Crema Express de Lentejas Rojas y Puré de Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (35, 120.0, 58); -- Lentejas Rojas (se cocinan súper rápido)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (35, 150.0, 7);  -- Puré de tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (35, 100.0, 57); -- Calabaza (para espesar la crema)

-- =============================================================================
-- NUEVOS ALIMENTOS BASE (IDs del 64 al 78 - Precios normalizados por Kg/L)
-- =============================================================================

-- --- DESAYUNOS / MERIENDAS ---

-- Bebida de Almendras Silk Original 1L a $3500 -> Litro = $3500.00 (Apto Vegano / Sin Lactosa)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (64, 'Bebida de Almendras Silk Original 1L', 'Leche de almendras', 3500.00, 'DESAYUNO', true, true, false, 24, 0.5, 3.0, 1.1, 'https://www.carrefour.com.ar/bebida-almendras-silk-1l');

-- Copos de Maíz sin azúcar Carrefour 500g a $2200 -> Kilo = $4400.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (65, 'Copos de Maíz sin azúcar Carrefour 500g', 'Copos de maíz', 4400.00, 'DESAYUNO', true, false, false, 370, 7.5, 84.0, 0.5, 'https://www.carrefour.com.ar/copos-maiz-sin-azucar-500g');

-- Mix de Frutos Secos (Almendras, castañas, maní) Carrefour 150g a $2500 -> Kilo = $16666.67
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (66, 'Mix de Frutos Secos clásico Carrefour 150g', 'Frutos secos mix', 16666.67, 'DESAYUNO', true, true, false, 580, 15.0, 30.0, 48.0, 'https://www.carrefour.com.ar/mix-frutos-secos-150g');


-- --- ALMUERZOS ---

-- Atún desmenuzado al natural Carrefour 170g a $1900 -> Kilo = $11176.47
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (67, 'Atun desmenuzado al natural Carrefour 170g', 'Atún al natural', 11176.47, 'ALMUERZO', false, true, false, 116, 26.0, 0.0, 1.0, 'https://www.carrefour.com.ar/atun-desmenuzado-natural-170g');

-- Garbanzos secos Carrefour 500g a $1800 -> Kilo = $3600.00 (Excelente fuente de proteína vegetal)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (68, 'Garbanzos secos Carrefour 500g', 'Garbanzos secos', 3600.00, 'ALMUERZO', true, true, false, 364, 19.0, 61.0, 6.0, 'https://www.carrefour.com.ar/garbanzos-secos-500g');

-- Fideos de Trigo Tallarines Lucchetti 500g a $1300 -> Kilo = $2600.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (69, 'Fideos Tallarines Lucchetti 500g', 'Fideos tallarines', 2600.00, 'ALMUERZO', true, false, false, 357, 12.0, 74.0, 1.5, 'https://www.carrefour.com.ar/fideos-tallarines-lucchetti-500g');

-- Supremos de Merluza Congelados (sin espinas) por kg -> Kilo = $8500.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (70, 'Suprema de merluza congelada por kg', 'Filet de merluza', 8500.00, 'ALMUERZO', false, true, false, 90, 19.0, 0.0, 1.2, 'https://www.carrefour.com.ar/filet-merluza-congelado-kg');


-- --- CENAS ---

-- Hamburguesa Vegana de Lentejas y mijo Vegetalex x4 u (320g) a $2200 -> Kilo = $6875.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (71, 'Hamburguesa de Lentejas Vegetalex 320g', 'Hamburguesa de lentejas', 6875.00, 'CENA', true, false, false, 160, 9.5, 18.0, 5.0, 'https://www.carrefour.com.ar/hamburguesa-lentejas-vegetalex');

-- Pechuga de Pollo trozada congelada Sadia por kg -> Kilo = $5900.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (72, 'Pechuga de pollo deshuesada congelada kg', 'Pechuga de pollo', 5900.00, 'CENA', false, true, false, 165, 31.0, 0.0, 3.6, 'https://www.carrefour.com.ar/pechuga-pollo-congelada-kg');

-- Cebolla blanca por kg -> Ya está por Kilo = $1100.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (73, 'Cebolla redonda fresca por kg', 'Cebolla', 1100.00, 'CENA', true, true, false, 40, 1.1, 9.3, 0.1, 'https://www.carrefour.com.ar/cebolla-kg');

-- Zanahoria fresca por kg -> Ya está por Kilo = $1300.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (74, 'Zanahoria fresca por kg', 'Zanahoria', 1300.00, 'CENA', true, true, false, 41, 0.9, 9.6, 0.2, 'https://www.carrefour.com.ar/zanahoria-kg');

-- Zapallito verde redondo por kg -> Kilo = $1800.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (75, 'Zapallito redondo verde por kg', 'Zapallitos', 1800.00, 'CENA', true, true, false, 17, 1.2, 3.1, 0.2, 'https://www.carrefour.com.ar/zapallito-redondo-kg');

-- Huevo blanco de campo (1 docena = 720g aprox) a $2400 -> Kilo = $3333.33
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (76, 'Huevos frescos de gallina docena', 'Huevo entero', 3333.33, 'CENA', true, true, false, 155, 13.0, 1.1, 11.0, 'https://www.carrefour.com.ar/huevos-blancos-docena');

-- Ricota Magra descremada San Ignacio 300g a $1800 -> Kilo = $6000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (77, 'Ricota Magra San Ignacio 300g', 'Ricota magra', 6000.00, 'CENA', true, true, true, 103, 11.5, 3.2, 5.0, 'https://www.carrefour.com.ar/ricota-magra-san-ignacio-300g');

-- Avena Instantánea Quaker 400g a $2100 -> Kilo = $5250.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (78, 'Avena Instantanea Quaker 400g', 'Avena instantánea', 5250.00, 'DESAYUNO', true, false, false, 389, 16.9, 66.3, 6.9, 'https://www.carrefour.com.ar/avena-instantanea-quaker-400g');


-- =============================================================================
-- ENTIDAD COMIDA (Nuevas comidas agregadas: IDs 36 al 45)
-- =============================================================================
INSERT INTO Comida (id, nombre, tipo) VALUES (36, 'Porridge de Avena y Bebida de Almendras con Maní', 'DESAYUNO'); -- Vegano, Fibra, Sin Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (37, 'Bowl de Yogur Natural, Copos de Maíz y Frutos Secos', 'DESAYUNO'); -- Clásico energético
INSERT INTO Comida (id, nombre, tipo) VALUES (38, 'Ensalada Rápida de Atún, Arroz y Huevo Duro', 'ALMUERZO');        -- Proteico Express, Sin TACC/Lactosa
INSERT INTO Comida (id, nombre, tipo) VALUES (39, 'Hummus de Garbanzos con Tostadas Celíacas', 'ALMUERZO');         -- Vegano, Mediterráneo
INSERT INTO Comida (id, nombre, tipo) VALUES (40, 'Tallarines con Puré de Tomate y Carne de Nalga', 'ALMUERZO');    -- Clásico dominguero
INSERT INTO Comida (id, nombre, tipo) VALUES (41, 'Filet de Merluza con Ensalada de Tomate y Zanahoria', 'ALMUERZO'); -- Liviano y Fresco
INSERT INTO Comida (id, nombre, tipo) VALUES (42, 'Wok de Pollo, Brócoli, Cebolla y Zanahoria', 'CENA');            -- Low-carb, Proteico, Sin Gluten
INSERT INTO Comida (id, nombre, tipo) VALUES (43, 'Revuelto de Zapallitos con Huevo y Queso Tybo', 'CENA');         -- Vegetariano, Clásico Express
INSERT INTO Comida (id, nombre, tipo) VALUES (44, 'Hamburguesa de Lentejas con Puré de Calabaza', 'CENA');          -- Vegetariano, Confort Food
INSERT INTO Comida (id, nombre, tipo) VALUES (45, 'Zapallitos Rellenos con Ricota Magra y Espinaca', 'CENA');       -- Vegetariano, Muy Liviano


-- =============================================================================
-- DETALLE DE INGREDIENTES Y GRAMAJES (comida_items para IDs 36 al 45)
-- =============================================================================

-- Comida 36: Porridge de Avena y Bebida de Almendras con Maní
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (36, 50.0, 78);  -- Avena instantánea
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (36, 200.0, 64); -- Leche de almendras
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (36, 20.0, 42);  -- Crema de maní

-- Comida 37: Bowl de Yogur Natural, Copos de Maíz y Frutos Secos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (37, 190.0, 50); -- Yogur natural descremado
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (37, 35.0, 65);  -- Copos de maíz
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (37, 25.0, 66);  -- Mix de frutos secos

-- Comida 38: Ensalada Rápida de Atún, Arroz y Huevo Duro
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (38, 120.0, 67); -- Atún al natural
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (38, 80.0, 8);   -- Arroz largo fino (ya cocido)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (38, 60.0, 76);  -- 1 Huevo cocido

-- Comida 39: Hummus de Garbanzos con Tostadas Celíacas
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (39, 150.0, 68); -- Garbanzos secos (cocidos y procesados)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (39, 60.0, 30);  -- Tostadas sin TACC

-- Comida 40: Tallarines con Puré de Tomate y Carne de Nalga
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (40, 100.0, 69); -- Tallarines de trigo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (40, 120.0, 53); -- Carne de nalga picada/cortada
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (40, 150.0, 7);  -- Puré de tomate

-- Comida 41: Filet de Merluza con Ensalada de Tomate y Zanahoria
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (41, 180.0, 70); -- Filet de merluza
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (41, 100.0, 55); -- Tomate redondo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (41, 80.0, 74);  -- Zanahoria rallada

-- Comida 42: Wok de Pollo, Brócoli, Cebolla y Zanahoria
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (42, 180.0, 72); -- Pechuga de pollo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (42, 100.0, 60); -- Brócoli
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (42, 60.0, 73);  -- Cebolla
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (42, 60.0, 74);  -- Zanahoria

-- Comida 43: Revuelto de Zapallitos con Huevo y Queso Tybo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (43, 200.0, 75); -- Zapallitos verdes
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (43, 120.0, 76); -- 2 Huevos revueltos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (43, 40.0, 61);  -- Queso Tybo en tiras

-- Comida 44: Hamburguesa de Lentejas con Puré de Calabaza
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (44, 160.0, 71); -- Hamburguesas de lenteja (2 unidades)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (44, 250.0, 57); -- Calabaza en puré

-- Comida 45: Zapallitos Rellenos con Ricota Magra y Espinaca
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (45, 250.0, 75); -- Zapallitos ahuecados
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (45, 100.0, 77); -- Ricota magra
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (45, 80.0, 35);  -- Espinaca cocida

-- =============================================================================
-- NUEVOS ALIMENTOS BASE (IDs del 79 al 91 - Precios normalizados por Kg/L)
-- =============================================================================

-- --- DESAYUNOS / MERIENDAS ---

-- Galletas de Arroz s/sal Carrefour 100g a $900 -> Kilo = $9000.00 (Ideales para Celíacos / Veganos)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (79, 'Galletas de Arroz s/sal Carrefour 100g', 'Galletas de arroz', 9000.00, 'DESAYUNO', true, true, false, 387, 8.0, 82.0, 3.0, 'https://www.carrefour.com.ar/galletas-arroz-sin-sal-100g');

-- Queso Untable Descremado Milkaut 290g a $2900 -> Kilo = $10000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (80, 'Queso untable descremado Milkaut 290g', 'Queso untable untable', 10000.00, 'DESAYUNO', true, true, true, 94, 9.0, 4.0, 4.5, 'https://www.carrefour.com.ar/queso-untable-descremado-milkaut-290g');

-- Manzana Red Delicious por kg -> Ya está por Kilo = $1950.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (81, 'Manzana Red Delicious fresca por kg', 'Manzana roja', 1950.00, 'DESAYUNO', true, true, false, 52, 0.3, 14.0, 0.2, 'https://www.carrefour.com.ar/manzana-red-kg');


-- --- ALMUERZOS ---

-- Lentejones secos seleccionados Carrefour 400g a $1500 -> Kilo = $3750.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (82, 'Lentejones secos seleccionados 400g', 'Lentejones secos', 3750.00, 'ALMUERZO', true, true, false, 352, 24.5, 60.0, 1.0, 'https://www.carrefour.com.ar/lentejones-secos-carrefour-400g');

-- Caballa al Natural en conserva Carrefour 380g a $3400 -> Kilo = $8947.37 (Gran fuente de Omega 3)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (83, 'Caballa al Natural Carrefour 380g', 'Caballa en conserva', 8947.37, 'ALMUERZO', false, true, false, 153, 21.0, 0.0, 7.7, 'https://www.carrefour.com.ar/caballa-al-natural-carrefour-380g');

-- Polenta instantánea Presto Pronta 500g a $1250 -> Kilo = $2500.00 (Naturalmente sin TACC)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (84, 'Polenta instantanea Presto Pronta 500g', 'Polenta harina de maíz', 2500.00, 'ALMUERZO', true, true, false, 345, 7.5, 76.0, 1.0, 'https://www.carrefour.com.ar/polenta-presto-pronta-500g');

-- Bife de cuadril vacuno fresco por kg -> Ya está por Kilo = $8900.00 (Corte súper magro)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (85, 'Bife de cuadril vacuno fresco por kg', 'Bife de cuadril', 8900.00, 'ALMUERZO', false, true, false, 142, 22.0, 0.0, 6.0, 'https://www.carrefour.com.ar/cuadril-vacuno-kg');


-- --- CENAS ---

-- Filet de merluza congelado al vacío por kg -> Ya está por Kilo = $7800.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (86, 'Filet de merluza fresca congelada por kg', 'Filet de merluza', 7800.00, 'CENA', false, true, false, 90, 19.0, 0.0, 1.2, 'https://www.carrefour.com.ar/filet-merluza-fresco-kg');

-- Puré de Papas instantáneo Maggi 125g a $1400 -> Kilo = $11200.00 (Rendimiento por peso seco)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (87, 'Pure de papas instantaneo Maggi 125g', 'Puré de papas', 11200.00, 'CENA', true, true, false, 355, 8.0, 77.0, 0.8, 'https://www.carrefour.com.ar/pure-papas-maggi-125g');

-- Lentejas de agua/Garbanzos en conserva Noel 300g a $1200 -> Kilo = $4000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (88, 'Garbanzos en conserva listos Noel 300g', 'Garbanzos en lata', 4000.00, 'CENA', true, true, false, 119, 7.0, 19.0, 2.0, 'https://www.carrefour.com.ar/garbanzos-conserva-noel-300g');

-- Semillas de Girasol peladas Carrefour 150g a $1500 -> Kilo = $10000.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (89, 'Semillas de Girasol peladas Carrefour 150g', 'Semillas de girasol', 10000.00, 'CENA', true, true, false, 584, 20.8, 20.0, 51.4, 'https://www.carrefour.com.ar/semillas-girasol-150g');

-- Harina de Garbanzo sin TACC Dimax 500g a $2200 -> Kilo = $4400.00 (Para fainá o tortillas veganas)
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (90, 'Harina de garbanzo sin TACC Dimax 500g', 'Harina de garbanzo', 4400.00, 'CENA', true, true, false, 387, 22.0, 58.0, 6.7, 'https://www.carrefour.com.ar/harina-garbanzo-dimax-500g');

-- Queso Mozzarella trozo Carrefour 500g a $4800 -> Kilo = $9600.00
INSERT INTO Alimento (id, nombre, nombreGenerico, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (91, 'Queso Mozzarella trozo Carrefour 500g', 'Queso mozzarella', 9600.00, 'CENA', true, true, true, 300, 22.0, 2.0, 22.0, 'https://www.carrefour.com.ar/queso-mozzarella-carrefour-500g');


-- =============================================================================
-- ENTIDAD COMIDA (Nuevas comidas agregadas: IDs 46 al 55)
-- =============================================================================
INSERT INTO Comida (id, nombre, tipo) VALUES (46, 'Tostadas de Arroz con Queso Untable y Manzana', 'DESAYUNO'); -- Celíaco, Liviano y Fresco
INSERT INTO Comida (id, nombre, tipo) VALUES (47, 'Porridge Energetico de Avena con Semillas de Girasol', 'DESAYUNO'); -- Vegano, Gran Aporte de Grasas Saludables
INSERT INTO Comida (id, nombre, tipo) VALUES (48, 'Bife de Cuadril con Ensalada de Choclo y Tomate', 'ALMUERZO');  -- Carnívoro Proteico, Sin TACC
INSERT INTO Comida (id, nombre, tipo) VALUES (49, 'Guiso Clasico de Lentejones con Pure de Tomate', 'ALMUERZO');  -- Vegano, Tradicional
INSERT INTO Comida (id, nombre, tipo) VALUES (50, 'Polenta Cremosa con Caballa al Natural', 'ALMUERZO');          -- Económico, Alto Omega 3, Sin TACC
INSERT INTO Comida (id, nombre, tipo) VALUES (51, 'Filet de Merluza con Pure de Papas Express', 'CENA');       -- Clásico Liviano, Familiar
INSERT INTO Comida (id, nombre, tipo) VALUES (52, 'Fainá Casera de Garbanzos con Ensalada de Tomate', 'CENA');   -- Vegano, Celíaco, Alternativa a la Pizza
INSERT INTO Comida (id, nombre, tipo) VALUES (53, 'Guiso Rapido de Garbanzos, Calabaza y Espinaca', 'CENA');     -- Vegano Express, Muy Nutritivo
INSERT INTO Comida (id, nombre, tipo) VALUES (54, 'Omelette de Mozzarella y Tomate con Semillas', 'CENA');       -- Vegetariano, Cetogénico
INSERT INTO Comida (id, nombre, tipo) VALUES (55, 'Revuelto Fit de Tofu, Cebolla y Choclo', 'CENA');             -- Vegano, Proteico Rápido


-- =============================================================================
-- DETALLE DE INGREDIENTES Y GRAMAJES (comida_items para IDs 46 al 55)
-- =============================================================================

-- Comida 46: Tostadas de Arroz con Queso Untable y Manzana
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (46, 40.0, 79);  -- Galletas de arroz (Aprox 4-5 galletas)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (46, 40.0, 80);  -- Queso untable descremado
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (46, 120.0, 81); -- Manzana roja en rodajas

-- Comida 47: Porridge Energetico de Avena con Semillas de Girasol
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (47, 60.0, 78);  -- Avena instantánea
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (47, 200.0, 64); -- Leche de almendras
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (47, 20.0, 89);  -- Semillas de girasol

-- Comida 48: Bife de Cuadril con Ensalada de Choclo y Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (48, 200.0, 85); -- Bife de cuadril
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (48, 80.0, 56);  -- Choclo amarillo en granos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (48, 100.0, 55); -- Tomate redondo fresco

-- Comida 49: Guiso Clasico de Lentejones con Pure de Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (49, 120.0, 82); -- Lentejones secos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (49, 150.0, 7);  -- Puré de tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (49, 50.0, 73);  -- Cebolla picada

-- Comida 50: Polenta Cremosa con Caballa al Natural
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (50, 80.0, 84);  -- Harina de maíz (Polenta seca)
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (50, 120.0, 83); -- Caballa desmenuzada al natural
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (50, 50.0, 7);   -- Toque de puré de tomate

-- Comida 51: Filet de Merluza con Pure de Papas Express
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (51, 180.0, 86); -- Filet de merluza
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (51, 60.0, 87);  -- Puré de papas deshidratado (rinde x3 en agua)

-- Comida 52: Fainá Casera de Garbanzos con Ensalada de Tomate
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (52, 100.0, 90); -- Harina de garbanzo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (52, 150.0, 55); -- Tomate redondo fresco

-- Comida 53: Guiso Rapido de Garbanzos, Calabaza y Espinaca
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (53, 150.0, 88); -- Garbanzos en conserva escurridos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (53, 150.0, 57); -- Calabaza en cubos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (53, 80.0, 35);  -- Espinaca

-- Comida 54: Omelette de Mozzarella y Tomate con Semillas
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (54, 120.0, 76); -- 2 Huevos medianos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (54, 50.0, 91);  -- Queso Mozzarella
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (54, 80.0, 55);  -- Tomate fresco en cubos
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (54, 15.0, 89);  -- Semillas de girasol

-- Comida 55: Revuelto Fit de Tofu, Cebolla y Choclo
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (55, 150.0, 38); -- Tofu clásico desgranado
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (55, 60.0, 73);  -- Cebolla picada
INSERT INTO comida_items (comida_id, cantidadGramos, alimento_id) VALUES (55, 80.0, 56);  -- Choclo amarillo
