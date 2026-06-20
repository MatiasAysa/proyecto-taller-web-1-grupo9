INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

DELETE FROM Alimento;

-->//LEER TODO SE PLANTEO DE LA SIGUIENTE FORMA , EL PRECIO ES POR KILO,OSEA LA AVENA ESTA 2200 EL KILO,AHORA TODO LO QUE ES
--> NUTRICIONAL ESTA POR CADA 100 GRAMOS DEL ALIMENTO,OSEA LA AVENA TIENE 389 CALORIAS POR CADA 100 GRAMOS

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(1,'Avena',3200,389,16.9,66.3,6.9);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(2,'Banana',1500,89,1.1,22.8,0.3);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(3,'Arroz Blanco',1800,365,7.1,80.0,0.7);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(4,'Papa',1200,77,2.0,17.0,0.1);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(5,'Tomate',2500,18,0.9,3.9,0.2);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(6,'Pechuga de Pollo',7000,165,31.0,0.0,3.6);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(7,'Bola de Lomo',19000,170,29.0,0.0,6.0);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(8,'Huevo Entero',3055 ,155,13.0,1.1,11.0);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(9,'Leche Descremada',1300,34,3.4,5.0,0.1);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(10,'Yogur Griego Natural',6000,97,10.0,3.6,5.0);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(11,'Pan Integral',3500,247,13.0,41.0,4.2);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(12,'Lentejas',2700,352,25.0,60.0,1.1);


-->//VERDURAS----------------------------
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(13,'Lechuga',1800,15,1.4,2.9,0.2);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(14,'Zanahoria',1500,41,0.9,10.0,0.2);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(15,'Cebolla',1200,40,1.1,9.3,0.1);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(16,'Morron Rojo',3500,31,1.0,6.0,0.3);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(17,'Zapallito',1700,17,1.2,3.1,0.3);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(18,'Brocoli',4500,34,2.8,6.6,0.4);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(19,'Espinaca',3000,23,2.9,3.6,0.4);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(20,'Pepino',2000,15,0.7,3.6,0.1);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(21,'Remolacha',1800,43,1.6,10.0,0.2);

INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(22,'Berenjena',2500,25,1.0,6.0,0.2);

-->//FRUTAS ------------
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(23,'Manzana',1800,52,0.3,14.0,0.2);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(24,'Pera',2200,57,0.4,15.0,0.1);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(25,'Naranja',1700,47,0.9,12.0,0.1);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(26,'Mandarina',1800,53,0.8,13.0,0.3);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(27,'Frutilla',5000,32,0.7,7.7,0.3);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(28,'Kiwi',6500,61,1.1,15.0,0.5);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(29,'Durazno',2500,39,0.9,10.0,0.3);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(30,'Ciruela',3500,46,0.7,11.0,0.3);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(31,'Anana',3000,50,0.5,13.0,0.1);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(32,'Melon',1800,34,0.8,8.0,0.2);

-->//LEGUMBRES -------------
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(33,'Garbanzos',3200,364,19.0,61.0,6.0);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(34,'Porotos Negros',2800,339,21.0,63.0,0.9);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(35,'Porotos Blancos',2600,333,21.0,60.0,1.5);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(36,'Arvejas Secas',2500,341,24.0,60.0,1.2);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(37,'Soja',3500,446,36.0,30.0,20.0);

-->//LIBRES DE GLUTEN (CELIACOS)
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(38,'Quinoa',8500,368,14.0,64.0,6.0);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(39,'Polenta',1800,360,8.0,79.0,1.5);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(40,'Batata',1600,86,1.6,20.0,0.1);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
VALUES(41,'Mandioca',2200,160,1.4,38.0,0.3);
INSERT INTO Alimento (id,nombre,precioEstimado,caloriasX100g,proteinasX100g,carbohidratosX100g,grasasX100g)
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


INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (1, 'Avena instantánea', 1500.00, 'DESAYUNO', true, false, false, 389, 16.9, 66.3, 6.9);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (2, 'Lentejas secas', 2200.00, 'ALMUERZO', true, true, false, 116, 9.0, 20.0, 0.4);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (3, 'Arroz base', 1100.00, 'CENA', true, true, false, 130, 2.7, 28.0, 0.3);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (4, 'Espinaca atado', 1800.00, 'CENA', true, true, false, 23, 2.9, 3.6, 0.4);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (9, 'Huevos integrales x Maple', 2000.00, 'ALMUERZO', true, true, false, 155, 13.0, 1.1, 11.0);


INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (5, 'Yogur Firme Premium', 3500.00, 'DESAYUNO', true, false, true, 61, 3.5, 4.7, 3.3);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (6, 'Granola con frutos secos', 4800.00, 'DESAYUNO', true, false, false, 450, 10.0, 60.0, 20.0);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (7, 'Pechuga de Pollo x kg', 8500.00, 'ALMUERZO', false, true, false, 165, 31.0, 0.0, 3.6);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (8, 'Vegetales selectos para Wok', 4200.00, 'CENA', true, true, false, 50, 2.0, 10.0, 0.2);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (10, 'Pan de molde Sin TACC', 3900.00, 'DESAYUNO', true, true, false, 240, 4.0, 48.0, 2.5);


INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (11, 'Banana Premium x kg', 1600.00, 'DESAYUNO', true, true, false, 89, 1.1, 22.8, 0.3);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (12, 'Leche de Almendras Sin TACC', 3200.00, 'DESAYUNO', true, true, false, 24, 1.0, 3.0, 1.1);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (13, 'Queso Untable descremado', 2900.00, 'DESAYUNO', true, true, true, 98, 7.0, 4.0, 6.0);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (14, 'Arroz Integral de grano largo', 1400.00, 'ALMUERZO', true, true, false, 111, 2.6, 23.0, 0.9);


INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (15, 'Carne picada vacuna magra x kg', 7900.00, 'ALMUERZO', false, true, false, 250, 26.0, 0.0, 15.0);


INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (16, 'Garbanzos envasados', 1850.00, 'ALMUERZO', true, true, false, 164, 8.9, 27.0, 2.6);



INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (17, 'Filete de Merluza fresco x kg', 7200.00, 'CENA', false, true, false, 91, 19.0, 0.0, 1.2);


INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (18, 'Puré de Calabaza', 1300.00, 'CENA', true, true, false, 45, 1.0, 11.0, 0.1);


INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (19, 'Mix de Frutos Secos seleccionados', 4100.00, 'CENA', true, true, false, 607, 15.0, 21.0, 54.0);


INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (20, 'Brócoli fresco x unidad', 2100.00, 'CENA', true, true, false, 34, 2.8, 7.0, 0.4);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (21, 'Leche entera clásica x 1L', 1400.00, 'DESAYUNO', true, true, true, 58, 3.0, 4.7, 3.0);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (22, 'Café molido instantáneo', 3100.00, 'DESAYUNO', true, true, false, 2, 0.2, 0.3, 0.0);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (23, 'Mermelada de frutilla light', 1950.00, 'DESAYUNO', true, true, false, 39, 0.4, 9.0, 0.1);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (24, 'Galletitas de agua de salvado', 1150.00, 'DESAYUNO', true, false, false, 410, 11.0, 65.0, 10.0);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (25, 'Queso Por salut x 300g', 3800.00, 'DESAYUNO', true, true, true, 250, 20.0, 1.5, 18.0);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (26, 'Fideos tallarines de sémola', 1250.00, 'ALMUERZO', true, false, false, 356, 12.0, 73.0, 1.2);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (27, 'Puré de tomate en caja', 950.00, 'ALMUERZO', true, true, false, 28, 1.4, 5.0, 0.2);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (28, 'Milanesas de nalga vacuna x kg', 8900.00, 'ALMUERZO', false, false, false, 220, 24.0, 15.0, 6.5);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (29, 'Papa negra x kg', 1100.00, 'ALMUERZO', true, true, false, 77, 2.0, 17.0, 0.1);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (30, 'Atún en lata desmenuzado al natural', 2300.00, 'ALMUERZO', false, true, false, 98, 22.0, 0.0, 0.8);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (31, 'Zanahoria fresca x kg', 1350.00, 'CENA', true, true, false, 41, 0.9, 10.0, 0.2);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (32, 'Pechuguitas de pollo congeladas x 500g', 4500.00, 'CENA', false, true, false, 120, 23.0, 0.0, 2.5);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (33, 'Calabaza Anco entera x unidad', 1700.00, 'CENA', true, true, false, 45, 1.0, 11.0, 0.1);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (34, 'Polenta instantánea base', 1100.00, 'CENA', true, true, false, 345, 7.5, 75.0, 1.0);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (35, 'Aceite de Girasol x 900ml', 2150.00, 'CENA', true, true, false, 884, 0.0, 0.0, 100.0);
*/