package seng202.group8;

        import org.junit.Test;
        import seng202.group8.Model.Airline;

        import static org.junit.Assert.assertTrue;

public class ModelTest {
    @Test
    public void calculateScoreReturnsCorrectScore() {
        Airline test = new Airline();
        assertTrue(test.testNumber == 1);
    }

    @Test
    public void calculateScorePerCapitaReturnsCorrectScore() {
    }

    @Test
    public void addGoldUpdatesGoldCount() {
    }
}
