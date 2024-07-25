INSERT INTO category (id, name, parent_id) VALUES (1, 'Electronics', NULL);
INSERT INTO category (id, name, parent_id) VALUES (2, 'Laptops', 1);
INSERT INTO category (id, name, parent_id) VALUES (3, 'Smartphones', 1);
INSERT INTO category (id, name, parent_id) VALUES (4, 'Home Appliances', NULL);
INSERT INTO category (id, name, parent_id) VALUES (5, 'Refrigerators', 4);
INSERT INTO category (id, name, parent_id) VALUES (6, 'Washing Machines', 4);

INSERT INTO product (id, name, description, images, thumbnail, quantity, cost) VALUES
(1, 'MacBook Pro', 'Apple MacBook Pro 16-inch', 'macbook_pro.jpg', 'macbook_pro_thumb.jpg', 50, 2399.99),
(2, 'iPhone 13', 'Apple iPhone 13', 'iphone_13.jpg', 'iphone_13_thumb.jpg', 100, 799.99),
(3, 'Samsung Galaxy S21', 'Samsung Galaxy S21', 'galaxy_s21.jpg', 'galaxy_s21_thumb.jpg', 150, 699.99),
(4, 'LG Refrigerator', 'LG Double Door Refrigerator', 'lg_refrigerator.jpg', 'lg_refrigerator_thumb.jpg', 20, 1199.99),
(5, 'Bosch Washing Machine', 'Bosch Front Load Washing Machine', 'bosch_washing_machine.jpg', 'bosch_washing_machine_thumb.jpg', 30, 899.99);

INSERT INTO product_category (product_id, category_id) VALUES
(1, 1), (1, 2),
(2, 1), (2, 3),
(3, 1), (3, 3),
(4, 4), (4, 5),
(5, 4), (5, 6);

-- Insert categories
INSERT INTO category (id, name, parent_id) VALUES (1, 'Books', NULL);
INSERT INTO category (id, name, parent_id) VALUES (2, 'Fiction', 1);
INSERT INTO category (id, name, parent_id) VALUES (3, 'Non-Fiction', 1);
INSERT INTO category (id, name, parent_id) VALUES (4, 'Science Fiction', 2);
INSERT INTO category (id, name, parent_id) VALUES (5, 'Biography', 3);
INSERT INTO category (id, name, parent_id) VALUES (6, 'Children\'s Books', 1);

-- Insert products
INSERT INTO product (id, name, description, images, thumbnail, quantity, cost) VALUES
(6, 'Dune', 'Frank Herbert\'s Dune', 'dune.jpg', 'dune_thumb.jpg', 200, 15.99),
(7, 'Sapiens', 'Yuval Noah Harari\'s Sapiens', 'sapiens.jpg', 'sapiens_thumb.jpg', 150, 18.99),
(8, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling\'s first Harry Potter book', 'harry_potter.jpg', 'harry_potter_thumb.jpg', 500, 12.99),
(9, 'The Catcher in the Rye', 'J.D. Salinger\'s classic novel', 'catcher_in_the_rye.jpg', 'catcher_in_the_rye_thumb.jpg', 120, 10.99),
(10, 'A Brief History of Time', 'Stephen Hawking\'s book on cosmology', 'brief_history.jpg', 'brief_history_thumb.jpg', 80, 14.99);

-- Insert product-category relationships
INSERT INTO product_category (product_id, category_id) VALUES
(6, 1), (6, 2), (6, 4),
(7, 1), (7, 3), (7, 5),
(8, 1), (8, 2), (8, 6),
(9, 1), (9, 2),
(10, 1), (10, 3);

-- Insert categories
INSERT INTO category (id, name, parent_id) VALUES (7, 'Clothing', NULL);
INSERT INTO category (id, name, parent_id) VALUES (8, 'Men\'s Wear', 7);
INSERT INTO category (id, name, parent_id) VALUES (9, 'Women\'s Wear', 7);
INSERT INTO category (id, name, parent_id) VALUES (10, 'Footwear', NULL);
INSERT INTO category (id, name, parent_id) VALUES (11, 'Formal Shoes', 10);
INSERT INTO category (id, name, parent_id) VALUES (12, 'Casual Shoes', 10);
INSERT INTO category (id, name, parent_id) VALUES (13, 'Accessories', NULL);
INSERT INTO category (id, name, parent_id) VALUES (14, 'Watches', 13);
INSERT INTO category (id, name, parent_id) VALUES (15, 'Jewelry', 13);

-- Insert products
INSERT INTO product (id, name, description, images, thumbnail, quantity, cost) VALUES
(11, 'Men\'s Leather Jacket', 'High-quality leather jacket for men', 'leather_jacket.jpg', 'leather_jacket_thumb.jpg', 30, 299.99),
(12, 'Women\'s Summer Dress', 'Light and comfortable summer dress for women', 'summer_dress.jpg', 'summer_dress_thumb.jpg', 100, 49.99),
(13, 'Running Shoes', 'Comfortable running shoes for all-day wear', 'running_shoes.jpg', 'running_shoes_thumb.jpg', 80, 89.99),
(14, 'Formal Black Shoes', 'Elegant black formal shoes for men', 'formal_shoes.jpg', 'formal_shoes_thumb.jpg', 40, 129.99),
(15, 'Luxury Watch', 'Stylish luxury watch with leather strap', 'luxury_watch.jpg', 'luxury_watch_thumb.jpg', 20, 499.99);

-- Insert product-category relationships
INSERT INTO product_category (product_id, category_id) VALUES
(11, 7), (11, 8),
(12, 7), (12, 9),
(13, 10), (13, 12),
(14, 10), (14, 11),
(15, 13), (15, 14);