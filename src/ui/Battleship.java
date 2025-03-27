/**
 * DESCRIPCIÓN DEL PROGRAMA:
 * Este es un programa que emula el juego de Battleship en una dimensión mediante arreglos. Le permite al usuario jugar por turnos contra la máquina en una única dificultad. Genera aleatoriamente un tablero para la máquina y le permite al usuario posicionar sus propios barcos. Cuando el usuario posiciona sus barcos sale como un 1, si está golpeado un 2 y si está hundido un 3. Lo mismo para el tablero de la máquina solo que oculta donde están sus barcos, muestra 0 si esa casilla ya fue atacada, 2 si es un barco golpeado y 3 si ya está hundido.
 * 
 * ENTRADAS:
 * INT posiciones de los barcos: posicionLancha, posicionBarcoMedico, posicionBarcoMunicion
 * INT casilla de ataque: ataque
 * 
 * SALIDAS:
 * INT[] tableros: tableroJugador, tableroCPU
 */

import java.util.Random;
import java.util.Scanner;

public class Battleship {
    public static Scanner reader;

    public static void main(String[] args) {
        reader = new Scanner(System.in);
        int[] tableroJugador = new int[10];
        int[] tableroJugadorOculto = tableroJugador;
        int[] tableroCPU = generarTableroCPU();
        int[] tableroCPUOculto = tableroCPU;
        boolean[] atacadoJugador = new boolean[10];
        boolean[] atacadoCPU = new boolean[10];
        
        final int TAMANO_LANCHA = 1;
        final int TAMANO_MEDICO = 2;
        final int TAMANO_MUNICION = 3;
        boolean flag;

        // Posicionar la lancha
        System.out.println("Ingrese la casilla donde quiere ubicar la lancha [BARCO DE 1 CASILLA]");
        int posicionLancha = reader.nextInt();
        posicionarBarcos(tableroJugador, posicionLancha, TAMANO_LANCHA, 4);
        imprimirTableroJugador(tableroJugador);

        // Posicionar el barco médico
        flag = false;
        while (!flag) {
            System.out.println("\nIngrese la casilla inicial donde quiere ubicar el barco médico [BARCO DE 2 CASILLAS]");
            int posicionBarcoMedico = reader.nextInt();
            flag = posicionarBarcos(tableroJugador, posicionBarcoMedico, TAMANO_MEDICO, 5);
            imprimirTableroJugador(tableroJugador);
        }

        // Posicionar el barco de munición
        flag = false;
        while (!flag) {
            System.out.println("\nIngrese la casilla donde quiere ubicar el barco de munición [BARCO DE 3 CASILLAS]");
            int posicionBarcoMunicion = reader.nextInt();
            flag = posicionarBarcos(tableroJugador, posicionBarcoMunicion, TAMANO_MUNICION, 6);
            imprimirTableroJugador(tableroJugador);
        }

        jugarBattleship(tableroJugador, tableroJugadorOculto, tableroCPU, tableroCPUOculto, atacadoJugador, atacadoCPU);
    }
    /**
     * DESCRIPCIÓN
     * Este método se encarga de posicionar los barcos. En el main se le solicitó al usuario la posición barco, se tiene en cuenta el tamaño del barco y su Id para luego identificar su hundimiento. En el tablero el barco no se registra como 1 si no con su id. Esto para hacer más sencillo su verificación de hundimiento. Es de retorno booleano para así, mediante una flag verificar si todo se hizo correctamente, y el usuario no intentó poner un barco en la casilla mayor a 10. Su retorno booleano es true si fue válido y false en caso de no.
     * @param int[] tablero
     * @param int posicionBarco
     * @param int tamanoBarco
     * @param int barcoId
     * @return boolean true
     */

