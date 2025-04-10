package model;

public class AircraftCarrier extends Ship {

    public AircraftCarrier() {
        super("Portaviones", 5);
    }

    @Override
    public void placeShip(Board board, boolean vertical, int startX, int startY) {
        if (!vertical) {
            System.out.println("El Portaaviones solo puede colocarse de forma vertical.");
            return;
        }

        if (board.canPlaceShip(startX, startY, size, true)) {
            board.placeShip(this, startX, startY, true);
        } else {
            System.out.println("No se puede colocar el Portaaviones en las coordenadas dadas.");
        }
    }
}
