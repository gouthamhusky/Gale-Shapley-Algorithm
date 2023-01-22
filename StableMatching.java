import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Create a Stable Matching based on the Gale-Shapley algorithm
 */
public class StableMatching {

    // The number of people that need to be matched
    private final int n;

    // preference order of the males
    private final Map<String, List<String>> malePreferences;

    // preference order of the females
    private final Map<String, List<String>> femalePreferences;

    // the stable matchings, initially empty
    private final Map<String, String> matchings;

    // map to keep track of the number of girls each guy has proposed to
    // until now
    private final Map<String, Integer> proposals;

    /**
     * Constructor method that initializes all the data members, including
     * background file util processes
     * @param n : sample size of each gender
     * @throws FileNotFoundException
     */
    StableMatching(int n) throws FileNotFoundException {
        this.n = n;
        FileUtils utils = new FileUtils();
        malePreferences = utils.renderPreferences(FileUtils.Gender.MALE);
        femalePreferences = utils.renderPreferences(FileUtils.Gender.FEMALE);
        matchings = new HashMap<>();
        proposals = new HashMap<>();
        for (String male: malePreferences.keySet()){
            proposals.put(male, 0);
        }
    }

    /**
     * Generate the stable pairs
     * @return a map of the matchings
     */
    public Map<String, String> createStableMatchings(){
        int matches = 0;

        // while all the guys are not matched yet
        while(matches != n){

            // iterate over the list of guys
            for(String male: malePreferences.keySet()){
                // if the current guy is unmatched
                if (!matchings.containsKey(male)){
                    // get the index of the next girl he has to propose to
                    int toPropose = proposals.get(male);
                    String potentialMatch =
                            malePreferences.get(male).get(toPropose);
                    // if this girl is also unmatched, potentially match them up
                    if(!matchings.containsValue(potentialMatch)) {
                        matchings.put(male, potentialMatch);
                        matches++;
                    }
                    // the girl was already matched up with some other guy
                    else{
                        List<String> preferences =
                                femalePreferences.get(potentialMatch);
                        String currentSuitor = null;
                        // get the current suitor of the girl
                        for(String m: matchings.keySet()){
                            if(Objects.equals(matchings.get(m), potentialMatch)) {
                                currentSuitor = m;
                                break;
                            }
                        }
                       /*
                        if she prefers the current guy over the one she is
                        already matched with, break up the old pair and
                        create this new pair
                        */
                        if(preferences.indexOf(male) < preferences.indexOf(currentSuitor)){
                            matchings.remove(currentSuitor);
                            matchings.put(male, potentialMatch);
                        }
                    }
                    // the current guy may need to propose to his next best
                    // preference later, so update the index
                    proposals.put(male, proposals.get(male)+1);
                }
            }
        }
        return matchings;
    }

    public static void main(String[] args) throws FileNotFoundException {
        StableMatching matchings = new StableMatching(5);
        Map<String, String> matches = matchings.createStableMatchings();

        System.out.println("The matches are: " + matches.toString());
    }



}
