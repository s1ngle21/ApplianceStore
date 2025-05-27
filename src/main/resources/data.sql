-- Manufacturers
INSERT INTO manufacturers (name)
SELECT 'Samsung' WHERE NOT EXISTS (SELECT 1 FROM manufacturers WHERE name = 'Samsung');
INSERT INTO manufacturers (name)
SELECT 'Dell' WHERE NOT EXISTS (SELECT 1 FROM manufacturers WHERE name = 'Dell');
INSERT INTO manufacturers (name)
SELECT 'HP' WHERE NOT EXISTS (SELECT 1 FROM manufacturers WHERE name = 'HP');
INSERT INTO manufacturers (name)
SELECT 'Apple' WHERE NOT EXISTS (SELECT 1 FROM manufacturers WHERE name = 'Apple');
INSERT INTO manufacturers (name)
SELECT 'Lenovo' WHERE NOT EXISTS (SELECT 1 FROM manufacturers WHERE name = 'Lenovo');
INSERT INTO manufacturers (name)
SELECT 'Acer' WHERE NOT EXISTS (SELECT 1 FROM manufacturers WHERE name = 'Acer');
INSERT INTO manufacturers (name)
SELECT 'AMD' WHERE NOT EXISTS (SELECT 1 FROM manufacturers WHERE name = 'AMD');

-- Employees
INSERT INTO employees (name, email, password, department)
SELECT 'Vanya', 'sambor@gmail.com', '$2a$12$d9970eyHKsg3Mnf3PLXiJuPRBvSjzR5qcE6P8vvalaGYD53Sx2oJm', 'security'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'sambor@gmail.com');

INSERT INTO employees (name, email, password, department)
SELECT 'Denys', 'dan23@gmail.com', '$2a$12$psaL2af.Zj7TVYpR7BygWODJ1uSPDpXBAjsYMXzQkDnwBDi71qbZS', 'security'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'dan23@gmail.com');

-- Appliances
INSERT INTO appliances (name, category, model, manufacturer_id, power_type, characteristic, description, power, price)
SELECT 'Claw', 'BIG', 'CLW-900X', 1, 'ACCUMULATOR', 'Літій-іонний акумулятор, 12 годин роботи', 'Потужний пилосос для великого будинку', 600, 129.99
WHERE NOT EXISTS (SELECT 1 FROM appliances WHERE model = 'CLW-900X');

INSERT INTO appliances (name, category, model, manufacturer_id, power_type, characteristic, description, power, price)
SELECT 'Bane', 'SMALL', 'BN-S220', 3, 'AC110', 'Міні-компресор для надувних виробів', 'Компактний та потужний прилад для дому та подорожей', 2200, 79.50
WHERE NOT EXISTS (SELECT 1 FROM appliances WHERE model = 'BN-S220');

INSERT INTO appliances (name, category, model, manufacturer_id, power_type, characteristic, description, power, price)
SELECT 'Ecu', 'SMALL', 'EC-800', 2, 'ACCUMULATOR', 'Акумулятор 4000 мА·г, зарядка USB-C', 'Ручний міксер із трьома насадками', 800, 45.00
WHERE NOT EXISTS (SELECT 1 FROM appliances WHERE model = 'EC-800');

INSERT INTO appliances (name, category, model, manufacturer_id, power_type, characteristic, description, power, price)
SELECT 'Kang Dae', 'BIG', 'KD-3600', 4, 'AC220', 'Інверторний двигун, режим енергозбереження', 'Пральна машина з великим барабаном', 3600, 499.99
WHERE NOT EXISTS (SELECT 1 FROM appliances WHERE model = 'KD-3600');

INSERT INTO appliances (name, category, model, manufacturer_id, power_type, characteristic, description, power, price)
SELECT 'Gust', 'BIG', 'GST-PRO', 5, 'ACCUMULATOR', 'Автономність до 5 год, 3 швидкості обертання', 'Газонокосарка з регулюванням висоти скошування', 650, 259.00
WHERE NOT EXISTS (SELECT 1 FROM appliances WHERE model = 'GST-PRO');

INSERT INTO appliances (name, category, model, manufacturer_id, power_type, characteristic, description, power, price)
SELECT 'Ancile', 'SMALL', 'ANC-230', 6, 'AC220', 'Потужність 1000 Вт, 2 температурні режими', 'Фен для волосся з функцією іонізації', 230, 39.99
WHERE NOT EXISTS (SELECT 1 FROM appliances WHERE model = 'ANC-230');

INSERT INTO appliances (name, category, model, manufacturer_id, power_type, characteristic, description, power, price)
SELECT 'Halo', 'BIG', 'HL-300', 7, 'ACCUMULATOR', 'Інтелектуальне очищення повітря, сенсори CO2', 'Побутовий очищувач повітря для великих кімнат', 300, 179.49
WHERE NOT EXISTS (SELECT 1 FROM appliances WHERE model = 'HL-300');
