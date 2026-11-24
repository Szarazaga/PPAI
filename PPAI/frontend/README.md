# Frontend — Caso de Uso 36: Consultar Seguimiento de Bolsines

Aplicación React + Vite para el sistema de gestión de bolsines de Correo Nacional. Consume el
backend Spring Boot disponible en `http://localhost:8081/api` y presenta la pantalla del Caso
de Uso como un flujo guiado paso a paso.

## Requisitos

- Node.js 18 o superior (probado con Node 26).
- Backend de PPAI en ejecución sobre el puerto `8081`.

## Instalación y ejecución

```bash
cd PPAI/frontend
npm install
npm run dev
```

La aplicación queda disponible en `http://localhost:5173`.

Build de producción:

```bash
npm run build
npm run preview
```

## Conexión con el backend

Por defecto se utiliza el **proxy de Vite** (`vite.config.js`): todas las llamadas a `/api`
se redirigen a `http://localhost:8081`, evitando problemas de CORS durante el desarrollo.
El proxy además elimina el encabezado `Origin` para que el backend (que declara
`@CrossOrigin(origins = "http://localhost:8081")` en `PantallaEncargado`) no rechace los
`POST` con un `403 Invalid CORS request`.

Si se desea apuntar a otra URL, definir la variable de entorno antes de `npm run dev`:

```bash
VITE_API_BASE_URL=http://localhost:8081/api npm run dev
```

> Nota: el controlador `PantallaEncargado` del backend tiene
> `@CrossOrigin(origins = "http://localhost:8081")`. Al usar el proxy de Vite no hay
> peticiones cross-origin, por lo que no es necesario modificarlo. Si se configura
> `VITE_API_BASE_URL` en forma absoluta, el navegador aplicará CORS y el backend deberá
> permitir el origen `http://localhost:5173`.

## Flujo implementado (flujo guiado paso a paso)

La interfaz funciona como una **máquina de estados** (`pasoActual`) con los siguientes estados,
en orden, y un stepper en la barra superior superior. El usuario solo ve y puede actuar sobre la
etapa activa; las acciones de la siguiente etapa quedan ocultas hasta avanzar.

1. **`LOGIN` — Inicio de Sesión (Paso 0).** Pantalla de inicio de sesión de Correo Nacional
   (`PantecaEncargado`, cuando `pasoActual === 'LOGIN'`): campos de usuario/contraseña
   (precargados en `encargado1` / `1234`), botón “Ingresar como encargado1” y “Ingresar al
   sistema”.
   - Éxito: `POST /api/auth/login` → avanza a **`INICIO_CONSULTA`**.
   - Falla: muestra el error devuelto por el backend; se queda en `LOGIN`.

2. **`INICIO_CONSULTA` — Iniciar Consulta de Ubicación (Paso 1).** Solo muestra la tarjeta de
   bienvenida con el botón **“Consultar Ubicación de Bolsines”**. Al hacer clic:
   - `GET /api/bolsines/consultar-ubicacion`.
   - `200 OK`: guarda `codigoCM`, `nombreCM`, `ubicaciones`, `urlMapa` → avanza a
     **`MAPA_Y_SELECCION`**.
   - `404` (**Flujo Alternativo A1**): muestra alerta con `error` devuelto → se queda en
     `INICIO_CONSULTA` permitiendo reintento.
   - Error HTTP: alerta de error → se queda.

