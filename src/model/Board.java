package model;

public class Board {
    private int[][] board;

    public Board(){
        board = new int[10][10];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j< board[0].length; j++){
                board[i][j] = 0;
            }
        }
    }
    
    
    public int[][] getMatrix() {
        return board;
    }
    // Verifica si un barco cabe sin salirse ni chocar
    public boolean canPlaceShip(int startX, int startY, int size, boolean vertical) {
        if (vertical) {
            if (startY + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if (board[startY + i][startX] != 0) return false;
            }
        } else {
            if (startX + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if (board[startY][startX + i] != 0) return false;
            }
        }
        return true;
    }

    // Coloca el barco en el tablero y guarda las coordenadas en el objeto Ship
    public void placeShip(Ship ship, int startX, int startY, boolean vertical) {
        int size = ship.getSize();

        if (vertical) {
            for (int i = 0; i < size; i++) {
                board[startY + i][startX] = 1;
                ship.addCoordinate(startX, startY + i);
            }
        } else {
            for (int i = 0; i < size; i++) {
                board[startY][startX + i] = 1;
                ship.addCoordinate(startX + i, startY);
            }
        }
    }

    // Muestra el tablero del propio jugador
    public void showOwnBoard() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
    }

    // Muestra el tablero del enemigo (sin barcos visibles)
    public void showEnemyBoard() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                int val = board[y][x];
                switch (val) {
                    case 2 -> System.out.print("2 "); // Tocaste un barco
                    case 3 -> System.out.print("3 "); // Hundiste un barco
                    case 9 -> System.out.print("0 "); // Agua disparada
                    default -> System.out.print("X "); // No disparado (oculto)
                }
            }
            System.out.println();
        }
    }
    
    
}
    
