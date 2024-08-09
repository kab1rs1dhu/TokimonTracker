package ca.cmpt213.a5_backend.tests;

import ca.cmpt213.a5_backend.controllers.TokimonController;
import ca.cmpt213.a5_backend.models.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * This class tests the TokimonController class
 * It tests the following endpoints:
 * - addCard
 * - getAllCards
 * - updateCard
 * - deleteCard
 * - getCardByID
 * - getCardByIDNotFound
 * - deleteNonExistentCard
 * - testNextCardID
 *
 * The tests are done using MockMvc
 * These tests are done to ensure that the endpoints are working as expected
 * Also postman was used to test all necessary endpoints and the results were as expected
 */
@SpringBootTest
@AutoConfigureMockMvc
class TokimonTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private static TokimonController tokimonController = new TokimonController();

    @BeforeAll
    public static void setUp() throws IOException {
        tokimonController.refresh();
        System.out.println("Setting up");

        //tokimonController.emptyJsonAndArray();
    }

    @Test
    void testAddCard() throws Exception {

        // Testing adding a card
        Card card = new Card("https://www.google.com", "add1", "Electric", 1, 1, 1,1);
        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(card))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"add1\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}"));

        // Testing delete
        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 1))
                .andExpect(status().isNoContent()
                );
    }

    @Test
    void testGetAllCards() throws Exception {

        // Testing adding cards

        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(new Card("https://www.google.com", "view1", "Electric", 1, 1, 1, 1)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"view1\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}"));
        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(new Card("https://www.google.com", "view2", "Electric", 1, 1, 1, 2)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":2,\"name\":\"view2\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}")
                );

        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(new Card("https://www.google.com", "view3", "Electric", 1, 1, 1, 3)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":3,\"name\":\"view3\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}")
                );

        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(new Card("https://www.google.com", "view4", "Electric", 1, 1, 1, 4)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":4,\"name\":\"view4\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}")
                );

        // Testing view all cards
        this.mockMvc.perform(
                        get("/api/tokimon/all")
                                .param("imageURL", "https://www.google.com")
                                .param("name", "testPokimon")
                                .param("type", "Electric")
                                .param("rarity", "1")
                                .param("healthPoints", "1")
                                .param("attack", "1"))
                .andExpect(status().isOk()
                );

        // Testing delete for view1, view2, view3, view4 cards
        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 1))
                .andExpect(status().isNoContent()
                );
        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 2))
                .andExpect(status().isNoContent()
                );
        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 3))
                .andExpect(status().isNoContent()
                );
        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 4))
                .andExpect(status().isNoContent()
                );
    }


    @Test
    public void testUpdateCard() throws Exception {
        // Testing adding a card
        Card card = new Card("https://www.google.com", "update1", "Electric", 1, 1, 1, 1);
        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(card))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"update1\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}"));

        // Testing update card
        Card updatedCard = new Card("https://www.google.com", "update2", "Electric", 1, 1, 1, 1);
        this.mockMvc.perform(
                        put("/api/tokimon/edit/{id}", 1).content(new ObjectMapper().writeValueAsString(updatedCard))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Testing get card by ID
        this.mockMvc.perform(
                        get("/api/tokimon/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"update2\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}"));

        // Testing delete
        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 1))
                .andExpect(status().isNoContent()
                );
    }

    @Test
    public void testDeleteCard () throws Exception {

        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(new Card("https://www.google.com", "del1", "Electric", 1, 1, 1, 1)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"del1\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}")
                );

        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(new Card("https://www.google.com", "del2", "Electric", 1, 1, 1, 2)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":2,\"name\":\"del2\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}")
                );

        // testing delete for del1 and del2 cards
        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 1))
                .andExpect(status().isNoContent()
                );

        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 2))
                .andExpect(status().isNoContent()
                );
    }


    // Tests if we want to delete a card that does not exist
    @Test
    void deleteNonExistentCard() throws Exception {
        this.mockMvc.perform(
                        delete("/{id}", 999))
                .andExpect(status().isNotFound());
    }

    // Test if we are getting the correct cardID
    @Test
    void testNextCardID() throws Exception {
        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(new Card("https://www.google.com", "test1", "Electric", 1, 1, 1, 1)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"test1\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}")
                );

        this.mockMvc.perform(
                        get("/api/tokimon/CardIDNumber"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));

        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 1))
                .andExpect(status().isNoContent()
                );
    }

    @Test
    void getCardByID() throws Exception {
        // Testing adding a card
        Card card = new Card("https://www.google.com", "get1", "Electric", 1, 1, 1, 1);
        this.mockMvc.perform(
                        post("/api/tokimon/add").content(new ObjectMapper().writeValueAsString(card))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"get1\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}"));

        // Testing get card by ID
        this.mockMvc.perform(
                        get("/api/tokimon/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"get1\",\"type\":\"Electric\",\"rarity\":1,\"imageURL\":\"https://www.google.com\",\"healthPoints\":1,\"attack\":1}"));

        // Testing delete
        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 1))
                .andExpect(status().isNoContent()
                );
    }

    @Test
    public void getCardByIDNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/api/tokimon/{id}", 999))
                .andExpect(status().isNotFound());
    }


}