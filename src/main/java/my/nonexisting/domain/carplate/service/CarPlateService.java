package my.nonexisting.domain.carplate.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

/**
 *
 * @author Texhnolyze
 */
@Service
public class CarPlateService {
    
    private static final char[] ALPHABET = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М'};
    private static final int LETTER_BASE = ALPHABET.length;
    private static final Map<Character, Integer> INDEX_OF = new HashMap<>();
    private static final int NUM_LETTERS = 3;
    private static final int NUM_NUMBERS = 3;
    private static final int NUM_POW = pow(10, NUM_NUMBERS - 1);
    private static final int LETTER_POW = pow(LETTER_BASE, NUM_LETTERS - 1);
    private static final int MAX_NUMBER = pow(10, NUM_NUMBERS);
    private static final int MAX_LETTER_NUMBER = pow(LETTER_BASE, NUM_LETTERS);
    
    static {
        Arrays.sort(ALPHABET);
        for (int i = 0; i < ALPHABET.length; i++) INDEX_OF.put(ALPHABET[i], i);
    }
    
    public String randomCarPlate() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();
        sb.append(ALPHABET[rand.nextInt(ALPHABET.length)]);
        for (int i = 0; i < NUM_NUMBERS; i++)
            sb.append(rand.nextInt(10));
        for (int i = 1; i < NUM_LETTERS; i++)
            sb.append(ALPHABET[rand.nextInt(ALPHABET.length)]);
        return sb.append(" 116 RUS").toString();
    }
    
    public String nextCarPlate(String prev) {
        StringBuilder sb = new StringBuilder(); 
        int[] n = new int[NUM_NUMBERS];
        for (int i = 0; i < NUM_NUMBERS; i++)
            n[i] = prev.charAt(i + 1) - '0';
        int next = 0;
        int pow = NUM_POW;
        for (int i = 0; i < NUM_NUMBERS; i++) {
            next += (n[i] * pow);
            pow /= 10;
        }
        next++;
        if (next != MAX_NUMBER) {
            sb.append(prev.charAt(0));
            int numDigits = log10(next) + 1;
            for (int i = 0; i < NUM_NUMBERS - numDigits; i++)
                sb.append('0');
            sb.append(next);
            for (int i = 0; i < NUM_LETTERS - 1; i++)
                sb.append(prev.charAt(1 + NUM_NUMBERS + i));
        } else {
            char[] c = new char[NUM_LETTERS];
            c[0] = prev.charAt(0);
            for (int i = 1; i < NUM_LETTERS; i++)
                c[i] = prev.charAt(1 + NUM_NUMBERS + (i - 1));
            int nextLetterNumber = (toNumber(c) + 1) % MAX_LETTER_NUMBER;
            String letters = toLetters(nextLetterNumber);
            sb.append(letters.charAt(0));
            for (int i = 0; i < NUM_NUMBERS; i++)
                sb.append('0');
            for (int i = 1; i < NUM_LETTERS; i++)
                sb.append(letters.charAt(i));
        }
        return sb.append(" 116 RUS").toString();
    }
    
    private static int toNumber(char[] c) {
        int pow = LETTER_POW;
        int res = 0;
        for (int i = 0; i < NUM_LETTERS; i++) {
            res += (INDEX_OF.get(c[i]) * pow);
            pow /= LETTER_BASE;
        }
        return res;
    }
    
    private static String toLetters(int letterNumber) {
        StringBuilder sb = new StringBuilder();
        int curr = letterNumber;
        int digitsRemained = NUM_LETTERS;
        int pow = LETTER_POW;
        while (digitsRemained > 0) {
            int digit = (curr / pow);
            sb.append(ALPHABET[digit]);
            curr -= (digit * pow);
            pow /= LETTER_BASE;
            digitsRemained--;
        }
        return sb.toString();
    }
    
    private static int log10(int a) {
        int n = 10;
        int log10 = 0;
        while (n <= a) {
            log10++;
            n *= 10;
        }
        return log10;
    }
    
    private static int pow(int a, int b) {
        if (b == 0)
            return 1;
        int res = a;
        for (int i = 0; i < b - 1; i++) 
            res *= a;
        return res;
    }
    
}
