package works.hop

class RoverSpec {

    def "rover should show it's position when prompted" (){
        given: "an instance of mars rover"
        Rover rover = new Rover("1 2 N")

        when: "the rover position is interrogated"
        var pos = rover.position()

        then: "value for x, y and dir should match the input values"
        rover.x = 1
        rover.y = 2
        rover.dir = "N"
        pos == "1 2 N"
    }

    def "rover movement should update its position" (){
        given: "an instance of mars rover"
        Rover rover = new Rover("1 1 N")

        when: "a rover either moves or turns"
        rover.operate(instruction)
        def pos = rover.position()

        then:
        pos == position

        where:
        instruction || position
            "L"     ||   "1 1 W"
    }
}
