package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClaimAnalyser {
    private static final int SIZE = 1000;
    private String[][] chars = new String[SIZE][SIZE];
    private Map<String, Claim> claimStore = new HashMap<>();

    public ClaimAnalyser(List<Claim> claims) {
        for (Claim claim : claims) {
            int x = claim.getX();
            int y = claim.getY();
            int width = claim.getWidth();
            int height = claim.getHeight();

            claimStore.put(claim.getId(), claim);

            for (int i = y; i < y + height; i++) {
                for (int j = x; j < x + width; j++) {
                    if (chars[i][j] == null) {
                        chars[i][j] = claim.getId();
                    } else if (chars[i][j].equals("X")) {
                        claim.overlaps();
                    } else {
                        claimStore.get(chars[i][j]).overlaps();
                        claim.overlaps();
                        chars[i][j] = "X";
                    }
                }
            }
        }
    }

    public List<Claim> getNonOverlappingClaims() {
        return claimStore.values().stream().filter(Claim::doesNotOverlap).collect(Collectors.toList());
    }

    public int countCommonClaims() {
        int count = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (chars[i][j] != null && chars[i][j].equals("X")) {
                    count++;
                }
            }
        }

        return count;
    }
}
