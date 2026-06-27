import java.util.ArrayList;

public abstract class WordRepository {

    // ** Constructeurs ** //

    public WordRepository(){
    }

    // ** Méthodes abstraites ** //

    public abstract Word getWord();

    public abstract Word getSetWord(int i);

    public abstract void displayWords();

    public abstract ArrayList<Word> getWords();

    public abstract boolean containsWord(Word w);
}