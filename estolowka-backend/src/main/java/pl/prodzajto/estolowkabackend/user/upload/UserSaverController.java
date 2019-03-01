package pl.prodzajto.estolowkabackend.user.upload;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@AllArgsConstructor
public class UserSaverController {
    private final UserCsvReader userCsvReader;

    @PostMapping("/uploadUsers")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile multipartFile) {
        if (userCsvReader.readCSVFile(multipartFile)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
