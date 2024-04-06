package UI;

import Interfaces.ITestable;

import java.util.Random;

/**
 * Clase encargada de toda la lógica que debe llevar la máquina para realizar tiros con "séntido"
 */
public class UIMachine extends UIGeneral implements ITestable {
    /*
     * Array usado como referencia para saber si actualmente se está atacando una nave, cuál fue el primer tiro que se
     * le hizo, dirección y orientación, su index
     *  0. Tiro base
     *  1. Orientación (+ ataque vertical, - ataque horizontal)
     *  2. Dirección (+ arriba/derecha, - abajo/izquierda)
     * Si la lista en su primera posición tiene algo quiere decir que ya se está ejecutando un ataque.
     */
    private final String[] LISTA_BASE = new String[4]; //index: 0 Tiro base, 1 orientación, 2 dirección
    @Override
    public void procesarFeedback(boolean feedback, String[] tiro) {
        /*
         * Si la lista está vacía quiere decir que, no se estaba realizando un ataque previo al tiro y a su vez, si se
         * impactó una nave, se rellena la primera posición del Array para indicar que se ha iniciado un ataque
         */
        if (LISTA_BASE[0] == null && feedback) {
            //Se saca el valor de la columna -1, porque en el juego el rango de columnas va de 1 - 10, mientras que en
            //una matriz va de 0 - 9.
            int columna = Integer.parseInt(tiro[1]);
            LISTA_BASE[0] = tiro[0] + columna; //Se indica la posición del tiro, ejem "A5".
        }
        //Si la lista ya tiene algo en la primera posición entonces ya se está realizando un ataque y, a su vez, si se
        //impactó la nave, se marca en dicho tiro como impactado (-1) en el tablero.
        if (feedback) {
            TABLERO_ENEMIGO[FILAS.indexOf(tiro[0])][Integer.parseInt(tiro[1]) - 1] = -1;
        }
        //Si el tiro no impactó entonces se marca la posición como tiro errado (0) en el tablero
        else {
            TABLERO_ENEMIGO[FILAS.indexOf(tiro[0])][Integer.parseInt(tiro[1]) - 1] = 0;
        }
    }

    /**
     * Encargado de recibir la retroalimentación, en este se asegura que la nave no solo ha sido impactada, sino que
     * también derribada.
     * @param tiro El tiro en cuestión, index 0. Fila, 1. Columna
     * @param nave Tamaño de la nave derribada
     */
    public void procesarFeedback(String[] tiro, int nave) {
        /*
         * En caso de que el ataque sea en séntido norte, en el tablero enemigo, se marca a partir de la posición del
         * tiro hacia abajo como 1, es decir, derribado. En este caso, la columna es fija, mientras que la fila varía.
         */
        if (LISTA_BASE[1].equals("+") && LISTA_BASE[2].equals("+")) {
            //La cantidad de posiciones que se marcan como derribadas es la misma que la del tamaño de la nave
            for (int i = 0; i < nave; i++) {
                TABLERO_ENEMIGO[FILAS.indexOf(tiro[0]) + i][Integer.parseInt(tiro[1]) - 1] = 1;
            }
            /*
             * En caso de que el ataque sea en séntido sur, en el tablero enemigo, se marca a partir de la posición del
             * tiro hacia arriba como 1, es decir, derribado. En este caso, la columna es fija, mientras que la fila
             * varía.
             */
        } else if (LISTA_BASE[1].equals("+") && LISTA_BASE[2].equals("-")) {
            //La cantidad de posiciones que se marcan como derribadas es la misma que la del tamaño de la nave
            for (int i = 0; i < nave; i++) {
                TABLERO_ENEMIGO[FILAS.indexOf(tiro[0]) - i][Integer.parseInt(tiro[1]) - 1] = 1;
            }
            /*
             * En caso de que el ataque sea en séntido este, en el tablero enemigo, se marca a partir de la posición del
             * tiro hacia la derecha como 1, es decir, derribado. En este caso, la fila es fija, mientras que la columna
             * varía.
             */
        } else if (LISTA_BASE[1].equals("-") && LISTA_BASE[2].equals("+")) {
            //La cantidad de posiciones que se marcan como derribadas es la misma que la del tamaño de la nave
            for (int i = 0; i < nave; i++) {
                TABLERO_ENEMIGO[FILAS.indexOf(tiro[0])][Integer.parseInt(tiro[1]) - 1 - i] = 1;
            }
            /*
             * En caso de que el ataque sea en séntido oeste, en el tablero enemigo, se marca a partir de la posición
             * del tiro hacia la izquierda como 1, es decir, derribado. En este caso, la fila es fija, mientras que la
             * columna varía.
             */
        } else if (LISTA_BASE[1].equals("-") && LISTA_BASE[2].equals("-")) {
            //La cantidad de posiciones que se marcan como derribadas es la misma que la del tamaño de la nave
            for (int i = 0; i < nave; i++) {
                TABLERO_ENEMIGO[FILAS.indexOf(tiro[0])][Integer.parseInt(tiro[1]) - 1 + i] = 1;
            }
        }
        /*
         * Finalmente, se considera que ya no se está atacando la misma, por ende se pasa a buscar en el tablero enemigo
         * si hay tiros marcados como impactados y a su vez, marcar la orientación y dirección como null, puesto que
         * no se está atacando en ningún séntido de momento.
         */
        LISTA_BASE[0] = buscarImpactos();
        LISTA_BASE[1] = null;
        LISTA_BASE[2] = null;
    }

