package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    private static Color lightBlue = new Color(204, 204, 255);
    private static Color avatar1 = new Color(0, 0, 128);
    private static Color avatar2 = new Color(204, 204, 255);
    private static Color gold = new Color(255, 223, 0);
    private static Color flower = new Color(255, 182, 193);
    private static Color grass1 = new Color(34, 139, 34);
    private static Color grass2 = new Color(144, 238, 144);

    public static final TETile AVATAR = new TETile('☺', avatar1, avatar2, "you");
    public static final TETile SADAVATAR = new TETile('☺', Color.white, Color.red, "sad you");
    public static final TETile GOLDAVATAR = new TETile('☺', Color.white, gold, "golden you");
    public static final TETile WALL = new TETile('.', new Color(216, 128, 128), Color.lightGray,
            "wall");
    public static final TETile MINIWALL = new TETile('.', Color.white, new Color(255, 182, 193),
            "mini wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.BLACK, Color.BLACK, "nothing");

    public static final TETile GRASS = new TETile('"', grass1, grass2, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, lightBlue, "water");
    public static final TETile FLOWER = new TETile('❀', flower, Color.black, "flower");
    public static final TETile GOLDFLOWER = new TETile('❀', gold, Color.black, "gold flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile DISPLAY = new TETile(' ', Color.green, Color.white, "blank");
    public static final TETile FIRE = new TETile('▲', Color.orange, Color.red, "fire");
    public static final TETile LIFE = new TETile('♥', Color.red, Color.lightGray, "life");
    public static final TETile LOSTLIFE = new TETile(' ', Color.red, Color.lightGray, "lost life");
}


