import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class KPA {

    private static final String[] COMMON_WORDS = {"the", "be", "to", "of", "and", "a", "in", "that", "have", "I"};
    private static final String VOWELS = "aeiou";
    private static final String COMMON_CONSONANTS = "tnsrl";
    private static final Pattern PUNCTUATION_PATTERN = Pattern.compile(".*[.,;?!].*");

    public static void main(String[] args) throws IOException {
        String ciphertext = ";Y|YxeX3!{AY7dC1)EEE]6CK|BiK>hf>d9<hPQ,[!'eo]R+dJ#j]v2`sZZ%x6w3PJLVH9.4C?\\\"v(#?s/vs)qe?9m 9L`5qp`7!,GU/}%oih&Yo8&dEho{l%+E&ith?omU~`PYV|H' \"xEH?'*%e<NLmKqK$f2wP~/A=m6[\\73}F8toy3f(vhp)O:{KQ-/Y=*7evxqh+TFQW`1Wp`-M%I20";
        List<String> potentialKeys = loadPasswords("src/passwords");

        List<String> foundKeys = new ArrayList<>();
        List<String> decodedMessages = new ArrayList<>();

        for (String key : potentialKeys) {
            String trialDecryption = Rotor96Crypto.encdec(Rotor96Crypto.DEC, key, ciphertext);

            if (startsWithKnownPlaintext(trialDecryption) && isReadableEnglish(trialDecryption)) {
                foundKeys.add(key);
                decodedMessages.add(trialDecryption);
                // Do not break; continue to check other keys
            }
        }

        if (!foundKeys.isEmpty()) {
            for (int i = 0; i < foundKeys.size(); i++) {
                System.out.println("Key: " + foundKeys.get(i));
                System.out.println("Decoded Message: " + decodedMessages.get(i));
                System.out.println(); // Add a blank line between different keys' results
            }
        } else {
            System.out.println("No valid key found.");
        }
    }

    public static List<String> loadPasswords(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public static boolean startsWithKnownPlaintext(String text) {
        return text.startsWith("Hi");
    }


    public static boolean isReadableEnglish(String text) {
        String lowerCaseText = text.toLowerCase();
        return hasMultipleCommonWords(lowerCaseText) &&
                containsVowelsAndConsonants(lowerCaseText) &&
                hasSpacesBetweenWords(lowerCaseText) &&
                containsPunctuation(lowerCaseText);
    }

    private static boolean hasMultipleCommonWords(String text) {
        long count = Arrays.stream(COMMON_WORDS)
                .filter(text::contains)
                .count();
        return count >= 3;
    }

    private static boolean containsVowelsAndConsonants(String text) {
        boolean hasVowels = VOWELS.chars().anyMatch(c -> text.indexOf(c) >= 0);
        boolean hasCommonConsonants = COMMON_CONSONANTS.chars().anyMatch(c -> text.indexOf(c) >= 0);
        return hasVowels && hasCommonConsonants;
    }

    private static boolean hasSpacesBetweenWords(String text) {
        return text.contains(" ");
    }

    private static boolean containsPunctuation(String text) {
        return PUNCTUATION_PATTERN.matcher(text).find();
    }

}
