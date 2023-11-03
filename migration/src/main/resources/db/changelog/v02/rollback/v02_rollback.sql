delete from restaurants where name in ('restaurant1', 'restaurant2', 'restaurant3');

delete from customers where email in ('customer1@gmail.com', 'customer2@gmail.com', 'customer3@gmail.com');

delete from couriers where phone in ('+79236425387', '+79014504912', '+79126678006');

delete from orders where timestamp in ('2023-04-11 15:05:00', '2023-05-12 15:10:00', '2023-06-13 15:15:00');

delete from restaurant_menu_items where name in ('menuitem1', 'menuitem2', 'menuitem3');

delete from order_items where order_id in (1, 2, 3);