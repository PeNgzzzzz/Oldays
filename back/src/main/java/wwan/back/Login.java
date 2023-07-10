package wwan.back;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class Login {

    @PostMapping("/login")
    public ResponseEntity<Map<String, Boolean>> userLogin(@RequestBody Map<String, String> info) {
        String username = info.get("username");
        String password = info.get("password");
        DBconnection db = new DBconnection();
        System.out.println(username);
        System.out.println(1111111);
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
}
