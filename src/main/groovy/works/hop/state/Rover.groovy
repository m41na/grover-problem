package works.hop.state

import works.hop.Dir

import java.util.regex.Pattern

class Rover {

    int mx
    int my
    State state;

    Rover(String plateau, String input) {
        //starting position
        if (input == null || input.trim().isEmpty()) {
            throw new RuntimeException("Expected valid starting positional in the form 'x y dir'")
        }
        if (!Pattern.compile("\\d \\d [NEWS]").matcher(input).matches()) {
            throw new RuntimeException("Expected valid starting positional in the form 'x y dir'")
        }
        var pos = input.split(" ")
        this.state = State.newState(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), Dir.valueOf(pos[2]))

        //plateau size
        if (plateau == null || plateau.trim().isEmpty()) {
            throw new RuntimeException("Expected valid plateau size in the form 'max-x max-y'")
        }
        if (!Pattern.compile("\\d \\d").matcher(plateau).matches()) {
            throw new RuntimeException("Expected valid plateau size in the form 'max-x max-y'")
        }
        var size = plateau.split(" ")
        this.mx = Integer.parseInt(size[0])
        this.my = Integer.parseInt(size[1])
    }

    String position() {
        return this.state.position()
    }

    void newState(State newState){
        this.state = newState
    }

    void operate(String input) {
        if (input == null) {
            printf("ignoring %s instruction%n", input)
        } else {
            var instructions = input.trim().split("")
            for (instruction in instructions) {
                switch (instruction) {
                    case "L":
                        newState(state.left())
                        break
                    case "R":
                        newState(state.right())
                        break
                    case "M":
                        newState(state.move(mx, my))
                        break
                    default:
                        printf("ignoring %s instruction%n", instruction)
                        break
                }
            }
        }
    }
}
