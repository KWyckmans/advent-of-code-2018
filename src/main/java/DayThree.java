import utils.Claim;
import utils.ClaimAnalyser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DayThree {
    public static void main(String[] args) {
        try {
            Path file = Paths.get(DayOne.class.getResource("daythree/input.txt").toURI());

            List<Claim> claims = Files.lines(file).map(Claim::new).collect(Collectors.toList());

            ClaimAnalyser analyser = new ClaimAnalyser(claims);
            int count = analyser.countCommonClaims();
            System.out.println(count);

            List<Claim> nonOverlappingClaims = analyser.getNonOverlappingClaims();
            System.out.println("Amount of non overlapping claims: " + nonOverlappingClaims.size());
            System.out.println(nonOverlappingClaims.get(0));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
