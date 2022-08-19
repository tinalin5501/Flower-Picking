package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Keyboard {
    private int WIDTH;
    private int HEIGHT;
    private String userInput = "";
    private String name = "";
    private SavedFile file = new SavedFile();
    private Font font = new Font("Monaco", Font.BOLD, 30);
    private Font smallFont = new Font("Monaco", Font.BOLD, 15);
    private Font bigFont = new Font("Monaco", Font.BOLD, 45);
    private Color lightBlue = new Color(204, 204, 255);
    private boolean stop = true;
    private boolean won = false;
    private boolean die = false;
    private boolean replay = false;
    private boolean quit = false;
    private boolean clicked = false;

    public Keyboard(int width, int height) throws InterruptedException {
        WIDTH = width;
        HEIGHT = height;
        startScreen();
        while (userInput.equals("")) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                userInput += curr;
            }
        }
        startGame(userInput);
    }

    public void startScreen() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(lightBlue);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT - 5, "FLOWER PICKING!");
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT - 15, "NEW GAME (N)");
        StdDraw.text(WIDTH / 2, HEIGHT - 20, "LOAD GAME (L)");
        StdDraw.text(WIDTH / 2, HEIGHT - 25, "QUIT (Q)");
        StdDraw.show();
        StdDraw.pause(1000);

    }

    public void startGame(String input) throws InterruptedException {
        StdDraw.clear(lightBlue);
        if (input.equals("N") || input.equals("n")) {
            userInput = "";
            nameScreen();
            lore();

            StdDraw.clear();
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, HEIGHT - 5, "PLEASE ENTER SEED! (only numbers)");
            StdDraw.setFont(smallFont);
            StdDraw.text(WIDTH / 2, HEIGHT - 10, "when you're seed is complete, type (S)");
            StdDraw.show();

            while (!StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                if (StdDraw.hasNextKeyTyped()) {
                    char curr = StdDraw.nextKeyTyped();
                    StdDraw.clear(lightBlue);
                    StdDraw.setFont(font);
                    StdDraw.text(WIDTH / 2, HEIGHT - 5, "PLEASE ENTER SEED! (only numbers)");
                    StdDraw.setFont(smallFont);
                    StdDraw.text(WIDTH / 2, HEIGHT - 10, "when you're seed is complete, type (S)");
                    userInput += curr;
                    StdDraw.text(WIDTH / 2, HEIGHT - 15, userInput);
                    StdDraw.show();
                }
            }
            String seed = userInput.substring(1);
            userInput = "";
            render(seed, "");
            endingScreen();
        }

        if (input.equals("L") || input.equals("l")) {
            load();
            endingScreen();
        }

        if (input.equals("Q") || input.equals("q")) {
            quit = true;
            endingScreen();
        }
    }

    public void load() {
        try {
            userInput = "";
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, HEIGHT - 5, "LOAD FILES");
            StdDraw.setFont(smallFont);
            StdDraw.text(WIDTH / 2, HEIGHT - 10, " TYPE THE FILE YOU WANT TO LOAD");
            String[] options = file.name();
            for (int i = 0; i < 4; i++) {
                if (options[i] != null) {
                    StdDraw.text(WIDTH / 2, HEIGHT - 15 - (i * 5), options[i]);
                } else {
                    StdDraw.text(WIDTH / 2, HEIGHT - 15 - (i * 5), "NO FILE AVAILABLE");
                }
            }
            StdDraw.show();

            while (!StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                if (StdDraw.hasNextKeyTyped()) {
                    char curr = StdDraw.nextKeyTyped();
                    StdDraw.clear(lightBlue);
                    StdDraw.setFont(font);
                    StdDraw.text(WIDTH / 2, HEIGHT - 5, "LOAD FILES");
                    StdDraw.setFont(smallFont);
                    StdDraw.text(WIDTH / 2, HEIGHT - 10, " TYPE THE FILE YOU WANT TO LOAD");
                    StdDraw.text(WIDTH / 2, HEIGHT - (HEIGHT - 5), "when complete, press ENTER");
                    for (int i = 0; i < 4; i++) {
                        if (options[i] != null) {
                            StdDraw.text(WIDTH / 2, HEIGHT - 15 - (i * 5), options[i]);
                        } else {
                            StdDraw.text(WIDTH / 2, HEIGHT - 15 - (i * 5), "NO FILE AVAILABLE");
                        }
                    }
                    name += curr;
                    StdDraw.text(WIDTH / 2, HEIGHT - (HEIGHT - 10), name);
                    StdDraw.show();
                }
            }
            String[] data = file.load(name);
            userInput = data[1];
            StdDraw.clear(lightBlue);
            StdDraw.setFont(bigFont);
            StdDraw.text(WIDTH / 2, HEIGHT - 5, "DO YOU WANT TO REPLAY SAVE FILE?");
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, HEIGHT - 10, "YES: (Y)");
            StdDraw.text(WIDTH / 2, HEIGHT - 15, "NO: (N)");
            StdDraw.show();

            while (!quit && !die && !won) {
                if (StdDraw.isKeyPressed(KeyEvent.VK_N)) {
                    render(data[0], userInput);
                } else if (StdDraw.isKeyPressed(KeyEvent.VK_Y)) {
                    replay = true;
                    render(data[0], "");
                }
            }
            endingScreen();

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lore() {
        StdDraw.clear(lightBlue);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, HEIGHT - 10, "It was a bright sunny day.");
        StdDraw.show();
        StdDraw.pause(1000);
        StdDraw.text(WIDTH / 2, HEIGHT - 20, name + " decided to go flower picking.");
        StdDraw.show();
        StdDraw.pause(2000);
        StdDraw.text(WIDTH / 2, HEIGHT - 30, "It was going well, until it wasn't...");
        StdDraw.show();
        StdDraw.pause(2000);
    }

    public void endingScreen() {
        StdDraw.clear(lightBlue);
        StdDraw.setFont(bigFont);
        if (won) {
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "WOOHOOO YOU WON!!!!!");
        } else if (die) {
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "YOU DIED :( ouch");
        }
        if (die || won) {
            StdDraw.setFont(smallFont);
            StdDraw.text(WIDTH / 2, HEIGHT / 2 - 5, "WATCH PREVIOUS PLAY (W)");
            StdDraw.text(WIDTH / 2, HEIGHT / 2 - 10, "QUIT (Q)");
            StdDraw.show();
        }
        while (!quit) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
                file.overwrite(name);
                quit = true;
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_W)) {
                try {
                    String[] data = file.load(name);
                    replay = true;
                    stop = true;
                    render(data[0], "");
                    endingScreen();
                } catch (IOException | InterruptedException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if (quit) {
            StdDraw.clear(lightBlue);
            StdDraw.setFont(bigFont);
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "BYE BYE~");
            StdDraw.show();
            StdDraw.pause(1000);
            System.exit(0);
        }
    }

    public void nameScreen() {
        int length = file.nameLength();

        if (length < 4) {
            StdDraw.clear(lightBlue);
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, HEIGHT - 5, "ENTER YOUR NAME!");
            StdDraw.setFont(smallFont);
            StdDraw.text(WIDTH / 2, HEIGHT - 10, "press ENTER when done");
            StdDraw.show();
            while (!StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                if (StdDraw.hasNextKeyTyped()) {
                    StdDraw.clear(lightBlue);
                    StdDraw.setFont(font);
                    StdDraw.text(WIDTH / 2, HEIGHT - 5, "ENTER YOUR NAME!");
                    StdDraw.setFont(smallFont);
                    StdDraw.text(WIDTH / 2, HEIGHT - 10, "press ENTER when done");
                    Character current = StdDraw.nextKeyTyped();
                    name += current;
                    StdDraw.text(WIDTH / 2, HEIGHT - 15, name);
                    StdDraw.show();
                }
            }
        } else {
            String[] options = file.name();
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, HEIGHT - 5, "LOAD FILES FULL");
            StdDraw.setFont(smallFont);
            StdDraw.text(WIDTH / 2, HEIGHT - 10, "TYPE THE FILE YOU WANT TO OVERRIDE");
            StdDraw.text(WIDTH / 2, HEIGHT - (HEIGHT - 5), "press ENTER when done");
            for (int i = 0; i < 4; i++) {
                StdDraw.text(WIDTH / 2, HEIGHT - 15 - (i * 5), options[i]);
            }
            StdDraw.show();
            while (!StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                if (StdDraw.hasNextKeyTyped()) {
                    StdDraw.clear(lightBlue);
                    StdDraw.setFont(font);
                    StdDraw.text(WIDTH / 2, HEIGHT - 5, "LOAD FILES FULL");
                    StdDraw.setFont(smallFont);
                    StdDraw.text(WIDTH / 2, HEIGHT - 10, "TYPE THE FILE YOU WANT TO OVERRIDE");
                    StdDraw.text(WIDTH / 2, HEIGHT - (HEIGHT - 5), "press ENTER when done");

                    for (int i = 0; i < 4; i++) {
                        StdDraw.text(WIDTH / 2, HEIGHT - 15 - (i * 5), options[i]);
                    }
                    Character current = StdDraw.nextKeyTyped();
                    name += current;
                    StdDraw.text(WIDTH / 2, HEIGHT - (HEIGHT - 10), name);
                    StdDraw.show();
                }
            }
            file.overwrite(name);
            nameScreen();
        }
    }


    private boolean quit(String input) {
        if (input.charAt(input.length() - 1) == 'Q' || input.charAt(input.length() - 1) == 'q') {
            return (input.charAt(input.length() - 2) == ':');
        }
        return false;
    }

    public void save(String direction, String seed) {
        try {
            file.createNew(name);
            System.out.println(userInput);
            file.save(seed, direction, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void instructions() {
        StdDraw.clear(lightBlue);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, HEIGHT / 3, "Move around with W, A, S, D");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "This is you! ☺");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 5, "You win when you get all the flowers! ❀");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 10, "Make sure not to step in your trail of fire!");
        StdDraw.show();
        StdDraw.pause(3000);

    }

    public void render(String seed, String direction) throws InterruptedException {
        int index = 0;
        instructions();
        TERenderer render = new TERenderer();
        render.initialize(WIDTH, HEIGHT);
        long lonseed = Long.parseLong(seed);
        MapGenerator world = new MapGenerator(lonseed, WIDTH, HEIGHT);
        Avatar character = new Avatar(direction, world, world.getFlowers());
        TETile[][] finalWorldFrame = character.moveAvatar();
        render.renderFrame(finalWorldFrame);
        while (stop) {
            if (StdDraw.isMousePressed()) {
                clicked = true;
            }
            if (StdDraw.hasNextKeyTyped() || clicked) {
                if (!clicked) {
                    Character curr;
                    if (replay) {
                        curr = userInput.charAt(index);
                        index++;
                        TimeUnit.SECONDS.sleep(1);
                        if (index == userInput.length()) {
                            replay = false;
                        }
                    } else {
                        curr = StdDraw.nextKeyTyped();
                        userInput += curr;
                    }
                    finalWorldFrame = character.moveAvatarKey(curr);
                }

                render.renderFrame(finalWorldFrame);
                if (character.isAlt()) {
                    StdDraw.setPenColor(Color.WHITE);
                    StdDraw.setFont(bigFont);
                    StdDraw.text(WIDTH / 2, HEIGHT - 10, "OUCH U GOT BURNED!!");
                    StdDraw.text(WIDTH / 2, HEIGHT - 20, "GET " + character.getWater() + " WATERS");
                }
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.setFont(smallFont);
                StdDraw.text(7, HEIGHT - 2, "TO QUIT AND SAVE (:Q)");
                StdDraw.text(WIDTH - 10, HEIGHT - 2, "FLOWERS LEFT: " + character.getFlowers());
                StdDraw.text(WIDTH - 31, HEIGHT - 2, "HEALTH BAR");
                StdDraw.text(25, HEIGHT - 2, "TILE: ");
                StdDraw.text(30, HEIGHT - 2, world.hoverTile());
                if (character.getGold() > 0) {
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.text(WIDTH / 2, HEIGHT - 5, "YOU ARE INDESTRUCTIBLE FOR " + character.getGold() + " MOVES");
                }
                StdDraw.show();
                clicked = false;
            }
            if (character.getLives() == 0) {
                save(userInput, seed);
                stop = false;
                die = true;
            } else if (character.getFlowers() == 0) {
                save(userInput, seed);
                stop = false;
                won = true;
            } else if (userInput.length() > 0 && quit(userInput)) {
                quit = true;
                save(userInput.substring(1, userInput.length() - 2), seed);
                stop = false;
            }
        }
    }
}

