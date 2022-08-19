package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;



import java.io.IOException;



public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() throws InterruptedException {
        new Keyboard(WIDTH, HEIGHT);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        SaveAndLoad saved = new SaveAndLoad();
        Avatar character;
        String seed = "";
        String direction = "";
        int index = 1;
        boolean quit = false;

        if (input.charAt(0) == ('N') || input.charAt(0) == 'n') {
            try {
                saved.createNew();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 1; i < input.length(); i++) {
                if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                    index = i;
                    break;
                }
                seed += input.charAt(i);
            }

        } else if (input.charAt(0) == 'l' || input.charAt(0) == 'L') {
            try {
                String[] data = saved.load();
                seed = data[0];
                direction = data[1];
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        for (int i = index + 1; i < input.length(); i++) {
            if (input.charAt(i) == ':') {
                quit = true;
                break;
            }
            direction += input.charAt(i);
        }

        long lonseed = Long.parseLong(seed);
        MapGenerator world = new MapGenerator(lonseed, WIDTH, HEIGHT);
        character = new Avatar(direction, world, world.getFlowers());
        TETile[][] finalWorldFrame = character.moveAvatar();

        if (quit) {
            try {
                saved.save(seed, direction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return finalWorldFrame;
    }

    public static void main(String[] args) throws InterruptedException {
        Engine test = new Engine();
        test.interactWithKeyboard();
        //render.renderFrame(test.interactWithInputString("N23947S:Q"));
////        render.renderFrame(test.interactWithInputString("l:q"));
//        render.renderFrame(test.interactWithInputString("lwd:Q"));
////        render.renderFrame(test.interactWithInputString("ld"));
//        render.renderFrame(test.interactWithInputString("lwww:Q"));
    }
}
