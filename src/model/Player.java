package model;
import java.util.ArrayList;

public class Player {
    private String username;
    private int points;
    private Board board;
    private ArrayList<Ship> ships;
    private int standardWins;
    private int customWins;

    public Player(String username) {
        this.username = username;
        this.points = 0;
        this.board = new Board();
        this.ships = new ArrayList<>();
        this.standardWins = 0;
        this.customWins = 0;
    }

    public String getUsername() { return username; }
    public int getPoints() { return points; }
    public Board getBoard() { return board; }
    public ArrayList<Ship> getShips() { return ships; }

    public void addStandardWin() { standardWins++; }
    public void addCustomWin() { customWins++; }
    public int getStandardWins() { return standardWins; }
    public int getCustomWins() { return customWins; }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    /**
     * Ubica un barco en el tablero y lo agrega a la lista de barcos.
     * @param ship El barco a colocar
     * @param startX Coordenada X inicial
     * @param startY Coordenada Y inicial
     * @param vertical true si es vertical, false si es horizontal
     */
    public void placeShipOnBoard(Ship ship, int startX, int startY, boolean vertical) {
        ship.placeShip(board, vertical, startX, startY);
        addShip(ship);
    }

    
    /**
     * Verifica si todos los barcos han sido hundidos (gana el otro jugador).
     */
    public boolean hasLost() {
        for (Ship ship : ships) {
            if (!ship.isSunk(board.getMatrix())) {
                return false;
            }
        }
        return true;
    }
}

