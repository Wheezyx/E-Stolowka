package pl.prodzajto.estolowkabackend.user;

import org.springframework.web.multipart.MultipartFile;

public interface UserCsvReader {
    boolean readCSVFile(MultipartFile multipartFile);
}
