INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

DELETE FROM Alimento;

-- Inserción de Alimentos para el Plan Económico (Vegetarianos y aptos base)
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa)
VALUES (1, 'Avena instantánea', 1500.00, 'DESAYUNO', true, false, false);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa)
VALUES (2, 'Lentejas secas', 2200.00, 'ALMUERZO', true, true, false);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa)
VALUES (3, 'Arroz base', 1100.00, 'CENA', true, true, false);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa)
VALUES (4, 'Espinaca atado', 1800.00, 'CENA', true, true, false);


-- Inserción de Alimentos para el Plan Premium
INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa)
VALUES (5, 'Yogur Firme Premium', 3500.00, 'DESAYUNO', true, false, true);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa)
VALUES (6, 'Granola con frutos secos', 4800.00, 'DESAYUNO', true, false, false);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa)
VALUES (7, 'Pechuga de Pollo x kg', 8500.00, 'ALMUERZO', false, true, false);

INSERT INTO Alimento (id, nombre, precioEstimado, tipoComida, esVegetariano, esCeliaco, contieneLactosa)
VALUES (8, 'Vegetales selectos para Wok', 4200.00, 'CENA', true, true, false);