3. **`MAPA_Y_SELECCION` — Mapa, Filtrado y Selección de Bolsín (Paso 2).** Muestra:
   - Insignia fija en la barra superior con la CM del encargado.
   - **Panel de filtros:** tarjeta “Filtros de Búsqueda (Paso 6)” de ancho 100% alineada con el
     contenedor inferior, con **una sola fila horizontal**: input numérico de `numeroPrecinto`,
     select/input de `codigoCmDestino` (`CM-ROS-02`, `CM-JUJ-01`, `CM-SLA-01`, `CM-MDQ-01`,
     `CM-MDZ-03`, `CM-BA-01`, etc.), botones **Limpiar Filtros** y **Filtrar** (`GET
     /api/bolsines/filtrar`), y el botón **“Reiniciar Consulta”** en la cabecera de la tarjeta
     (prop `onReiniciarConsulta`, vuelve a `INICIO_CONSULTA`).
   - Mapa Leaflet/OpenStreetMap con marcadores sobre `[latitud, longitud]` + tabla de bolsines
     (botón “Seleccionar Bolsín” y clic en marcador). Las tarjetas del mapa y la tabla tienen la
     misma altura; el contenedor del mapa es `overflow: hidden`; el mapa recalcula su tamaño con
     `map.invalidateSize()` y ajusta el encuadre con `map.fitBounds(bounds, { padding: [45, 45],
     maxZoom: 7 })` para centrar los marcadores `#101`, `#111`, `#112`, `#113` sin pegarse a los
     bordes.
   - Clic en un bolsín (`POST /api/bolsines/seleccionar-bolsin?numeroBolsin=N`) → avanza a
     **`CONFIRMACION_CORREO`** (modal abierto).

**Filtros mutuamente excluyentes (sin cartel rojo):** si `numeroPrecinto` tiene texto
el select de CM destino queda `disabled`; si `codigoCmDestino` está seleccionado el input de
precinto queda `disabled` (estilos visuales claros de campo bloqueado: opacidad reducida +
`cursor-not-allowed`). Al borrar el texto del precinto, el CM destino vuelve a `''`;
hacer clic en **Limpiar Filtros** vuelve ambos campos a habilitados. La interacción sigue
siendo: puedes filtrar por número de precinto o por CM destino, nunca ambos a la vez.`numeroPrecinto` o `codigoCmDestino` (no ambos a la vez). Si se ingresan ambos, el sistema
muestra una alerta de advertencia (“Debe filtrar por un solo criterio…”) y no invoca la API
hasta que se corrija.

4. **`CONFIRMACION_CORREO` — Confirmación de Envío de Correo al GCM Destino (Paso 3).**
   Muestra el mapa y tabla atenuados/bloqueados (`pointer-events: none`) y un modal con el N° de
   bolsín seleccionado, ubicación actual y `mensajeConfirmacion`. Acciones:
   - **“Confirmar y Enviar Correo”** → `POST /api/bolsines/confirmar-envio-correo?confirmado=true`.
   - **“Volver a elegir otro bolsín”** → vuelve a `MAPA_Y_SELECCION`.
   - **“Cancelar Envío”** → `POST /api/bolsines/confirmar-envio-correo?confirmado=false`
     (Flujo Alternativo A5).
   - Cualquiera de ellos → avanza a **`FIN_CU`**.

5. **`FIN_CU` — Resultado Final del Caso de Uso (Paso 4).** Muestra tarjeta de confirmación
   (exito del CU 31) o aviso del A5, y el botón **“Finalizar / Realizar nueva consulta”**
   → vuelve a `INICIO_CONSULTA`.

Stepper: cuatro pasos etiquetados `INICIO_CONSULTA`, `MAPA_Y_SELECCION`,
`CONFIRMACION_CORREO` y `FIN_CU` (ver `Stepper.jsx`). El estado activo en el stepper refleja
exactamente el valor de `pasoActual`.

## Estructura

```
src/
├── components/
│   ├── FiltroBolsines.jsx      # Panel de filtros (Paso 2 / A2)
│   ├── MapaBolsines.jsx        # Mapa Leaflet + marcadores
│   ├── ModalConfirmacion.jsx   # Confirmación de envío de correo
│   ├── Stepper.jsx             # Barra de progreso del flujo (4 pasos)
│   └── TablaBolsines.jsx       # Tabla/tarjetas de bolsines en viaje
├── pages/
│   └── PantecaEncargado.jsx  # Wizard de flujo guiado: login + CU 36 pasos
├── services/
│   └── api.js                  # Cliente Axios y manejo de errores HTTP
├── App.jsx                     # Renders el WizardEncargado
├── index.css                   # Estilos institucionales + estilos del wizard
└── main.jsx
```
