import static UI.MenuPrincipal.*;
//Se trae el menu entrada y juego, que son public, de esa manera no es necesario crear una instancia de la clase

public class Main {
    public static void main(String[] args) {
        entrada(); //Se ellije el modo de juego
        if (juegoCreado != null) { //El juego debe haber sido creado
            juegoCreado.preArranque(); //Se ubican las naves de cada jugador en su debido tablero
            juegoCreado.iniciar(); //Empieza el intercambio de disparos
            System.out.println(STR."El ganador es: \{juegoCreado.getGanador().getName()}"); //Se imprime el ganador
        }
        System.out.println("Finalizando..."); //Fin del programa
    }
}
