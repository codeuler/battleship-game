package model;

import java.util.Arrays;

/**
 * Esta clase es la representación del juego como tal, es decir, será la encargada de dar turnos, feedback e indicar el
 * ganador.
 */
public class Game {
    // Se hace uso del polimorfismo, puesto que user 1 y 2 van a ser o player o machine, que a su vez heredan de user
    // Como no se van a modificar en el transcurso de la ejecución pueden ser final, es decir, constantes
    private final User USER_1;
    private final User USER_2;
    private User ganador;
    private String[][] tableroUser1 = new String[10][10];
    private String[][] tableroUser2 = new String[10][10];
    private final String FILAS = "ABCDEFGHIJ";
    private final String[] NAVES = {"Destructor","Submarino", "Crucero", "Acorazado", "Carrier"};
    final int[] ESPACIOS = {5, 4, 3, 3, 2}; //Espacios que llevan las anteriores naves, destructor 5, sub 4, ...

    /**
     * Constructor del método, se deben enviar dos objetos de tipo user, para empalmarlos con los user de la clase
     * @param user1 Jugador 1
     * @param user2 Jugador 2
     */
    public Game(User user1, User user2) {
        this.USER_1 = user1;
        this.USER_2 = user2;
    }

    /**
     * Método para que los jugadores ubiquen los barcos en su respectivo tablero
     */
    public void preArranque() {
        try {
            System.out.println("\nA continuación, los jugadores ubican sus barcos");
            System.out.println("\t\nInicia --> " + USER_1.getName() + "\n");
            this.tableroUser1 = USER_1.entregarTableroCreado();
            Thread.sleep(1500); //Se detiene el juego 1.5 segundos, para que los espectadores procesen la información
                                     //de los tableros
            System.out.println("\t\nSigue --> " + USER_2.getName()+ "\n");
            this.tableroUser2 = USER_2.entregarTableroCreado();
           Thread.sleep(1500);
            //Se detiene el juego 1.5 segundos, para que los espectadores procesen la información
            //de los tableros
        }catch (InterruptedException e){
            System.out.println("Error al intentar realizar un delay de 4 segundos ");
        }

    }

    /**
     * Este método pone en marcha el juego, es decir, estará dando los turnos y feedback hasta que detecte que alguno de
     * los jugadores ha ganado.
     */
    public void iniciar() {
        User[] players = {USER_1, USER_2}; //Se va a usar un index para turnarlos, por eso es necesario tenerlos en un
                                           //un Array
        String[][] tablero; //Tablero del contrincante del usuario elegido
        int index = 0; //Irá aumentando de uno en uno, y se analizará si es par o no.

        System.out.println("\n---------------------------¡QUE GANE EL MEJOR!---------------------------\n");
        //El siguiente do while, se ejecutará hasta encontrar un ganador
        do {
            //Si el index es par, se elige al USER_1 y tableroUser2, es decir, el tablero de su contrincante, de ser
            //impar se elige USER_2 y tableroUser1
            tablero = (index % 2) == 0 ? tableroUser2 : tableroUser1;
            System.out.println("Turno de --> " + players[index % 2].getName());
            //Le cede el turno al jugador de paso
            turnar(players[index++ % 2], tablero);
            System.out.println();
            //Imprime el tablero del contrincante, donde se logra evidenciar tiros errados e impactados
            imprimirPostTiro(tablero);
            System.out.println();
            try {
                Thread.sleep(1500); //Se detiene 1.5 para que los jugadores/espectadores procesen la información
            }catch (InterruptedException e){
                System.out.println("Error al intentar realizar un delay de 4 segundos ");
            }
        } while (!evaluarFin(tablero)); //Hasta que no se detecte que el juego debe acabar, se repetirá el mismo proceso
        this.ganador = players[--index % 2];
        /*
         * Se asigna el ganador, con -- porque después de elegir que jugador iba, se incrementó el index, para el
         * siguiente jugador, pero como queremos saber qué jugador es el que ganó, por ende miramos el anterior index
         */

    }

    /**
     * Imprime el tablero indicando tanto los tiros impactados como los errados
     * @param tablero Tablero a imprimir
     */
    private void imprimirPostTiro(String[][] tablero) {
        for (int i = 0; i < 10; i++){
            System.out.print("\t"); //Cada fila es identada
            for (int j = 0; j < 10; j++){
                //Si en dicha casilla no hay nada, entonces se imprime la fila y columna
                if (tablero[i][j] == null || Integer.parseInt(tablero[i][j]) > 0){
                    System.out.print(FILAS.charAt(i) + "" + (j+1) + " ");
                }
                //Si en dicha casilla hay un número menor a 0, quiere decir que es una nave impactada
                else if(Integer.parseInt(tablero[i][j]) < 0) {
                    System.out.print("!! ");
                }
                //Si en dicha casilla hay un 0, quiere decir que, es un tiro errado
                else {
                    System.out.print("-- ");
                }
            }
            System.out.println(); //Salto de línea al terminar de imprimir cada fila
        }
    }

    /**
     * Al llamar este método, se le indica a un jugador que es su turno de disparar
     * @param userTurnado Jugador de turno
     * @param tableroUser Tablero de su contrincante
     */
    private void turnar(User userTurnado, String[][] tableroUser) {
        //Se le pedirá al jugador que entregué un disparo luego, se guardará el tiro realizado por userTurnado
        String[] tiro = userTurnado.tirar();
        System.out.print("\tTiro: " + Arrays.toString(tiro)); //Se imprime por pantalla el tiro
        //Se le entrega una retroalimentación al jugador
        giveFeedback(ubicarTiroUser(tiro, tableroUser), userTurnado, tiro, tableroUser);
        /*
         *ubicarTiro ubicará el tiro del usuario en el tablero rival.
         *Evaluar tiro será un boolean[] en cuya primera posición indicará si el tiro realizado impacto o no,
         * el segundo si se ha derribado una nave
         */
    }

