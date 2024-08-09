# Используем официальный Node.js образ
FROM node:18

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы package.json и package-lock.json
COPY frontend/package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем весь проект во временную директорию
COPY frontend .

# Открываем порт, на котором будет работать фронтенд
EXPOSE 3000

# Запускаем приложение
CMD ["npm", "run", "dev"]