import com.globsest.testsoap.entity.UserRole;
import com.globsest.testsoap.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRoleRepository roleRepository;

    public DataInitializer(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            List<String> roles = List.of("ADMIN", "OPERATOR", "ANALYST");
            roles.forEach(name -> {
                UserRole role = new UserRole();
                role.setName(name);
                roleRepository.save(role);
            });
        }
    }
}
