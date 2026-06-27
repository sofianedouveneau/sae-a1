import java.util.ArrayList;

public class FixedWordRepository extends WordRepository{
    private Word fixedWord;

    public FixedWordRepository(String word) {
        this.fixedWord = new Word(word);
    }

    public Word getWord() {
        return fixedWord;
    }
    
    public Word getSetWord(int i) {
        return fixedWord;
    }

    public void displayWords() {
        System.out.println(fixedWord.getValue());
    }

    public ArrayList<Word> getWords() {
        ArrayList<Word> list = new ArrayList<>();
        list.add(fixedWord);
        return list;
    }

    public boolean containsWord(Word w) {
        if (fixedWord.getValue().equalsIgnoreCase(w.getValue())) {
            return true;
        }

        return false;
    }

}
