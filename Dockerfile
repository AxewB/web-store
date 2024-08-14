# Используем базовый образ с Maven и OpenJDK
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем остальные файлы проекта и собираем приложение
COPY . .
RUN mvn package -DskipTests

# Используем базовый образ OpenJDK для запуска приложения
FROM eclipse-temurin:17-jdk

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл из предыдущего этапа сборки
COPY --from=build /app/target/*.jar app.jar

# Копируем файлы данных
COPY src/data /app/src/data

# Открываем порт, на котором работает приложение
EXPOSE 8080

# Запускаем приложение
CMD ["java", "-jar", "app.jar"]