import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class GameTest {

    @Test
    public void testHasValidLength() {
        try {
            Word motCourt = new Word("java");
            Word motLong = new Word("orange");
            Word mot = new Word("table");
            assertFalse("Un mot de 4 lettres ne devrait pas être valide", motCourt.hasValidLength());
            assertFalse("Un mot de 6 lettres ne devrait pas être valide", motLong.hasValidLength());
            assertTrue("Le mot 'table' devrait avoir une longueur de 5", mot.hasValidLength());
        } catch (AssertionError e) {
            System.out.println("Échec du test HasValideLength : " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testHasNoSymbols() {
        try {
            Word motSymbol = new Word("ab1de");
            Word mot = new Word("piano");
            assertFalse("Le mot 'ab1de' contient un symbole", motSymbol.hasNoSymbols());
            assertTrue("Le mot 'piano' ne contient pas de symbole", mot.hasNoSymbols());
        } catch (AssertionError e) {
            System.out.println("Échec de hasNoSymbols : " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testHasNoDuplicateLetter() {
        try {
            Word motDoublon = new Word("pomme");
            Word mot = new Word("piano");
            assertFalse("Le mot 'pomme' contient un doublon", motDoublon.hasNoDuplicateLetter());
            assertTrue("Le mot 'piano' n'a pas de doublon", mot.hasNoDuplicateLetter());
        } catch (AssertionError e) {
            System.out.println("Échec de HasNoDuplicateLetter : " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testIsValid() {
        try {
            Word motParfait = new Word("rules");
            Word tropLong = new Word("clavier");
            Word avecDoublon = new Word("tarte");
            assertTrue("Le mot 'rules' remplit toutes les conditions", motParfait.isValid());
            assertFalse("Un mot de 7 lettres ne peut pas être valide globalement", tropLong.isValid());
            assertFalse("Un mot avec doublon ne peut pas être valide globalement", avecDoublon.isValid());
        } catch (AssertionError e) {
            System.out.println("Échec de isValid : " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testAnalyseWord() {
        try {
            Game a = new Game("ligne", "ligne");
            Game b = new Game("table", "ligne");
            assertTrue("Le mot 'ligne' correspond au mot secret", a.getWord().getValue().equalsIgnoreCase(a.getWordUser()));
            assertFalse("Le mot 'table' ne correspond pas au mot secret", b.getWord().getValue().equalsIgnoreCase(b.getWordUser()));
        } catch (AssertionError e) {
            System.out.println("Échec de AnalyseWord : " + e.getMessage());
            throw e;
        }
    }
}
