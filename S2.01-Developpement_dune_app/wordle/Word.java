public class Word {

    // ** Attributs ** //

    private String word;

    // ** Constructeurs ** //

    public Word(){
    }

    public Word(String word){
        this.word = word.toLowerCase();
    }

    // ** Méthodes ** /

    public boolean hasValidLength(){
        return word.length() == 5;
    }

    public boolean hasNoSymbols() {
        for (char character : word.toCharArray()){
            if (!Character.isLetter(character)){
                return false;
            }
        }
        return true;
    }

    public boolean hasNoDuplicateLetter() {
        for (int i =0; i<word.length(); i++){
            for (int j= i+1; j<word.length(); j++){
                if(word.charAt(j) == word.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid(){
        return hasNoSymbols() && hasNoDuplicateLetter() && hasValidLength();
    }
    
    public String getValue(){
        return word;
    }

    public Word getWord() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWord'");
    }

}
