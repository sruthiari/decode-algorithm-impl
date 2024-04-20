import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptimizedCOA {
    public static void main(String[] args) {
        String ciphertext = "BJ|/u9u[I9n*,[83 Hgy\"1^^B-(?/ _`-g(Ei.v_0/[z~h,4nHvcZM9Kj}@b^3Z,8n<B/cQ!nn04|N\\iHcu>`>QJ~q7| v$>rV~Uz8Rf1sxU&.)5zKo.47_*+|(AjX3}ulVQ#p:=w`J3A9Xs+B\\ +Y5oWH!87-iH['OBc(;%rnn9WNU4\"Z*DSXaSQ!7?ee$  ze{[Zn-D{/GvQB<YtB.o7_*+39o# bYlRV]=*:{ry~=.h6}J^DU1Y(%to_nj4x[tOMp[&[j/'0N1t4s2HCn`TQ-]K=Y%o}UrMD!Q5Xup!BE<H@$#N^@4aGNE6;3vLK!|lO%-9d>+5J=>$Y6(:A)*\"botZ<W2m~|cWHwoY;zh</]Y;4$b";
        try {
            List<String> keys = Files.readAllLines(Paths.get("src/passwords"));

            for (String key : keys) {
                String plaintext = Rotor96Crypto.encdec(Rotor96Crypto.DEC, key, ciphertext);
                if (isEnglish(plaintext)) {
                    System.out.println("Key found: " + key);
                    System.out.println("Plaintext: " + plaintext);
                    break; // Assuming only one correct key, stop after finding it
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isEnglish(String text) {
        return containsCommonWords(text) &&
                hasHighFrequencyOfCommonLetters(text) &&
                containsCommonPairsAndTriples(text) &&
                hasCommonInitialAndFinalLetters(text);
    }

    private static boolean containsCommonWords(String text) {
        String regex = "\\b(the|be|to|of|and|a|in|that|have|I)\\b";
        return Pattern.compile(regex).matcher(text).find();
    }

    private static boolean hasHighFrequencyOfCommonLetters(String text) {
        String regex = "[etaoinshrdlcumwfgypbvkjxqz]";
        Matcher matcher = Pattern.compile(regex).matcher(text);
        int count = 0;
        while (matcher.find()) count++;
        return count >= (text.length() * 0.4);
    }

    private static boolean containsCommonPairsAndTriples(String text) {
        String pairsRegex = "(th|he|in|er|an|re|nd|at|on|nt)";
        String triplesRegex = "(the|and|tha|ent|ion|tio|for|nde|has|nce)";
        return Pattern.compile(pairsRegex).matcher(text).find() && Pattern.compile(triplesRegex).matcher(text).find();
    }

    private static boolean hasCommonInitialAndFinalLetters(String text) {
        String[] words = text.split("\\s+");
        String initialLettersRegex = "[tasonh]";
        String finalLettersRegex = "[eysdtn]";
        int initialLetterMatchCount = 0, finalLetterMatchCount = 0;
        for (String word : words) {
            if (Pattern.compile(initialLettersRegex).matcher(word.substring(0, 1)).find()) initialLetterMatchCount++;
            if (Pattern.compile(finalLettersRegex).matcher(word.substring(word.length() - 1)).find())
                finalLetterMatchCount++;
        }
        return initialLetterMatchCount >= (words.length * 0.3) && finalLetterMatchCount >= (words.length * 0.3);
    }
}
