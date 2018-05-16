DELETE FROM STOCK;
DELETE FROM ORDER_DETAIL;
DELETE FROM ORDER_TABLE;
DELETE FROM PRODUCT;
DELETE FROM PRODUCT_CATEGORY;
DELETE FROM SUPPLIER;
DELETE FROM CUSTOMER;
DELETE FROM LOCATION;
DELETE FROM ADDRESS;

INSERT INTO SUPPLIER VALUES (1, 'Supplier 1 Name');

INSERT INTO PRODUCT_CATEGORY VALUES (1, 'Product Category 1', 'Product Category 1 Description');
INSERT INTO PRODUCT_CATEGORY VALUES (2, 'Product Category 2', 'Product Category 2 Description');
INSERT INTO PRODUCT_CATEGORY VALUES (3, 'Product Category 3', 'Product Category 3 Description');

INSERT INTO PRODUCT VALUES (1, 'Product 1', 'Product 1 Description', 12, 1, 1, 1);
INSERT INTO PRODUCT VALUES (2, 'Product 2', 'Product 2 Description', 300, 12, 2, 1);
INSERT INTO PRODUCT VALUES (3, 'Product 3', 'Product 3 Description', 7, 4, 2, 1);
INSERT INTO PRODUCT VALUES (4, 'Product 4', 'Product 4 Description', 167, 2, 3, 1);

INSERT INTO SUPPLIER VALUES (201, 'Supplier');
INSERT INTO CUSTOMER VALUES (100, 'CUST', 'OMER', '', 'cc');
INSERT INTO PRODUCT_CATEGORY VALUES (201, 'PC', 'PCDescription');
INSERT INTO PRODUCT VALUES (201, 'Product 1', 'Product 1 Description', 1.2, 1, 201, 201);
INSERT INTO PRODUCT VALUES (202, 'Product 2', 'Product 2 Description', 11.4, 1, 201, 201);
INSERT INTO PRODUCT VALUES (203, 'Product 3', 'Product 3 Description', 61, 1, 201, 201);
INSERT INTO ADDRESS VALUES (201, 'RO', 'Cluj-Napoca', 'CJ', 'Brassai Samuel, 9');
INSERT INTO ADDRESS VALUES (202, 'RO', 'Cluj-Napoca', 'CJ', 'Ciocarliei, 6');
INSERT INTO ADDRESS VALUES (203, 'RO', 'Sfintu-Gheorghe', 'CV', 'Nicolae Iorga, 3');
INSERT INTO ADDRESS VALUES (204, 'RO', 'Miercurea-Ciuc', 'HR', 'Culmei, 14');
INSERT INTO LOCATION VALUES (201, 'Location 1', 201);
INSERT INTO LOCATION VALUES (202, 'Location 2', 202);
INSERT INTO LOCATION VALUES (203, 'Location 3', 203);
INSERT INTO STOCK (PRODUCT_NUMBER, LOCATION_NUMBER, QUANTITY) VALUES (201, 202, 4);
INSERT INTO STOCK (PRODUCT_NUMBER, LOCATION_NUMBER, QUANTITY) VALUES (201, 203, 6);
INSERT INTO STOCK (PRODUCT_NUMBER, LOCATION_NUMBER, QUANTITY) VALUES (202, 201, 10);
INSERT INTO STOCK (PRODUCT_NUMBER, LOCATION_NUMBER, QUANTITY) VALUES (202, 202, 4);
INSERT INTO STOCK (PRODUCT_NUMBER, LOCATION_NUMBER, QUANTITY) VALUES (202, 203, 5);
INSERT INTO STOCK (PRODUCT_NUMBER, LOCATION_NUMBER, QUANTITY) VALUES (203, 201, 5);
INSERT INTO STOCK (PRODUCT_NUMBER, LOCATION_NUMBER, QUANTITY) VALUES (203, 203, 9);
INSERT INTO STOCK (PRODUCT_NUMBER, LOCATION_NUMBER, QUANTITY) VALUES (203, 202, 10);
