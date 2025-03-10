# Промежуточная аттестация

### Задания

Напишите автотесты на API сервиса x-clients, модуль employee

### Реквизиты

- URL: https://x-clients-be.onrender.com
- Swagger: https://x-clients-be.onrender.com/docs/#/

### Дефекты в тестах

Схема и модели с кодами ответов в Swagger не соответствуют тому, что отвечает API на запросы пользователя.

#### Бизнес-тесты

1. `canGetEmployeesByCompanyId` - В ответе приходит `url` и `email` равные `null` (хотя должны приходить данные, которые пользователь отправил при создании сотрудника)
2. `canGetEmployeeById` - такая же проблема, как и в п. 1
3. `canUpdateEmployeeData` - возвращаются не все обязательные поля

#### Контракт-тесты

1. `checkGetEmployeeBodyWithValidator` - вместо `email` получаем `null`, хотя в схеме указано, что должен быть `string`
2. `checkGetEmployeesBodyWithValidator` - то же, что и в п. 1
3. `checkUpdateEmployeeBodyWithValidator` - возвращаются не все обязательные поля
4. `code201WhenEmployeeIsUpdated` - возвращается код 200, хотя в схеме указан 201
5. `code404IfEmployeeIsNotFound` - возвращается код 200, хотя должен 404
6. `code404IfEmployeeIsNotFoundWhileUpdate` - возвращается код 500, хотя должен 404