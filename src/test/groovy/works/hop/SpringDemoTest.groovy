package works.hop

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import works.hop.controller.RoverController


@SpringBootTest
class SpringDemoTest {

    @Autowired
    RoverController controller

    @Test
    void contextLoads() {
        Assertions.assertThat (controller).isNotNull()
    }
}
