package ohtuesimerkki;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class StatisticsTest {
    Reader readerStub = new Reader() {

        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp(){
        // luodaan Statistics-olio joka käyttää "stubia"
        stats = new Statistics(readerStub);
    }

    @Test
    public void topScorersPalauttaaOikeankokoisenListan() {
        assertEquals(4, stats.topScorers(3).size());
    }

    @Test
    public void searchPalauttaaNimenJosLoytyy() {
        assertNotNull(stats.search("urr"));
    }

    @Test
    public void searchPalauttaaNullJosEiLoydy() {
        assertNull(stats.search("Alibaba"));
    }

    @Test
    public void teamPalauttaaEiTyhjanListanJosLoytyy(){
        assertNotEquals(0, stats.team("DET").size());
    }

    @Test
    public void teamPalauttaaTyhjanListanJosEiLoydy() {
        assertEquals(0, stats.team("Alibaba").size());
    }
}
