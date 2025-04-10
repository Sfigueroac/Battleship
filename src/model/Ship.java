package model;

import java.util.ArrayList;

public abstract class Ship {
    protected String name;
    protected int size;
    protected ArrayList<Coordinate> coords;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
        this.coords = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Coordinate> getCoords() {
        return coords;
    }

    public void addCoordinate(int x, int y) {
        coords.add(new Coordinate(x, y));
    }

    /**
     * Verifica si todas las partes del barco fueron tocadas (valor 2) y lo hunde (cambia a 3).
     * @param board El tablero del jugador dueño del barco.
     * @return true si el barco fue hundido, false si aún no.
     */
    public boolean isSunk(int[][] board) {
        for (Coordinate c : coords) {
            if (board[c.getY()][c.getX()] != 2) {
                return false;
            }
        }
        // Si todas las casillas están tocadas, cambiarlas a 3 (hundido)
        for (Coordinate c : coords) {
            board[c.getY()][c.getX()] = 3;
        }
        return true;
    }

    // Método abstracto que cada subclase debe implementar para ubicarse en el tablero
    public abstract void placeShip(Board board, boolean isVertical, int startX, int startY);
}