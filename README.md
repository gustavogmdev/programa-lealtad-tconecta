# Requisitos previos
   
Para poder ejecutar este proyecto, necesitas tener instalado:
- Docker
- Docker Compose
- Cliente para probar APIs como Postman o hoppscotch
- Cliente MySQL para agregar acciones(compras que dan puntos) y recompensas
  
No necesitas instalar Java ni MySQL manualmente.

# Opción 1: Usar imagen desde Docker Hub (recomendado)

1. Crea una carpeta vacía en tu computadora.
2. Dentro de esa carpeta, crea un archivo llamado `docker-compose.yml` con este contenido:

   
```yaml
version: "3.8"

services:
  db:
    image: mysql:8
    container_name: mysql-lealtad
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: programalealtad
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - appnet
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  backend:
    image: gustavogm772/programa-lealtad
    container_name: programa-lealtad
    ports:
      - "8080:8080"
    depends_on:
      db:
        condition: service_healthy
    networks:
      - appnet
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/programalealtad?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
      JWT_SECRET: T9YpN3vUeXz6mCkR7qL2bVtA8sDfJwGh
    restart: on-failure

networks:
  appnet:

volumes:
  mysql_data:
```
      

3. Abre una terminal en esa carpeta y ejecuta:
```yaml
 docker-compose up
 ```
Esto descargará la base de datos y la aplicación ya construida.

# Opción 2: Compilar desde el código fuente (opcional)

1. Clona el repositorio desde GitHub:
 ```yaml
 git clone https://github.com/gustavogmdev/programa-lealtad-tconecta
```
2. Abre la terminal en la carpeta `programa-lealtad-tconecta`.

3. Ejecuta:
```yaml 
 mvn clean package
```
4. Luego en esa misma carpeta, ejecuta:
 ```yaml
 docker-compose up --build
```
# Flujo para probar la aplicación en Postman o hoppscotch

1. Registra un usuario
 POST http://localhost:8080/api/usuarios/registrar

 Body JSON:
 
```yaml 
 {
 "username": "Alfredo",
 "password": "1234"
 }
```
2. Inicia sesión
 POST http://localhost:8080/api/auth/login

 Body JSON:
 
 ```yaml
 {
 "username": "Alfredo",
 "password": "1234"
 }
 ```
 Guarda el token que obtengas.

3. Agrega acciones (como administrador o desde base de datos):
 ```yaml
 INSERT INTO accion (nombre, puntos) VALUES ('Registro', 100);
 INSERT INTO accion (nombre, puntos) VALUES ('Compra', 150);
 INSERT INTO accion (nombre, puntos) VALUES ('Pago de servicios', 200);
 INSERT INTO accion (nombre, puntos) VALUES ('Referido', 300);
 INSERT INTO accion (nombre, puntos) VALUES ('Reseña', 50);
```
4. Agrega recompensas:
 ```yaml
 INSERT INTO recompensa (nombre, valor_en_puntos) VALUES ('Tarjeta regalo $100', 500);
 INSERT INTO recompensa (nombre, valor_en_puntos) VALUES ('Descuento 10%', 300);
 ```
5. Registra puntos:
 POST http://localhost:8080/api/puntos/registrar
```yaml
 Headers: Authorization: Bearer <token>
 ```
 Body JSON:
 
 ```yaml
 {
 "accionId": 1
 }
```
6. Consulta tu saldo:
 GET http://localhost:8080/api/usuarios/saldo
```yaml
 Headers: Authorization: Bearer <token>
 ```
7. Ver recompensas disponibles
GET http://localhost:8080/api/recompensas
```yaml
 Headers: Authorization: Bearer <token>
 ```
8. Canjea una recompensa:
 POST http://localhost:8080/api/canjes?recompensaId=1
```yaml
 Headers: Authorization: Bearer <token>
 ```
9. Consulta tus canjes:
 GET http://localhost:8080/api/canjes/mios
```yaml
 Headers: Authorization: Bearer <token>
 ```
# Notas:

- Asegúrate de que todos los endpoints protegidos tengan el header:
 Authorization: Bearer <tu_token>
- Para detener la app, usa Ctrl + C en la terminal o `docker-compose down`.
- Puedes ver el contenedor corriendo en Docker Desktop o con `docker ps`.
- Si en la opción 1 la imagen no se descarga, hacer pull manualmente (enlace abajo).
  
Comando pull: 
```yaml
docker pull gustavogm772/programa-lealtad
```
Repositorio de código:
GitHub: https://github.com/gustavogmdev/programa-lealtad-tconecta

Imagen Docker pública: 
Docker Hub: https://hub.docker.com/r/gustavogm772/programa-lealtad

