 - Proyecto: API de Detección de Mutantes (Examen Mercado Libre)

Este proyecto implementa una API RESTful con **Spring Boot** para determinar si una secuencia de ADN pertenece a un mutante. Cumple con los requisitos del **Nivel 3**, incluyendo la lógica de detección, persistencia en **H2** y el endpoint de estadísticas (`/stats`).

---

- Despliegue en Producción (Render URL)

La API está desplegada y corriendo en un servicio web continuo.

**URL Pública de la API:**
[https://mutantes-api-zt9e.onrender.com]

---

-  Instrucciones de Ejecución Local

Para ejecutar la API en tu entorno local, necesitas tener instalado **Java 21** (o la versión que uses) y **Gradle**.

1.  **Clonar el Repositorio:**
    ```bash
    git clone [https://github.com/nataliautn66-art/mutantes-api.git](https://github.com/nataliautn66-art/mutantes-api.git)
    cd mutantes-api
    ```

2.  **Ejecutar la Aplicación (Usando el Wrapper de Gradle):**
    ```bash
    ./gradlew bootRun
    ```

---

-  Endpoints de la API

| Endpoint | Método | Descripción |
| :--- | :--- | :--- |
| `/mutant/` | `POST` | Analiza una secuencia de ADN y la guarda en la base de datos H2. |
| `/stats` | `GET` | Devuelve las estadísticas de las verificaciones de ADN. |

1. Detección de Mutantes (`POST /mutant/`)

* **Ruta:** `/mutant/`
* **Cuerpo (Body):** Envía el array de ADN en formato JSON.

**Ejemplo:**
```json
{
    "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
}
Respuesta,Código HTTP
Mutante,200 OK
Humano,403 Forbidden
2. Estadísticas (GET /stats)
Ruta: /stats

Descripción: Devuelve un JSON con count_mutant_dna, count_human_dna y el ratio.

Ejemplo de Respuesta:

JSON

{
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
}
- Siguiente Paso: Diagrama de Secuencia y Cobertura

Una vez que hayas actualizado el `README.md` en GitHub, continuemos con el siguiente requisito del Nivel 3:

Paso 2: Crear el Diagrama de Secuencia

El requisito del examen es incluir un Diagrama de Secuencia que muestre el flujo del servicio.

**Acción:** Generaremos un Diagrama de Secuencia para el *endpoint* **`POST /mutant/`**. Puedes usar una herramienta en línea gratuita (como PlantUML, Mermaid o cualquier editor de diagramas) para crearlo y luego guardar la imagen (PNG/SVG) o el PDF en tu repositorio.

Aquí está la representación del flujo que debes dibujar o generar:

**Flujo del Diagrama: `POST /mutant/`**

1.  **Cliente** → **MutantController**: Envía la solicitud `POST /mutant/` con el ADN.
2.  **MutantController** → **MutantService**: Llama al método de servicio (`checkAndSave(DNA)`).
3.  **MutantService** → **DnaRecordRepository**: **Verifica si el ADN existe** para evitar duplicados.
4.  **MutantService** → **MutantDetector**: Si es nuevo, ejecuta la lógica `isMutant(DNA)`.
5.  **MutantService** → **DnaRecordRepository**: **Guarda el registro** (indicando si es mutante o humano) en la Base de Datos H2.
6.  **MutantService** → **MutantController**: Devuelve el resultado.
7.  **MutantController** → **Cliente**: Devuelve la respuesta HTTP (200 o 403).



**Una vez que el diagrama esté creado y subido a GitHub (idealmente en una carpeta `docs/` o similar), pasamos a la Cobertura de Código.**
