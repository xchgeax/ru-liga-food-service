
insert into restaurants (address, status) values ('restaurant1address', 'close');
insert into restaurants (address, status) values ('restaurant2address', 'open');
insert into restaurants (address, status) values ('restaurant3address', 'open');

insert into customers(phone, email, address) values ('+79109876543', 'customer1@gmail.com','customer1address');
insert into customers(phone, email, address) values ('+77479876543', 'customer2@gmail.com','customer2address');
insert into customers(phone, email, address) values ('+79529876543', 'customer3@gmail.com','customer3address');

insert into couriers(phone, status, coordinates) values ('+79236425387', 'free','55.643, 66.634');
insert into couriers(phone, status, coordinates) values ('+79014504912', 'delivers','77.523, 88.324');
insert into couriers(phone, status, coordinates) values ('+79126678006', 'delivers','99.234, 11.123');

insert into orders (customer_id, restaurant_id, status, courier_id, timestamp) values (1,1,'complete',1,'2023-04-11 15:05:00');
insert into orders (customer_id, restaurant_id, status, courier_id, timestamp) values (3,2,'in_process',2,'2023-05-12 15:10:00');
insert into orders (customer_id, restaurant_id, status, courier_id, timestamp) values (3,3,'in_delivery',3,'2023-06-13 15:15:00');

insert into restaurant_menu_items(restaurant_id, name, price, image, description) values (1,'menuitem1',200,'http://food/image1.jpg', 'description');
insert into restaurant_menu_items(restaurant_id, name, price, image, description) values (2,'menuitem2',100,'http://food/image2.jpg', 'description');
insert into restaurant_menu_items(restaurant_id, name, price, image, description) values (2,'menuitem3',150,'http://food/image3.jpg', 'description');

insert into order_items(order_id, restaurant_menu_item, price, quantity) values (1, 1, 100, 1);
insert into order_items(order_id, restaurant_menu_item, price, quantity) values (2, 2, 200, 2);
insert into order_items(order_id, restaurant_menu_item, price, quantity) values (3, 3, 300, 3);