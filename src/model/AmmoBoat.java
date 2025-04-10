package model;

public class AmmoBoat extends Ship {

    public AmmoBoat() {
        super("Barco de Municiones", 3);
    }

    @Override
    public void placeShip(Board board, boolean vertical, int startX, int startY) {
        if (!vertical) {
            System.out.println("El Barco de Munición solo puede colocarse de forma vertical.");
            return;
        }

        if (board.canPlaceShip(startX, startY, size, true)) {
            board.placeShip(this, startX, startY, true);
        } else {
            System.out.println("No se puede colocar el Barco de Munición en las coordenadas dadas.");
        }
    }
}
