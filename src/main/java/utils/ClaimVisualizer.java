package utils;

import java.util.List;

public class ClaimVisualizer {
    private static final int SIZE = 1000;
    private char[][] chars = new char[SIZE][SIZE];

    public ClaimVisualizer(List<Claim> claims) {
        for (Claim claim : claims) {
            int x = claim.getX();
            int y = claim.getY();
            int width = claim.getWidth();
            int height = claim.getHeight();

            for (int i = y; i < y + height; i++) {
                for (int j = x; j < x + width; j++) {
                    if (chars[i][j] == '#' || chars[i][j] == 'X') {
                        chars[i][j] = 'X';
                    } else {
                        chars[i][j] = '#';
                    }
                }
            }
        }
    }

    public void visualise() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(chars[i][j]);
            }
            System.out.println();
        }
    }
}
