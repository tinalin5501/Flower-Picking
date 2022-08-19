package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.Random;

public class Avatar {
    private MapGenerator world;
    private TETile[][] map;
    private TETile[][] temp = new TETile[80][50];
    private String direction;
    private int avatarX = 1;
    private int avatarY = 1;
    private int tempX;
    private int tempY;
    private int flowers;
    private int lives = 3;
    private int water = 0;
    private boolean alt = false;
    private Random rand;
    private RandomUtils ranutils = new RandomUtils();
    private int gold = 0;

    public Avatar(String direction, MapGenerator world, int flowers) {
        map = world.makeWorld();
        this.world = world;
        rand = new Random(flowers);
        this.flowers = flowers;
        this.direction = direction;
    }

    public TETile[][] moveAvatar() {
        for (int i = 0; i < direction.length(); i++) {
            moveAvatarKey(direction.charAt(i));
        }
        return map;
    }

    public TETile[][] moveAvatarKey(Character key) {

        if (key.equals('W') || key.equals('w')) {
            move(0, 1);
        } else if (key.equals('S') || key.equals('s')) {
            move(0, -1);
        } else if (key.equals('A') || key.equals('a')) {
            move(-1, 0);
        } else if (key.equals('D') || key.equals('d')) {
            move(1, 0);
        }

        return map;
    }

    public TETile[][] move(int x, int y) {
        TETile tile = map[avatarX + x][avatarY + y];
        if (alt) {
            if (tile != Tileset.MINIWALL) {
                if (tile == Tileset.WATER) {
                    water -= 1;

                }
                if (water <= 0) {
                    alt = false;
                    undoGame();
                    return map;
                }
                map[avatarX][avatarY] = Tileset.FLOOR;
                map[avatarX + x][avatarY + y] = Tileset.SADAVATAR;
                avatarY = avatarY + y;
                avatarX = avatarX + x;
            }
            return map;
        }
        if (tile == Tileset.FLOWER) {
            flowers -= 1;
        }
        if (tile == Tileset.FIRE && gold == 0) {
            map[55 - (3 - lives)][48] = Tileset.LOSTLIFE;
            lives -= 1;
            alt = true;
            return miniGame();
        }
        if (!(tile == Tileset.WALL)) {
            if (tile == Tileset.GOLDFLOWER) {
                gold += 15;
            }

            map[avatarX][avatarY] = Tileset.FIRE;
            if (gold > 0) {
                gold -= 1;
                map[avatarX + x][avatarY + y] = Tileset.GOLDAVATAR;
            } else {
                map[avatarX + x][avatarY + y] = Tileset.AVATAR;
            }
            avatarY = avatarY + y;
            avatarX = avatarX + x;
        }
        return map;
    }

    public void undoGame() {
        for (int i = 30; i <= 50; i++) {
            for (int j = 10; j <= 20; j++) {
                map[i][j] = temp[i][j];
            }
        }
        avatarX = tempX;
        avatarY = tempY;
    }

    public TETile[][] miniGame() {
        tempX = avatarX;
        tempY = avatarY;
        for (int i = 31; i <= 49; i++) {
            for (int j = 11; j <= 19; j++) {
                temp[i][j] = map[i][j];
                map[i][j] = Tileset.FLOOR;
            }
        }
        for (int i = 30; i <= 50; i++) {
            temp[i][10] = map[i][10];
            temp[i][20] = map[i][20];
            map[i][10] = Tileset.MINIWALL;
            map[i][20] = Tileset.MINIWALL;
        }
        for (int i = 11; i <= 19; i++) {
            temp[30][i] = map[30][i];
            temp[50][i] = map[50][i];
            map[30][i] = Tileset.MINIWALL;
            map[50][i] = Tileset.MINIWALL;
        }
        addWater();
        avatarX = 31;
        avatarY = 11;
        map[avatarX][avatarY] = Tileset.SADAVATAR;
        return map;
    }

    public void addWater() {
        int numwater = ranutils.uniform(rand, 5, 15);
        for (int i = 0; i < numwater; i++) {
            int x = ranutils.uniform(rand, 32, 49);
            int y = ranutils.uniform(rand, 11, 19);
            if (map[x][y] == Tileset.FLOOR) {
                map[x][y] = Tileset.WATER;
                water++;
            }

        }
    }

    public int getFlowers() {
        return flowers;
    }
    public int getLives() {
        return lives;
    }
    public boolean isAlt() {
        return alt;
    }
    public int getWater() {
        return water;
    }
    public int getGold() {
        return gold;
    }
}
