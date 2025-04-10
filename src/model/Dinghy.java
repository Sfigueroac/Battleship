package model;

public class Dinghy extends Ship {

    public Dinghy() {
        super("Lancha", 1);
    }

    @Override
    public void placeShip(Board board, boolean vertical, int startX, int startY) {
        if (board.canPlaceShip(startX, startY, size, vertical)) {
            board.placeShip(this, startX, startY, vertical);
        } else {
            System.out.println("No se puede posicionar Lancha en esas coordenadas");
        }
    }
}
