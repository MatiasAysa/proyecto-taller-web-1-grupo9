INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

DELETE FROM Alimento;

-- Inserción de Alimentos para el Plan Económico (Vegetarianos y aptos base)
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (1, 'Avena instantánea', 1500.00, 'DESAYUNO', true, false, false, 389, 16.9, 66.3, 6.9);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (2, 'Lentejas secas', 2200.00, 'ALMUERZO', true, true, false, 116, 9.0, 20.0, 0.4);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (3, 'Arroz base', 1100.00, 'CENA', true, true, false, 130, 2.7, 28.0, 0.3);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (4, 'Espinaca atado', 1800.00, 'CENA', true, true, false, 23, 2.9, 3.6, 0.4);


-- Inserción de Alimentos para el Plan Premium
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (5, 'Yogur Firme Premium', 3500.00, 'DESAYUNO', true, false, true, 61, 3.5, 4.7, 3.3);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (6, 'Granola con frutos secos', 4800.00, 'DESAYUNO', true, false, false, 450, 10.0, 60.0, 20.0);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (7, 'Pechuga de Pollo x kg', 8500.00, 'ALMUERZO', false, true, false, 165, 31.0, 0.0, 3.6);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa, calorias, proteinas, carbohidratos, grasas)
VALUES (8, 'Vegetales selectos para Wok', 4200.00, 'CENA', true, true, false, 50, 2.0, 10.0, 0.2);