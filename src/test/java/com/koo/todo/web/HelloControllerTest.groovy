package com.koo.todo.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Ignore
import spock.lang.Specification
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment=RANDOM_PORT)
class HelloControllerTest extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    def "헬로 페이지 테스트"() {
        given:
        def body = this.restTemplate.getForObject("/hello", String.class)

        when:
        def result = body.contains("HelloWorld")

        then:
        result == true
        noExceptionThrown()

    }

}
