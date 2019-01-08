package pl.prodzajto.estolowkabackend.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@AllArgsConstructor
public class UserSaverController {
    private final UserCsvReader userCsvReader;

    @RequestMapping(method = RequestMethod.POST, path = "/uploadUsers")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile multipartFile) {
        if (userCsvReader.readCSVFile(multipartFile)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
