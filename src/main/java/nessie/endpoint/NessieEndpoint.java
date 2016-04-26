package nessie.endpoint;

import nessie.model.MonsterWatching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NessieEndpoint {

    @RequestMapping("/findNessie")
    public ResponseEntity findNessie(
            @RequestParam("lake") Integer lakeChoice,
            @RequestParam("change") Boolean changeLakes
    ) {
        try {
            MonsterWatching monsterWatching = new MonsterWatching(lakeChoice);
            if (changeLakes) {
                monsterWatching.changeLakes();
            }
            return ResponseEntity.ok(new NessieResponse(monsterWatching.isNessieFound()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e));
        }
    }
}
