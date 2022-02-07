/*
	Accents remover for text files and dictionaries in French
	Requires Java 8+
	Author: Léo Gillet
*/

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;
import java.lang.*;

public class AccRemover {
	public static List<String> readFileInList(String fileName) {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static void exportArraytoFile(List<String> arrlist, String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName); 
			for (int i = 0; i < arrlist.size(); i++) {
	  			writer.write(arrlist.get(i) + System.lineSeparator());
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Erreur lors de l'export");
		}
	}

	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
    	return Arrays.stream(items).anyMatch(inputStr::contains);
	}

	public static String replaceAccents(String word, String[] accents, String replacementStr) {
		StringBuffer new_word = new StringBuffer();
		char replacement = replacementStr.charAt(0);
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			if (stringContainsItemFromList(Character.toString(letter), accents)) {
				new_word.append(replacement);
			} else {new_word.append(letter);}
		}
		return new_word.toString();
	}

	public static List<String> triMagique(List<String> lines) {
		List<String> sorted_words = new ArrayList<String>();
		StringBuffer new_word;
		String[] forbidden_chars = {" ", "æ", "-", "."};
		String[] e_accents = {"é", "è", "ê", "ë"};
		String[] a_accents = {"à", "â", "ä"};
		String[] i_accents = {"ï", "î"};
		String[] o_accents = {"ô", "ö"};
		String[] u_accents = {"ù", "û", "ü"};
		String[] y_accents = {"ÿ"};
		String[] c_accents = {"ç"};
		System.out.println(lines.size());
		for (int i = 0; i < lines.size(); i++) {
			String word = lines.get(i);
			if (word.length() > 4 && word.length() < 8) {
				if (!stringContainsItemFromList(word, forbidden_chars)) {
					if (stringContainsItemFromList(word, e_accents)) {word = replaceAccents(word, e_accents, "e");}
					if (stringContainsItemFromList(word, a_accents)) {word = replaceAccents(word, a_accents, "a");}
					if (stringContainsItemFromList(word, i_accents)) {word = replaceAccents(word, i_accents, "i");}
					if (stringContainsItemFromList(word, o_accents)) {word = replaceAccents(word, o_accents, "o");}
					if (stringContainsItemFromList(word, u_accents)) {word = replaceAccents(word, u_accents, "u");}
					if (stringContainsItemFromList(word, y_accents)) {word = replaceAccents(word, y_accents, "y");}
					if (stringContainsItemFromList(word, c_accents)) {word = replaceAccents(word, c_accents, "c");}
					sorted_words.add(word);
				}
			}
		}
		return sorted_words;
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			String fileName = args[0];
			List<String> lines = readFileInList(fileName);
			List<String> new_lines = triMagique(lines);
			System.out.println(new_lines);
		} 
		else if (args.length == 2) {
			String import_file = args[0];
			String export_file = args[1];
			List<String> lines = readFileInList(import_file);
			List<String> new_lines = triMagique(lines);
			exportArraytoFile(new_lines, export_file);
		}
		else if (args.length == 0) {
			System.out.println("Accents remover for text files and dictionaries in French for a Wordle game");
			System.out.println("Requires Java 8+");
			System.out.println("Author: Léo Gillet\n");
			System.out.println("How to use:");
			System.out.println("java AccRemover <import file name> (<exported file name>)\n");
			System.out.println("This program applies the filter to each line.");
			System.out.println("Words under 5 and over 7 characters will be removed.");
			System.out.println("Words containing these following characters will be removed : ");
			System.out.println(". \" \" - æ");
			System.out.println("Accented characters will be substituted with the corresponding unaccented character");
		}
	}
}