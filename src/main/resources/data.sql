
DELETE FROM Alimento;
DELETE FROM RestriccionAlimentaria;
DELETE FROM Usuario;

INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

/*
LEER TODO SE PLANTEO DE LA SIGUIENTE FORMA , EL PRECIO ES POR KILO,OSEA LA AVENA ESTA 2200 EL KILO,AHORA TODO LO QUE ES
NUTRICIONAL ESTA POR CADA 100 GRAMOS DEL ALIMENTO,OSEA LA AVENA TIENE 389 CALORIAS POR CADA 100 GRAMOS
*/
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(1,'Avena',3200,389,16.9,66.3,6.9);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(2,'Banana',1500,89,1.1,22.8,0.3);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(3,'Arroz Blanco',1800,365,7.1,80.0,0.7);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(4,'Papa',1200,77,2.0,17.0,0.1);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(5,'Tomate',2500,18,0.9,3.9,0.2);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(6,'Pechuga de Pollo',7000,165,31.0,0.0,3.6);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(7,'Bola de Lomo',19000,170,29.0,0.0,6.0);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(8,'Huevo Entero',3055 ,155,13.0,1.1,11.0);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(9,'Leche Descremada',1300,34,3.4,5.0,0.1);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(10,'Yogur Griego Natural',6000,97,10.0,3.6,5.0);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(11,'Pan Integral',3500,247,13.0,41.0,4.2);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(12,'Lentejas',2700,352,25.0,60.0,1.1);


-->//VERDURAS----------------------------
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(13,'Lechuga',1800,15,1.4,2.9,0.2);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(14,'Zanahoria',1500,41,0.9,10.0,0.2);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(15,'Cebolla',1200,40,1.1,9.3,0.1);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(16,'Morron Rojo',3500,31,1.0,6.0,0.3);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(17,'Zapallito',1700,17,1.2,3.1,0.3);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(18,'Brocoli',4500,34,2.8,6.6,0.4);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(19,'Espinaca',3000,23,2.9,3.6,0.4);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(20,'Pepino',2000,15,0.7,3.6,0.1);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(21,'Remolacha',1800,43,1.6,10.0,0.2);

INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(22,'Berenjena',2500,25,1.0,6.0,0.2);

-->//FRUTAS ------------
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(23,'Manzana',1800,52,0.3,14.0,0.2);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(24,'Pera',2200,57,0.4,15.0,0.1);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(25,'Naranja',1700,47,0.9,12.0,0.1);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(26,'Mandarina',1800,53,0.8,13.0,0.3);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(27,'Frutilla',5000,32,0.7,7.7,0.3);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(28,'Kiwi',6500,61,1.1,15.0,0.5);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(29,'Durazno',2500,39,0.9,10.0,0.3);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(30,'Ciruela',3500,46,0.7,11.0,0.3);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(31,'Anana',3000,50,0.5,13.0,0.1);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(32,'Melon',1800,34,0.8,8.0,0.2);

-->//LEGUMBRES -------------
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(33,'Garbanzos',3200,364,19.0,61.0,6.0);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(34,'Porotos Negros',2800,339,21.0,63.0,0.9);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(35,'Porotos Blancos',2600,333,21.0,60.0,1.5);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(36,'Arvejas Secas',2500,341,24.0,60.0,1.2);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(37,'Soja',3500,446,36.0,30.0,20.0);

