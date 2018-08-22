package support.test;

import com.woowahan.moduchan.domain.user.NormalUser;
import com.woowahan.moduchan.repository.NormalUserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public abstract class AcceptanceTest {
    private static final String DEFAULT_LOGIN_USER = "moduchan_admin";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private NormalUserRepository normalUserRepository;

    public TestRestTemplate template() {
        return template;
    }

    public TestRestTemplate basicAuthTemplate(String email, String password) {
        return template.withBasicAuth(email, password);
    }

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate("a", "a");
    }

    protected NormalUser findByEmail(String email) {
        return normalUserRepository.findByEmail(email).get();
    }
}
