package pl.prodzajto.estolowkabackend.user;

import com.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
@AllArgsConstructor
public class UserCsvReaderImpl implements UserCsvReader {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean readCSVFile(MultipartFile multipartFile) {
        try (Reader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
             CSVReader reader = new CSVReader(inputStreamReader)) {
            String[] record;
            while ((record = reader.readNext()) != null) {
                userRepository.save(createUser(record));
            }
        } catch (IOException e) {
            return false;
        }
        System.out.println(passwordEncoder.encode("admin"));
        return true;
    }

    private UserEntity createUser(String[] record) {
        UserEntity user = new UserEntity();
        user.setEmail(record[0]);
        user.setName(record[1]);
        user.setSurname(record[2]);
        user.setIndex(Integer.parseInt(record[3]));
        user.setPassword(passwordEncoder.encode(randomPassword()));
        return user;
    }

    private String randomPassword() {
        RandomStringGenerator randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
        return randomStringGenerator.generate(8);
    }
}
