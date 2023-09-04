package works.hop

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import works.hop.mars.Rover

static void main(String[] args) {

    String plateau = "5 5"
    String position = "1 2 N"
    String instructions = "LMLMLMLMM"

    Rover rover = new Rover(plateau, position)
    rover.operate(instructions)

    printf("rover resting position is (%d, %d, %s)%n", rover.x, rover.y, rover.dir)

    works.hop.mars.state.Rover rover2 = new works.hop.mars.state.Rover(plateau, position)
    rover2.operate(instructions)

    printf("rover2 resting position is (%d, %d, %s)%n", rover2.state.x, rover2.state.y, rover2.state.dir)

    //start spring
    SpringApplication.run(SpringDemo, args)
}

@SpringBootApplication
class SpringDemo {}