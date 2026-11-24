PPAI - Sistema de Consulta de Bolsines

Proyecto práctico de aplicación integradora (Diseño de Sistemas de Información - UTN FRC) para el seguimiento y consulta de ubicación de bolsines de comisiones médicas.

## Tecnologías utilizadas
* **Backend:** Java 17+, Spring Boot, Maven
* **Base de datos:** H2 (en memoria)
* **Servicios auxiliares:** Mock de rastreo GPS (`gps-tracker-mock`)

---

## Requisitos previos
Antes de levantar el proyecto, asegúrate de tener instalado:
* **JDK 17** o superior
* **Maven** (o utilizar el wrapper `./mvnw` incluido en el proyecto)
* **Git**

---

## Cómo levantar el proyecto

### 1. Clonar el repositorio
git clone [https://github.com/tu-usuario/tu-repositorio.git](https://github.com/tu-usuario/tu-repositorio.git)
cd tu-repositorio

### 2. Levantar la api del servicio de GPS
cd PPAI/gps-tracker-mock
./mvnw spring-boot:run

### 3. Levantar el backend
cd PPAI/backend
./mvnw spring-boot:run

### 4. Levantar el frontend
cd PPAI/frontend
npm run dev

Aclaraciones:
- Puertos utilizados:
    - Backend: 8081
    - Api del GPS: 8080
    - Frontend: 5174
 
- Base de datos:
    - Se utiliza una base de datos en memoria usando JPA (como en el repo que subió la cátedra)
    - El archivo data.sql contiene todos los mock de prueba
