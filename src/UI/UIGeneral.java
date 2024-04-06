package UI;

import Interfaces.ITestable;


/**
 * Clase abstracta que recoge los comportamientos y atributos comunes que tendrán las interfaces con las que
 * interactuarán los users.
 */
public abstract class UIGeneral implements ITestable {
    /**
     * Matriz de tipo constante usada como tablero para marcar los impacto que se realizan en el tablero del
     * contrincante.
     *  null: Indica que no se ha disparado en ese lugar.
     *  -1: Indica que hay una nave impactada en dicha posición.
     *  1: Indica que hay una nave derribada en dicha posición.
     */
    public final Integer[][] TABLERO_ENEMIGO = new Integer[10][10];

    //Tablero del user con el que va a interactuar al UI
    public String[][] tableroPropio = new String[10][10];

    /**
     * Método abstracto encargado de pedir la ubicación en la cual serán ubicadas las naves.
     * @param mostrarInfo Boolean, True para mostrar la ubicación en la que será ubicada de la nave, False para no
     *                    hacerlo.
     * @return Matriz representante del tablero en el cual se han ubicado éxitosamente todas y cada unas de las naves
     * del jugador en cuestión.
     */
    public abstract String[][] ingresarNaves(boolean mostrarInfo);
    /**
     * Encargado de recibir la retroalimentación, en este se asegura que no se ha derribado ninguna nave en el tiro
     * previo
     * @param feedback Bolean True, indica que se ha impactado una nave, false indica lo contrario.
     * @param tiro El tiro en cuestión, index 0: Fila, 1: Columna.
     */
    public abstract void procesarFeedback(boolean feedback,String[] tiro);
}
