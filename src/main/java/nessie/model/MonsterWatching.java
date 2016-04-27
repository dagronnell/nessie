package nessie.model;

import nessie.service.RandomGeneratorService;

public class MonsterWatching {
    private static final int NUMBER_OF_LAKES = 3;

    private int lakeChoice;
    private int lakeWithNessie;
    private boolean lakeChanged = false;

    private final RandomGeneratorService random;

    public MonsterWatching(int lakeChoice, RandomGeneratorService rndService) {
        this.random = rndService;
        this.lakeWithNessie = rndService.randomInt(NUMBER_OF_LAKES);
        chooseLake(lakeChoice);
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
        int emptyLake = random.randomInt(NUMBER_OF_LAKES);
        do {
            emptyLake = (emptyLake + 1) % NUMBER_OF_LAKES;
        } while (emptyLake == lakeChoice || emptyLake == lakeWithNessie);

        return emptyLake;
    }

    public boolean isNessieFound() {
        return lakeChoice == lakeWithNessie;
    }


    private void validateStateBeforeChangingLakes() {
        if (lakeChanged) {
            throw new IllegalStateException("Can only change lakes once.");
        }
    }
}
