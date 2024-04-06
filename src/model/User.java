package model;

import UI.UIGeneral;

/**
 * Descripción: Clase abstracta a fin de recolectar comportamientos en común de la cual se hereda Player y user
 */
public abstract class User {
    private String name;

    /**
     * Descripción: Este método será llamado cuando el juego le pida a los jugadores ubicar las naves en su tablero
     *
     * @return String[][]: Un tablero donde se encuentra ubicados cada una de las naves, cada compartimento de la nave
     * será indicado así:
     * 1. Destructor, 2. Submarino, 3. Crucero, 4. Acorazado, 5. Carrier
     */

    public abstract String[][] entregarTableroCreado();

    /**
     * Este método será llamado cuando el juego le pida a los jugadores disparar
     * @return String[] {Fila, Columna}
     */
    public abstract String[] tirar();

    /**
     * Al llamar a este método, se le indica si el tiro realizado impactó o no
     * @param feedback True Indica si la nave fue impactada, False no
     * @param tiro String[] tiro previamente realizado {Fila, Columna}
     */
    public abstract void recibirFeedback(boolean feedback,String[] tiro);

    /**
     * Al llamar a este método, se asegura que el tiro realizado impactó alguna nave y además la derribó
     * @param tiro String[] tiro previamente realizado {Fila, Columna}
     */
    public abstract void recibirFeedback(String[] tiro, int nave);

    /**
     * Obtener el nombre del usuario
     * @return nombre del usuario
     */
    public String getName() {
        return name;
    }

    /**
     * Fijar el nombre del usuario
     * @param name nombre del usuario
     */
    public void setName(String name) {
        this.name = name;
    }
}
