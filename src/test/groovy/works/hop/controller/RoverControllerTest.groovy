package works.hop.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import works.hop.mars.state.Rover
import works.hop.repository.RoverRepository

import static org.hamcrest.Matchers.any
import static org.mockito.Mockito.any
import static org.mockito.Mockito.when
import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RoverController.class)
class RoverControllerTest  {

    @Autowired
    MockMvc mvc;

    @MockBean
    RoverRepository repository

    @Test
    void when_given_valid_rover_id_position_endpoints_should_return_a_valid_position() {
        var id = UUID.fromString("5c74cd31-05d9-452a-9649-6f725c482bf7")
        when(repository.fetch(any(UUID.class))).thenReturn(new Rover("10 10", "1 1 N"))

        mvc.perform(
            get("/rover/{roverId}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("1 1 N")))

    }

    @Test
    void fetching_existing_rovers_should_return_list_of_rover_ids() {
        when(repository.existing()).thenReturn([
                UUID.fromString("15918c60-ad11-4d32-a90c-862e5ebcc3cc"),
                UUID.fromString("5c74cd31-05d9-452a-9649-6f725c482bf7"),
                UUID.fromString("5e9a1c00-117b-433f-8eb1-6a852661880c")
        ])

        mvc.perform(
                get("/rover"))
                .andDo {print(it)}
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("5e9a1c00-117b-433f-8eb1-6a852661880c")))
    }

    @Test
    void creating_rover_should_add_a_new_record_in_the_database() {
        var plateau = "5 5"
        var position = "1 1 N"
        var id = UUID.fromString("5c74cd31-05d9-452a-9649-6f725c482bf7")

        when(repository.create(null, plateau, position)).thenReturn(id)

        mvc.perform(post("/rover")
                .contentType("application/json")
                .content("{\n" +
                        "    \"plateau\": \"5 5\",\n" +
                        "    \"position\": \"1 2 N\"\n" +
                        "}"))
            .andDo {print(it)}
            .andExpect(status().isAccepted())
            .andExpect(content().string(containsString("")))
    }

    @Test
    void operating_a_rover_will_update_its_positional_value() {
        var plateau = "5 5"
        var position = "1 1 N"
        var id = UUID.fromString("5c74cd31-05d9-452a-9649-6f725c482bf7")

        when(repository.create(null, plateau, position)).thenReturn(id)

        mvc.perform(post("/rover")
                .contentType("application/json")
                .content("{\n" +
                        "    \"plateau\": \"5 5\",\n" +
                        "    \"position\": \"1 2 N\"\n" +
                        "}"))
                .andDo {print(it)}
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("")))
    }

    @Test
    void removing_a_rover_should_return_the_last_positional_value() {
        var id = UUID.fromString("5c74cd31-05d9-452a-9649-6f725c482bf7")

        mvc.perform(delete("/rover/{roverId}", id))
                .andDo {print(it)}
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("")))
    }
}