    public static boolean posicionarBarcos(int[] tablero, int posicionBarco, int tamanoBarco, int barcoId) {
        if (posicionBarco - 1 + tamanoBarco > tablero.length) {
            return false;
        }
        for (int i = 0; i < tamanoBarco; i++) {
            if (tablero[posicionBarco - 1 + i] > 0) {
                return false;
            }
        }
        for (int i = 0; i < tamanoBarco; i++) {
            tablero[posicionBarco - 1 + i] = barcoId;
        }
        return true;
    }


/**
 * Descripción: Lo que hace este método es crear un arreglo con los tamaños de los barcos, t de manera random entre 1 y 10 colocar un id dentro del arreglo teniendo en cuenta los tamaños, primero pone 1 barco de 3 casillas id 4, busca un espacio libre del tamaño siguiente y asi asigna un barco de 2 casillas id 5 y por ultimo busca un espacio libre y pone un barco de 1 casilla id 6. Devuelve el arreglo generado, y no tiene entradas
 * @return int[] tablero 
 */
    public static int[] generarTableroCPU() {
        int[] tablero = new int[10];
        Random rand = new Random();
        int[] tamanos = {3, 2, 1};
        int barcoId = 4;

        for (int tamano : tamanos) {
            boolean colocado = false;
            while (!colocado) {
                int posicion = rand.nextInt(11 - tamano);
                boolean espacioLibre = true;
                for (int i = 0; i < tamano && espacioLibre; i++) {
                    if (tablero[posicion + i] != 0) {
                        espacioLibre = false;
                    }
                }
                if (espacioLibre) {
                    for (int i = 0; i < tamano; i++) {
                        tablero[posicion + i] = barcoId;
                    }
                    colocado = true;
                    barcoId++;
                }
            }
        }
        return tablero;
    }

/**
 * DESCRIPCIÓN: Es sencillo, agarra el arreglo del tablero, todo lo que esté encima de 4 (un barco), el resto lo muestra como es, 0 si es 0, 2 si es 2, 3 si es 3. Su unica entrada es el tablero
 * @param int[] tablero
 */

    public static void imprimirTableroJugador(int[] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i] == 0) {
                System.out.print("0");
            } else if (tablero[i] == 2) {
                System.out.print("2");
            } else if (tablero[i] == 3) {
                System.out.print("3");
            } else if(tablero[i]>3){
                System.out.print("1");
            }
        }
        System.out.println();
    }
/**
 * El tablero de la CPU es más complejo. En el main se creó un arreglo llamado atacado para guardar aquellos puntos que ya hayan sido atacados. Cualquiera que no haya sido atacado se muestra como un asterisco *, ya que está oculto. Si ya está atacado muestra 0 si no habian barcos, 2 si está golpeado, 3 si está hundiendo.
 * @param int[] tablero
 * @param boolean[] atacado
 */

    public static void imprimirTableroCPU(int[] tablero, boolean[] atacado) {
        for (int i = 0; i < tablero.length; i++) {
            if (!atacado[i]) {
                System.out.print("*");
            } else if (tablero[i] == 0) {
                System.out.print("0");
            } else if (tablero[i] == 2) {
                System.out.print("2");
            } else if (tablero[i] == 3) {
                System.out.print("3");
            }
        }
        System.out.println();
    }

/**
 * DESCRIPCIÓN: Verificar los hundimientos fue lo más complicado, teniendo en cuenta que no pueden cambiar todos los 2 por 3 ya que pueden haber barcos golpeados que no se hundan con ese movimiento. 
 * La solución fue generarlos un id, y un arreglo que nunca se modifica, titulado como oculto. De esa manera, si el barco se hunde, cambia a 3 todos los 2 que en el otro arreglo coincidan todos sus 
 * iDs. Es vacío porque simplemente verifica y en caso de ser así, modifica internamente el arreglo y simplemente muestra un mensaje
 * @param int[] tablero
 * @param int[] tableroOculto
 * @param int barcoId
 * @param int ataque
 */
    public static void verificarHundimientoJugador(int[] tablero,int[]tableroOculto, int barcoId, int ataque) {
        boolean hundido = true;
    
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i] == barcoId) { 
                hundido = false;
                break;
            }
        }
    
        if (hundido) {
            for (int i = 0; i < tableroOculto.length; i++) {
                 if (tableroOculto[ataque] == tableroOculto[i]) { // Asegurar que sean solo las partes del barco impactadas
                    tablero[i] = 3;
                }
            }
            System.out.println("Hundiste un barco");
        }
    }
