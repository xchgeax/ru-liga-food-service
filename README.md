# Сервис по доставке еды.

## Модули приложения

### dependency_bom
* Модуль отвечает за общие зависимости сервисов. pom.xml содержит версии зависимостей и настройки для остальных модулей.

### common

* Модуль содержит кодовую базу используемую несколькоми модулями одновременно: некоторые сущности, репозитории, общие конфигурации.

### migration

* Модуль содержит все файлы для миграции Liquibase.

### cloud-gateway

* Общий шлюз по адресу 127.0.01:8080 для доступа ко всем сервисам.

### auth-service

* Модуль отвечает за авторизацию и аутентификацию.

### notification-service

* Модуль отвечает за рассылку уведомлений.

### order-service

* Модуль отвечает за создание, получение и редактирование заказов.

### kitchen-service

* Модуль отвечает за создание, получение и редактирование ресторанов, а также позиций в этих ресторанах, позволяет кухне прнимать, отказываться и завершать заказы.

### delivery-service

* Модуль отвечает за создание, получение и редактирование курьеров, автоматически назначает блюжайших курьеров для доставки заказов, а также предоставляет API для управления процесссом доставки.


## Использованные технологии:
* Java 11
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Lombok
* RabbitMQ
* Feign
* Swagger
* LiquiBase
* PostgreSQL
* MyBatis
* JUnit
* Slf4j + Log4j

