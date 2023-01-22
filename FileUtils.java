import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileUtils {
    enum Gender{
        MALE, FEMALE;
    }

    public Map<String, List<String>> renderPreferences(Gender gender) throws FileNotFoundException {

        Map<String, List<String>> allPreferences = new HashMap<>();
        File prefFile;
        if (gender == Gender.FEMALE)
            prefFile = new File("resources/femalepref.txt");
        else
            prefFile = new File("resources/malepref.txt");

        Scanner reader = new Scanner(prefFile);
        while(reader.hasNextLine()){
            String line = reader.nextLine();
            String[] names = line.split(",");
            List<String> preferences = new ArrayList<>();
            for(int i = 1; i < names.length; i++)
                preferences.add(names[i]);
            allPreferences.put(names[0], preferences);
        }
        return allPreferences;
    }
}
