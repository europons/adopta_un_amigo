# 🐾 Adopta un Amigo 🐾

Mini aplicación desarrollada con **Spring Boot** para gestionar la adopción de mascotas.  
Permite registrar, listar, editar, eliminar y filtrar mascotas, con validación de datos y manejo de errores.

---

## 🚀 Objetivo

El objetivo de la aplicación es practicar el desarrollo de una mini web con **Spring Boot**, **Thymeleaf** y **validación de formularios**, utilizando una lista en memoria como almacenamiento temporal.

---

## ⚙️ Tecnologías y dependencias

- **Java 25+**
- **Spring Boot Starter Web**
- **Spring Boot Starter Thymeleaf**
- **Spring Boot Starter Validation**
- **Lombok**
- **Bootstrap 5**

---

## 📂 Estructura del proyecto

src/
 └── main/
     ├── java/daw/jgp/adoptaunamigo/
     │   ├── controller/
     │   │   ├── MascotaFormController.java
     │   │   ├── MascotaViewController.java
     │   │   └── MascotaApiController.java (opcional)
     │   ├── model/
     │   │   └── Mascota.java
     │   └── service/
     │       └── MascotaService.java
     └── resources/
         └── templates/
             ├── formulario.html
             ├── detalle.html
             └── lista.html

---

## 🌐 Rutas principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| **GET** | `/mascotas/nueva` | Muestra el formulario para registrar una nueva mascota |
| **POST** | `/mascotas/nueva` | Procesa el formulario y crea una nueva mascota |
| **GET** | `/mascotas/{id}` | Muestra el detalle de una mascota |
| **GET** | `/mascotas` | Lista todas las mascotas registradas |
| **GET** | `/mascotas/editar/{id}` | Muestra el formulario de edición |
| **POST** | `/mascotas/editar` | Guarda los cambios realizados |
| **GET** | `/mascotas/eliminar/{id}` | Elimina una mascota |
| **GET** | `/mascotas/filtrar` | Filtra por especie o nombre |

---

## 🧩 Validaciones

- `@NotBlank`, `@NotNull`, `@Min`, `@Size` → validaciones básicas de los campos.
- **Regla personalizada:** si la especie es `"Otros"`, la descripción es obligatoria.
- Manejo de errores con mensajes flash y redirección segura.

---

## 🎨 Interfaz

Diseñada con **Bootstrap 5**, con una estructura clara y responsive:
- Formularios con mensajes de error
- Listado con botones de acción (Detalles, Editar, Eliminar)
- Pie de página informativo

---

## 👨‍💻 Autor

Desarrollado por **Javier García Pons**  
Proyecto de prácticas de **Spring Boot (2º DAW)** — *Desenvolupament WEB en Entorn Servidor*  
