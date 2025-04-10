package model;

public class MedicalBoat extends Ship {

    public MedicalBoat() {
        super("Barco Médico", 2);
    }

    @Override
    public void placeShip(Board board, boolean vertical, int startX, int startY) {
        if (!vertical) {
            System.out.println("El Barco Médico solo puede colocarse de forma vertical.");
            return;
        }

        if (board.canPlaceShip(startX, startY, size, vertical)) {
            board.placeShip(this, startX, startY, vertical);
        } else {
            System.out.println("No se puede colocar el Barco Médico en las coordenadas dadas.");
        }
    }
}