    /**
     * Entrega el ganador del juego
     * @return User ganador
     */
    public User getGanador() { //entrega el valor del ganador
        return this.ganador;
    }

    /**
     * Método encargado de darle una retroalimentación a user del tiro previamente realizado
     * @param feedback Array booleano cuya primera posición indica si se ha impactado o no y la segunda si se ha
     *                 derribado la nave
     * @param usuarioTurnado User que realizó el tiro
     * @param tiro Array de String, cuya primera posición indica fila y la segunda la columna
     * @param tableroUser Tablero del contrincante, al cual se le ubicará el tiro
     */
    private void giveFeedback(boolean[] feedback, User usuarioTurnado, String[] tiro, String[][] tableroUser) {
        int indexNave = Integer.parseInt(tableroUser[FILAS.indexOf(tiro[0])][Integer.parseInt(tiro[1])-1]) * -1;
        indexNave--;
        //index de la nave en ESPACIOS y NAVES

        //Al haber impactado
        if(feedback[1]){
            int tamanioNave = ESPACIOS[indexNave]; //Basándonos en el index, se saca el tamaño de la nave
            //Se entrega índica a user, que el tiro derribó una nave y su tamaño.
            usuarioTurnado.recibirFeedback(tiro,tamanioNave);
            System.out.println("\tNave " + NAVES[indexNave] +" Derribada : " + tamanioNave + " espacios");
                          //el indexNave es el inverso al que hay en la lista de naves, por eso hay que restarle a 6
        }
        //al no haber impactado
        else {
            //Se entrega índica a user si el tiro impacto una nave y el tiro en cuestión.
            usuarioTurnado.recibirFeedback(feedback[0],tiro);}
    }

    /**
     * Método encargado de evaluar si en el tablero entregado queda alguna nave en pie
     * @param tableroEvaluar Tablero a evaluar
     * @return True si no queda ninguna nave en pie, False si queda cualquier nave en pie
     */
    private boolean evaluarFin(String[][] tableroEvaluar) {
        for (String[] filas : tableroEvaluar) {
            for (String fila : filas) {
                if (fila == null) { //Si es null, quiere decir que allí no se ha disparado
                    continue; //por ende pasa a la siguiente iteración
                }
                //Convierte el String que se encuentre en la celda a un número entero
                int valor = Integer.parseInt(fila);

                //Si el número es positivo quiere decir que, aún no se ha impactado, lo que indica que el juego debe
                //continuar
                if (valor > 0) {
                    return false;
                }
            }
        }
        //Si no encontró ninguna nave sin impactar, entonces retorna true.
        return true;
    }

    /**
     * Ubica el tiro del user en el tablero de su contrario
     * @param tiro String[] {Fila, Columna}
     * @param tableroUser Tablero rival
     * @return Boolean[] {¿Impactado?,¿Derribado?}
     */
    private boolean[] ubicarTiroUser(String[] tiro, String[][] tableroUser) {

        int fila = this.FILAS.indexOf(tiro[0]);
         // - 1 porque en el juego el rango de columnas va de 1 a 10, pero el index del trableroUser va de 0 a 9
        int columna = Integer.parseInt(tiro[1]) - 1;
        boolean[] retorno = new boolean[2];
        /*
         * El siguiente if else evalúa si esa posición tiene una nave o no, null indica que no ha sido impactado nada,
         * si no es null, quiere decir que hay una nave en ese lugar, por ende, se procede a ser marcada como impactada
         */
        if (tableroUser[fila][columna] == null) { //Si es null quiere decir que
            tableroUser[fila][columna] = "0"; //Se marca como tiro al aire
            System.out.println("\tTiro al aire");
        } else {
            String nave = tableroUser[fila][columna];
            tableroUser[fila][columna] = "-" + tableroUser[fila][columna]; //Se marca como nave impactada
            System.out.println(" : Nave impactada");
            retorno[0] = true;
            if (!buscarNave(tableroUser, nave)) { //Busca en el tablero si aún quedan compartimentos de la misma nave
                //Si no encuentra compartimentos quiere decir que la nave ha sido derribada
                retorno[1] = true;
            }
        }

        return retorno;
    }

    /**
     * Busca en el tablero, si encuentra compartimentos de la nave en cuestión
     * @param tablero Tablero a analizar
     * @param nave Valor de la nave a buscar
     * @return True si encuentra al menos un compartimento, False si no lo encuentra
     */
    private boolean buscarNave(String[][] tablero, String nave) { //Busca si la nave enviada le queda compartimentos sin impactar

        for (String[] strings : tablero) {
            for (int j = 0; j < tablero.length; j++) {
                //Ignora las casillas que no tienen nada
                if (strings[j] == null){
                    continue;
                }
                //Si la casilla tiene el valor de la nave, quiere decir que aún no la ha derribado
                else if (strings[j].equals(nave)) {
                    return true;
                }
            }
        }
        //Si termina de recorrer el arreglo que no encuentra coincidencias, quiere decir que la nave ha sido derribada
        return false;
    }

}
