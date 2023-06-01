package convertor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RomanianNumberToWords {
    private static final Map<Integer, String> numberWords = new HashMap<>();

    static {
        numberWords.put(0, "zero");
        numberWords.put(1, "unu");
        numberWords.put(2, "doua");
        numberWords.put(3, "trei");
        numberWords.put(4, "patru");
        numberWords.put(5, "cinci");
        numberWords.put(6, "șase");
        numberWords.put(7, "șapte");
        numberWords.put(8, "opt");
        numberWords.put(9, "nouă");
        numberWords.put(10, "zece");
        numberWords.put(11, "unsprezece");
        numberWords.put(12, "doisprezece");
        numberWords.put(13, "treisprezece");
        numberWords.put(14, "paisprezece");
        numberWords.put(15, "cincisprezece");
        numberWords.put(16, "șaisprezece");
        numberWords.put(17, "șaptesprezece");
        numberWords.put(18, "optsprezece");
        numberWords.put(19, "nouăsprezece");
        numberWords.put(20, "douăzeci");
        numberWords.put(30, "treizeci");
        numberWords.put(40, "patruzeci");
        numberWords.put(50, "cincizeci");
        numberWords.put(60, "șaizeci");
        numberWords.put(70, "șaptezeci");
        numberWords.put(80, "optzeci");
        numberWords.put(90, "nouăzeci");
    }

    public static StringBuilder convertNumberToWords(String number) {
        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Number cannot be null or empty");
        }
        List<String> wordsList = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        StringBuilder numberClone = new StringBuilder(number);

        int count = 1;

        int countZeros = getCountZeros(number);

        if (numberClone.length() == 4) {
            return checkThousandNumber(result, numberClone);
        } else if (numberClone.length() > 4 && number.length() - countZeros == 1) {
            checkNumbersWithZeros(wordsList, numberClone);
        } else {
            while (numberClone.length() >= 3) {
                String threeDigitToConvert = numberClone.substring(numberClone.length() - 3);
                count = convertThreeDigitNumbersAndSetOrderIfConditionsMeet(wordsList, numberClone, count, threeDigitToConvert);
                numberClone.delete(numberClone.length() - 3, numberClone.length());
                eliminateOrdinIfZerosBefore(wordsList, numberClone, threeDigitToConvert);

                count = checkLastThreeDigitAndSetOrder(wordsList, numberClone, count, threeDigitToConvert);

                checkLengthAndRemoveUnnecessaryWords(wordsList, numberClone);
            }
            convertOneOrTwoRemainingDigitAndSetConvertToWords(number, wordsList, numberClone, count);
        }
        return reverseResultList(wordsList, result);
    }

    private static void convertOneOrTwoRemainingDigitAndSetConvertToWords(String number,
                                                                          List<String> wordsList,
                                                                          StringBuilder numberClone,
                                                                          int count) {
        if (numberClone.length() == 1) {
            if (wordsList.get(wordsList.size() - 1).equals(" ")) {
                wordsList.add(" ");
                wordsList.add(wordsList.size() - 1, NumereEnum.getByNumber(number.length()).toString());
            }
            wordsList.add(" ");
            wordsList.add(convertTwoDigitNumber(String.valueOf(numberClone)));
        } else if (numberClone.length() == 2) {
            if (number.length() > 4 && Integer.parseInt(number.substring(2, 5)) == 0) {
                wordsList.remove(wordsList.size() - 1);
                wordsList.remove(wordsList.size() - 1);
                count += 3;
                wordsList.add(NumereEnum.getByNumber(count).toString());
                wordsList.add(" ");
            }
            wordsList.add(convertTwoDigitNumber(String.valueOf(numberClone)));
        }
    }

    private static void checkLengthAndRemoveUnnecessaryWords(List<String> wordsList,
                                                             StringBuilder numberClone) {
        if (numberClone.length() == 1) {
            wordsList.remove(wordsList.size() - 1);
//        } else if (numberClone.length() == 0) {
//            wordsList.remove(wordsList.size() - 1);
//            wordsList.remove(wordsList.size() - 1);
//        }
        }
    }


    private static int convertThreeDigitNumbersAndSetOrderIfConditionsMeet(List<String> wordsList,
                                                                           StringBuilder numberClone,
                                                                           int count,
                                                                           String threeDigitToConvert) {
        if (numberClone.length() >= 3 && Integer.parseInt(threeDigitToConvert) != 0) {
            wordsList.add(convertThreeDigitNumber(threeDigitToConvert));
            wordsList.add(" ");
            if (numberClone.length() >= 3) {
                count += 3;
            }
        }
        if (numberClone.length() >= 3 && threeDigitToConvert.charAt(0) != '0') {
            wordsList.add(NumereEnum.getByNumber(count).toString());
            wordsList.add(" ");
        }
        return count;
    }

    private static int checkLastThreeDigitAndSetOrder(List<String> wordsList,
                                                      StringBuilder numberClone,
                                                      int count,
                                                      String threeDigitToConvert) {
        if (numberClone.length() == 3 && Integer.parseInt(threeDigitToConvert) % 100 == 0) {
            count += 3;
            wordsList.add(NumereEnum.getByNumber(count).toString());
            wordsList.add(" ");
            wordsList.add(convertThreeDigitNumber(String.valueOf(numberClone)));
            numberClone.delete(numberClone.length() - 3, numberClone.length());
        }
        return count;
    }

    private static void eliminateOrdinIfZerosBefore(List<String> wordsList,
                                                    StringBuilder numberClone,
                                                    String threeDigitToConvert) {
        if (Integer.parseInt(threeDigitToConvert) == 0 && numberClone.length() <= 3) {
            if (wordsList.get(wordsList.size() - 2).equals("mii")) {
                wordsList.remove(wordsList.size() - 1);
                wordsList.remove(wordsList.size() - 1);
            }
        }
    }

    private static void checkNumbersWithZeros(List<String> wordsList,
                                              StringBuilder numberClone) {
        int counter = 1;
        while (numberClone.length() >= 3) {
            String word = numberClone.substring(numberClone.length() - 3, numberClone.length());
            if (Integer.parseInt(word) == 0) {
                counter += 3;
            } else {
                wordsList.add(NumereEnum.getByNumber(counter).toString());
                wordsList.add(" ");
                wordsList.add(convertThreeDigitNumber(word));
            }
            numberClone.delete(numberClone.length() - 3, numberClone.length());
        }
        if (numberClone.length() > 0) {
            wordsList.add(NumereEnum.getByNumber(counter).toString());
            wordsList.add(" ");
            wordsList.add(convertTwoDigitNumber(String.valueOf(numberClone)));
        }
    }

    private static int getCountZeros(String number) {
        int countZeros = 0;
        if (number.length() > 4) {
            for (int i = 1; i <= number.length() - 1; i++) {
                if (number.charAt(i) == '0') {
                    countZeros += 1;
                }
            }
        }
        return countZeros;
    }

    private static StringBuilder reverseResultList(List<String> wordsList,
                                                   StringBuilder result) {
        for (int i = wordsList.size() - 1; i >= 0; i--) {
            result.append(wordsList.get(i));
        }
        return result;
    }


    private static StringBuilder checkThousandNumber(StringBuilder result,
                                                     StringBuilder numberClone) {
        boolean firstDigit = true;
        String remainingDigits = "";
        if (numberClone.indexOf("1") == 0 || numberClone.indexOf("2") == 0) {
            firstDigit = numberClone.indexOf("1") == 0;
            numberClone.deleteCharAt(0);
            remainingDigits = convertThreeDigitNumber(String.valueOf(numberClone));
        }
        return firstDigit ? result.append("o mie").append(remainingDigits) :
                result.append("doua mii ").append(remainingDigits);
    }

    private static String convertTwoDigitNumber(String number) {
        if (Integer.parseInt(number) <= 20) {
            return numberWords.get(Integer.parseInt(number));
        }

        int tensPlace = (Integer.parseInt(number) / 10) * 10;
        int onesPlace = (Integer.parseInt(number) % 10);

        if (onesPlace == 0) {
            return numberWords.get(tensPlace);
        } else {
            return numberWords.get(tensPlace) + " si " + numberWords.get(onesPlace);
        }
    }

    private static String convertThreeDigitNumber(String number) {
        int hundredsPlace = (Integer.parseInt(number) / 100);
        int remainingTwoDigits = (Integer.parseInt(number) % 100);

        String result = "";

        if (hundredsPlace != 0) {
            result = numberWords.get(hundredsPlace) + " sute";
        }
        if (hundredsPlace == 1) {
            result = "o suta";
        }

        if (remainingTwoDigits != 0) {
            result += " " + convertTwoDigitNumber(String.valueOf(remainingTwoDigits));
        }
        return result.trim();
    }

}