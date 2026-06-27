import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;


public class WordRepository extends Word {

    // ** Attributs ** //

    private static ArrayList<Word> wordsUnfiltered = new ArrayList<>();
    private static final ArrayList<Word> wordsFiltered = new ArrayList<>();

    static {
        wordsUnfiltered = new ArrayList<>();

        try {
            String content = Files.readString(
                Path.of("data/mots.json")
            );

            content = content.trim();

            if (content.startsWith("[")) {
                content = content.substring(1);
            }
            if (content.endsWith("]")) {
                content = content.substring(0, content.length() - 1);
            }

            String[] items = content.split(",");

            for (String item : items) {
                String cleanWord = item.replaceAll("[^a-zA-ZÀ-ÿ]", "").trim();
                if (!cleanWord.isEmpty()) {
                    wordsUnfiltered.add(new Word(cleanWord));
                }
            }

            wordsUnfiltered.forEach(word -> {
                if (word.isValid()) {
                    wordsFiltered.add(word);
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ** Constructeurs ** //

    public WordRepository(){
    }

    public WordRepository(String word) {
        super(word);
    }

    // ** Méthodes ** //

    public String getRandomFilteredWord(){
        Random rand = new Random();

        int i = rand.nextInt(wordsFiltered.size());
        
        return wordsFiltered.get(i).getValue();
    }

    public String getSetFilteredWord(int i){
        return wordsFiltered.get(i).getValue();
    }

    public void displayFilteredWords(){
        for(int i=0; i<wordsFiltered.size(); i++){
            System.out.println(this.getSetFilteredWord(i));
        }
    }

    public ArrayList<Word> getFilteredWords(){
        return wordsFiltered;
    }

    public static void main(String[] args){
        
        WordRepository a = new WordRepository();

        System.out.println("mot aléatoire : "+a.getRandomFilteredWord());

        // a.displayFilteredWords();
    }

}