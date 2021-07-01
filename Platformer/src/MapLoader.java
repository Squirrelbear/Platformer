import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * MapLoader class:
 * Loads a map from a file into the ObjectManager.
 */
public class MapLoader {
    /**
     * Reference to the ObjectManager for adding/removing objects.
     */
    private ObjectManager objectManager;

    /**
     * Initialises the MapLoader ready for loadMap().
     *
     * @param objectManager Reference to the ObjectManager for adding/removing objects.
     */
    public MapLoader(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    /**
     * Tries to open the file and load the map. Any errors will
     * be reported. Each line contains text indicating the type of
     * GameObject to make and properties for the position and in some
     * cases the width/height.
     *
     * @param fileName File to try and load.
     */
    public void loadMap(String fileName) {
        Scanner scan;
        try {
            scan = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Failed to open file: " + fileName);
            return;
        }

        objectManager.clearObjects();
        while(scan.hasNextLine()) {
            GameObject obj = createGameObjectFactory(scan.nextLine());
            if(obj != null) {
                objectManager.addObject(obj);
            }
        }
    }

    /**
     * Creates a game object from the specified data if it is valid.
     *
     * @param data The line of data to parse as an object.
     * @return Null or a valid GameObject.
     */
    public GameObject createGameObjectFactory(String data) {
        String[] splitData = data.split(" ");
        // Note this doesn't validate the length 5 data properly
        if(splitData.length < 3) {
            System.out.println("ERROR data incomplete! Data was: " + data);
            return null;
        }
        // Convert the array from position 1 to N to ints.
        int[] rectData = getRectDataFromStrArray(splitData);
        if(rectData.length == 0) {
            System.out.println("ERROR invalid rect data. Data was: " + data);
            return null;
        }

        // Create the object using the data
        switch(splitData[0]) {
            case "Block":
                return new Block(new Position(rectData[0],rectData[1]),rectData[2],rectData[3]);
            case "GrassBlock":
                return new GrassBlock(new Position(rectData[0],rectData[1]),rectData[2],rectData[3]);
            case "Spikes":
                return new Spikes(new Position(rectData[0],rectData[1]),rectData[2],rectData[3]);
            case "Player":
                return new Player(new Position(rectData[0],rectData[1]),objectManager);
            case "VictoryFlag":
                return new VictoryFlag(new Position(rectData[0],rectData[1]));
            case "Collectible":
                return new Collectible(new Position(rectData[0],rectData[1]));
            default:
                System.out.println("ERROR invalid object type. Data was: " + data);
                return null;
        }
    }

    /**
     * Expects to convert position 1 to N from String to int. If any conversion
     * fails the array will return with a size of 0.
     *
     * @param data The split strings.
     * @return An array of size 0, or the valid converted Strings to ints.
     */
    private int[] getRectDataFromStrArray(String[] data) {
        int[] result = new int[data.length-1];
        for(int i = 0; i < result.length; i++) {
            try {
                result[i] = Integer.parseInt(data[i+1]);
            } catch(Exception e) {
                return new int[0];
            }
        }
        return result;
    }
}
