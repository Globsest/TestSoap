# Test SOAP Service

- XSD схема по адресу: `src/main/resources/wsdl/user-service.xsd`
- Доступ по адресу: `http://localhost:8080/ws`

---

## Сборка и запуск проекта

- Java 17
- Maven 3.6+

### ▶Сборка

### Шаги 


# 1. Перейти в корень проекта
cd путь_до_папки/TestSoap

# 2. Сборка проекта
mvn clean install

# 3. Запуск приложения
mvn spring-boot:run

После запуска WSDL доступен по адресу - http://localhost:8080/ws/users.wsdl

 
Отправка SOAP-запросов через curl (выполнял через Git Bash)
1. Перейти в папку с запросами - cd путь_до_папки/TestSoap/soap-requests
2. Выполнить нужный curl-запрос:


Создать пользователя (createUser.xml)
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -H "SOAPAction: \"http://globsest.com/testsoap/CreateUser\"" \
  -d @createUser.xml

Получить всех пользователей (getAllUsers.xml)
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -H "SOAPAction: \"http://globsest.com/testsoap/GetAllUsers\"" \
  -d @getAllUsers.xml

Получить одного пользователя (getUser.xml)
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -H "SOAPAction: \"http://globsest.com/testsoap/GetUser\"" \
  -d @getUser.xml

Обновить пользователя (updateUser.xml)
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -H "SOAPAction: \"http://globsest.com/testsoap/UpdateUser\"" \
  -d @updateUser.xml

Удалить пользователя (deleteUser.xml)
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -H "SOAPAction: \"http://globsest.com/testsoap/DeleteUser\"" \
  -d @deleteUser.xml