    /**
     * Método encargado de buscar posiciones en el tablero enemigo que estén marcados como impactados
     * @return Posición en la cual se encuentra la primera indicidencia o null, en caso de no hallar nada.
     */
    private String buscarImpactos() {
        //A continuación se recorre toda la lista
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (TABLERO_ENEMIGO[i][j] == null) { //Se ignoran todas las casillas que no posean nada
                    continue;
                }
                //Si alguna casilla tiene el valor de -1 (< 0) quiere decir que esa posición tiene un impacto, por ende
                //se retorna la posición de dicha columna, Fila Columna ejem. "A7"
                if (TABLERO_ENEMIGO[i][j] < 0) {
                    String filas = String.valueOf(FILAS.charAt(i));
                    return filas + (j + 1);
                }
            }
        }
        return null; //Si no encuentra ninguna incidencia entonces se retorna null, indicando que no se hayo nada
    }

    /**
     * Método usado para retornar la posición del tiro a disparar
     * @return Posición de la casilla a atacar.
     */
    public String[] apuntar() {

        if (LISTA_BASE[0] == null) { //Si no se está atacando nada entonces se hace un tiro aleatorio.
            return disparoAleatorio();
        }
        return atacarHorizontalVertical(new Random().nextBoolean());
    }

    /**
     * Método usado para ejecutar determinar si el ataque que se está haciendo o se va a hacer, es o será realizado de
     * manera vertica u horizontal
     * @param inicioHorizontal Boolean usado para elegir si se ataca horizontal (true) o vertical (false)
     * @return Tiro obtenido en base al switch case posición del tiro.
     */
    private String[] atacarHorizontalVertical(boolean inicioHorizontal) {
        //Con base en la segunda posición se ejecutará una condición.
        return switch (LISTA_BASE[1]) {
            //Al no tener nada quiere decir que, no ha definido la orientación el ataque , por ende hasta ahora se
            //realizará, así que con base en inicioHorizontal se elegirá si empezar a atacar horizontal o vertical
            case null -> inicioHorizontal ? atacarHorizontal() : atacarVertical();
            case "+" -> atacarHorizontal();
            case "-" -> atacarVertical();
            default -> null; //Se espera que nunca se ejecute...
        };
    }

    /**
     * Método encargado de realizar disparos de manera horizontal, en primer lugar hacia arriba y posteriormente hacia
     * abajo.
     * @return coordenada del tiro a realizar. index 0: fila, 1: columna.
     */
    private String[] atacarHorizontal() {
        //Se extrae el valor de la fila del tiro base.
        int fila = FILAS.indexOf(LISTA_BASE[0].substring(0, 1));
        int columna = (LISTA_BASE[0].length() < 3) ? Integer.parseInt(LISTA_BASE[0].substring(1, 2)) - 1 :
                Integer.parseInt(LISTA_BASE[0].substring(1, 3)) - 1;
        //si en esa posición/tiro el tamaño es inferior a 2 quiere decir que, la columna está en unidades ejem A3,
        //pero si es mayor a 2 quiere decir la columna está en decenas ejem, A10

        //Se empezará a atacar a partir del tiro base hacia arriba
        while (fila-- > 0) {
            //Si dicha posición no tiene nada, se procede a atacar y marcar la orientación y dirección del ataque
            // + (horizontal) + (arriba)
            if (TABLERO_ENEMIGO[fila][columna] == null) {
                String[] tiro = {String.valueOf(FILAS.charAt(fila)), String.valueOf(columna + 1)};
                LISTA_BASE[1] = "+";
                LISTA_BASE[2] = "+";
                return tiro;
            }
            //Si en esa posición hay una nave derribada, entonces no se sigue en ese sentido
            else if (TABLERO_ENEMIGO[fila][columna] >= 0) {
                break;
            }
        }
        //Se obtiene de nuevo el valor de la fila, ya que anteriormente fue decrementada y, se procede a atacar a partir
        //del tiro base hacia abajo
        fila = FILAS.indexOf(LISTA_BASE[0].substring(0, 1));
        while (fila++ < 9) {
            //Si dicha posición no tiene nada, se procede a atacar y marcar la orientación y dirección del ataque
            // + (horizontal) - (abajo)
            if (TABLERO_ENEMIGO[fila][columna] == null) {
                String[] tiro = {String.valueOf(FILAS.charAt(fila)), String.valueOf(columna + 1)};
                LISTA_BASE[1] = "+";
                LISTA_BASE[2] = "-";
                return tiro;
            }
            //Si en esa posición hay una nave derribada, entonces no se sigue en ese sentido
            else if (TABLERO_ENEMIGO[fila][columna] >= 0) {
                break;
            }
        }
        //Al final si no se atacó en esta orientación, entonces se pasa a la orientación vertical.
        return atacarVertical();
    }

    /**
     * Método encargado de realizar disparos de manera horizontal, en primer lugar hacia arriba y posteriormente hacia
     * abajo.
     * @return coordenada del tiro a realizar. index 0: fila, 1: columna.
     */
    private String[] atacarVertical() {
        //Se extrae el valor de la fila del tiro base.
        int fila = FILAS.indexOf(LISTA_BASE[0].substring(0, 1));
        //si en esa posición/tiro el tamaño es inferior a 2 quiere decir que, la columna está en unidades ejem A3,
        //pero si es mayor a 2 quiere decir la columna está en decenas ejem, A10
        int columna = (LISTA_BASE[0].length() < 3) ? Integer.parseInt(LISTA_BASE[0].substring(1, 2)) - 1 :
                Integer.parseInt(LISTA_BASE[0].substring(1, 3)) - 1;

        //Se empezará a atacar a partir del tiro base hacia la derecha
        while (columna++ < 9) {
            //Si dicha posición no tiene nada, se procede a atacar y marcar la orientación y dirección del ataque
            // - (vertica) + (derecha)
            if (TABLERO_ENEMIGO[fila][columna] == null) {
                String[] tiro = {String.valueOf(FILAS.charAt(fila)), String.valueOf(columna + 1)};
                LISTA_BASE[1] = "-";
                LISTA_BASE[2] = "+";
                return tiro;
            }
            //Si en esa posición hay una nave derribada, entonces no se sigue en ese sentido
            else if (TABLERO_ENEMIGO[fila][columna] >= 0) {
                break;
            }
        }
        //Se obtiene de nuevo el valor de la columna, ya que anteriormente fue incrementada y, se procede a atacar a partir
        //del tiro base hacia abajo
        columna = (LISTA_BASE[0].length() < 3) ? Integer.parseInt(LISTA_BASE[0].substring(1, 2)) - 1 :
                Integer.parseInt(LISTA_BASE[0].substring(1, 3)) - 1;
        while (columna-- > 0) {
            //Si dicha posición no tiene nada, se procede a atacar y marcar la orientación y dirección del ataque
            // - (vertical) - (izquierda)
            if (TABLERO_ENEMIGO[fila][columna] == null) {
                String[] tiro = {String.valueOf(FILAS.charAt(fila)), String.valueOf(columna + 1)};
                LISTA_BASE[1] = "-";
                LISTA_BASE[2] = "-";
                return tiro;
            }
            //Si en esa posición hay una nave derribada, entonces no se sigue en ese sentido
            else if (TABLERO_ENEMIGO[fila][columna] >= 0) {
                break;
            }
        }
        //Al final si no se atacó en esta orientación, entonces se pasa a la orientación horizontal.
        return atacarHorizontal();
    }

    /**
     * Método encargado de generar disparos aleatorios válidos, es decir, que no se hayan realizado antes.
     * @return Disparo aleatorio válido index 0: fila, 1: columna.
     */
    private String[] disparoAleatorio() {
        //La idea es que tienda a ser lo más aleatorio posible, he ahí la razón del parámetro
        Random aleatorio = new Random(System.currentTimeMillis());
        String[] disparoRandom = new String[2];
        do {
            //Se elige aleatoriamente la fila y columna
            disparoRandom[0] = String.valueOf(FILAS.charAt(aleatorio.nextInt(10)));
            disparoRandom[1] = String.valueOf(aleatorio.nextInt(10) + 1);
        } while (!probarTiro(disparoRandom));
        return disparoRandom;
    }

    /**
     * Método encargado de revisar si el disparo proporcionado no se ha realizado antes
     * @param disparoRandom Coordenadas del tiro en cuestión.
     * @return true, En caso de que no se haya realizado un tiro previo en dicha posición, false, en caso contrario
     */
    private boolean probarTiro(String[] disparoRandom) {
        return TABLERO_ENEMIGO[FILAS.indexOf(disparoRandom[0])][Integer.parseInt(disparoRandom[1]) - 1] == null;
    }
    @Override
    public String[][] ingresarNaves(boolean mostrarInfo) {
        String[][] tableroPrueba = new String[10][10];
        String letras = "ABCDEFGHIJ";
        Random numeroAleatorio = new Random(System.currentTimeMillis());


        for (int i = 1; i <= 5; i++) {
            String[] ubicacion = new String[4];
            do {
                ubicacion[0] = String.valueOf(letras.charAt(numeroAleatorio.nextInt(10)));
                numeroAleatorio.setSeed(System.currentTimeMillis());
                ubicacion[1] = String.valueOf(numeroAleatorio.nextInt(10) + 1);
                numeroAleatorio.setSeed(System.currentTimeMillis());
                ubicacion[2] = (new Random().nextBoolean()) ? "+" : "-";
                ubicacion[3] = (new Random().nextBoolean()) ? "+" : "-";
            } while (probarTablero(tableroPrueba.clone(), i, ubicacion));
            ubicarNave(tableroPrueba, i, ubicacion);
            ubicarNave(this.tableroPropio, i, ubicacion);
        }
        if (mostrarInfo) {
            //En caso de quererse mostrar la ubicación de la nave recientemente ubicada
            System.out.println();
            construirTablero(this.tableroPropio);
        }
        return this.tableroPropio.clone();
    }
}
