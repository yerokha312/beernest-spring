INSERT INTO brand (name)
VALUES ('Brewmaster''s Delight');
INSERT INTO brand (name)
VALUES ('Craft Brew Co.');
INSERT INTO brand (name)
VALUES ('HopCrafters');
INSERT INTO brand (name)
VALUES ('HopHead Brewery');
INSERT INTO brand (name)
VALUES ('Hoppington Brews');
INSERT INTO brand (name)
VALUES ('Suds & Buds');
INSERT INTO contact_info (phone_number, address)
VALUES ('555-1111', '234 Pine St, Hopetown');
INSERT INTO contact_info (phone_number, address)
VALUES ('555-2222', '567 Maple Ave, Ale City');
INSERT INTO contact_info (phone_number, address)
VALUES ('555-4321', '789 Maple Ave, Beerburg');
INSERT INTO contact_info (phone_number, address)
VALUES ('555-5432', '789 Birch Blvd, Aleburg');
INSERT INTO contact_info (phone_number, address)
VALUES ('555-8765', '456 Pine St, Brewtown');
INSERT INTO contact_info (phone_number, address)
VALUES ('555-9876', '123 Oak Lane, Hopsville');
INSERT INTO customer (first_name, last_name, dob, email, password, reg_date, active)
VALUES ('David', 'Anderson', '1991-04-08', 'david@example.com',
        '$2a$10$SQ.7ynnJeT89Rg4.WU1eJeel3VSaj13vN3I0lZjxC30ylVu9hmKK.', '2024-01-18', true);
INSERT INTO customer (first_name, last_name, dob, email, password, reg_date, active)
VALUES ('Emily', 'Taylor', '1987-12-15', 'emily@example.com',
        '$2a$10$SQ.7ynnJeT89Rg4.WU1eJeel3VSaj13vN3I0lZjxC30ylVu9hmKK.', '2024-01-18', true);
INSERT INTO customer (first_name, last_name, dob, email, password, reg_date, active)
VALUES ('Eva', 'Clark', '1994-09-27', 'eva@example.com', '$2a$10$SQ.7ynnJeT89Rg4.WU1eJeel3VSaj13vN3I0lZjxC30ylVu9hmKK.',
        '2024-01-19', true);
INSERT INTO customer (first_name, last_name, dob, email, password, reg_date, active)
VALUES ('Gary', 'Turner', '1985-06-13', 'gary@example.com',
        '$2a$10$SQ.7ynnJeT89Rg4.WU1eJeel3VSaj13vN3I0lZjxC30ylVu9hmKK.', '2024-01-19', true);
INSERT INTO customer (first_name, last_name, dob, email, password, reg_date, active)
VALUES ('Mike', 'Williams', '1989-02-25', 'mike@example.com',
        '$2a$10$SQ.7ynnJeT89Rg4.WU1eJeel3VSaj13vN3I0lZjxC30ylVu9hmKK.', '2024-01-17', true);
INSERT INTO customer (first_name, last_name, dob, email, password, reg_date, active)
VALUES ('Sarah', 'Johnson', '1993-07-18', 'sarah@example.com',
        '$2a$10$SQ.7ynnJeT89Rg4.WU1eJeel3VSaj13vN3I0lZjxC30ylVu9hmKK.', '2024-01-17', true);
