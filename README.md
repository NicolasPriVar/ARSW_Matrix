# ARSW - Matrix Simulation: Neo vs. Agentes

Este proyecto implementa una simulación inspirada en *The Matrix*, donde **Neo** debe escapar de un tablero de agentes y llegar a un teléfono ubicado en el centro de una matriz 10x10. La simulación emplea **programación concurrente en Java** usando hilos para representar a Neo y los agentes que lo persiguen.

## 🎯 Objetivo

Simular un entorno dinámico en el que:
- Neo se mueve de forma autónoma buscando el camino más corto hacia el teléfono.
- Hasta 4 agentes lo persiguen, intentando capturarlo antes de que llegue a su objetivo.
- El juego termina si Neo es atrapado o si llega exitosamente al teléfono.

## 🧠 Lógica de la Simulación

- **Tablero (`Tablero`)**: Matriz 10x10 que representa el entorno. Contiene:
  - Muros (`Entidad.MURO`)
  - Casillas vacías (`Entidad.VACIO`)
  - Neo (`Entidad.NEO`)
  - Agentes (`Entidad.AGENTE`)
  - Teléfono (`Entidad.TELEFONO`)

- **Neo (`Neo`)**:
  - Se ejecuta como un hilo (`Runnable`).
  - Evalúa movimientos posibles (arriba, abajo, izquierda, derecha).
  - Prioriza movimientos que lo acerquen al teléfono.
  - Se detiene si:
    - Llega al teléfono (gana).
    - Es alcanzado por un agente (pierde).

- **Agentes (`Agente`)**:
  - Se ejecutan de manera coordinada por el `ControladorAgentes`.
  - Se acercan a la posición de Neo calculando el camino más corto (usando Manhattan distance).
  - Si uno de ellos llega a la posición de Neo, termina la simulación con derrota para Neo.

- **Controlador de Agentes (`ControladorAgentes`)**:
  - Ejecutado como un hilo adicional.
  - Coordina el movimiento secuencial de los agentes cada 2 segundos.
  - Verifica si algún agente atrapó a Neo.

- **Aplicación principal (`MatrixApp`)**:
  - Inicializa el tablero y coloca elementos aleatoriamente.
  - Genera los muros, el teléfono, Neo y los agentes.
  - Ejecuta los hilos y gestiona el ciclo de vida del juego.

## 🛠️ Tecnologías y Conceptos Utilizados

### Tecnologías
- **Java 17**
- **Spring Boot** *(solo para facilitar el arranque del proyecto; no se utiliza en el flujo lógico principal)*

### Conceptos de Programación Aplicados
- **Programación Concurrente**: Uso de múltiples hilos (`ExecutorService`, `Runnable`) para simular comportamientos paralelos.
- **Sincronización de Recursos**: Acceso sincronizado al tablero para evitar condiciones de carrera.
- **Diseño modular y limpio**: Separación de lógica, modelo y controladores.
- **Algoritmos de búsqueda simplificados**: Estrategia greedy basada en distancia Manhattan.

## 🧪 Ejecución

Al ejecutar la aplicación, se inicializa la simulación y se imprime el tablero en consola en cada turno. Neo y los agentes se moverán automáticamente, y el juego finalizará cuando uno gane.

Ejemplo de consola:

![image](https://github.com/user-attachments/assets/43c3fa29-ed9e-4314-b106-aa015abdae3d)  
![image](https://github.com/user-attachments/assets/5f239cf5-c3c7-42aa-9855-ac5fe3b63107)  
En este caso, Neo llegó al teléfono, por ende se muestra:  
![image](https://github.com/user-attachments/assets/ed94b1f2-c043-447f-b127-7e868beaa4e6)  


## 📌 Notas adicionales

- La simulación termina automáticamente, mostrando el resultado en consola.
- El diseño es extensible para agregar más entidades (por ejemplo, obstáculos móviles, otros personajes, etc.).
- El uso de hilos y sincronización permite estudiar conceptos de concurrencia aplicados a entornos simulados.

