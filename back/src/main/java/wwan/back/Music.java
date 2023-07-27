package wwan.back;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class Music {
    @PostMapping("/search")
    public ResponseEntity<Map<String, Boolean>> searchMusic(@RequestBody Map<String, String> info) {
        String name = info.get("songName");
        DBconnection db = new DBconnection();
        if (db.searchMusic(name)) {
            db.exit();
            return ResponseEntity.ok(Map.of("found", true));
        } else {
            db.exit();
            return ResponseEntity.ok(Map.of("found", false));
        }
    }

    @GetMapping("/topFive")
    public ResponseEntity<List<Map<String, String>>> topFive() {
        DBconnection db = new DBconnection();
        List<Map<String, String>> result = db.topFive();
        db.exit();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/playListFive")
    public ResponseEntity<List<Map<String, String>>> playListFive(@RequestBody Map<String, String> info) {
        String name = info.get("username");
        DBconnection db = new DBconnection();
        List<Map<String, String>> result = db.userFive(name);
        db.exit();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/songInfo")
    public ResponseEntity<Map<String, String>> songInfo(@RequestBody Map<String, String> info) {
        String name = info.get("songName");
        DBconnection db = new DBconnection();
        Map<String, String> result = db.songInfo(name);
        System.out.println(result);
        db.exit();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/commentSong")
    public void commentSong(@RequestBody Map<String, String> info) {
        String name = info.get("songName");
        String rating = info.get("rating");
        String comment = info.get("comment");
        String userName = info.get("userName");
        System.out.println(name + rating + comment + userName);
        DBconnection db = new DBconnection();
        db.rateMusics(userName, name, rating, comment);
        db.exit();
    }

    @PostMapping("/getComments")
    public ResponseEntity<List<Map<String, String>>> getComments(@RequestBody Map<String, String> info) {
        String name = info.get("songName");
        DBconnection db = new DBconnection();
        List<Map<String, String>> result = db.getComments(name);
        System.out.println(name);
        System.out.println(result);
        db.exit();
        return ResponseEntity.ok(result);
    }
}