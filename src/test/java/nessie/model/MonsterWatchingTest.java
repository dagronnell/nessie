package nessie.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MonsterWatchingTest {

    private MonsterWatching monsterWatching;

    @Test(expected = IllegalArgumentException.class)
    public void cantChooseBelow0()  {
        monsterWatching = new MonsterWatching(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantChooseAbove2() {
        monsterWatching = new MonsterWatching(3);
    }

    @Test
    public void chooseLake0AndDontChangeAndLooseWhenNessieIsInLake1() throws Exception {
        monsterWatching = MonsterWatching.createForTest(0, 1);
        assertThatNessieIsNotFound();
    }

    @Test
    public void chooseLake0AndDontChangeAndWinWhenNessieIsInLake0() throws Exception {
        monsterWatching = MonsterWatching.createForTest(0, 0);
        assertThatNessieIsFound();
    }

    @Test
    public void chooseLake0AndChangeAndWinWhenNessieIsInLake1() throws Exception {
        monsterWatching = MonsterWatching.createForTest(0, 1);
        monsterWatching.changeLakes();
        assertThatNessieIsFound();
    }

    @Test
    public void chooseLake0AndChangeAndWinWhenNessieIsInLake2() throws Exception {
        monsterWatching = MonsterWatching.createForTest(0, 2);
        monsterWatching.changeLakes();
        assertThatNessieIsFound();
    }

    @Test
    public void chooseLake0AndChangeAndLooseWhenNessieIsInLake0() throws Exception {
        monsterWatching = MonsterWatching.createForTest(0, 0);
        monsterWatching.changeLakes();
        assertThatNessieIsNotFound();
    }

    @Test(expected = IllegalStateException.class)
    public void changeLakesTwiceThrows() throws Exception {
        monsterWatching = new MonsterWatching(0);
        monsterWatching.changeLakes();
        monsterWatching.changeLakes();
    }

    private void assertThatNessieIsFound() {
        assertThat(monsterWatching.isNessieFound(), is(true));
    }

    private void assertThatNessieIsNotFound() {
        assertThat(monsterWatching.isNessieFound(), is(false));
    }
}

