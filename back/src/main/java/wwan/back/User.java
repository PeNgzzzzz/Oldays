package wwan.back;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class User {

    @PostMapping("/login")
    public ResponseEntity<Map<String, Boolean>> userLogin(@RequestBody Map<String, String> info) {
        String username = info.get("username");
        String password = info.get("password");
        DBconnection db = new DBconnection();
        if (db.checkUserName(username).isEmpty()) {
            db.addUser(username, password);
            db.exit();
            return ResponseEntity.ok(Map.of("success", true));
        }
        if (Objects.equals(db.checkUserName(username), password)) {
            db.exit();
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            db.exit();
            return ResponseEntity.ok(Map.of("success", false));
        }
    }

    @PostMapping("/getUserData")
    public ResponseEntity<Map<String, List<Map<String, String>>>> userData(@RequestBody Map<String, String> info) {
        String username = info.get("username");
        DBconnection db = new DBconnection();
        Map<String, List<Map<String, String>>> result = db.userData(username);
        db.exit();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/recommandateFive")
    public ResponseEntity<List<Map<String, String>>> recommend(@RequestBody Map<String, String> info) {
        String username = info.get("username");
        DBconnection db = new DBconnection();
        List<Map<String, String>> result = db.recommend(username);
        db.exit();
        return ResponseEntity.ok(result);
    }
}
