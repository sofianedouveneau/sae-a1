import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    
    // ** La classe game gère la logique du jeu ** //


    // ** Attributs ** //
    
    private String userName;
    private WordRepository wordRepository;
    private Word word;
    private String wordUser;
    //private String secretWord;

    private Scanner scanner;
    
    // ** Constructeurs ** //

    public Game() {
        JsonWordRepository a = new JsonWordRepository();
        this.word = a.getWord();
        this.wordRepository = a;
        this.scanner = new Scanner(System.in);
        userName();
        wordVerification();
    }

    public Game(String word, String wordUser) {
        FixedWordRepository a = new FixedWordRepository(word);
        this.wordRepository = a;
        this.wordUser = wordUser;
        this.word = a.getWord();
    }

    // ** Méthodes ** //

    // Afficher l'état courant de la partie
    // vérifie la validité de la tentative, tant qu'il n'est pas valide on redemande
    // vérifie si la partie est terminée


    //Demander et enregistrer le nom du joueur. 
    public void userName() {

        System.out.print("Veuillez entrer votre pseudonyme : ");
        this.userName = scanner.nextLine();
    }

    // Créer le mot secret en début de partie : 
    public void getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(wordRepository.getWords().size());
        this.word = wordRepository.getSetWord(index);
    }

    // Saisit la tentative proposée par le joueur

     public String wordUserInput(){
        Word w = null;
        String input = "";

        while(w == null || !w.isValid() || !wordRepository.containsWord(w)){
            System.out.print("Entrez un mot français à 5 lettres (sans symboles ni de lettre dupliquées) : ");
            input = scanner.nextLine();

            w = new Word(input);

            if(!w.isValid()){
                System.out.println("Votre mot n'est pas conforme aux règles, réessayez.");
            }
        }

        return input;
    }

    // Vérification du mot 

    // A FAIRE!!!!!!!

    public void wordVerification() {

        this.wordUser = wordUserInput();
        int tentatives = 1;

        while(!this.wordUser.equalsIgnoreCase(this.word.getValue()) && tentatives<6){
            System.out.println(showAllAttempts());
            this.wordUser = wordUserInput();
            tentatives = tentatives + 1;
            System.out.println("Tentative numéro : "+tentatives);
        }

        if(this.wordUser.equalsIgnoreCase(this.word.getValue())){
            if(tentatives<=3){
                System.out.println("Waouh "+this.userName+" ! Tu es trop fort ! Tu as trouvé le mot !");
            }
            if(3<tentatives && tentatives<=6){
                System.out.println("Bien joué "+this.userName+", tu as trouvé le mot");
            }
        }
        else{
            System.out.println("Raté ! Tu n'as pas trouvé le mot en 6 tentatives. Gros nul bouahaha");
            System.out.println("La réponse était : "+this.word.getValue()+". Gros noobbbbbb XDD");
        }
    }


    // Analyse lettre par lettre

    private String analyzeLetterAt(int position, String secretWord) {

        char letter = wordUser.charAt(position);
    
        if (letter == secretWord.charAt(position)) {
            return "OK";
        }
        else if (secretWord.indexOf(letter) != -1) {
            return "PRESENT";
        }
        else {
            return "ABSENT";
        }
    }


    public String[] analyzeWord(String secretWord) {

        String[] resultats = new String[wordUser.length()];
    
        for (int position = 0; position < wordUser.length(); position++) {
            resultats[position] = analyzeLetterAt(position, secretWord);
        }
    
        return resultats;
    }


    // Afficher l'état courant de la partie
    public String showAllAttempts() {
        String[] resultats = analyzeWord(word.getValue());

        String lettres = "";
        String etats = "";

        for (int position = 0; position < wordUser.length(); position++) {
            lettres += "[ " + wordUser.charAt(position) + " ]";
            etats += resultats[position] + " ";
        }

        return lettres + " -> " + etats.trim();
    }

    public String getWordUser(){
        return this.wordUser;
    }

    public Word getWord(){
        return this.word;
    }

}