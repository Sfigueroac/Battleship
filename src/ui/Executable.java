package ui;

import java.util.Scanner;
import model.AircraftCarrier;
import model.AmmoBoat;
import model.Controller;
import model.CustomShip;
import model.Dinghy;
import model.MedicalBoat;
import model.SupplyBoat;
import model.WarShip;

public class Executable {
    private static Scanner input;
    private static Controller controller;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        controller = new Controller();

        boolean running = true;
        while (running) {
            System.out.println("[1] Jugar partida estándar");
            System.out.println("[2] Jugar partida personalizada");
            System.out.println("[3] Ver estadísticas");
            System.out.println("[4] Salir");
            System.out.print("Seleccione una opción: ");
            int option = input.nextInt();
            input.nextLine(); // limpiar buffer

            switch (option) {
                case 1:
                    startStandardGame();
                    break;
                case 2:
                    startCustomGame();
                    break;
                case 3:
                    controller.showStats();
                    break;
                case 4:
                    running = false;
                    System.out.println("¡Gracias por jugar!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void startStandardGame() {
        System.out.print("Ingrese su nombre de usuario: ");
        String username = input.nextLine();
    
        controller.createPlayers(username);
    
        // Colocación segura de los 6 barcos estándar
while (true) {
    System.out.println("Coloca tu Lancha (1 casilla):");
    int x = askCoordinate("X");
    int y = askCoordinate("Y");
    Dinghy dinghy = new Dinghy();
    if (controller.tryPlaceShip(controller.getPlayer(0), dinghy, x, y, false)) break;
    System.out.println("No se puede colocar la Lancha en esas coordenadas. Inténtalo de nuevo.");
}

while (true) {
    System.out.println("Coloca tu Barco Médico (2 casillas, vertical):");
    int x = askCoordinate("X");
    int y = askCoordinate("Y");
    MedicalBoat med = new MedicalBoat();
    if (controller.tryPlaceShip(controller.getPlayer(0), med, x, y, true)) break;
    System.out.println("No se puede colocar el Barco Médico en esas coordenadas. Inténtalo de nuevo.");
}

while (true) {
    System.out.println("Coloca tu Barco de Provisiones (3 casillas, horizontal):");
    int x = askCoordinate("X");
    int y = askCoordinate("Y");
    SupplyBoat supply = new SupplyBoat();
    if (controller.tryPlaceShip(controller.getPlayer(0), supply, x, y, false)) break;
    System.out.println("No se puede colocar el Barco de Provisiones en esas coordenadas. Inténtalo de nuevo.");
}

while (true) {
    System.out.println("Coloca tu Barco de Munición (3 casillas, vertical):");
    int x = askCoordinate("X");
    int y = askCoordinate("Y");
    AmmoBoat ammo = new AmmoBoat();
    if (controller.tryPlaceShip(controller.getPlayer(0), ammo, x, y, true)) break;
    System.out.println("No se puede colocar el Barco de Munición en esas coordenadas. Inténtalo de nuevo.");
}

while (true) {
    System.out.println("Coloca tu Buque de Guerra (4 casillas, horizontal):");
    int x = askCoordinate("X");
    int y = askCoordinate("Y");
    WarShip war = new WarShip();
    if (controller.tryPlaceShip(controller.getPlayer(0), war, x, y, false)) break;
    System.out.println("No se puede colocar el Buque de Guerra en esas coordenadas. Inténtalo de nuevo.");
}

while (true) {
    System.out.println("Coloca tu Portaaviones (5 casillas, vertical):");
    int x = askCoordinate("X");
    int y = askCoordinate("Y");
    AircraftCarrier carrier = new AircraftCarrier();
    if (controller.tryPlaceShip(controller.getPlayer(0), carrier, x, y, true)) break;
    System.out.println("No se puede colocar el Portaaviones en esas coordenadas. Inténtalo de nuevo.");
}
        controller.placeStandardShipsMachine();
    
        System.out.println("\n--- Tu tablero ---");
        controller.getPlayer(0).getBoard().showOwnBoard();
    
        System.out.println("\n--- Tablero del enemigo ---");
        controller.getPlayer(1).getBoard().showEnemyBoard();
    
        System.out.println("\n¡Muy bien! ¡Ahora vamos a jugar!");
    
        boolean gameOver = false;
        while (!gameOver) {
            System.out.println("\nTu turno. Elige coordenadas para atacar:");
            int x = askCoordinate("X");
            int y = askCoordinate("Y");
            gameOver = controller.playStandardTurn(x, y);
        }
    }
    
    private static int askCoordinate(String axis) {
        int coord = -1;
        while (true) {
            try {
                System.out.print("Ingresa la coordenada " + axis + " (1-10): ");
                String line = input.nextLine();
                coord = Integer.parseInt(line) - 1;
                if (coord >= 0 && coord < 10) {
                    return coord;
                } else {
                    System.out.println("La coordenada debe estar entre 1 y 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
            }
        }
    }
    private static void startCustomGame() {
        System.out.print("Ingrese su nombre de usuario: ");
        String username = input.nextLine();
    
        controller.createPlayers(username);
    
        // 1. Preguntar cuántos barcos quiere (máximo 10)
        int totalShips = 0;
        while (true) {
            System.out.print("¿Cuántos barcos deseas colocar? (Máximo 10): ");
            try {
                totalShips = Integer.parseInt(input.nextLine());
                if (totalShips >= 1 && totalShips <= 10) break;
                System.out.println("Debe ser un número entre 1 y 10.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingresa un número.");
            }
        }
    
        // 2. Colocar cada barco validando en el momento
        for (int i = 0; i < totalShips; i++) {
            System.out.println("\nBarco #" + (i + 1));
            while (true) {
                // Longitud
                int length = 0;
                while (true) {
                    System.out.print("Longitud del barco (1 a 5 casillas): ");
                    try {
                        length = Integer.parseInt(input.nextLine());
                        if (length >= 1 && length <= 5) break;
                        System.out.println("Debe estar entre 1 y 5.");
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                    }
                }
    
                // Orientación
                boolean vertical = false;
                while (true) {
                    System.out.print("¿Orientación vertical (V) u horizontal (H)? ");
                    String dir = input.nextLine().trim().toUpperCase();
                    if (dir.equals("V")) {
                        vertical = true;
                        break;
                    } else if (dir.equals("H")) {
                        vertical = false;
                        break;
                    } else {
                        System.out.println("Solo puedes escribir V o H.");
                    }
                }
    
                // Coordenadas
                int x = askCoordinate("X");
                int y = askCoordinate("Y");
    
                // Crear e intentar colocar
                CustomShip custom = new CustomShip("Personalizado " + (i + 1), length);
                if (controller.tryPlaceShip(controller.getPlayer(0), custom, x, y, vertical)) {
                    break; // Éxito: pasamos al siguiente barco
                } else {
                    System.out.println("No se puede colocar el barco en esas coordenadas. Inténtalo de nuevo.");
                }
            }
        }
    
        // 3. Colocar barcos automáticos para la máquina
        controller.placeCustomShipsMachine(totalShips);
    
        // 4. Mostrar tableros
        System.out.println("\n--- Tu tablero ---");
        controller.getPlayer(0).getBoard().showOwnBoard();
    
        System.out.println("\n--- Tablero del enemigo ---");
        controller.getPlayer(1).getBoard().showEnemyBoard();
    
        // 5. Iniciar combate
        System.out.println("\n¡Comienza la batalla personalizada!");
        boolean gameOver = false;
        while (!gameOver) {
            System.out.println("\nTu turno. Elige coordenadas para atacar:");
            int x = askCoordinate("X");
            int y = askCoordinate("Y");
            gameOver = controller.playCustomTurn(x, y);
        }
    }
    

    
    


    }


