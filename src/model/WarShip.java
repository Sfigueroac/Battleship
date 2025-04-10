package model;

public class WarShip extends Ship {

    public WarShip() {
        super("Buque de Guerra", 4);
    }

    @Override
    public void placeShip(Board board, boolean vertical, int startX, int startY) {
        if (vertical) {
            System.out.println("El Buque de Guerra solo puede colocarse de forma horizontal.");
            return;
        }

        if (board.canPlaceShip(startX, startY, size, false)) {
            board.placeShip(this, startX, startY, false);
        } else {
            System.out.println("No se puede colocar el Buque de Guerra en las coordenadas dadas.");
        }
    }
}
