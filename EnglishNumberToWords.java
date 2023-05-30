package convertor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnglishNumberToWords {
    private static final Map<Integer, String> numberWords = new HashMap<>();

    static {
        numberWords.put(0, "zero");
        numberWords.put(1, "one");
        numberWords.put(2, "two");
        numberWords.put(3, "three");
        numberWords.put(4, "four");
        numberWords.put(5, "five");
        numberWords.put(6, "six");
        numberWords.put(7, "seven");
        numberWords.put(8, "eight");
        numberWords.put(9, "nine");
        numberWords.put(10, "ten");
        numberWords.put(11, "eleven");
        numberWords.put(12, "twelve");
        numberWords.put(13, "thirteen");
        numberWords.put(14, "fourteen");
        numberWords.put(15, "fifteen");
        numberWords.put(16, "sixteen");
        numberWords.put(17, "seventeen");
        numberWords.put(18, "eighteen");
        numberWords.put(19, "nineteen");
        numberWords.put(20, "twenty");
        numberWords.put(30, "thirty");
        numberWords.put(40, "forty");
        numberWords.put(50, "fifty");
        numberWords.put(60, "sixty");
        numberWords.put(70, "seventy");
        numberWords.put(80, "eighty");
        numberWords.put(90, "ninety");
    }

    public static StringBuilder convertNumberToWords(String number) {
        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Number cannot be null or empty");
        }
        List<String> wordsList = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        StringBuilder numberClone = new StringBuilder(number);

        int count = 4;

        while (numberClone.length() >= 3) {
            String res = convertThreeDigitNumber(numberClone.substring(numberClone.length() - 3));
            wordsList.add(res);
            wordsList.add(" ");
            if (numberClone.length() > 3) {
                wordsList.add(NumbersEnum.getByNumber(count).toString());
                wordsList.add(" ");
            }
            numberClone.delete(numberClone.length() - 3, numberClone.length());
            count += 3;
        }

        if (numberClone.length() > 0) {
            wordsList.add(convertTwoDigitNumber(String.valueOf(numberClone)));
        }
        for (int i = wordsList.size() - 1; i >= 0; i--) {
            result.append(wordsList.get(i));
        }

        return result;
    }

    private static String convertTwoDigitNumber(String number) {
        if (Integer.parseInt(number) <= 20) {
            return numberWords.get(Integer.parseInt(number));
        }

        int tensPlace = (Integer.parseInt(number) / 10) * 10;
        int onesPlace = (Integer.parseInt(number) % 10);

        if (onesPlace == 0) {
            return numberWords.get(tensPlace);
        }

        return numberWords.get(tensPlace) + " " + numberWords.get(onesPlace);
    }

    private static String convertThreeDigitNumber(String number) {
        int hundredsPlace = (Integer.parseInt(number) / 100);
        int remainingTwoDigits = (Integer.parseInt(number) % 100);

        String result = numberWords.get(hundredsPlace) + " hundred";

        if (remainingTwoDigits != 0) {
            result += " " + convertTwoDigitNumber(String.valueOf(remainingTwoDigits));
        }

        return result;
    }

}