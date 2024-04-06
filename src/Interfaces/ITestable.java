package Interfaces;

/**
 * Esta Interface es implementada para hacer pruebas y cambios en un tablero de juego
 *
 * @author euler
 */
public interface ITestable {
    //Las naves del juego, de mayor a menor tamaño
    String[] NAVES = {"Destructor", "Submarino", "Crucero", "Acorazado", "Carrier"};
    //El tamaño de las anteriores naves
    int[] ESPACIOS = {5, 4, 3, 3, 2};
    //Filas del tablero, que van de A-j
    String FILAS = "ABCDEFGHIJ";
    //A la hora de imprimir el tablero creado, se usarán los siguientes símbolos para representar cada una de las
    //anteriores naves (en el mismo orden).
    String[] SIMBOLOS_NAVES = {".", "-", "*", "+", "&"};

    /**
     * Método de tipo default cuya función es probar si se puede ubicar una nave específica en un tablero específico
     *
     * @param tablero  Tablero en el cual se ubicará la nave
     * @param nave     Índice de la nave - 1, en NAVES
     * @param posicion Punto de partida del cual se empezará a ubicar la nave. En su index: 0 fila, 1 columna,
     *                 2 orientación, 3 dirección.
     * @return True de poderse ubicar sin problemas, Fase de no lograse ubicar
     */
    default boolean probarTablero(String[][] tablero, int nave, String[] posicion) {
        //posición [fila en letra, columna, orientation, dirección
        int posicionInicio; //desde donde empieza a ser ubicada la nave
        int posicionFin; //desde donde termina de ser ubicada la nave
        boolean columna = false; //true implica que va a ser ubicado de manera vertical de lo contrario será horizontal
        try {
            /*
             * La siguiente serie de if decide desde que posición hasta qué posición va a ubicarse la nave.
             * La orientación puede ser    | La Dirección puede ser
             * (+) Horizontal               (+) Arriba  - (-) Abajo
             * (-) Vertical                 (+) Derecha - (-) Izquierda
             */

            if (posicion[2].equals("+") && posicion[3].equals("+")) { //Hacia arriba
                /*
                 * En este caso, la columna va a ser siempre la misma, lo que va a variar el el valor de la fila.
                 * Terminará de ubicar la nave hasta la fila indicada y desde ese posición en la que termina menos
                 * los espacios que posee la nave - 1 (-1 porque empezamos a contar desde el punto de inicio y no desde el
                 * siguiente) y a su vez, todo los anterior más uno
                 * Ejemplo: E5++; nave: 1;
                 * posicionFin = 4, posicionInicio = 4 - 5 = -1 + 1 = 0
                 * Columna indica que será ubicada de manera vertical
                 */
                posicionFin = FILAS.indexOf(posicion[0]);
                posicionInicio = posicionFin - ESPACIOS[nave - 1] + 1;

                columna = true;
            } else if (posicion[2].equals("+") && posicion[3].equals("-")) { //Hacia abajo
                /*
                 * En este caso, la columna va a ser siempre la misma, lo que va a variar el el valor de la fila.
                 * Comenzará a ubicar la nave desde la fila indicada hasta esa posición más los espacios que posee la
                 * nave - 1 (cuando se ubican las naves en el tablero, iran de mayor a menor, y de 1 a 5) y a su vez,
                 * todo los anterior menos uno.
                 * Ejemplo: A5+-; nave: 1;
                 * posicionInicio = 0, posicionFin = 0 + 5 = 5 - 1 = 0
                 * Columna indica que será ubicada de manera vertical
                 */
                posicionInicio = FILAS.indexOf(posicion[0]);
                posicionFin = posicionInicio + ESPACIOS[nave - 1] - 1;
                columna = true;
            } else if (posicion[2].equals("-") && posicion[3].equals("+")) { //Hacia la Derecha
                /*
                 * En este caso, la fila va a ser siempre la misma, lo que va a variar el el valor de la columna.
                 * Empezará a ubicar la nave a partir de la columna indicada - 1 (porque en el tablero el rango va de
                 * 1-10, pero en la matriz el rango va de 0 - 9) en la lista posición, hasta la cantidad de
                 * espacios que posee la nave - 1 (cuando se ubican las naves en el tablero, iran de mayor a menor, y
                 * de 1 a 5), todo los anterior menos uno.
                 * Ejemplo: A5-+; nave: 1;
                 * posicionInicio = 5, posicionFin = 5 + 5 = 10 - 1 = 9
                 * Al no asignar valor a columna, este quedará en false, lo que indica que se ubica de manera vertical
                 */
                posicionInicio = Integer.parseInt(posicion[1]) - 1;
                posicionFin = posicionInicio + ESPACIOS[nave - 1] - 1;
            } else if (posicion[2].equals("-") && posicion[3].equals("-")) { //Hacia la izquierda
                /*
                 * En este caso, la fila va a ser siempre la misma, lo que va a variar el el valor de la columna.
                 * Comenzará a ubicar la nave desde la columna indicada - 1 (porque en el tablero el rango va de 1-10,
                 *  en la matriz el rango va de 0 - 9) hasta esa posición más los espacios que posee la
                 * nave - 1 (cuando se ubican las naves en el tablero, iran de mayor a menor, y de 1 a 5) y a su vez,
                 * todo los anterior más uno.
                 * Ejemplo: A5--; nave: 1;
                 * posicionInicio = 4, posicionFin = 4 - 5 = -1 + 1 = 0
                 * Al no asignar valor a columna, este quedará en false, lo que indica que se ubica de manera vertical
                 */
                posicionFin = Integer.parseInt(posicion[1]) - 1;
                posicionInicio = posicionFin - ESPACIOS[nave - 1] + 1;
            } else { //Si no coincide quiere decir que hubo un error de tipeo
                throw new Exception("Error de tipeo - Intenta de nuevo");
            }
            //Luego se analiza si esos valores obtenidos no son negativos y van en forma vertical, porque de serlo salen
            //de rango
            if (posicionInicio >= 0 && posicionFin >= 0 && columna) {
                for (int i = posicionInicio; i <= posicionFin; i++) {
                    //se examina cada una de las celdas donde ira la nave, si no es null quiere decir que ya está ocupado
                    //por ende la comprobación nos indica que ese lugar esa posición no puede ser usada
                    if (tablero[i][Integer.parseInt(posicion[1]) - 1] != null) {
                        System.out.println("\tSector ocupado - Intenta de nuevo");
                        //Si hay un solo lugar que ya esté ocupado, quiere decir que la nave no puede ir ahí
                        return true;
                    }
                }
                //Si no van de forma vertical
            } else if (posicionInicio >= 0 && posicionFin >= 0) {
                for (int i = posicionInicio; i <= posicionFin; i++) {
                    //se examina cada una de las celdas donde ira la nave, si no es null quiere decir que ya está ocupado
                    //por ende la comprobación nos indica que ese lugar esa posición no puede ser usada
                    if (tablero[FILAS.indexOf(posicion[0])][i] != null) {
                        System.out.println("\tSector ocupado - Intenta de nuevo");
                        //Si hay un solo lugar que ya esté ocupado, quiere decir que la nave no puede ir ahí
                        return true;
                    }
                }
            } else {//si no coincide quiere decir que salió del index por ende no sirve
                throw new Exception("Fuera de rango - Intenta de nuevo");
            }
            //Si logra sortear lo anterior sin ningún problema, quiere decir que puede ser ubicada de esa manera
            System.out.println("\tUbicada Exitosamente");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) { //En caso de que se busque fuera del rango de la matriz
            System.out.println("\tFuera de rango - Intenta de nuevo");
            return true;
        } catch (Exception e) { //En caso de darse algún otro error
            System.out.println(STR."\t\{e.getMessage()}");
            return true;
        }
    }

