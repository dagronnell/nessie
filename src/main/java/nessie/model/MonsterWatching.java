package nessie.model;

import java.util.Random;

public class MonsterWatching {
    private static final Random rnd = new Random(System.nanoTime());
    private static final int NUMBER_OF_LAKES = 3;
    private int lakeChoice;
    private int lakeWithNessie;
    private boolean lakeChanged = false;

    public MonsterWatching(int lakeChoice) {
        this.lakeWithNessie = randomInt(NUMBER_OF_LAKES);
        chooseLake(lakeChoice);
    }

    static MonsterWatching createForTest(int lakeChoce, int lakeWithNessie) {
        MonsterWatching monsterWatching = new MonsterWatching(lakeChoce);
        monsterWatching.lakeWithNessie = lakeWithNessie;
        return monsterWatching;
    }

    private void chooseLake(int lakeNum) {
        validateLakeChoice(lakeNum);
        lakeChoice = lakeNum;
    }

    private void validateLakeChoice(int lakeNum) {
        if (lakeNum < 0 || lakeNum >= NUMBER_OF_LAKES) {
            throw new IllegalArgumentException("Must choose lake 0, 1 or 2");
        }
    }

    public void changeLakes() {
        validateStateBeforeChangingLakes();

        int emptyLake = findEmptySelectableLake();
        do {
            lakeChoice = (lakeChoice + 1) % NUMBER_OF_LAKES;
        } while (lakeChoice == emptyLake);
        lakeChanged = true;
    }

    private int findEmptySelectableLake() {
        int emptyLake = randomInt(NUMBER_OF_LAKES);
        do {
            emptyLake = (emptyLake + 1) % NUMBER_OF_LAKES;
        } while (emptyLake == lakeChoice || emptyLake == lakeWithNessie);

        return emptyLake;
    }

    public boolean isNessieFound() {
        return lakeChoice == lakeWithNessie;
    }

    private static int randomInt(int maxExclusive) {
        return rnd.nextInt(maxExclusive);
    }

    private void validateStateBeforeChangingLakes() {
        if (lakeChanged) {
            throw new IllegalStateException("Can only change lakes once.");
        }
    }
}
