# Запуск проекта

Можно запустить все по отдельности или в docker контейнере.

## По отдельности

Для этого в папке проекта (web-store) необходимо установить все зависимости через maven и ввести команду для запуска сервера:

```bash
mvn spring-boot:run
```

Сервер будет доступен по адресу localhost:8080/api/{products, category и т.п. из контроллеров}

Далее схожим образом делаем в папке frontend. 

Устанавливаем зависимости

```bash
npm install
```

И запускаем

```bash
npm run dev
```

Сайт будет доступен по адресу localhost:3000/

## Docker

Для запуска проекта в контейнере, необходимо открыть в терминале директорию с данным проектом и ввести команду

```bash
sudo docker-compose up
```

Далее выполнятся все скрипты и можно будет взаимодействовать с приложением

# Инфомация по проекту

Данный репозиторий включает в себя два компонента:

- frontend часть
- backend часть

Про каждую далее будет краткое описание того, что находится в проекте.

Изначально, при старте проекта база данных будет пустая, поэтому будут подгружаться данные из json файлов для удобного тестирования.

Пользователь администратор (для доступа к админ панели):

- username: admin
- password: admin12345

## Backend

Является корневой папкой проекта.

Написан с использованием Spring. Логика подчиняется паттерну MVC.

Содержит в себе настройки для запуска docker контейнера.

Вся логика находится в директории src и содержит в себе:

- data - содержит в себе тестовые данные, которые загружаются при первом запуске проекта;
- main/java... - содержит в себе всю MVC логику приложения. Она состоит в свою очередь из:
  - controllers - контроллеры, которые отвечают за получение и отправку запросов и ответов от сервера
  - helperClasses - дополнительные классы, которые необязательно будет включать в результат (в них входит чтение данных из файла, преобразование их и загрузка в БД)
  - models - модели базы данных (таблицы, сущности)
  - payload - классы, связанные с запросами и ответами на регистрацию и вход в аккаунт
  - repository - репозитории, которые отвечают за доступ к базе данных
  - security - содержит в себе настройки аутентификации приложения
  - service - сервисы, отвечающие за получение данных из репозитория

Для получения более подробной информации, можно почитать комментарии внутри классов.

## Frontend

Находится в корневой папке проекта backend.

Написан с использованием Vue.js.

Проект содержит в себе несколко основных страниц:

- Главная страница - отображает часть общего списка продуктов
- Профиль пользователя - там можно посмотреть информацию о пользователе. Профиль содержит следующие вкладки:
  - Аккаунт - просто информация об аккаунте: id, username и всякая информация об аутентификации. Некоторые поля поставил просто так, чтобы заполнить страницу. При необходимости можно будет заменить.
  - Заказы - история заказов пользователя
  - Настройки - пока в них ничего, в планах было добавить хотя бы переключение темы приложения
- Корзина - отображает продукты, которые пользователь добавил в корзину
- Панель администратора. Находится в профиле, если пользователь имеет достаточно прав.