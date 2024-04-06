package UI;

import model.Game;
import model.Machine;
import model.Player;

import java.util.Scanner;

/**
 * Esta clase estática es usada para que el usuario elija el modo de juego,
 * @author euler
 */
public class MenuPrincipal {
    public static Game juegoCreado; //El juego en cuestión, instancia de Game

    /**
     * Descripción: Este método le permitirá al usuario elegir el modo de juego a ejecutar
     */
    public static void entrada() {
        int respuesta = -1;
        do {
            System.out.println("\n\t\t\tBATTLESHIP GAME\n");
            System.out.println("A continuación elige el modo de juego a seguir");
            System.out.println("\t1.Machine vs Player\n" +
                    "\t2.Machine vs Machine\n" +
                    "\t0.Salir");
            Scanner sc = new Scanner(System.in);

            //Si lo ingresado por el usuario no se puede pasear a Integer, quiere decir que ingreso un valor invalido
            try {
                respuesta = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("¡Valor no válido! - Intenta nuevamente");
            }

            //Sé instancia el game con el tipo de user correspondiente
            switch (respuesta){
                case 1:
                    respuesta = 0;
                    juegoCreado = new Game(new Machine(false),new Player());
                    //El parámetro false, indica que machine no muestre su tablero por consola, posterior a su creación
                    break;
                case 2:
                    respuesta = 0;
                    juegoCreado = new Game(new Machine(true),new Machine(true));
                    break;
                case 3:
                    respuesta = 0;
                    juegoCreado = new Game(new Player(),new Player());
                    break;
                case 0:
                    break;
            }

        } while (respuesta != 0); //Si se logró instanciar exitosamente Game, termina su labor este método
    }
}
