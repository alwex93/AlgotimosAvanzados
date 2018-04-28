package Modelo;

import Modelo.Piezas.*;

import java.awt.*;
import java.util.ArrayList;

public class Tablero implements ModelInterface{

    private int[][] tablero;
    private static int DEFAULT = 8;
    private int dimension;

    private static int ORDER_COLOCAR = -1;
    private static int ORDER_QUITAR = 1;

    public Tablero(){
        tablero = new int[DEFAULT][DEFAULT];
        dimension = DEFAULT;
    }

    public Tablero(int dim){
        tablero = new int[dim][dim];
        for(int fila = 0; fila < dim; fila++){
            for(int columna = 0; columna < dim; columna++){
                tablero[fila][columna] = 0;
            }
        }
        dimension = dim;
    }

    public boolean colocarPieza(Posicion pos, Pieza pieza){
        marcado(pieza, pos, ORDER_COLOCAR);
        tablero[pos.getX()][pos.getY()] = pieza.getValue();
        pieza.colocar();
        return pieza.isColocada();
    }

    public void quitarPieza(Posicion pos, Pieza pieza){
        marcado(pieza, pos, ORDER_QUITAR);
        tablero[pos.getX()][pos.getY()] = 0;
        pieza.quitar();
    }

    private void marcado(Pieza pieza, Posicion pos, int order){
        switch (pieza.getValue()){
            case 'D':
                marcarHorizontal(order, pos);
                marcarVertical(order, pos);
                marcarDiagonalDerecha(order, pos);
                marcarDiagonalIzquierda(order, pos);
                break;
            case 'T':
                marcarHorizontal(order, pos);
                marcarVertical(order, pos);
                break;
            case 'A':
                marcarDiagonalDerecha(order, pos);
                marcarDiagonalIzquierda(order, pos);
                break;
            case '1':
                break;
            case '2':
                break;
        }
    }


    private void marcarHorizontal(int order, Posicion piezaPos){
        for (int columna = 0; columna < dimension; columna++){
            tablero[piezaPos.getX()][columna] += order;
        }
    }

    private void marcarVertical(int order, Posicion piezaPos){
        for (int fila = 0; fila < dimension; fila++){
            tablero[fila][piezaPos.getY()] += order;
        }
    }

    private void marcarDiagonalDerecha(int order, Posicion piezaPos){
        int fila = piezaPos.getX();
        int columna = piezaPos.getY();

        for(int d = 0; fila - d >= 0 && columna + d < dimension; d++){
            tablero[fila - d][columna + d] += order;
        }

        for(int d = 0; fila + d < dimension && columna - d >= 0; d++){
            tablero[fila + d][columna - d] += order;
        }
    }

    private void marcarDiagonalIzquierda(int order, Posicion piezaPos){
        int fila = piezaPos.getX();
        int columna = piezaPos.getY();

        for(int d = 0; fila + d >= 0 && columna + d >= 0; d--){
            tablero[fila + d][columna + d] += order;
        }

        for(int d = 0; fila + d < dimension && columna + d < dimension; d++){
            tablero[fila + d][columna + d] += order;
        }
    }

    public ArrayList<Posicion> getPosLibres(){
        ArrayList<Posicion> libres = new ArrayList<>();
        for(int fila = 0; fila < dimension; fila++){
            for(int columna = 0; columna < dimension; columna++){
                if(tablero[fila][columna] == 0){
                    libres.add(new Posicion(fila, columna));
                }
            }
        }
        return libres;
    }

    public boolean piezaColocada(Pieza pieza) {
        return pieza.isColocada();
    }

    public void print() {
        System.out.println("Tablero: \n");
        for (int[] pro1 : tablero) {
            for (int y = 0; y < dimension; y++) {
                if (pro1[y] == 0){
                    System.out.print((char)-1 + " ");
                } else if (pro1[y] < 0){
                    System.out.print("X ");
                } else {
                    System.out.print((char)pro1[y] + " ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("\n\n");
    }

    @Override
    public void actualizarDimensionTablero(int nuevaDimension) {
        tablero = new int[nuevaDimension][nuevaDimension];
        dimension = nuevaDimension;
    }

    @Override
    public Image[][] getTablero() {
        Image[][] imagenes = new Image[dimension][dimension];
        for (int fila = 0; fila < dimension; fila++){
            for (int columna = 0; columna < dimension; columna++){
                imagenes[fila][columna] = getImagen((char) tablero[fila][columna]);
            }
        }
        return imagenes;
    }

    private Image getImagen(char caracter){
        switch (caracter) {
            case 'D':
                return new Dama().getImage();
            case 'A':
                return new Alfil().getImage();
            case 'T':
                return new Torre().getImage();
            case 'L':
                return new Inv1().getImage();
            case 'H':
                return new Inv2().getImage();
            default: return null;
        }
    }
}
