

INSERT INTO manufacturers (name) VALUES
('Samsung'), ('Dell'), ('HP'), ('Apple'), ('Lenovo'), ('Acer'), ('AMD');


INSERT INTO employees (name, email, password, department) VALUES
('Vanya','sambor@gmail.com','$2a$12$psaL2af.Zj7TVYpR7BygWODJ1uSPDpXBAjsYMXzQkDnwBDi71qbZS','security'),
('Denys','dan23@gmail.com','$2a$12$psaL2af.Zj7TVYpR7BygWODJ1uSPDpXBAjsYMXzQkDnwBDi71qbZS','security');

INSERT INTO appliances (name, category, model, manufacturer_id, power_type, characteristic, description, power, price) VALUES
('Claw', 'BIG', 'CLW-900X', 1, 'ACCUMULATOR', 'Літій-іонний акумулятор, 12 годин роботи', 'Потужний пилосос для великого будинку', 600, 129.99),
('Bane', 'SMALL', 'BN-S220', 3, 'AC110', 'Міні-компресор для надувних виробів', 'Компактний та потужний прилад для дому та подорожей', 2200, 79.50),
('Ecu', 'SMALL', 'EC-800', 2, 'ACCUMULATOR', 'Акумулятор 4000 мА·г, зарядка USB-C', 'Ручний міксер із трьома насадками', 800, 45.00),
('Kang Dae', 'BIG', 'KD-3600', 4, 'AC220', 'Інверторний двигун, режим енергозбереження', 'Пральна машина з великим барабаном', 3600, 499.99),
('Gust', 'BIG', 'GST-PRO', 5, 'ACCUMULATOR', 'Автономність до 5 год, 3 швидкості обертання', 'Газонокосарка з регулюванням висоти скошування', 650, 259.00),
('Ancile', 'SMALL', 'ANC-230', 6, 'AC220', 'Потужність 1000 Вт, 2 температурні режими', 'Фен для волосся з функцією іонізації', 230, 39.99),
('Halo', 'BIG', 'HL-300', 7, 'ACCUMULATOR', 'Інтелектуальне очищення повітря, сенсори CO2', 'Побутовий очищувач повітря для великих кімнат', 300, 179.49);


