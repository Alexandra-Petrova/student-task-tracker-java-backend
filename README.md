# Student Task Tracker Backend

Бэкенд-сервис для трекинга учебных задач.
Реализован функционал для управления задачами с поддержкой пользователей, групп задач, JWT-авторизацией, ролями (USER/ADMIN) и сбором статистики.

## Требования для запуска

* Docker
* Docker Compose

## Запуск проекта

1. Сначала необходимо собрать приложение:

```bash
./gradlew clean bootJar
```

2. Запустить Docker:

```bash
docker-compose up -d --build
```

Docker выполнит:

* запуск контейнера PostgreSQL
* создание базы `studentTaskTracker`
* сборку Docker-образа приложения
* копирование собранного jar в контейнер
* запуск Spring Boot на [http://localhost:8080](http://localhost:8080)

3. После запуска перейти в Swagger:
   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

4. После регистрации или авторизации необходимо нажать на кнопку **Authorize** и ввести полученный `access token` для дальнейшей работы.

5. Для остановки приложения:

```bash
docker-compose down
```

## Пользователь с ролью ADMIN

Создаётся автоматически при старте приложения:

* **email:** [admin@mail.ru](mailto:admin@mail.ru)
* **password:** password

---

## Авторизация (доступно всем)

**Auth Controller (auth-controller-impl):**

* `POST /api/auth/sign-up` – регистрация пользователя по email с ролью USER
* `POST /api/auth/sign-in` – авторизация пользователя и получение токенов
* `POST /api/auth/refresh` – отправка refreshToken для получения нового accessToken

---

## Задачи (Task Controller, доступно авторизованным пользователям)

**Статусы задач:** `NEW`, `IN_PROGRESS`, `DONE`

* `POST /api/tasks` – создание задачи с названием и описанием, статус всегда `NEW`. Можно указать группу, если она создана; иначе `null`.
* `GET /api/tasks` – получение всех своих задач
* `GET /api/tasks/{id}` – получение задачи по id
* `PUT /api/tasks/{id}` – изменение названия, описания, статуса задачи, дедлайна, группы задачи по id
* `POST /api/tasks/{taskId}/group/{groupId}` – добавление задачи в группу
* `PUT /api/tasks/{taskId}/group/{groupId}` – изменение группы задачи
* `DELETE /api/tasks/{taskId}/group` – удаление группы у задачи
* `DELETE /api/tasks/{taskId}` – удаление задачи

---

## Группы задач (Task Group Controller, доступно авторизованным пользователям)

* `POST /api/task-groups` – создание группы с названием
* `GET /api/task-groups` – получение всех своих групп
* `GET /api/task-groups/{id}` – получение группы по id
* `PUT /api/task-groups/{id}` – изменение названия группы по id
* `DELETE /api/task-groups/{id}` – удаление группы

---

## Эндпоинты для ADMIN (Admin Controller)

* `GET /api/admin/users` – получение всех пользователей
* `GET /api/admin/tasks` – получение всех задач в системе
* `GET /api/admin/groups` – получение всех групп в системе
* `GET /api/admin/users/{userId}/tasks` – получение задач пользователя по userId
* `GET /api/admin/stats/groups` – получение статистики: количество задач в каждой группе
* `GET /api/admin/stats/status` – получение статистики: количество задач в каждом статусе
