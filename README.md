# Batalla Naval en Java

## Descripción del Proyecto
Este proyecto implementa el clásico juego de Batalla Naval en Java, donde los jugadores pueden enfrentarse contra un algoritmo desarrollado para jugar o ver cómo el algoritmo se enfrenta contra sí mismo. El juego se ejecuta en la consola y ofrece dos modos de juego: "Machine vs Player" y "Machine vs Machine".

En "Machine vs Player", el algoritmo ubica sus naves aleatoriamente y luego el jugador puede ubicar sus propias naves en el tablero. Posteriormente, el jugador y el algoritmo se turnan para realizar disparos y hundir las naves enemigas.

En "Machine vs Machine", los usuarios pueden observar una batalla entre dos instancias del algoritmo, con pausas entre cada disparo para apreciar el juego.

El proyecto se enfoca en la aplicación de conceptos de programación orientada a objetos, como clases, interfaces, herencia, polimorfismo, entre otros.

## Instrucciones de Uso
Al ejecutar el programa (clase Main), se presentará un menú en la consola donde el usuario puede seleccionar el modo de juego:

1. **Machine vs Player:** El jugador puede ubicar sus naves y luego jugar contra el algoritmo.
2. **Machine vs Machine:** Observar una batalla entre dos instancias del algoritmo.
## Estructura del Proyecto
El proyecto se organiza en diferentes paquetes y clases:

* **model:** Contiene las clases relacionadas con la lógica del juego.
  * **User:** Clase abstracta que define los métodos comunes para jugadores (humanos o máquinas).
  * **Machine:** Clase que representa al jugador controlado por el algoritmo.
  * **Player:** Clase que representa al jugador humano.
  * **Game:** Clase que maneja la lógica del juego, como turnos, disparos y detección de ganadores.
* **UI:** Contiene las clases relacionadas con la interfaz de usuario en la consola.
  * **UIGeneral:** Clase abstracta que define los métodos comunes para la interfaz de usuario.
  * **UIMachine:** Clase que maneja la interacción con el jugador controlado por el algoritmo.
  * **UIPlayer:** Clase que maneja la interacción con el jugador humano.
## Algoritmo de la Máquina
El algoritmo que controla la máquina realiza tiros aleatorios hasta que impacta directamente en una nave enemiga. Una vez que impacta, decide la dirección del siguiente disparo, y si falla, cambia de dirección hasta hundir la nave enemiga. Posteriormente, continúa buscando nuevas naves enemigas para derribar.

1. Clonar este repositorio en tu máquina local:
   ```bash
   git clone <url-repositorio>
   cd <carpeta-repositorio>
   ```

2. Compilar el código utilizando el comando `javac`:
   ```bash
   cd src/
   javac Main.java
   ```

3. Ejecutar el archivo `Main.java` para iniciar el juego:
   ```bash
   java Main
   ```
