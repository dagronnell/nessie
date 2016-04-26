package nessie.integration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import nessie.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class NessieEndpointTest {

    private String PATH = "findNessie";
    private int ENOUGH_CALLS = 500;
    private float MARGIN_FACTOR = 0.2f;

    private static final Random rnd = new Random(System.nanoTime());

    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUpRestAssured() {
        RestAssured.port = port;
    }

    @Test
    public void changeLakesGivesTwoThirdsChanceToSucceed() throws Exception {
        int foundCnt = 0;
        for (int cnt = 0; cnt < ENOUGH_CALLS; cnt++) {
            int choice = rnd.nextInt(3);
            boolean found = callFindNessie(choice, true);
            if (found) {
                foundCnt ++;
            }
        }

        int expected = 2 * ENOUGH_CALLS / 3;

        int lowerBound = (int) (expected * (1 - MARGIN_FACTOR));
        int upperBound = (int) (expected * (1 + MARGIN_FACTOR));
        assertThat(foundCnt, greaterThan(lowerBound));
        assertThat(foundCnt, lessThan(upperBound));
    }

    private boolean callFindNessie(int lakeChoice, boolean changeLake) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .param("lake", lakeChoice)
                .param("change", changeLake)
                .get(PATH)
                .then()

                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getBoolean("found");
    }
}
