package ExamenPrimerParcial;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author CarlosTule
 */
public class ClienteBuscaminas {

    public static final byte NIVEL_PRINCIPANTE = 10;
    public static final byte NIVEL_INTERMEDIO = 17;
    public static final byte NIVEL_DIFICIL = 31;

    public static final byte MINAS_NIVEL_PRINCIPANTE = 10;
    public static final byte MINAS_NIVEL_INTERMEDIO = 40;
    public static final byte MINAS_NIVEL_DIFICIL = 99;

    public static List<Casilla> tablero = new ArrayList<>(); // Un arrayList con las casillas que formarán el Tablero
    public static byte filas, columnas, intentos, minasDificultad, numeroMinas, a, b;
    public static boolean isGanador = true;
    
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void main(String[] args) {
        try {
            intentos = 0;
            Scanner sc = new Scanner(System.in);
            System.out.println("Qué IP ocuparás?:");
            String ipAddr = sc.next();
            System.out.println("Qué puerto ocuparás?:");
            int port = sc.nextInt();
            Socket cliente = new Socket(ipAddr, port);
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());

            System.out.println("Conexion establecida");
            System.out.println("Dificultad:\n 1 - Sencillo    2 - Intermedio     3 - Dificil");
            byte dificultad = sc.nextByte();
            dos.writeByte(dificultad);
            clearScreen();
            if (dificultad == 2) {
                columnas = filas = NIVEL_INTERMEDIO;
                minasDificultad = MINAS_NIVEL_INTERMEDIO;
            } else if (dificultad == 3) {
                filas = NIVEL_DIFICIL;
                columnas = NIVEL_INTERMEDIO;
                minasDificultad = MINAS_NIVEL_DIFICIL;
            } else {
                columnas = filas = NIVEL_PRINCIPANTE;
                minasDificultad = MINAS_NIVEL_PRINCIPANTE;
            }
            Casilla matrizTablero[][] = new Casilla[filas][columnas];
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            tablero.clear();
            tablero = (List<Casilla>) ois.readObject();
            dos.writeUTF("OK");

            int z = 0;
            for (int i = 0; i < filas; i++) {
                //System.out.println("");
                for (int j = 0; j < columnas; j++, z++) {
                    matrizTablero[i][j] = tablero.get(z);
                    //System.out.print(" "+matrizTablero[i][j].getMinasAlrededor()+" ");
                }
            }
            while (isGanador) {
                imprimir(matrizTablero);
                System.out.println("\nCasilla - 1   Bandera - 2 ");
                try{
                    
                }catch(InputMismatchException e){
                    continue;
                }
                if (sc.nextByte() == 1) {
                    System.out.print("Coordenada X:");
                    a = sc.nextByte();
                    System.out.print("Coordenada Y:");
                    b = sc.nextByte();
                    isGanador = seleccionaMina(matrizTablero, b, a, false);
                } else {
                    System.out.print("Coordenadas X de la bandera: ");
                    a = sc.nextByte();
                    System.out.print("CoordenadasY de la bandera: ");
                    b = sc.nextByte();
                    isGanador = seleccionaMina(matrizTablero, b, a, true);
                }
                clearScreen();
                if (isGanador == false) {
                    System.out.println("Game over :(");
                    imprimirTodo(matrizTablero);
                    dos.writeUTF("LOST");
                    break;
                }
                if (intentos == minasDificultad) {
                    System.out.println("¡Has ganado!");
                    dos.writeUTF("WIN");
                    break;
                }
                intentos++;
            }
            dos.close();
            ois.close();
            cliente.close();
        } catch (IOException | ClassNotFoundException e) {
            e.toString();
        }
    }

    public static void imprimirTodo(Casilla[][] matrizTablero) { //Imprime el tablero si el jugador perdió
        for (int i = 0; i < filas - 1; i++) {
            System.out.println("");
            for (int j = 0; j < columnas - 1; j++) {
                Casilla casilla = matrizTablero[i][j];
                if (casilla.getIsMina() == false) {
                    System.out.print(" " + casilla.getMinasAlrededor() + " ");
                } else {
                    System.out.print(" * ");
                }
            }
        }
    }

    public static void imprimir(Casilla[][] matrizTablero) { //Imprime el tablero con los valores ocultos
        int i = 0;
        int j = 0;
        for (i = 0; i < filas-1 ; i++) {
            System.out.println("");
            for (j = 0; j < columnas-1 ; j++) {
                Casilla casilla = matrizTablero[i][j];

                if (casilla.getIsShown() == (byte) 0) {
                    System.out.print(" - ");
                } else if (casilla.getIsShown() == (byte) 1) {
                    System.out.print(" " + casilla.getMinasAlrededor() + " ");
                } else {
                    System.out.print(" X ");
                }
            }
        }
    }

    public static boolean seleccionaMina(Casilla[][] matrizTablero, byte fila, byte columna, boolean isBandera) {
        try{
            Casilla seleccion = matrizTablero[fila][columna];
            if (isBandera == false) {
                //es mina
                if (seleccion.getIsMina() == true) {
                    return false;
                }
                //destapar
                seleccion.setIsShown((byte) 1);
                //Si la mina es cero avisa a sus hermanos ceros
                if (seleccion.getMinasAlrededor() == 0) {
                    avisarCeros(seleccion, matrizTablero);
                }
            } else {
                //Poner bandera
                seleccion.setIsShown((byte) 2);
            }
        return true;
        }catch(IndexOutOfBoundsException | InputMismatchException e){
            System.out.println("Coordenadas incorrectas");
            return true;
        }   
    }

    public static void avisarCeros(Casilla casillaActual, Casilla[][] conteo) {
        byte i_aux = casillaActual.getFila();
        byte j_aux = casillaActual.getColumna();
        //Se puso una mina, así que avisamos a sus vecinos
        if (casillaActual.getTipo() == 1) {
            if (casillaActual.getFila() == 0) {
                if (casillaActual.getColumna() == 0) { //superior izquierda
                    destaparCero(conteo[0][1], conteo);
                    destaparCero(conteo[1][0], conteo);
                    destaparCero(conteo[1][1], conteo);
                } else if (casillaActual.getColumna() == (columnas - 1)) { //superior derecha
                    destaparCero(conteo[0][(j_aux) - 2], conteo);
                    destaparCero(conteo[1][(j_aux) - 1], conteo);
                    destaparCero(conteo[1][(j_aux) - 2], conteo);
                }
            } else { //casillaActual.getFila()==(filas-1)
                if (casillaActual.getColumna() == 0) { //inferior izquierda
                    destaparCero(conteo[(i_aux) - 1][1], conteo);
                    destaparCero(conteo[(i_aux) - 2][0], conteo);
                    destaparCero(conteo[(i_aux) - 2][1], conteo);
                } else {//inferior derecha
                    destaparCero(conteo[(i_aux) - 1][(j_aux) - 2], conteo);
                    destaparCero(conteo[(i_aux) - 1][(j_aux) - 2], conteo);
                    destaparCero(conteo[(i_aux) - 2][(j_aux) - 1], conteo);
                }
            }
        } else if (casillaActual.getTipo() == 2) {
            if (i_aux == 0) {//superiores
                destaparCero(conteo[0][(j_aux) - 1], conteo);
                destaparCero(conteo[0][(j_aux) + 1], conteo);
                destaparCero(conteo[1][(j_aux) - 1], conteo);
                destaparCero(conteo[1][(j_aux) + 1], conteo);
                destaparCero(conteo[1][j_aux], conteo);
            } else if (i_aux == (filas - 1)) {//inferiores
                destaparCero(conteo[(filas - 1)][(j_aux) - 1], conteo);
                destaparCero(conteo[(filas - 1)][(j_aux) + 1], conteo);
                destaparCero(conteo[(filas - 2)][(j_aux) - 1], conteo);
                destaparCero(conteo[(filas - 2)][(j_aux) + 1], conteo);
                destaparCero(conteo[(filas - 2)][(j_aux)], conteo);
            } else if (j_aux == 0) {//izquierda
                destaparCero(conteo[(i_aux) - 1][0], conteo);
                destaparCero(conteo[(i_aux) + 1][0], conteo);
                destaparCero(conteo[(i_aux) - 1][1], conteo);
                destaparCero(conteo[(i_aux) + 1][1], conteo);
                destaparCero(conteo[i_aux][1], conteo);
            } else if (j_aux == (columnas - 1)) {//derecha
                destaparCero(conteo[(i_aux) - 1][j_aux], conteo);
                destaparCero(conteo[(i_aux) + 1][j_aux], conteo);
                destaparCero(conteo[(i_aux) - 1][(j_aux) - 1], conteo);
                destaparCero(conteo[(i_aux) + 1][(j_aux) - 1], conteo);
                destaparCero(conteo[i_aux][(j_aux) - 1], conteo);
            }
        } else {
            destaparCero(conteo[i_aux][(j_aux) - 1], conteo);
            destaparCero(conteo[i_aux][(j_aux) + 1], conteo);
            destaparCero(conteo[(i_aux) - 1][(j_aux) - 1], conteo);
            destaparCero(conteo[(i_aux) - 1][j_aux], conteo);
            destaparCero(conteo[(i_aux) - 1][(j_aux) + 1], conteo);
            destaparCero(conteo[(i_aux) + 1][(j_aux) - 1], conteo);
            destaparCero(conteo[(i_aux) + 1][j_aux], conteo);
            destaparCero(conteo[(i_aux) + 1][(j_aux) + 1], conteo);
        }
    }

    public static void destaparCero(Casilla casilla, Casilla conteo[][]) {
        if(casilla.getIsShown()!=0){
            return;
        }
        if (casilla.getMinasAlrededor() == 0) {
            casilla.setIsShown((byte) 1);
            avisarCeros(casilla, conteo);
        }
    }
}
