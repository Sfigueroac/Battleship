package model;

public class CustomShip extends Ship {

    public CustomShip(String name, int size) {
        super(name, size);
    }

    @Override
    public void placeShip(Board board, boolean vertical, int startX, int startY) {
        board.placeShip(this, startX, startY, vertical);
    }
}
