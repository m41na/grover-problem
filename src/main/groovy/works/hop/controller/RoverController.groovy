package works.hop.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import works.hop.mars.state.Rover
import works.hop.repository.RoverRepository

@RestController
@RequestMapping("/rover")
class RoverController {

    private Map<UUID, Rover> rovers = [:]
    @Autowired
    RoverRepository repo

    @GetMapping("/{roverId}")
    ResponseEntity<String> position(@PathVariable UUID roverId) {
        Rover rover = repo.fetch(roverId)
        if (rover == null) {
            var plateau = "5 5"
            var position = "0 0 N"
            UUID uuid = repo.create(roverId, plateau, position)
            rovers.put(uuid, new Rover(plateau, position))
            return new ResponseEntity(position, HttpStatus.OK)
        }
        rovers.put(roverId, rover)
        return new ResponseEntity(rover.position(), HttpStatus.OK)
    }

    @GetMapping()
    ResponseEntity<List<UUID>> roversIds() {
        return ResponseEntity.ok(repo.existing())
    }

    @PostMapping()
    ResponseEntity<UUID> create(@RequestBody Map<String, String> params) {
        UUID uuid = repo.create(null, params.plateau, params.position)
        rovers.put(uuid, new Rover(params.plateau, params.position))
        return ResponseEntity.accepted().body(uuid)
    }

    @PutMapping(value = "/{roverId}", consumes = "text/plain")
    ResponseEntity<String> operate(@PathVariable UUID roverId, @RequestBody String instructions) {
        Rover rover = rovers.get(roverId)
        if (rover != null) {
            rover.operate(instructions)
            String pos = rover.position();
            repo.update(roverId, pos)
            return ResponseEntity.ok(pos)
        } else {
            rover = repo.fetch(roverId)
            if (rover == null) {
                var plateau = "5 5"
                var position = "0 0 N"
                UUID uuid = repo.create(roverId, plateau, position)
                rover = new Rover(plateau, position)
                rovers.put(uuid, rover)
            } else {
                rovers.put(roverId, rover)
            }
            rover.operate(instructions)
            return ResponseEntity.ok(rover.position())
        }
    }

    @DeleteMapping("/{roverId}")
    ResponseEntity<String> remove(@PathVariable UUID roverId) {
        repo.remove(roverId)
        Rover rover = rovers.remove(roverId)
        return ResponseEntity.accepted().body(rover.position())
    }
}
