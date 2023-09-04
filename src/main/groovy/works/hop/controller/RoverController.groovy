package works.hop.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import works.hop.mars.state.Rover

@RestController
@RequestMapping("/rover")
class RoverController {

    private Map<UUID, Rover> rovers = [:]

    @GetMapping("/{roverId}")
    ResponseEntity<String> position(@PathVariable UUID roverId) {
        return new ResponseEntity(rovers.get(roverId).position(), HttpStatus.OK)
    }

    @PostMapping()
    ResponseEntity<UUID> create(@RequestBody Map<String, String> params) {
        UUID uuid = UUID.randomUUID()
        rovers.put(uuid, new Rover(params.plateau, params.position))
        return ResponseEntity.accepted().body(uuid)
    }

    @PutMapping(value = "/{roverId}", consumes = "text/plain")
    ResponseEntity<String> operate(@PathVariable UUID roverId, @RequestBody String instructions) {
        Rover rover = rovers.get(roverId)
        rover.operate(instructions)
        return ResponseEntity.ok(rover.position())
    }

    @DeleteMapping("/{roverId}")
    ResponseEntity<String> remove(@PathVariable UUID roverId) {
        Rover rover = rovers.remove(roverId)
        return ResponseEntity.accepted().body(rover.position())
    }
}
