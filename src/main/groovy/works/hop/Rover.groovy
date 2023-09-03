package works.hop

import java.util.regex.Pattern

enum Dir {
    N, E, S, W
}

class Rover {

    int x
    int y
    Dir dir
    int mx
    int my

    Rover(String plateau, String input) {
        //starting position
        if (input == null || input.trim().isEmpty()) {
            throw new RuntimeException("Expected valid starting positional in the form 'x y dir'")
        }
        if (!Pattern.compile("\\d \\d [NEWS]").matcher(input).matches()) {
            throw new RuntimeException("Expected valid starting positional in the form 'x y dir'")
        }
        var pos = input.split(" ")
        this.x = Integer.parseInt(pos[0])
        this.y = Integer.parseInt(pos[1])
        this.dir = Dir.valueOf(pos[2])

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
        return String.format("%d %d %s", this.x, this.y, this.dir)
    }

    void operate(String input) {
        if (input == null) {
            printf("ignoring %s instruction%n", input)
        } else {
            var instructions = input.trim().split("")
            for (instruction in instructions) {
                switch (instruction) {
                    case "L":
                        turnLeft()
                        break
                    case "R":
                        turnRight()
                        break
                    case "M":
                        moveForward()
                        break
                    default:
                        printf("ignoring %s instruction%n", instruction)
                        break
                }
            }
        }
    }

    void turnLeft() {
        switch (this.dir) {
            case Dir.N:
                this.dir = Dir.W
                break
            case Dir.W:
                this.dir = Dir.S
                break
            case Dir.S:
                this.dir = Dir.E
                break
            case Dir.E:
                this.dir = Dir.N
                break
            default:
                printf("ignoring instruction to turn left")
                break
        }
    }

    void turnRight() {
        switch (this.dir) {
            case Dir.N:
                this.dir = Dir.E
                break
            case Dir.E:
                this.dir = Dir.S
                break
            case Dir.S:
                this.dir = Dir.W
                break
            case Dir.W:
                this.dir = Dir.N
                break
            default:
                printf("ignoring instruction to turn right")
                break
        }
    }

    void moveForward() {
        switch (this.dir) {
            case Dir.N:
                if(this.y + 1 <= this.my) {
                    this.y++
                }
                break
            case Dir.W:
                if(this.x - 1 >= 0) {
                    this.x--
                }
                break
            case Dir.S:
                if(this.y - 1 >= 0) {
                    this.y--
                }
                break
            case Dir.E:
                if(this.x + 1 <= this.mx) {
                    this.x++
                }
                break
            default:
                printf("ignoring instruction to move")
                break
        }
    }
}
