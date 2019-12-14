import java.util.HashSet;
import java.util.Set;

public class Island extends HashSet<Object_> {
    private int islandIndex;

    Island(int islandIndex) {
        this.islandIndex = islandIndex;
    }

    public Island() {

    }

    public int getIslandIndex() {
        return islandIndex;
    }

}
