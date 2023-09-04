package works.hop

import works.hop.mars.Dir
import works.hop.mars.state.Rover
import spock.lang.Specification

class RoverSpec extends Specification{

    def "rover should be initialized with valid inputs" (){
        when: "creating rover with null input"
        def rover = new Rover(plateau, position)

        then:
        def err = thrown(RuntimeException)
        err.message == error

        where:
        plateau |   position    | error
        null    |   null        | "Expected valid starting positional in the form 'x y dir'"
        "5 5"   |   null        | "Expected valid starting positional in the form 'x y dir'"
        "5 5"   |   "1 2 X"     | "Expected valid starting positional in the form 'x y dir'"
        "5 5"   |   "1 2 3"     | "Expected valid starting positional in the form 'x y dir'"
        "5 5"   |   "X 2 3"     | "Expected valid starting positional in the form 'x y dir'"
        "5 5"   |   "1 X 3"     | "Expected valid starting positional in the form 'x y dir'"
        null    |   "1 2 N"     | "Expected valid plateau size in the form 'max-x max-y'"
        "5 X"   |   "1 2 N"     | "Expected valid plateau size in the form 'max-x max-y'"
        "X 5"   |   "1 2 N"     | "Expected valid plateau size in the form 'max-x max-y'"
    }

    def "rover should show it's position when prompted" (){
        given: "an instance of mars rover"
        Rover rover = new Rover("5 5", "1 2 N")

        when: "the rover position is interrogated"
        var pos = rover.position()

        then: "value for x, y and dir should match the input values"
        rover.state.x == 1
        rover.state.y == 2
        rover.state.dir == Dir.N
        pos == "1 2 N"
    }

    def "rover movement should update its position" (){
        given: "an instance of mars rover"
        Rover rover = new Rover("5 5", initial)

        when: "a rover either moves or turns"
        rover.operate(instruction)
        def pos = rover.position()

        then:
        pos == position

        where:
        initial | instruction || position
        "1 1 N" |   "X"       ||  "1 1 N"
        "1 1 N" |   "L"       ||  "1 1 W"
        "1 1 N" |   "R"       ||  "1 1 E"
        "1 1 E" |   "L"       ||  "1 1 N"
        "1 1 E" |   "R"       ||  "1 1 S"
        "1 1 S" |   "L"       ||  "1 1 E"
        "1 1 S" |   "R"       ||  "1 1 W"
        "1 1 W" |   "L"       ||  "1 1 S"
        "1 1 W" |   "R"       ||  "1 1 N"

        "1 1 N" |   "M"       ||  "1 2 N"
        "1 1 E" |   "M"       ||  "2 1 E"
        "1 1 S" |   "M"       ||  "1 0 S"
        "1 1 W" |   "M"       ||  "0 1 W"

        "1 2 N" | "LMLMLMLMM" ||  "1 3 N"
        "3 3 E" | "MMRMMRMRRM"||  "5 1 E"
        "1 2 N" | "ABCDEFGHI" ||  "1 2 N"
        "1 2 N" | "         " ||  "1 2 N"

        "5 5 N" |   "M"       ||  "5 5 N"
        "5 5 E" |   "M"       ||  "5 5 E"
        "0 0 W" |   "M"       ||  "0 0 W"
        "0 0 S" |   "M"       ||  "0 0 S"
    }
}
