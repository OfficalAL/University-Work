package scrabble;

import java.util.*;

public class ScrabbleScorer {
    private final Map<Character, Integer> tileScores;
    
    public ScrabbleScorer() {
        char[] tiles = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int[] scores = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
        tileScores = new HashMap<>();
        for (int i = 0; i < tiles.length; i++) {
            tileScores.put(tiles[i], scores[i]);
        }
    }
    
    public int scoreForTile(char tile) {
        char upperTile = Character.toUpperCase(tile);
        if (tileScores.containsKey(upperTile)) {
            return tileScores.get(upperTile);
        }
        return 0;
    }
    
    public int scoreForWord(String word) {
        char[] tiles = word.toCharArray();
        int totalScore = 0;
        for (char tile : tiles) {
            totalScore += scoreForTile(tile);
        }
        return totalScore;
    }
    
    public String highestScoringWord(List<String> words) {
        if (words == null || words.isEmpty()) {
            return null;
        }
        String highestWord = words.get(0);
        int highestScore = scoreForWord(highestWord);
        for (String word : words) {
            int currentScore = scoreForWord(word);
            if (currentScore > highestScore) {
                highestScore = currentScore;
                highestWord = word;
            }
        }
        return highestWord;
    }

    public static void main(String[] args) {
        ScrabbleScorer scorer = new ScrabbleScorer();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a word to score: ");
            String word = scanner.nextLine();
            int score = scorer.scoreForWord(word);
            System.out.println("Score for '" + word + "': " + score);
        }
    }
}