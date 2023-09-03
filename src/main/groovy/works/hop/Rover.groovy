package works.hop

class Rover {

    int x
    int y
    String dir

    Rover(String input){
        if(input == null) {
            throw new RuntimeException("Expected input in the form 'x y dir'")
        }
        var split = input.trim().split(" ")
        if (split.length != 3){
            throw new RuntimeException("Expected input in the form 'x y dir'")
        }
        this.x = Integer.parseInt(split[0])
        this.y = Integer.parseInt(split[1])
        this.dir = split[2]
    }

    String position(){
        return String.format("%d %d %s", this.x, this.y, this.dir)
    }

    void operate(String instruction){
        switch (instruction){
            case "L":
                turnLeft();
                break
            case "R":
                turnRight()
                break
            case "M":
                moveForward()
                break
            default:
                printf("ignoring %s instruction%n", instruction)
                break;
        }
    }

    void turnLeft(){
        switch (this.dir){
            case "N":
                this.dir = "W"
                break
            case "W":
                this.dir = "S"
                break
            case "S":
                this.dir = "E"
                break
            case "E":
                this.dir = "N"
                break
            default:
                printf("ignoring turn instruction")
                break
        }
    }

    void turnRight() {
        switch (this.dir) {
            case "N":
                this.dir = "E"
                break
            case "E":
                this.dir = "S"
                break
            case "S":
                this.dir = "W"
                break
            case "W":
                this.dir = "N"
                break
            default:
                printf("ignoring turn instruction")
                break
        }
    }

    void moveForward() {
        switch (this.dir) {
            case "N":
                this.y++
                break
            case "W":
                this.x--
                break
            case "S":
                this.y--
                break
            case "E":
                this.x++
                break
            default:
                printf("ignoring move instruction")
                break
        }
    }
}
