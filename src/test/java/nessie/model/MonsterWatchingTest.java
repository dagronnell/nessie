package nessie.model;

import nessie.service.RandomGeneratorService;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MonsterWatchingTest {

    private MonsterWatching monsterWatching;

    @Test(expected = IllegalArgumentException.class)
    public void cantChooseBelow0()  {
        monsterWatching = new MonsterWatching(-1, new RandomGeneratorService());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantChooseAbove2() {
        monsterWatching = new MonsterWatching(3, new RandomGeneratorService());
    }

    @Test
    public void chooseLake0AndDontChangeAndMissWhenNessieIsInLake1() throws Exception {
        RandomGeneratorService randomMock = mockRandomService(1);
        monsterWatching = new MonsterWatching(0, randomMock);

        assertThatNessieIsNotFound();
    }

    @Test
    public void chooseLake0AndDontChangeAndFindWhenNessieIsInLake0() throws Exception {
        RandomGeneratorService randomMock = mockRandomService(0);
        monsterWatching = new MonsterWatching(0, randomMock);
        assertThatNessieIsFound();
    }

    @Test
    public void chooseLake0AndChangeAndFindWhenNessieIsInLake1() throws Exception {
        RandomGeneratorService randomMock = mockRandomService(1);
        monsterWatching = new MonsterWatching(0, randomMock);
        monsterWatching.changeLakes();
        assertThatNessieIsFound();
    }

    @Test
    public void chooseLake0AndChangeAndFindWhenNessieIsInLake2() throws Exception {
        RandomGeneratorService randomMock = mockRandomService(2);
        monsterWatching = new MonsterWatching(0, randomMock);
        monsterWatching.changeLakes();
        assertThatNessieIsFound();
    }

    @Test
    public void chooseLake0AndChangeAndMissWhenNessieIsInLake0() throws Exception {
        RandomGeneratorService randomMock = mockRandomService(0);
        monsterWatching = new MonsterWatching(0, randomMock);
        monsterWatching.changeLakes();
        assertThatNessieIsNotFound();
    }

    @Test(expected = IllegalStateException.class)
    public void changeLakesTwiceThrows() throws Exception {
        monsterWatching = new MonsterWatching(0, new RandomGeneratorService());
        monsterWatching.changeLakes();
        monsterWatching.changeLakes();
    }

    private RandomGeneratorService mockRandomService(int mockedRandomValue) {
        return new RandomGeneratorService() {
            @Override
            public int randomInt(int maxExclusive) {
                return mockedRandomValue;
            }
        };
    }

    private void assertThatNessieIsFound() {
        assertThat(monsterWatching.isNessieFound(), is(true));
    }

    private void assertThatNessieIsNotFound() {
        assertThat(monsterWatching.isNessieFound(), is(false));
    }
}

