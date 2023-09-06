package works.hop.spock

import spock.lang.Specification
import spock.lang.Unroll

class OddEventCampImplSpec extends Specification{

    def setupSpec(){
        println "set up before class"
    }

    def setup(){
        println "setup before test"
    }

    def "throw exception for number less than or equal to zero" (){
        given:
        var checker = new NegativeChecker()
        var oddEvenCamp = new OddEventCampImpl(validator: checker)

        when:
        oddEvenCamp.check(-1)

        then:
        thrown(NegativeNumberException)
    }

    @Unroll
    def "for the given value #number return will be #answer data driven"(){
        given:
        OddEvenCamp oddEvenCamp = new OddEventCampImpl(validator: Mock(Validator))

        when:
        int result = oddEvenCamp.check(number)

        then:
        result == answer

        where:
        number  | answer
            1   |   0
            2   |   1
    }

    def "for checking the behavior of interaction with the validator"(){
        given:
        Validator<?> validator = Mock(Validator)
        OddEvenCamp oddEvenCamp = new OddEventCampImpl(validator: validator)

        when:
        oddEvenCamp.check(0)

        then:
        1 * validator.validate(0) >> { throw new NegativeNumberException("fail")}
        thrown(NegativeNumberException)
    }

    def cleanup(){
        println "setup after test"
    }

    def cleanupSpec(){
        println "set up after class"
    }
}