INSERT INTO customer_contact_junction (customer_id, contact_info_id)
VALUES (1, 1);
INSERT INTO customer_contact_junction (customer_id, contact_info_id)
VALUES (2, 2);
INSERT INTO customer_contact_junction (customer_id, contact_info_id)
VALUES (3, 3);
INSERT INTO customer_contact_junction (customer_id, contact_info_id)
VALUES (4, 4);
INSERT INTO customer_contact_junction (customer_id, contact_info_id)
VALUES (5, 5);
INSERT INTO customer_contact_junction (customer_id, contact_info_id)
VALUES (6, 6);
INSERT INTO customer_role_junction (user_id, role_id)
VALUES (1, 1);
INSERT INTO customer_role_junction (user_id, role_id)
VALUES (2, 1);
INSERT INTO customer_role_junction (user_id, role_id)
VALUES (3, 1);
INSERT INTO customer_role_junction (user_id, role_id)
VALUES (4, 1);
INSERT INTO customer_role_junction (user_id, role_id)
VALUES (5, 1);
INSERT INTO customer_role_junction (user_id, role_id)
VALUES (6, 1);
INSERT INTO orders (creation_date, total_price, status, customer_id, contact_info_id, delivered)
VALUES ('2024-01-17 10:00:00', 29.99, 'PENDING', 1, 1, false);
INSERT INTO orders (creation_date, total_price, status, customer_id, contact_info_id, delivered)
VALUES ('2024-01-17 11:30:00', 45.50, 'PAID', 2, 2, true);
INSERT INTO orders (creation_date, total_price, status, customer_id, contact_info_id, delivered)
VALUES ('2024-01-18 09:45:00', 34.50, 'PAID', 3, 3, true);
INSERT INTO orders (creation_date, total_price, status, customer_id, contact_info_id, delivered)
VALUES ('2024-01-18 10:30:00', 28.75, 'PENDING', 4, 4, false);
INSERT INTO orders (creation_date, total_price, status, customer_id, contact_info_id, delivered)
VALUES ('2024-01-19 11:15:00', 22.75, 'PENDING', 5, 5, false);
INSERT INTO orders (creation_date, total_price, status, customer_id, contact_info_id, delivered)
VALUES ('2024-01-19 12:45:00', 38.99, 'PAID', 6, 6, true);
INSERT INTO sub_style (name, style)
VALUES ('Amber Ale', 'ALE');
INSERT INTO sub_style (name, style)
VALUES ('Belgian Witbier', 'ALE');
INSERT INTO sub_style (name, style)
VALUES ('Doppelbock', 'LAGER');
INSERT INTO sub_style (name, style)
VALUES ('Märzen', 'LAGER');
INSERT INTO sub_style (name, style)
VALUES ('Pilsner', 'LAGER');
INSERT INTO sub_style (name, style)
VALUES ('Wheat Beer', 'ALE');
INSERT INTO beer_description (description)
VALUES ('Bold and hop-forward double IPA');
INSERT INTO beer_description (description)
VALUES ('Crisp and clean pilsner with a floral aroma');
INSERT INTO beer_description (description)
VALUES ('Light and fruity pale ale with a tropical twist');
INSERT INTO beer_description (description)
VALUES ('Robust and malty stout with hints of chocolate');
INSERT INTO beer_description (description)
VALUES ('Smooth and citrus-infused pale ale');
INSERT INTO beer_description (description)
VALUES ('Velvety smooth porter with a hint of coffee');
INSERT INTO beer (beer_code, name, style, sub_style_id, brand_id, alcohol, container, size, purchase_price,
                  selling_price, country, description_id, sold_amount, stock_amount)
VALUES ('BDCAN002', 'Bavarian Dunkel Can 2', 'LAGER', 2, 3, 5.8, 'CAN', 500, 5.99, 10.49, 'Germany', 3, 50, 100);
INSERT INTO beer (beer_code, name, style, sub_style_id, brand_id, alcohol, container, size, purchase_price,
                  selling_price, country, description_id, sold_amount, stock_amount)
VALUES ('CBCAN001', 'Classic Lager Can', 'LAGER', 2, 2, 4.5, 'CAN', 500, 3.99, 7.99, 'Germany', 1, 60, 110);
INSERT INTO beer (beer_code, name, style, sub_style_id, brand_id, alcohol, container, size, purchase_price,
                  selling_price, country, description_id, sold_amount, stock_amount)
VALUES ('HBLAG002', 'Hoppy Lager 2', 'LAGER', 2, 2, 6.0, 'CAN', 500, 4.49, 8.49, 'Germany', 2, 55, 95);
INSERT INTO beer (beer_code, name, style, sub_style_id, brand_id, alcohol, container, size, purchase_price,
                  selling_price, country, description_id, sold_amount, stock_amount)
VALUES ('HDIPA003', 'Hoppy Double IPA 3', 'ALE', 1, 4, 8.2, 'BOTTLE', 330, 8.99, 14.99, 'USA', 4, 20, 75);
INSERT INTO beer (beer_code, name, style, sub_style_id, brand_id, alcohol, container, size, purchase_price,
                  selling_price, country, description_id, sold_amount, stock_amount)
VALUES ('HHIPA002', 'Hoppy IPA 2', 'ALE', 1, 1, 6.8, 'BOTTLE', 330, 6.99, 11.49, 'USA', 5, 40, 90);
INSERT INTO beer (beer_code, name, style, sub_style_id, brand_id, alcohol, container, size, purchase_price,
                  selling_price, country, description_id, sold_amount, stock_amount)
VALUES ('SCAPA001', 'Summer Citrus APA', 'ALE', 1, 3, 5.5, 'BOTTLE', 355, 7.49, 12.99, 'USA', 6, 25, 85);
INSERT INTO order_item (order_id, beer_id, quantity)
VALUES (1, 1, 2);
INSERT INTO order_item (order_id, beer_id, quantity)
VALUES (2, 2, 3);
INSERT INTO order_item (order_id, beer_id, quantity)
VALUES (3, 1, 1);
INSERT INTO order_item (order_id, beer_id, quantity)
VALUES (4, 2, 2);
INSERT INTO order_item (order_id, beer_id, quantity)
VALUES (5, 4, 3);
INSERT INTO order_item (order_id, beer_id, quantity)
VALUES (6, 5, 2);
