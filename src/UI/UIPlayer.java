package UI;

import Interfaces.ITestable;

import java.util.Scanner;

public class UIPlayer extends UIGeneral implements ITestable {
    private final Scanner sc = new Scanner(System.in);

    /**
     * Menu que le permite al usuario elegir entre revisar el manual o ingresar todas y cada una de las naves
     * @return Tablero del jugador creado
     */
    public String[][] menuTablero() {
        String respuesta;

        do {
            System.out.println("¿Qué quieres hacer?");
            System.out.println("\t1. Ingresar naves.");
            System.out.println("\t2. Revisar Manual de ingreso de naves.");
            respuesta = sc.nextLine();
            switch (respuesta) {
                case "1":
                    this.tableroPropio = ingresarNaves(true);
                    break;
                case "2":
                    System.out.println("""
                            Para ingresar la naves ten en cuenta lo siguiente:
                             En el juego se deben ubicar 5 naves las cuales:
                             
                            	Carrier, con 2 espacios.
                            	Acorazado, con 3 espacios.
                            	Crucero, con 3 espacios.
                            	Submarino, con 4 espacios.
                            	Destructor, con 5 espacios.
                            	
                             Para ubicarlos debes dar:
                             
                              Fila: En un rango de (A-J)
                              Columna: En un rango de (1-10)
                              Orientación:
                              
                               "N" Norte (arriba)
                               "S" Sur (abajo)
                               "E" Este (derecha)
                               "O" Oeste (izquierda)
                            	ejem "B8S". Esto indica que lo posicionas en B (fila) 5 (columna) S (Orientación) (parte de B8 hacia abajo)
                            """);
                    respuesta = null;
                    break;
                default:
                    System.out.println("\t\tIngresa un valor correcto");
                    respuesta = null;
            }
        } while (respuesta == null);
        return this.tableroPropio;

    }

    @Override
    public String[][] ingresarNaves(boolean mostrarInfo) {
        String[][] tableroPrueba = new String[10][10];

        for (int i = 1; i <= 5; i++) {
            System.out.println("A continuación ubicaras " + NAVES[i - 1] + " con " + ESPACIOS[i - 1] + " espacios");
            String[] ubicacion = new String[4];
            do {
                System.out.println("\tIngresa la fila (A - J)");
                ubicacion[0] = sc.nextLine().toUpperCase().replace(" ", "");
                System.out.println("\tIngresa la Columna (1 - 10)");
                ubicacion[1] = sc.nextLine().replace(" ", "");
                System.out.println("\tIngresa la Orientación ( N S E O )");
                ubicacion[2] = sc.nextLine().toUpperCase().replace(" ", "");
                System.out.println("\tTu nave será ubicada así: " + ubicacion[0] + ubicacion[1] + ubicacion[2]);
                switch (ubicacion[2]) {
                    case "N" -> {
                        ubicacion[2] = "+";
                        ubicacion[3] = "+";
                    }
                    case "S" -> {
                        ubicacion[2] = "+";
                        ubicacion[3] = "-";
                    }
                    case "E" -> {
                        ubicacion[2] = "-";
                        ubicacion[3] = "+";
                    }
                    case "O" -> {
                        ubicacion[2] = "-";
                        ubicacion[3] = "-";
                    }
                }
            } while (probarTablero(tableroPrueba.clone(), i, ubicacion));
            ubicarNave(tableroPrueba, i, ubicacion);
            ubicarNave(this.tableroPropio, i, ubicacion);
            System.out.println("\tAsí va quedando tu tablero");
            System.out.println();
            construirTablero(this.tableroPropio);
            System.out.println("\n");
        }
        return this.tableroPropio;
    }

    /**
     * Método que guía al jugador para ingresar las coordenadas del tiro a realizar
     * @return Tiro realizado por el jugador
     */
    public String[] ingresarTiro() {
        String[] tiro = new String[2];
        System.out.println("\nA continuación, vas a disparar");
        do {
            System.out.println("\tIngresa la fila (A-J)");
            //Se setea lo ingresado a mayúsculas y se borran los espacios
            tiro[0] = sc.nextLine().toUpperCase().replace(" ", "");
            System.out.println("\tIngresa la columna (1-10)");
            //Se borran los espacios
            tiro[1] = sc.nextLine().replace(" ", "");
        } while (!probarTiro(tiro));
        return tiro;
    }

    /**
     * Método que evalúa si el tiro pasado por parámetro no se ha realizado antes
     * @param tiro Tiro realizado por el jugador
     * @return true si el tiro no se había realizado, false de lo contrario
     */
    private boolean probarTiro(String[] tiro) {
        try {
            boolean retorno = TABLERO_ENEMIGO[FILAS.indexOf(tiro[0])][Integer.parseInt(tiro[1]) - 1] == null;
            System.out.println("\t" + (retorno?"Tiro válido":"Ups, ya habías realizado dicho tiro"));
            return retorno;
        } catch (Exception e) {
            System.out.println("\tError de tipeo - Intenta de nuevo");
        }
        return false;

    }
    @Override
    public void procesarFeedback(boolean feedback,String[] tiro){
        int fila = FILAS.indexOf(tiro[0]);
        int columna = Integer.parseInt(tiro[1]) - 1;
        if(feedback){
            TABLERO_ENEMIGO[fila][columna] = -1; //impactado
        }else {
            TABLERO_ENEMIGO[fila][columna] = 0; //tiro al aire
        }

    }
    /**
     * Encargado de recibir la retroalimentación, en este se asegura que la nave no solo ha sido impactada, sino que
     * también derribada.
     * @param tiro El tiro en cuestión, index 0. Fila, 1. Columna
     */
    public void procesarFeedback(String[] tiro){ //Si derriban la nave
        int fila = FILAS.indexOf(tiro[0]);
        int columna = Integer.parseInt(tiro[1]) - 1;
            TABLERO_ENEMIGO[fila][columna] = -1; //Derribado, pero no se va a marcar como derribado sino impactado
    }
}