-->//LIBRES DE GLUTEN (CELIACOS)
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(38,'Quinoa',8500,368,14.0,64.0,6.0);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(39,'Polenta',1800,360,8.0,79.0,1.5);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(40,'Batata',1600,86,1.6,20.0,0.1);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(41,'Mandioca',2200,160,1.4,38.0,0.3);
INSERT INTO Alimento (id,nombre,precioPorKilo,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(42,'Amaranto',9500,371,14.0,65.0,7.0);

-->//COMIDA 1 ------------
INSERT INTO Comida (id, nombre, tipo, celiaco, diabetico, intoleranciaLactosa, vegetariano, vegano)
VALUES (1, 'Avena con Banana y Leche', 'DESAYUNO', false, true, false, true, false);

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(1,100,1,1); -- avena

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(2,100,2,1); -- banana

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(3,250,9,1); -- leche



-->//------------------------------------------------------------------------------------------------}
--> LEER LAS COMIDAS FUERON PLANTEADAS SEGUN INGREDIENTES, LAS CANTIDADES SON UN ESTIMADO,TOTAL DESPUES LO SETEAMOS DEPENDIENDO LOS LOS
--> REQUERIMENTOS DEL USUARIO
-->//COMIDA 2 ------------

INSERT INTO Comida (id,nombre,tipo,celiaco,diabetico,intoleranciaLactosa,vegetariano,vegano)
VALUES (2,'Yogur con Frutillas','DESAYUNO',true,true,false,true,false);

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(4,200,10,2); -- yogur

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(5,100,27,2); -- frutilla

-->//COMIDA 3 ------------

INSERT INTO Comida (id,nombre,tipo,celiaco,diabetico,intoleranciaLactosa,vegetariano,vegano)
VALUES (3,'Arroz con Pollo y Tomate','ALMUERZO',true,true,true,false,false);

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(6,100,3,3); -- arroz

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(7,150,6,3); -- pollo

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(8,50,5,3); -- tomate

-->//COMIDA 4 ------------

INSERT INTO Comida (id,nombre,tipo,celiaco,diabetico,intoleranciaLactosa,vegetariano,vegano)
VALUES (4,'Pollo con Papa','ALMUERZO',true,true,true,false,false);

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(9,200,6,4); -- pollo

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(10,200,4,4); -- papa

-->//COMIDA 5 ------------

INSERT INTO Comida (id,nombre,tipo,celiaco,diabetico,intoleranciaLactosa,vegetariano,vegano)
VALUES (5,'Lentejas con Tomate','ALMUERZO',true,true,true,true,true);

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(11,150,12,5); -- lentejas

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(12,50,5,5); -- tomate

-->//COMIDA 6 ------------
INSERT INTO Comida (id,nombre,tipo,celiaco,diabetico,intoleranciaLactosa,vegetariano,vegano)
VALUES (6,'Garbanzos con Zanahoria','ALMUERZO',true,true,true,true,true);

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(13,150,33,6); -- garbanzos

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(14,100,14,6); -- zanahoria


-->//COMIDA 7 ------------

INSERT INTO Comida (id,nombre,tipo,celiaco,diabetico,intoleranciaLactosa,vegetariano,vegano)
VALUES (7,'Carne con Papa','CENA',true,true,true,false,false);

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(15,200,7,7); -- bola de lomo

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(16,200,4,7); -- papa

-->//COMIDA 8 ------------

INSERT INTO Comida (id,nombre,tipo,celiaco,diabetico,intoleranciaLactosa,vegetariano,vegano)
VALUES (8,'Omelette de Espinaca','CENA',true,true,true,true,false);

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(17,120,8,8); -- huevo

INSERT INTO Ingrediente(id,cantidadGramos,alimento_id,comida_id)
VALUES(18,50,19,8); -- espinaca

-->//COMIDA 9 ------------

-->//COMIDA 10 ------------





/*
DELETE FROM Alimento;
DELETE FROM RestriccionAlimentaria;
DELETE FROM Usuario;

INSERT INTO Usuario(id, email, password, rol, activo)
VALUES(1, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

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

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (16, 'Galletas de Arroz s/sal Sanissimo 100g', 1950.00, 'DESAYUNO', true, true, false, 376, 7.8, 81.0, 1.2, 'https://www.carrefour.com.ar/galletas-de-arroz-tostadas-sin-sal-sanissimo-100-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (17, 'Queso crema clásico Casancrem 290g', 3100.00, 'DESAYUNO', true, true, true, 194, 7.0, 4.5, 16.0, 'https://www.carrefour.com.ar/queso-crema-clasico-casancrem-290-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (18, 'Pan rallado tradicional Carrefour 500g', 1400.00, 'DESAYUNO', true, false, false, 350, 11.0, 72.0, 1.5, 'https://www.carrefour.com.ar/pan-rallado-carrefour-500-g/p');

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

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (19, 'Suprema de pollo congelada Sadia 1kg', 8500.00, 'ALMUERZO', false, true, false, 120, 22.0, 0.0, 3.5, 'https://www.carrefour.com.ar/suprema-de-pollo-sadia-1-kg/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (20, 'Medallones de lentejas y quinoa NotCo 2 un', 3400.00, 'ALMUERZO', true, true, false, 180, 12.0, 15.0, 7.0, 'https://www.carrefour.com.ar/hamburguesa-not-burger-notco-2-unidades-200-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (21, 'Fideos de arroz s/TACC Matarazzo 500g', 2800.00, 'ALMUERZO', true, true, false, 348, 6.5, 78.0, 0.8, 'https://www.carrefour.com.ar/fideos-de-arroz-penne-rigate-matarazzo-sin-tacc-500-g/p');

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

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (22, 'Suprema de pollo congelada Sadia 1kg', 8500.00, 'CENA', false, true, false, 120, 22.0, 0.0, 3.5, 'https://www.carrefour.com.ar/suprema-de-pollo-sadia-1-kg/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (23, 'Medallones de lentejas y quinoa NotCo 2 un', 3400.00, 'CENA', true, true, false, 180, 12.0, 15.0, 7.0, 'https://www.carrefour.com.ar/hamburguesa-not-burger-notco-2-unidades-200-g/p');

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas, url_supermercado)
VALUES (24, 'Fideos de arroz s/TACC Matarazzo 500g', 2800.00, 'CENA', true, true, false, 348, 6.5, 78.0, 0.8, 'https://www.carrefour.com.ar/fideos-de-arroz-penne-rigate-matarazzo-sin-tacc-500-g/p');

--================== RESTRICCIONES ALIMENTARIAS ===================
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('NINGUNO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('CELIACO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('DIABETICO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('VEGETARIANO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('VEGANO');
INSERT INTO RestriccionAlimentaria (nombre) VALUES ('INTOLERANCIA_LACTOSA');

 */