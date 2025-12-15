# Gostoso App

## Integrantes
- Italo Campodonico (trabajo individual) 

## Descripción
Gostoso es una aplicación móvil de comida desarrollada en Android utilizando Kotlin y Jetpack Compose.  
Permite a los usuarios registrarse, iniciar sesión, visualizar contenido y gestionar su perfil con persistencia de sesión.

---

## Funcionalidades
- Registro de usuarios con validación de datos
- Inicio de sesión con autenticación JWT
- Persistencia de sesión mediante DataStore
- Visualización de contenido desde API REST
- Perfil de usuario con avatar (cámara o galería)
- Manejo de estados de carga, éxito y error
- Navegación entre pantallas con Navigation Compose
- Animaciones y transiciones de interfaz

---

## Endpoints utilizados

### API REST (Backend)
Base URL:  
https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

- **POST /auth/signup** → Registro de usuario
- **POST /auth/login** → Inicio de sesión
- **GET /auth/me** → Obtención del perfil del usuario autenticado

La autenticación se maneja mediante JWT utilizando un interceptor automático en Retrofit.

### Backend propio (repositorio)
- https://github.com/Italo-Campodonico/Gostoso-api

---

## Instrucciones para ejecutar el proyecto
1. Clonar el repositorio
2. Abrir el proyecto en Android Studio
3. Sincronizar dependencias de Gradle
4. Ejecutar la aplicación en un emulador o dispositivo físico (Android 8.0 o superior)

---

## APK firmado
- https://github.com/Italo-Campodonico/Gostosov2/releases/tag/0.0.1

---

## Keystore
- El APK fue firmado con un keystore propio.
- Archivo .jks ubicado en: /keystore/gostoso-release.jks
- El archivo no se versiona por motivos de seguridad.


---

## Código fuente
- App móvil: https://github.com/Italo-Campodonico/Gostosov2
- Backend: https://github.com/Italo-Campodonico/Gostoso-api

---

## Evidencia de trabajo
- Proyecto individual
- Historial de commits: https://github.com/Italo-Campodonico/Gostosov2/commits/main
