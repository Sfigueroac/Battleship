package ui;

import java.util.Scanner;
import model.Battleship;
import model.ShipType;

public class Executable {

    private static Scanner input;
    private static Battleship battleship; 
    public static void main(String[] args) {

        
    }

    public void addShips(){
        System.out.println("Digite el tipo de barco que desea posicionar: ");
        System.out.println("[1] LANCHA\n[2] MEDICO\n [3] MUNICION\n [4] PROVISIONES\n [5] GUERRA\n [6] PORTAAVIONES");
        int option = input.nextInt();
        ShipType type = battleship.calculateShipType(option);
        

    }
}