/**
 * DESCRIPCIÓN: Este arreglo es parecido al anterior, simplemente cambia el mensaje del final.
 * @param int[] tablero
 * @param int[] tableroOculto
 * @param int barcoId
 * @param int ataque
 */
    
    public static void verificarHundimientoCPU(int[] tablero,int[] tableroOculto, int barcoId, int ataque) {
        boolean hundido = true;
    
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i] == barcoId) { 
                hundido = false;
                break;
            }
        }
    
        if (hundido) {
            for (int i = 0; i < tableroOculto.length; i++) {
                 if (tableroOculto[ataque] == tableroOculto[i]) { // Asegurar que sean solo las partes del barco impactadas
                    tablero[i] = 3;
                }
            }
            System.out.println("La CPU te ha hundido un barco");
        }
    }
    
    
    
    
/**
 * DESCRIPCIÓN: En este método sucede la magia, genera los turnos, y mientras el juego siga activo, le pide al usuario el valor del ataque y siempre y cuando sea válida, utiliza
 * los métodos previamente creados para validar si golpeó un barco, si hubo hundimiento, y por último llamar al método que marca que el juego finalizó, para validar también si alguien
 * ya ganó. Es void ya que no hay necesidad de que devuelva nada.
 * @param int[] tableroJugador
 * @param int[] tableroJugadorOculto
 * @param int[] tableroCPU
 * @param int[] tableroCPUOculto
 * @param int[] atacadoJugador
 * @param int[] atacadoCPU
 */
    public static void jugarBattleship(int[] tableroJugador, int[] tableroJugadorOculto, int[] tableroCPU, int[] tableroCPUOculto, boolean[] atacadoJugador, boolean[] atacadoCPU) {
        Random rand = new Random();
        boolean juegoActivo = true;
        
        while (juegoActivo) {
            System.out.println("\nTu turno: Ingresa una posición (1-10) para atacar:");
            int ataque = reader.nextInt() - 1;
            
            if (ataque < 0 || ataque >= tableroCPU.length) {
                System.out.println("Posición inválida. Intenta de nuevo.");
                continue;
            }
            
            atacadoCPU[ataque] = true;
            if (tableroCPU[ataque] >= 4) {
                int barcoId = tableroCPU[ataque];
                tableroCPU[ataque] = 2;
                System.out.println("¡Le diste a un barco!");
                verificarHundimientoJugador(tableroCPU, tableroCPUOculto, barcoId, ataque);
            } else {
                System.out.println("Fallaste.");
            }
            imprimirTableroCPU(tableroCPU, atacadoCPU);
            
            if (juegoTerminado(tableroCPU)) {
                System.out.println("¡Has hundido todos los barcos de la CPU! ¡Ganaste!");
                break;
            }
            

            int ataqueCPU;
            do {
                ataqueCPU = rand.nextInt(10);
            } while (atacadoJugador[ataqueCPU]);

            atacadoJugador[ataqueCPU] = true;
            System.out.println("La CPU ataca la posición: " + (ataqueCPU + 1));

            if (tableroJugador[ataqueCPU] >= 1) {
                int barcoId = tableroJugador[ataqueCPU];
                tableroJugador[ataqueCPU] = 2; 
                System.out.println("La CPU impactó un barco!");
                verificarHundimientoCPU(tableroJugador, tableroJugadorOculto, barcoId, ataqueCPU);  // 🔹 Verificar si el barco del jugador se hunde
            } else {
                System.out.println("La CPU falló.");
            }
            imprimirTableroJugador(tableroJugador);
                        
            if (juegoTerminado(tableroJugador)) {
                System.out.println("¡La CPU ha hundido todos tus barcos! ¡Perdiste!");
                break;
            }
            
        }
    }

    /**
     * Descripción: Este método simplemente devuelve el booleando si en el tablero que le entregan (1 a la vez) ya todos los métodos están el algún valor que sea mayor igual a 4 o 1
     * Que son los valores que se le dan a un barco
     * @param tablero
     * @return
     */
    public static boolean juegoTerminado(int[] tablero) {
        for (int celda : tablero) {
            if (celda >= 4 || celda == 1) {
                return false;
            }
        }
        return true;
    }
}
