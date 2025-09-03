-- Insert into Categories
INSERT INTO Categories (category_id, category_name) VALUES
(1, 'Electronics'),
(2, 'Clothing'),
(3, 'Groceries');

-- Insert into Suppliers
INSERT INTO Suppliers (supplier_id, supplier_name, contact_email) VALUES
(1, 'Tech World', 'contact@techworld.com'),
(2, 'Fashion Hub', 'support@fashionhub.com'),
(3, 'Fresh Farm', 'info@freshfarm.com');

-- Insert into Products
INSERT INTO Products (product_id, product_name, category_id, supplier_id, price) VALUES
(101, 'Smartphone', 1, 1, 15000.00),
(102, 'Laptop', 1, 1, 55000.00),
(103, 'T-Shirt', 2, 2, 500.00),
(104, 'Jeans', 2, 2, 1200.00),
(105, 'Apples', 3, 3, 150.00),
(106, 'Milk', 3, 3, 60.00);

-- Insert into Inventory
INSERT INTO Inventory (inventory_id, product_id, quantity, last_updated) VALUES
(1, 101, 50, '2025-08-20'),
(2, 102, 30, '2025-08-21'),
(3, 103, 100, '2025-08-22'),
(4, 104, 60, '2025-08-23'),
(5, 105, 200, '2025-08-24'),
(6, 106, 150, '2025-08-25');

-- Insert into Orders
INSERT INTO Orders (order_id, product_id, order_date, quantity_ordered, total_price) VALUES
(1001, 101, '2025-08-26', 2, 30000.00),
(1002, 102, '2025-08-27', 1, 55000.00),
(1003, 103, '2025-08-28', 5, 2500.00),
(1004, 105, '2025-08-29', 10, 1500.00),
(1005, 106, '2025-08-30', 20, 1200.00),
(1006, 104, '2025-08-31', 3, 3600.00);
