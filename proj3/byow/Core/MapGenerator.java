package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;

public class MapGenerator {
    private TETile[][] map;
    private int WIDTH;
    private int HEIGHT;
    private Random rand;
    private int numRoom = 0;
    private RandomUtils ranutils = new RandomUtils();
    private int[][] room;
    private int[][] roomCenters;
    private int flowers = 0;

    public MapGenerator(long seed, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        map = new TETile[width][height];
        rand = new Random(seed);
        int rooms = ranutils.uniform(rand, 7, 10);
        room = new int[rooms + 1][4];
        roomCenters = new int[rooms + 1][2];
        makeRooms(rooms);
        rectangleRoom(3, 3, 0, 0);
        makeHallways();
        placeFlowers();
        goldFlowers();
        for (int i = 0; i < width; i++) {
            map[i][height - 1] = Tileset.DISPLAY;
            map[i][height - 2] = Tileset.DISPLAY;
            map[i][height - 3] = Tileset.DISPLAY;
        }
        map[width - 27][height - 2] = Tileset.LIFE;
        map[width - 26][height - 2] = Tileset.LIFE;
        map[width - 25][height - 2] = Tileset.LIFE;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] == null) {
                    map[i][j] = Tileset.GRASS;
                }
            }
        }

        map[1][1] = Tileset.AVATAR;
    }


    public TETile[][] makeWorld() {
        return map;
    }

    // creates all of the rooms
    public void makeRooms(int rooms) {
        for (int i = 0; i < rooms; i++) {
            randomRec();
        }
    }

    // creates a rectangular room
    public void rectangleRoom(int width, int height, int x, int y) {
        for (int i = x + 1; i < x + width; i++) {
            for (int j = y + 1; j < y + height; j++) {
                map[i][j] = Tileset.FLOOR;
            }
        }
        for (int i = x; i < x + width + 1; i++) {
            map[i][y] = Tileset.WALL;
            map[i][y + height] = Tileset.WALL;
        }
        for (int i = y + 1; i < y + height; i++) {
            map[x][i] = Tileset.WALL;
            map[x + width][i] = Tileset.WALL;
        }
        room[numRoom][0] = x;
        room[numRoom][1] = y;
        room[numRoom][2] = x + width;
        room[numRoom][3] = y + height;
        roomCenters[numRoom][0] = x + (width / 2);
        roomCenters[numRoom][1] = y + (height / 2);
        numRoom++;
    }

    // creates random x, y, width, height for the new rectangle
    public void randomRec() {
        int width = ranutils.uniform(rand, WIDTH / 10, WIDTH / 5);
        int height = ranutils.uniform(rand, HEIGHT / 10, HEIGHT / 3);
        int x = ranutils.uniform(rand, 2, WIDTH - (WIDTH / 5));
        int y = ranutils.uniform(rand, 2, HEIGHT - (HEIGHT / 3));
        adjust(width, height, x, y);
    }

    public void adjust(int width, int height, int x, int y) {
        int xMin = x;
        int yMin = y;
        int xMax = x + width;
        int yMax = y + height;

        // compares the new rectangle coordinates to already created rectangles
        for (int i = 0; i < numRoom; i++) {
            int ixMin = room[i][0];
            int iyMin = room[i][1];
            int ixMax = room[i][2];
            int iyMax = room[i][3];

            if (!(xMin > ixMax + 1 || xMax < ixMin - 1 || yMin > iyMax + 1 || yMax < iyMin - 1)) {
                randomRec();
                return;
            }
        }
        rectangleRoom(xMax - xMin, yMax - yMin, xMin, yMin);
    }

    public void makeHallways() {
        for (int i = 0; i < roomCenters.length - 1; i++) {
            connect(roomCenters[i], roomCenters[i + 1]);
        }
    }

    //@Source https://roguesharp.wordpress.com
    public void connect(int[] start, int[] end) {
        int startx = start[0];
        int starty = start[1];
        int endx = end[0];
        int endy = end[1];


        for (int i = Math.min(startx, endx); i <= Math.max(startx, endx); i++) {
            map[i][starty] = Tileset.FLOOR;
            if (map[i][starty + 1] == null) {
                map[i][starty + 1] = Tileset.WALL;
            }
            if (map[i][starty - 1] == null) {
                map[i][starty - 1] = Tileset.WALL;
            }

        }

        for (int j = Math.min(starty, endy); j <= Math.max(starty, endy); j++) {
            map[endx][j] = Tileset.FLOOR;
            if (map[endx + 1][j] == null) {
                map[endx + 1][j] = Tileset.WALL;
            }
            if (map[endx - 1][j] == null) {
                map[endx - 1][j] = Tileset.WALL;
            }
        }

        if (startx < endx) {
            if (starty < endy) {
                map[endx + 1][starty - 1] = Tileset.WALL;
            } else {
                map[endx + 1][starty + 1] = Tileset.WALL;
            }
        } else {
            if (starty < endy) {
                map[endx - 1][starty - 1] = Tileset.WALL;
            } else {
                map[endx - 1][starty + 1] = Tileset.WALL;
            }
        }
    }

    public void placeFlowers() {
        int numFlowers = ranutils.uniform(rand, 20, 50);
        for (int i = 0; i < numFlowers; i++) {
            int x = ranutils.uniform(rand, 2, WIDTH - (WIDTH / 5));
            int y = ranutils.uniform(rand, 2, HEIGHT - (HEIGHT / 3));
            if (map[x][y] == Tileset.FLOOR) {
                map[x][y] = Tileset.FLOWER;
                flowers++;
            }
        }
    }

    public void goldFlowers() {
        int numFlowers = ranutils.uniform(rand, 10, 15);
        for (int i = 0; i < numFlowers; i++) {
            int x = ranutils.uniform(rand, 2, WIDTH - (WIDTH / 5));
            int y = ranutils.uniform(rand, 2, HEIGHT - (HEIGHT / 3));
            if (map[x][y] == Tileset.FLOOR) {
                map[x][y] = Tileset.GOLDFLOWER;
            }
        }
    }

    public int getFlowers() {
        return flowers;
    }

    public String hoverTile() {
        int xpos = (int) Math.round(StdDraw.mouseX());
        int ypos = (int) Math.round(StdDraw.mouseY());
        if (xpos < WIDTH && ypos < HEIGHT) {
            return map[xpos][ypos].description();
        }
        return "";
    }
}
