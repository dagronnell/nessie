package nessie.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomGeneratorService {

    private final Random rnd = new Random(System.nanoTime());

    public int randomInt(int maxExclusive) {
        return rnd.nextInt(maxExclusive);
    }

}