    /**
     * Método de tipo default cuya función es ubicar una nave específica en un tablero específico
     * @param tablero Tablero en el cual se ubicará la nave
     * @param nave Índice de la nave - 1, en NAVES
     * @param posicion Punto de partida del cual se empezará a ubicar la nave. En su index: 0 fila, 1 columna,
     *                 2 orientación, 3 dirección.
     */
    default void ubicarNave(String[][] tablero, int nave, String[] posicion) {
        int posicionInicio = -1; //desde donde empieza a ser ubicada la nave
        int posicionFin = -1; //desde donde termina de ser ubicada la nave
        boolean columna = false; //true implica que va a ser ubicado de manera vertical de lo contrario será horizontal
        //Para entender la siguiente sucesión de if, mirar el método probarTablero()
        if (posicion[2].equals("+") && posicion[3].equals("+")) {
            posicionFin = FILAS.indexOf(posicion[0]);
            posicionInicio = posicionFin - ESPACIOS[nave - 1] + 1;
            columna = true;
        } else if (posicion[2].equals("+") && posicion[3].equals("-")) {
            posicionInicio = FILAS.indexOf(posicion[0]);
            posicionFin = posicionInicio + (ESPACIOS[nave - 1] - 1);
            columna = true;
        } else if (posicion[2].equals("-") && posicion[3].equals("+")) {
            posicionInicio = Integer.parseInt(posicion[1]) - 1;
            posicionFin = posicionInicio + ESPACIOS[nave - 1] - 1;
        } else if (posicion[2].equals("-") && posicion[3].equals("-")) {
            posicionFin = Integer.parseInt(posicion[1]) - 1;
            posicionInicio = posicionFin - ESPACIOS[nave - 1] + 1;
        } //C5 C6 C7 C8 C9
        if (posicionInicio >= 0 && posicionFin >= 0 && columna) {
            //Si va a ser ubicada verticalmente
            for (int i = posicionInicio; i <= posicionFin; i++) {
                tablero[i][(Integer.parseInt(posicion[1])) - 1] = String.valueOf(nave);
                //debe ser -1, porque el usuario nos da la columna + 1, es decir, para nosotros la columna 1 realmente
                //es la 0
            }
        } else if (posicionInicio >= 0 && posicionFin >= 0) {
            //Si va a se ubicada horizontalmente
            for (int i = posicionInicio; i <= posicionFin; i++) {
                tablero[FILAS.indexOf(posicion[0])][i] = String.valueOf(nave);
            }
        }
    }

    /**
     * Método de tipo default encargado de imprimir un tablero que se le pase por parámetro
     * @param tablero Tablero/Matriz a imprimir
     */
    default void construirTablero(String[][] tablero) {
        for (int i = 0; i <= tablero.length - 1; i++) {
            System.out.print("\t "); //Cada fila es tabulada
            for (int j = 0; j <= tablero[i].length - 1; j++) {
                //Operador ternario usado, si el valor es null, quiere decir que allí no se ha ubicado nada, y por ende
                // se imprime la fila y columna, de lo contrario, un número > 0 indica el index de la nave
                System.out.print((tablero[i][j] == null) ? String.valueOf(FILAS.charAt(i)) + (j + 1) + " "
                        : " " + SIMBOLOS_NAVES[Integer.parseInt(tablero[i][j]) - 1] + " ");
            }
            System.out.println(); //Al terminar de imprimir la fila hace salto de línea
        }
    }

}


