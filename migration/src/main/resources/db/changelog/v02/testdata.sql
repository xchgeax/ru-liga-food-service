
insert into restaurants (name, address, status) values ('restaurant1', 'restaurant1address', 'CLOSED');
insert into restaurants (name, address, status) values ('restaurant2', 'restaurant2address', 'OPEN');
insert into restaurants (name, address, status) values ('restaurant3', 'restaurant3address', 'OPEN');

insert into customers(phone, email, coordinates) values ('+79109876543', 'customer1@gmail.com','55.762960, 37.602162');
insert into customers(phone, email, coordinates) values ('+77479876543', 'customer2@gmail.com','55.755759, 37.653571');
insert into customers(phone, email, coordinates) values ('+79529876543', 'customer3@gmail.com','55.765223, 37.543281');

insert into couriers(phone, status, coordinates) values ('+79236425387', 'AVAILABLE','55.787740, 37.638793');
insert into couriers(phone, status, coordinates) values ('+79014504912', 'BUSY','55.799912, 37.556369');
insert into couriers(phone, status, coordinates) values ('+79126678006', 'BUSY','55.753688, 37.619787');

insert into orders (id, customer_id, restaurant_id, status, courier_id, timestamp) values ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 1,1,'CUSTOMER_CREATED',1,'2023-04-11 15:05:00');
insert into orders (id, customer_id, restaurant_id, status, courier_id, timestamp) values ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 3,2,'DELIVERY_PENDING',2,'2023-05-12 15:10:00');
insert into orders (id, customer_id, restaurant_id, status, courier_id, timestamp) values ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 3,3,'DELIVERY_PICKING',3,'2023-06-13 15:15:00');

insert into restaurant_menu_items(restaurant_id, name, price, image, description) values (1,'menuitem1',200,'https://food/image1.jpg', 'description');
insert into restaurant_menu_items(restaurant_id, name, price, image, description) values (2,'menuitem2',100,'https://food/image2.jpg', 'description');
insert into restaurant_menu_items(restaurant_id, name, price, image, description) values (2,'menuitem3',150,'https://food/image3.jpg', 'description');

insert into order_items(order_id, restaurant_menu_item, price, quantity) values ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 1, 100, 1);
insert into order_items(order_id, restaurant_menu_item, price, quantity) values ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 2, 200, 2);
insert into order_items(order_id, restaurant_menu_item, price, quantity) values ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 3, 300, 3);