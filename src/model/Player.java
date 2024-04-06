package model;


import UI.UIPlayer;
/**
 * Clase que hereda de User, encargada de interactuar con UIPlayer, y a su vez de entregar tableros, disparar y recibir
 * un feedback, de game.
 * Es decir, es la representación de una Persona en el juego virtual
 */
public class Player extends User{
    //Instancia una UIPlayer, que será encargada de interactuar con la persona que esté jugando
    private final UIPlayer INTERFAZ = new UIPlayer();

    /**
     * Constructor de la clase, donde se asigna un nombre aleatorio al jugador
     */
    public Player(){
        this.setName("Player #"+ (int) (Math.random() * 100) );
    }
    @Override
    public String[][] entregarTableroCreado() {
        return INTERFAZ.menuTablero();
    }


    @Override
    public String[] tirar() {
        return INTERFAZ.ingresarTiro();
    }

    @Override
    public void recibirFeedback(boolean feedback, String[] tiro) {
        INTERFAZ.procesarFeedback(feedback,tiro);
    }

    @Override
    public void recibirFeedback(String[] tiro, int nave) {
        INTERFAZ.procesarFeedback(tiro);
    }

}
