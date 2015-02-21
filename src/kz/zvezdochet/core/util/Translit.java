package kz.zvezdochet.core.util;

import java.util.HashMap;
import java.util.Map;

public class Translit {

	/**
	 * Конвертация кириллицы в транслит
	 * @param text строка кириллицы
	 * @param lowercase признак преобразования символов в нижний регистр
	 * @return строка транслит
	 */
	public static String convert(String text, boolean lowercase) {
		if (lowercase)
			text = text.toLowerCase();
		text = text.replaceAll("[^ a-zA-ZА-Яа-яёЁ]+", " ")
			.replace(" ", "-");

        char charBuffer[] = text.toCharArray();
        StringBuffer sb = new StringBuffer(text.length());
        for (int i = 0; i < charBuffer.length; i++) {
            String replace = charTable[charBuffer[i]];
            if (null == replace)
                sb.append(charBuffer[i]);
            else
                sb.append(replace);
        }
        return sb.toString();
    }

    private static final String[] charTable = new String[65536];
    static {
        charTable['А'] = "A";
        charTable['Б'] = "B";
        charTable['В'] = "V";
        charTable['Г'] = "G";
        charTable['Д'] = "D";
        charTable['Е'] = "E";
        charTable['Ё'] = "E";
        charTable['Ж'] = "ZH";
        charTable['З'] = "Z";
        charTable['И'] = "I";
        charTable['Й'] = "I";
        charTable['К'] = "K";
        charTable['Л'] = "L";
        charTable['М'] = "M";
        charTable['Н'] = "N";
        charTable['О'] = "O";
        charTable['П'] = "P";
        charTable['Р'] = "R";
        charTable['С'] = "S";
        charTable['Т'] = "T";
        charTable['У'] = "U";
        charTable['Ф'] = "F";
        charTable['Х'] = "H";
        charTable['Ц'] = "C";
        charTable['Ч'] = "CH";
        charTable['Ш'] = "SH";
        charTable['Щ'] = "SH";
        charTable['Ъ'] = "'";
        charTable['Ы'] = "Y";
        charTable['Ь'] = "'";
        charTable['Э'] = "E";
        charTable['Ю'] = "U";
        charTable['Я'] = "YA";

        for (int i = 0; i < charTable.length; i++) {
            char idx = (char)i;
            char lower = new String(new char[] {idx}).toLowerCase().charAt(0);
            if (charTable[i] != null)
                charTable[lower] = charTable[i].toLowerCase();
        }
    }

    private static final Map<String, String> letters = new HashMap<String, String>();
    static {
        letters.put("А", "A");
        letters.put("Б", "B");
        letters.put("В", "V");
        letters.put("Г", "G");
        letters.put("Д", "D");
        letters.put("Е", "E");
        letters.put("Ё", "E");
        letters.put("Ж", "ZH");
        letters.put("З", "Z");
        letters.put("И", "I");
        letters.put("Й", "I");
        letters.put("К", "K");
        letters.put("Л", "L");
        letters.put("М", "M");
        letters.put("Н", "N");
        letters.put("О", "O");
        letters.put("П", "P");
        letters.put("Р", "R");
        letters.put("С", "S");
        letters.put("Т", "T");
        letters.put("У", "U");
        letters.put("Ф", "F");
        letters.put("Х", "H");
        letters.put("Ц", "C");
        letters.put("Ч", "CH");
        letters.put("Ш", "SH");
        letters.put("Щ", "SH");
        letters.put("Ъ", "");
        letters.put("Ы", "Y");
        letters.put("Ъ", "");
        letters.put("Э", "E");
        letters.put("Ю", "U");
        letters.put("Я", "YA");
        letters.put("а", "a");
        letters.put("б", "b");
        letters.put("в", "v");
        letters.put("г", "g");
        letters.put("д", "d");
        letters.put("е", "e");
        letters.put("ё", "e");
        letters.put("ж", "zh");
        letters.put("з", "z");
        letters.put("и", "i");
        letters.put("й", "i");
        letters.put("к", "k");
        letters.put("л", "l");
        letters.put("м", "m");
        letters.put("н", "n");
        letters.put("о", "o");
        letters.put("п", "p");
        letters.put("р", "r");
        letters.put("с", "s");
        letters.put("т", "t");
        letters.put("у", "u");
        letters.put("ф", "f");
        letters.put("х", "h");
        letters.put("ц", "c");
        letters.put("ч", "ch");
        letters.put("ш", "sh");
        letters.put("щ", "sh");
        letters.put("ъ", "");
        letters.put("ы", "y");
        letters.put("ъ", "");
        letters.put("э", "e");
        letters.put("ю", "u");
        letters.put("я", "ya");
    }
}
