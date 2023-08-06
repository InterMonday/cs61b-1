package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import javafx.geometry.Pos;
import javafx.util.Pair;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game {
    static TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static Random RANDOM;
    private static Point pos;
    private static class Rect {
        private final int m_x, m_y, m_w, m_h;
        public Rect(int x, int y, int w, int h){
            m_x = x;
            m_y = y;
            m_w = w;
            m_h = h;
        }
        //包含
        private boolean isContain(Rect r){
            return m_x >= r.m_x && m_y >= r.m_y && m_w >= r.m_w && m_h >= r.m_h;
        }
        public static boolean isCont(Rect[] rects, Rect r){
            for (Rect rect : rects){
                if (rect == null) continue;
                if(r.isContain(rect) || rect.isContain(r))
                    return true;
            }
            return false;
        }
        //相交
        private boolean isIntse(Rect r){
            if(m_x > r.m_x + r.m_w || m_x + m_w < r.m_x || m_y > r.m_y + r.m_h || m_y + m_h < r.m_h)
                return false;
            return true;
        }
        public static boolean isIntersect(Rect[] rects, Rect r){
            for (Rect rect : rects){
                if (rect == null) continue;
                if(r.isIntse(rect))
                    return true;
            }
            return false;
        }
    }
    private static boolean first = true;//判段是否是第一次移动
    private static boolean newGame;//新建游戏
    private static final String filePath = "./TE.ser";
    private static final String filePath1 = "./POS.ser";

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() throws IOException {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        initTE(finalWorldFrame);
        ter.initialize(WIDTH,HEIGHT + 1);
        StdDraw.clear();
        StdDraw.text(40, 19, "New Game(N)");
        StdDraw.text(40, 16, "Load Game(L)");
        StdDraw.text(40, 13, "Quit Game(:Q)");
        StdDraw.show();

        int rand = 0;
        while (true){//输入N,Q或L退出循环
            if(StdDraw.hasNextKeyTyped()) {
                char n = StdDraw.nextKeyTyped();
                System.out.println(n);
                if (n == 'n' || n == 'N') {
                    newGame = true;
                    StdDraw.text(40, 10, "请输入种子：");
                    StdDraw.show();
                    break;
                }
                if(n == 'l' || n == 'L'){
                    finalWorldFrame = loadGame();
                    newGame = false;
                    first = false;
                    break;
                }
                if(n == 'q' || n == 'Q'){
                    return;
                }
            }
        }
        while (newGame){//获取种子，输入S结束并退出循环
            if(StdDraw.hasNextKeyTyped()) {
                char n = StdDraw.nextKeyTyped();
                System.out.println(n);
                if (n == 's' || n == 'S') {
                    System.out.println(rand);
                    break;
                }
                else {
                    rand = rand * 10 + n - '0';
                    StdDraw.clear();
                    StdDraw.text(40, 19, "New Game(N)");
                    StdDraw.text(40, 16, "Load Game(L)");
                    StdDraw.text(40, 13, "Quit Game(:Q)");
                    StdDraw.text(40, 10, "请输入种子：");
                    StdDraw.text(40, 7, Integer.toString(rand));
                    StdDraw.show();
                }
            }
        }

        if (newGame) {
            //绘制地图
            final long SEED = rand;
            RANDOM = new Random(SEED);
            int rectCount = RANDOM.nextInt(30) + 1;//矩形数量
            Rect[] rects = new Rect[rectCount];
            createRect(finalWorldFrame, rects, rectCount);
            linkRect(finalWorldFrame, rects);

            //确定出生点
            selectBirth(finalWorldFrame, rects);
        }
        ter.renderFrame(finalWorldFrame);

        //wasd移动,:Q退出
        boolean quit = false;
        while (true){
            if(StdDraw.hasNextKeyTyped()) {
                char n = StdDraw.nextKeyTyped();
                if (n == ':') {
                    quit = true;
                    continue;
                }
                if (quit && (n == 'Q' || n == 'q')) {
                    saveGame(finalWorldFrame);
                    break;
                }
                quit = false;
                wasdMove(finalWorldFrame, n);
                ter.renderFrame(finalWorldFrame);
            }

            if ((int) StdDraw.mouseX()< WIDTH && (int) StdDraw.mouseY()< HEIGHT){
                System.out.println((int) StdDraw.mouseX() + " " + (int) StdDraw.mouseY());
                StdDraw.clear();
                ter.renderFrame(finalWorldFrame);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(40, 30, finalWorldFrame[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description());
                StdDraw.show();
                StdDraw.pause(1500);
            }
        }
    }

    private static void saveGame(TETile[][] t) {
        File file = new File(filePath);
        File file1 = new File(filePath1);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!file1.exists()) {
                file1.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file);
            FileOutputStream fs1 = new FileOutputStream(file1);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            ObjectOutputStream os1 = new ObjectOutputStream(fs1);
            os.writeObject(t);
            os1.writeObject(pos);
            os.close();
            os1.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static TETile[][] loadGame() {
        File file = new File(filePath);
        File file1 = new File(filePath1);
        pos = new Point(0, 0);
        TETile[][] t = new TETile[WIDTH][HEIGHT];
        initTE(t);
        if (file.exists()){
            try {
                FileInputStream fs = new FileInputStream(file);
                FileInputStream fs1 = new FileInputStream(file1);
                ObjectInputStream os = new ObjectInputStream(fs);
                ObjectInputStream os1 = new ObjectInputStream(fs1);
                t = (TETile[][]) os.readObject();
                pos = (Point) os1.readObject();
                os.close();
                os1.close();
                return t;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return t;
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);
        initTE(finalWorldFrame);
        int index = 0;
        long rand = 0;
        newGame = false;
        while (index < input.length()){
            if (input.charAt(index) == 'n' || input.charAt(index) == 'N' ) {
                newGame = true;
                rand = getRand(input, index + 1);
                System.out.println(rand);
            }
            if(newGame && (input.charAt(index) == 's' || input.charAt(index) == 'S')) {
                index++;
                break;
            }
            if(!newGame && (input.charAt(index) == 'l' || input.charAt(index) == 'L')){
                finalWorldFrame = loadGame();
                index++;
                first = false;
                break;
            }
            if(!newGame && (input.charAt(index) == 'q' || input.charAt(index) == 'Q')){
                return finalWorldFrame;
            }
            index++;
        }
        if(newGame) {
            final long SEED = rand;
            RANDOM = new Random(SEED);

            int rectCount = RANDOM.nextInt(30) + 1;//矩形数量
            Rect[] rects = new Rect[rectCount];
            createRect(finalWorldFrame, rects, rectCount);

            linkRect(finalWorldFrame, rects);
            //确定出生点
            selectBirth(finalWorldFrame, rects);

        }
        ter.renderFrame(finalWorldFrame);
        //wasd移动
        while (index < input.length()) {
            char n = input.charAt(index);
            if (n == ':' && ++index < input.length()) {
                n = input.charAt(index);
                if (n == 'Q' || n == 'q') {
                    saveGame(finalWorldFrame);
                    break;
                }
            }
            wasdMove(finalWorldFrame, n);
            ter.renderFrame(finalWorldFrame);
            index++;
        }

        return finalWorldFrame;
    }

    /**
     * wasd移动
     */
    public static void wasdMove(TETile[][] t, char n){
        int nx = pos.x, ny = pos.y;
        if (n == 'w' || n == 'W') {
            ny++;
        } else if (n == 'a' || n == 'A') {
            nx--;
        } else if (n == 's' || n == 'S') {
            ny--;
        } else if (n == 'd' || n == 'D') {
            nx++;
        }
        System.out.println(n + "wasd");

        if (!t[nx][ny].equals(Tileset.FLOOR))
            return;
        if (first) {
            t[pos.x][pos.y] = Tileset.UNLOCKED_DOOR;
            first = false;
        } else t[pos.x][pos.y] = Tileset.FLOOR;
        pos.x = nx;
        pos.y = ny;
        t[pos.x][pos.y] = Tileset.PLAYER;
    }

    /**
     * 选择出生地
     */
    public static void selectBirth(TETile[][] t, Rect[] rects){
        int index = RANDOM.nextInt(rects.length);
        int xb = RANDOM.nextInt(rects[index].m_w) + rects[index].m_x + 1;
        pos = new Point(xb, rects[index].m_y);
        t[pos.x][pos.y] = Tileset.PLAYER;
    }

    /**
     * 创建矩形数组
     */
    public static void createRect(TETile[][] t, Rect[] rects, int rectCount){
        while (rectCount > 0){//创建矩形
            int x = RANDOM.nextInt(80), y = RANDOM.nextInt(30);
            int[] rect = RANDOM.ints(2, 1, 15).toArray();
            if(rect[0] + x + 1 >= WIDTH || rect[1] + y + 1 >= HEIGHT)//出界
                continue;
            Rect temp = new Rect(x, y, rect[0], rect[1]);
            if (Rect.isCont(rects, temp))
                continue;
            rects[rectCount - 1] = temp;
            drawRect(t, x, y, rect[0], rect[1]);
            rectCount--;
        }
    }

    public static void linkDotX(TETile[][] t, int x1, int x2, int y){
        while (x2 <= x1){
            t[x2][y] = Tileset.FLOOR;
            if(t[x2][y - 1] != Tileset.FLOOR) t[x2][y - 1] = Tileset.WALL;
            if(t[x2][y + 1] != Tileset.FLOOR) t[x2][y + 1] = Tileset.WALL;
            x2++;
        }
    }

    public static void linkDotY(TETile[][] t, int x, int yy1, int yy2){
        int y1, y2;
        if(yy1 > yy2){//如果y2比y1小，说明需要从y2矩形内部穿过，矩形底部会出现缺口，为弥补缺口跳过第一步。
            y1 = yy2 + 1;
            y2 = yy1;
        } else {
            y1 = yy1;
            y2 = yy2;
        }
        if(t[x][y1 - 1] != Tileset.FLOOR) t[x][y1 - 1] = Tileset.WALL;
        if(t[x - 1][y1 - 1 ] != Tileset.FLOOR) t[x - 1][y1 - 1] = Tileset.WALL;
        if(t[x + 1][y1 - 1] != Tileset.FLOOR) t[x + 1][y1 - 1] = Tileset.WALL;
        while (y1 <= y2){
            t[x][y1] = Tileset.FLOOR;
            if(t[x - 1][y1] != Tileset.FLOOR) t[x - 1][y1] = Tileset.WALL;
            if(t[x + 1][y1] != Tileset.FLOOR) t[x + 1][y1] = Tileset.WALL;
            y1++;
        }
        if(t[x][y1] != Tileset.FLOOR) t[x][y1] = Tileset.WALL;
        if(t[x - 1][y1] != Tileset.FLOOR) t[x - 1][y1] = Tileset.WALL;
        if(t[x + 1][y1] != Tileset.FLOOR) t[x + 1][y1] = Tileset.WALL;
    }

    public static void selectDots(TETile[][] t, Rect l, Rect r){
        int x1, y1, x2, y2;
        if(l.m_x > r.m_x){
            x1 = l.m_x;
            y1 = RANDOM.nextInt(l.m_h) + l.m_y + 1;
            x2 = RANDOM.nextInt(r.m_w) + r.m_x + 1;
            y2 = r.m_y;
        }
        else {
            x1 = r.m_x;
            y1 = RANDOM.nextInt(r.m_h) + r.m_y + 1;
            x2 = RANDOM.nextInt(l.m_w) + l.m_x + 1;
            y2 = l.m_y;
        }

        linkDotY(t, x2, y1, y2);
        linkDotX(t, x1, x2, y1);
    }

    /**
     * 链接矩形
     */
    public static void linkRect(TETile[][] t, Rect[] rects){
        Rect[] linked = new Rect[rects.length];
        linked[0] = rects[0];
        for (int i = 1; i < rects.length; i++){
            if(!Rect.isIntersect(linked, rects[i])){
                int j = RANDOM.nextInt(i);
                System.out.println(j + " " + i);
                selectDots(t, linked[j], rects[i]);
            }
            linked[i] = rects[i];
        }
    }

    //初始化
    public static void initTE(TETile[][] t){
        for (int i = 0; i < WIDTH; i++){
            for (int j = 0;j < HEIGHT; j++)
                t[i][j] = Tileset.NOTHING;
        }
    }
    //画矩形
    public static void drawRect(TETile[][] t, int x, int y, int w, int h){
        int xw = x + w + 1, yh = y + h + 1;
        for (int i = x; i <= xw; i++){
            for (int j = y; j <= yh; j++){
                if(t[i][j] == Tileset.FLOOR) //是地板就跳过
                    continue;
                else if (i == x || j == y|| i == xw || j == yh)//边界围墙
                    t[i][j] = Tileset.WALL;
                else t[i][j] = Tileset.FLOOR;//内部地板
            }
        }
    }

    /**
     * 获得随机种子
     */
    public static long getRand(String s, int i){
        long res = 0;
        while (i < s.length()){
            if(s.charAt(i) == 's' || s.charAt(i) == 'S')
                return res;
            res = res * 10 + s.charAt(i) - '0';
            i++;
        }
        return res;
    }
}
