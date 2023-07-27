package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import javafx.util.Pair;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
//        for (int x = 20; x < 35; x += 1) {
//            for (int y = 5; y < 10; y += 1) {
//                world[x][y] = Tileset.FLOOR;
//            }
//        }
        HexWorld.addHexagon(world, 3, new Pair<>(10, 20), Tileset.FLOWER);

        // draws the world to the screen
        ter.renderFrame(world);
    }


}
