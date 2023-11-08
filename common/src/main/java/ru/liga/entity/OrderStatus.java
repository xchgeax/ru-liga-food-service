package ru.liga.entity;

public enum OrderStatus {
    CUSTOMER_CREATED {
        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказ %s оформлен и ожидает оплаты.", order.getId());
        }
    },
    CUSTOMER_PAID {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getStatus() == OrderStatus.CUSTOMER_CREATED;
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказ %s оплачен.", order.getId());
        }
    },
    CUSTOMER_CANCELLED {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getStatus() == OrderStatus.CUSTOMER_CREATED || order.getStatus() == OrderStatus.CUSTOMER_PAID;
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказ %s отменён.", order.getId());
        }
    },
    KITCHEN_ACCEPTED {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getStatus() == OrderStatus.CUSTOMER_PAID;
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказ %s готовится.", order.getId());
        }
    },
    KITCHEN_DENIED {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getStatus() == OrderStatus.CUSTOMER_PAID;
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказ %s отменён кухней, свяжитесь с рестораном по номеру %s для уточнения подробностей.",
                    order.getId(),
                    order.getRestaurant().getAddress());
        }
    },
    KITCHEN_REFUNDED {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getStatus() == OrderStatus.KITCHEN_DENIED;
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказа %s был возмещён.", order.getId());
        }
    },
    DELIVERY_PENDING {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getStatus() == KITCHEN_ACCEPTED;
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Для вашего заказа %s не найдено свободного курьера.", order.getId());
        }
    },
    DELIVERY_PICKING {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getCourier() != null && (order.getStatus() == OrderStatus.KITCHEN_ACCEPTED || order.getStatus() == OrderStatus.DELIVERY_PENDING);
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказ %s ожидает доставки", order.getId());
        }

        @Override
        public String getCourierNotificationMessage(Order order) {
            return String.format("Вам назначен заказ %s", order.getId());
        }
    },
    DELIVERY_DELIVERING {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getStatus() == OrderStatus.DELIVERY_PICKING;
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказ %s доставляется.", order.getId());
        }

        @Override
        public String getCourierNotificationMessage(Order order) {
            return String.format("Вы начали доставку заказа %s", order.getId());
        }
    },
    DELIVERY_COMPLETE {
        @Override
        public boolean isAcceptableForOrder(Order order) {
            return order.getStatus() ==OrderStatus.DELIVERY_DELIVERING;
        }

        @Override
        public String getCustomerNotificationMessage(Order order) {
            return String.format("Ваш заказ %s доставлен к вам.", order.getId());
        }

        @Override
        public String getCourierNotificationMessage(Order order) {
            return String.format("Вы доставили заказ %s", order.getId());
        }
    };

    public boolean isAcceptableForOrder(Order order) {return false;}

    public String getCustomerNotificationMessage(Order order) {return "";}

    public String getCourierNotificationMessage(Order order) {return "";}
}
