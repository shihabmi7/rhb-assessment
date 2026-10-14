INSERT INTO customers (name, email, city) VALUES ('Alice Tan', 'alice@example.com', 'Kuala Lumpur');
INSERT INTO customers (name, email, city) VALUES ('Bob Lim', 'bob@example.com', 'Penang');
INSERT INTO customers (name, email, city) VALUES ('Charlie Wong', 'charlie@example.com', 'Kuala Lumpur');
INSERT INTO customers (name, email, city) VALUES ('Diana Lee', 'diana@example.com', 'Johor Bahru');
INSERT INTO customers (name, email, city) VALUES ('Ethan Ng', 'ethan@example.com', 'Penang');

INSERT INTO orders (customer_id, product, amount, status, created_at) VALUES (1, 'Laptop', 3499.00, 'PAID', CURRENT_TIMESTAMP);
INSERT INTO orders (customer_id, product, amount, status, created_at) VALUES (1, 'Mouse', 59.90, 'SHIPPED', CURRENT_TIMESTAMP);
INSERT INTO orders (customer_id, product, amount, status, created_at) VALUES (2, 'Keyboard', 189.00, 'PENDING', CURRENT_TIMESTAMP);
INSERT INTO orders (customer_id, product, amount, status, created_at) VALUES (3, 'Monitor', 899.00, 'PAID', CURRENT_TIMESTAMP);
INSERT INTO orders (customer_id, product, amount, status, created_at) VALUES (4, 'Headphones', 249.50, 'CANCELLED', CURRENT_TIMESTAMP);
