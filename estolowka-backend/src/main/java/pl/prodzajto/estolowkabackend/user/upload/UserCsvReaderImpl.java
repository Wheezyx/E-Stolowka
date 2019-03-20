package pl.prodzajto.estolowkabackend.user.upload;

import com.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserRepository;
import pl.prodzajto.estolowkabackend.user.UserRole;
import pl.prodzajto.estolowkabackend.user.UserRoleRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserCsvReaderImpl implements UserCsvReader {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
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
        return true;
    }

    private UserEntity createUser(String[] record) {
        HashSet<UserRole> defaultRole = new HashSet<>();
        defaultRole.add(userRoleRepository.findByRole("USER"));

        return UserEntity.builder()
                .email(record[0])
                .name(record[1])
                .surname(record[2])
                .index(Integer.parseInt(record[3]))
                .password(passwordEncoder.encode(randomPassword()))
                .roles(defaultRole)
                .enabled(true)
                .build();
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
