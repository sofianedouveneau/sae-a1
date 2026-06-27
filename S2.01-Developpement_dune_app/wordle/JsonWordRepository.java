import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

public class JsonWordRepository extends WordRepository {
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
                String cleanWord = item.replaceAll("[^a-zA-Z\u00C0-\u00FF]", "").trim();
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

    public JsonWordRepository() {
        super();
    }

    public Word getWord(){
        Random rand = new Random();

        int i = rand.nextInt(wordsFiltered.size());
        
        return wordsFiltered.get(i);
    }

    public Word getSetWord(int i){
        return wordsFiltered.get(i);
    }

    public void displayWords(){
        for(int i=0; i<wordsFiltered.size(); i++){
            System.out.println(this.getSetWord(i));
        }
    }

    public ArrayList<Word> getWords(){
        return wordsFiltered;
    }

    public boolean containsWord(Word w) {
            for (Word currWord : wordsFiltered) {

            if (currWord.getValue().equalsIgnoreCase(w.getValue())) {
                return true;
            }
        }
        return false;
    }

}
