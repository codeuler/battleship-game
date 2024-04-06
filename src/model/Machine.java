package model;

import UI.UIMachine;

/**
 * Clase que hereda de User, encargada de interactuar con UIMachine, y a su vez de entregar tableros, disparar y recibir
 * un feedback, de game
 */
public class Machine extends User{
    //Instancia una UIMachine, que será encargada de llevar la lógica del juego que le corresponde
    private final UIMachine INTERFAZ = new UIMachine();
    //True para mostrar el tablero al ubicar las naves o, False para no hacerlo
    private final boolean mostrarInfo;

    /**
     * Constructor de la clase
     * @param mostrarInfo Indica si se desea mostrar el tablero al ubicar las naves
     */
    public Machine(boolean mostrarInfo){
        this.mostrarInfo = mostrarInfo;
        this.setName("Machine #"+ (int) (Math.random() * 100) +(int)(Math.random()*2));
    }
    @Override
    public String[][] entregarTableroCreado() {
         return INTERFAZ.ingresarNaves(this.mostrarInfo);

    }

    @Override
    public String[] tirar() {
       return INTERFAZ.apuntar();
    }

    @Override
    public void recibirFeedback(boolean feedback, String[] tiro) {
        INTERFAZ.procesarFeedback(feedback,tiro);
    }

    @Override
    public void recibirFeedback(String[] tiro, int nave) {
        INTERFAZ.procesarFeedback(tiro, nave);

    }


}
