# Usa una imagen de Node.js para construir la aplicación
FROM node:18 as build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos package.json y package-lock.json
COPY package*.json ./

# Instala las dependencias de la aplicación
RUN npm install

# Copia los archivos de la aplicación
COPY . .

# Asegúrate de que la carpeta de fuentes existe antes de copiar
RUN mkdir -p /app/src/assets/fonts

# Copia los archivos de fuentes
COPY src/assets/fonts /app/src/assets/fonts

# Construye la aplicación Angular
RUN npm run build --prod

# Usa una imagen de Nginx para servir la aplicación
FROM nginx:alpine

# Copia los archivos de construcción de la aplicación Angular a Nginx
COPY --from=build /app/dist/lq-frontend /usr/share/nginx/html

# Expone el puerto 80 para el servidor Nginx
EXPOSE 80

# Inicia el servidor Nginx
CMD ["nginx", "-g", "daemon off;"]
