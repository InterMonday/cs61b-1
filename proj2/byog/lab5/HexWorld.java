package byog.lab5;
import javafx.util.Pair;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static void addHexagon(TETile[][] world, int size, Pair<Integer, Integer> p, TETile t){
//        int[][] m = new int[10][10];
//        for (int i = 0; i < 10; i++){
//            for (int j = 0; j < 10; j++)
//                m[i][j] = 0;
//        }
        if(size < 2) return;
        int colSize = size, max = size + (colSize - 1) * 2;
        while(colSize > 0){
            for (int i = 0; i < max; i++){
                if(i < colSize - 1 || i > max - colSize) {
                    world[p.getKey() + i][p.getValue() + size - colSize + 1] = Tileset.NOTHING;
//                    m[size - colSize + 1][i] = 0;
//                    System.out.print(' ');
                } else {
                    world[p.getKey() + i][p.getValue() + size - colSize + 1]= t;
//                    m[size - colSize + 1][i] = 1;
//                    System.out.print('x');
                }
            }
//            System.out.println();
            colSize--;
        }
        while(colSize < size){
            for (int i = 0; i < max; i++){
                if(i < colSize || i > max - colSize - 1) {
                    world[p.getKey() + i][p.getValue() + size + colSize + 1] = Tileset.NOTHING;
//                    m[size + 1 + colSize][i] = 0;
                } else {
                    world[p.getKey() + i][p.getValue() + size + colSize+ 1] = t;
//                    m[size + 1 + colSize][i] = 1;
                }
            }
            colSize++;
        }
//        for (int i = 0; i < 10; i++){
//            for (int j = 0; j < 10; j++)
//                System.out.print(m[i][j]);
//            System.out.println();
//        }
    }

    public static void main(String[] args) {
//        addHexagon(4);
//        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);
//
//        // initialize tiles
//        TETile[][] world = new TETile[WIDTH][HEIGHT];
//        for (int x = 0; x < WIDTH; x += 1) {
//            for (int y = 0; y < HEIGHT; y += 1) {
//                world[x][y] = Tileset.NOTHING;
//            }
//        }
//
//        // fills in a block 14 tiles wide by 4 tiles tall
//        for (int x = 20; x < 35; x += 1) {
//            for (int y = 5; y < 10; y += 1) {
//                world[x][y] = Tileset.FLOOR;
//            }
//        }
//
//        // draws the world to the screen
//        ter.renderFrame(world);
    }
}
