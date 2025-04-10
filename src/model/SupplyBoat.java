package model;

public class SupplyBoat extends Ship {

    public SupplyBoat() {
        super("Barco de Proviciosiones", 3);
    }

    @Override
    public void placeShip(Board board, boolean vertical, int startX, int startY) {
        if (vertical) {
            System.out.println("El Barco de Provisiones solo puede colocarse de forma horizontal.");
            return;
        }

        if (board.canPlaceShip(startX, startY, size, false)) {
            board.placeShip(this, startX, startY, false);
        } else {
            System.out.println("No se puede colocar el Barco de Provisiones en las coordenadas dadas.");
        }
    }
}
