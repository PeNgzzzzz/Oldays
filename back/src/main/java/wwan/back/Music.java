package wwan.back;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.Objects;

@RestController
public class Music {
    @PostMapping("/search")
    public ResponseEntity<Map<String, Boolean>> searchMusic(@RequestBody Map<String, String> info) {
        String name = info.get("name");
        DBconnection db = new DBconnection();
        if (db.searchMusic(name).isEmpty()) {
            db.exit();
            return ResponseEntity.ok(Map.of("success", false));
        } else {
            db.exit();
            return ResponseEntity.ok(Map.of("success", true));
        }
    }

    @PostMapping("/topFive")
    public ResponseEntity<Map<String, Boolean>> topFive(@RequestBody Map<String, String> info) {
        DBconnection db = new DBconnection();
        if (db.topFive().isEmpty()) {
            db.exit();
            return ResponseEntity.ok(Map.of("success", false));
        } else {
            db.exit();
            return ResponseEntity.ok(Map.of("success", true));
        }
    }
}