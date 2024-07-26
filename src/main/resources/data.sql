-- roles ------------------------------

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

-- products and categories-------------------------------



INSERT INTO category (id, name, parent_id) VALUES (1, 'Electronics', NULL);
INSERT INTO category (id, name, parent_id) VALUES (2, 'Laptops', 1);
INSERT INTO category (id, name, parent_id) VALUES (3, 'Smartphones', 1);
INSERT INTO category (id, name, parent_id) VALUES (4, 'Home Appliances', NULL);
INSERT INTO category (id, name, parent_id) VALUES (5, 'Refrigerators', 4);
INSERT INTO category (id, name, parent_id) VALUES (6, 'Washing Machines', 4);

INSERT INTO product (id, name, description, images, thumbnail, quantity, cost) VALUES
(1, 'MacBook Pro', 'Apple MacBook Pro 16-inch', '{"macbook_pro.jpg"}', 'macbook_pro_thumb.jpg', 50, 2399.99),
(2, 'iPhone 13', 'Apple iPhone 13', '{"iphone_13.jpg"}', 'iphone_13_thumb.jpg', 100, 799.99),
(3, 'Samsung Galaxy S21', 'Samsung Galaxy S21', '{"galaxy_s21.jpg"}', 'galaxy_s21_thumb.jpg', 150, 699.99),
(4, 'LG Refrigerator', 'LG Double Door Refrigerator', '{"lg_refrigerator.jpg"}', 'lg_refrigerator_thumb.jpg', 20, 1199.99),
(5, 'Bosch Washing Machine', 'Bosch Front Load Washing Machine', '{"bosch_washing_machine.jpg"}', 'bosch_washing_machine_thumb.jpg', 30, 899.99);

INSERT INTO product_category (product_id, category_id) VALUES
(1, 1), (1, 2),
(2, 1), (2, 3),
(3, 1), (3, 3),
(4, 4), (4, 5),
(5, 4), (5, 6);

-- Insert categories
INSERT INTO category (id, name, parent_id) VALUES (7, 'Books', NULL);
INSERT INTO category (id, name, parent_id) VALUES (8, 'Fiction', 7);
INSERT INTO category (id, name, parent_id) VALUES (9, 'Non-Fiction', 7);
INSERT INTO category (id, name, parent_id) VALUES (10, 'Science Fiction', 8);
INSERT INTO category (id, name, parent_id) VALUES (11, 'Biography', 9);
INSERT INTO category (id, name, parent_id) VALUES (12, 'Children''s Books', 7);

-- Insert products
INSERT INTO product (id, name, description, images, thumbnail, quantity, cost) VALUES
(6, 'Dune', 'Frank Herbert''s Dune', '{"dune.jpg"}', 'dune_thumb.jpg', 200, 15.99),
(7, 'Sapiens', 'Yuval Noah Harari''s Sapiens', '{"sapiens.jpg"}', 'sapiens_thumb.jpg', 150, 18.99),
(8, 'Harry Potter and the Philosopher''s Stone', 'J.K. Rowling''s first Harry Potter book', '{"harry_potter.jpg"}', 'harry_potter_thumb.jpg', 500, 12.99),
(9, 'The Catcher in the Rye', 'J.D. Salinger''s classic novel', '{"catcher_in_the_rye.jpg"}', 'catcher_in_the_rye_thumb.jpg', 120, 10.99),
(10, 'A Brief History of Time', 'Stephen Hawking''s book on cosmology', '{"brief_history.jpg"}', 'brief_history_thumb.jpg', 80, 14.99);

-- Insert product-category relationships
INSERT INTO product_category (product_id, category_id) VALUES
(6, 7), (6, 8), (6, 10),
(7, 7), (7, 9), (7, 11),
(8, 7), (8, 8), (8, 12),
(9, 7), (9, 8),
(10, 7), (10, 9);

-- Insert categories
INSERT INTO category (id, name, parent_id) VALUES (13, 'Clothing', NULL);
INSERT INTO category (id, name, parent_id) VALUES (14, 'Men''s Wear', 13);
INSERT INTO category (id, name, parent_id) VALUES (15, 'Women''s Wear', 13);
INSERT INTO category (id, name, parent_id) VALUES (16, 'Footwear', NULL);
INSERT INTO category (id, name, parent_id) VALUES (17, 'Formal Shoes', 16);
INSERT INTO category (id, name, parent_id) VALUES (18, 'Casual Shoes', 16);
INSERT INTO category (id, name, parent_id) VALUES (19, 'Accessories', NULL);
INSERT INTO category (id, name, parent_id) VALUES (20, 'Watches', 19);
INSERT INTO category (id, name, parent_id) VALUES (21, 'Jewelry', 19);

-- Insert products
INSERT INTO product (id, name, description, images, thumbnail, quantity, cost) VALUES
(11, 'Men''s Leather Jacket', 'High-quality leather jacket for men', '{"leather_jacket.jpg"}', 'leather_jacket_thumb.jpg', 30, 299.99),
(12, 'Women''s Summer Dress', 'Light and comfortable summer dress for women', '{"summer_dress.jpg"}', 'summer_dress_thumb.jpg', 100, 49.99),
(13, 'Running Shoes', 'Comfortable running shoes for all-day wear', '{"running_shoes.jpg"}', 'running_shoes_thumb.jpg', 80, 89.99),
(14, 'Formal Black Shoes', 'Elegant black formal shoes for men', '{"formal_shoes.jpg"}', 'formal_shoes_thumb.jpg', 40, 129.99),
(15, 'Luxury Watch', 'Stylish luxury watch with leather strap', '{"luxury_watch.jpg"}', 'luxury_watch_thumb.jpg', 20, 499.99);

-- Insert product-category relationships
INSERT INTO product_category (product_id, category_id) VALUES
(11, 13), (11, 14),
(12, 13), (12, 15),
(13, 16), (13, 18),
(14, 16), (14, 17),
(15, 19), (15, 20);