package byow.Core;

import java.io.Serializable;

//@Source https://www.vogella.com/tutorials/JavaSerialization/article.html
public class Serialization implements Serializable {
    private String seed;
    private String direction;


    public Serialization(String seed, String direction) {
        this.seed = seed;
        this.direction = direction;
    }

    public String getSeed() {
        return seed;
    }

    public String getDirection() {
        return direction;
    }


    @Override
    public String toString() {
        return "Serialization [seed =" + seed + ", direction =" + direction + "]";
    }


}
