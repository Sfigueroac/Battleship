package model;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private Player[] players;
    private Random random;
    private int machineStandardWins = 0;
    private int machineCustomWins = 0;


    public Controller() {
        players = new Player[2]; // [0] = humano, [1] = máquina
        random = new Random();
    }

    public void createPlayers(String username) {
        players[0] = new Player(username);
        players[1] = new Player("Máquina");
    }

    public Player getPlayer(int index) {
        if (index < 0 || index >= players.length) return null;
        return players[index];
    }

    public void placeStandardShipsHuman(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {
        Player player = players[0];
    
        int index = 0;
    
        // Lancha (1 casilla) - cualquier dirección, usamos horizontal (false)
        Dinghy dinghy = new Dinghy();
        player.placeShipOnBoard(dinghy, xCoords.get(index), yCoords.get(index), false);
        index++;
    
        // Barco Médico (2 casillas, vertical)
        MedicalBoat med = new MedicalBoat();
        player.placeShipOnBoard(med, xCoords.get(index), yCoords.get(index), true);
        index++;
    
        // Barco de Provisiones (3 casillas, horizontal)
        SupplyBoat supply = new SupplyBoat();
        player.placeShipOnBoard(supply, xCoords.get(index), yCoords.get(index), false);
        index++;
    
        // Barco de Munición (3 casillas, vertical)
        AmmoBoat ammo = new AmmoBoat();
        player.placeShipOnBoard(ammo, xCoords.get(index), yCoords.get(index), true);
        index++;
    
        // Buque de Guerra (4 casillas, horizontal)
        WarShip war = new WarShip();
        player.placeShipOnBoard(war, xCoords.get(index), yCoords.get(index), false);
        index++;
    
        // Portaaviones (5 casillas, vertical)
        AircraftCarrier carrier = new AircraftCarrier();
        player.placeShipOnBoard(carrier, xCoords.get(index), yCoords.get(index), true);
    }
    

    public void placeStandardShipsMachine() {
        Player machine = players[1];

        while (true) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            Dinghy d = new Dinghy();
            if (machine.getBoard().canPlaceShip(x, y, d.getSize(), false)) {
                machine.placeShipOnBoard(d, x, y, false);
                break;
            }
        }

        while (true) {
            int x = random.nextInt(10);
            int y = random.nextInt(9);
            MedicalBoat m = new MedicalBoat();
            if (machine.getBoard().canPlaceShip(x, y, m.getSize(), true)) {
                machine.placeShipOnBoard(m, x, y, true);
                break;
            }
        }

        while (true) {
            int x = random.nextInt(8);
            int y = random.nextInt(10);
            SupplyBoat s = new SupplyBoat();
            if (machine.getBoard().canPlaceShip(x, y, s.getSize(), false)) {
                machine.placeShipOnBoard(s, x, y, false);
                break;
            }
        }

        while (true) {
            int x = random.nextInt(10);
            int y = random.nextInt(8);
            AmmoBoat a = new AmmoBoat();
            if (machine.getBoard().canPlaceShip(x, y, a.getSize(), true)) {
                machine.placeShipOnBoard(a, x, y, true);
                break;
            }
        }

        while (true) {
            int x = random.nextInt(7);
            int y = random.nextInt(10);
            WarShip w = new WarShip();
            if (machine.getBoard().canPlaceShip(x, y, w.getSize(), false)) {
                machine.placeShipOnBoard(w, x, y, false);
                break;
            }
        }

        while (true) {
            int x = random.nextInt(10);
            int y = random.nextInt(6);
            AircraftCarrier a = new AircraftCarrier();
            if (machine.getBoard().canPlaceShip(x, y, a.getSize(), true)) {
                machine.placeShipOnBoard(a, x, y, true);
                break;
            }
        }
    }

    public boolean playStandardTurn(int x, int y) {
        Player human = players[0];
        Player machine = players[1];
    
        // Ataque del jugador humano
        int result = processAttack(machine, x, y);
        System.out.println("Resultado: " + attackMessage(result));
        machine.getBoard().showEnemyBoard();
    
        if (machine.hasLost()) {
            System.out.println("¡Ganaste! Hundiste toda la flota enemiga.");
            human.addStandardWin();
            return true; // Juego terminado
        }
    
        // Turno máquina
        System.out.println("\nTurno de la máquina...");
        int ax, ay;
        while (true) {
            ax = random.nextInt(10);
            ay = random.nextInt(10);
            int[][] matrix = human.getBoard().getMatrix();
            if (matrix[ay][ax] == 0 || matrix[ay][ax] == 1) break;
        }
    
        int mResult = processAttack(human, ax, ay);
        System.out.println("La máquina atacó (" + (ax + 1) + ", " + (ay + 1) + "): " + attackMessage(mResult));
        human.getBoard().showOwnBoard();
    
        if (human.hasLost()) {
            System.out.println("¡La máquina ha ganado!");
            machine.addStandardWin();
            return true; // Juego terminado
        }
    
        return false; // El juego continúa
    }
    

    private int processAttack(Player target, int x, int y) {
        int[][] matrix = target.getBoard().getMatrix();

        if (matrix[y][x] == 0) {
            matrix[y][x] = 9;
            return 0;
        }

        if (matrix[y][x] == 1) {
            matrix[y][x] = 2;
            for (Ship ship : target.getShips()) {
                if (ship.getCoords().contains(new Coordinate(x, y))) {
                    boolean sunk = ship.isSunk(matrix);
                    return sunk ? 3 : 2;
                }
            }
        }

        return -1;
    }

    private String attackMessage(int code) {
        return switch (code) {
            case 0 -> "¡Agua!";
            case 2 -> "¡Le diste a un barco!";
            case 3 -> "¡Hundiste un barco!";
            default -> "Ya habías disparado aquí.";
        };
    }
    public boolean tryPlaceShip(Player player, Ship ship, int x, int y, boolean vertical) {
        if (player.getBoard().canPlaceShip(x, y, ship.getSize(), vertical)) {
            player.placeShipOnBoard(ship, x, y, vertical);
            return true;
        }
        return false;
    }

    public void placeCustomShipsMachine(int quantity) {
        Player machine = players[1];
    
        for (int i = 0; i < quantity; i++) {
            int size = random.nextInt(5) + 1; // tamaño aleatorio entre 1 y 5
            boolean vertical = random.nextBoolean();
            int x, y;
    
            CustomShip ship = new CustomShip("Auto " + (i + 1), size);
    
            while (true) {
                x = random.nextInt(10);
                y = random.nextInt(10);
    
                if (machine.getBoard().canPlaceShip(x, y, size, vertical)) {
                    machine.placeShipOnBoard(ship, x, y, vertical);
                    break;
                }
            }
        }
    }

    public boolean playCustomTurn(int x, int y) {
        Player human = players[0];
        Player machine = players[1];
    
        // Turno del jugador humano
        int result = processAttack(machine, x, y);
        System.out.println("Resultado: " + attackMessage(result));
        machine.getBoard().showEnemyBoard();
    
        if (machine.hasLost()) {
            System.out.println("¡Ganaste! Hundiste toda la flota enemiga.");
            human.addCustomWin();
            return true;
        }
    
        // Turno de la máquina
        System.out.println("\nTurno de la máquina...");
        int ax, ay;
        while (true) {
            ax = random.nextInt(10);
            ay = random.nextInt(10);
            int[][] matrix = human.getBoard().getMatrix();
            if (matrix[ay][ax] == 0 || matrix[ay][ax] == 1) break;
        }
    
        int mResult = processAttack(human, ax, ay);
        System.out.println("La máquina atacó (" + (ax + 1) + ", " + (ay + 1) + "): " + attackMessage(mResult));
        human.getBoard().showOwnBoard();
    
        if (human.hasLost()) {
            System.out.println("¡La máquina ha ganado!");
            machine.addCustomWin();
            return true;
        }
    
        return false;
    }
    public void showStats() {
        Player human = players[0];
    
        System.out.println("\n--- Estadísticas ---");
        System.out.println("Jugador: " + human.getUsername());
        System.out.println("Partidas estándar ganadas: " + human.getStandardWins());
        System.out.println("Partidas personalizadas ganadas: " + human.getCustomWins());
        System.out.println("Máquina - estándar ganadas: " + machineStandardWins);
        System.out.println("Máquina - personalizadas ganadas: " + machineCustomWins);
    }
    
    

    
    
}
