package ca.cmpt213.a5_backend.controllers;

import ca.cmpt213.a5_backend.models.Card;
import ca.cmpt213.a5_backend.models.Logic;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Controller class for the Tokimon API
 *  * This class is responsible for handling HTTP requests and responses
 *  * It is the main interface between the front-end and the back-end
 *
 *  getAllCards - returns all cards in the database
 *  getCardsNextID - returns the next ID number for a new card
 *  getCard - returns a card by ID
 *  addCard - adds a new card to the database
 *  updateCard - updates a card in the database
 *  deleteCard - deletes a card from the database
 *
 *  There are tests for these endpoints in the TokimonControllerTest.java file

 */

@RestController
@RequestMapping("/api/tokimon")
public class TokimonController {
    private final Logic logic = new Logic();
    private static int id_count;

public TokimonController() {
    if(!logic.getCards().isEmpty()) {
        id_count = logic.getCards().get(logic.getCards().size() - 1).getID() + 1;
    }
    else{
        id_count = 1;
    }
}

    public void refresh(){
    logic.emptyJsonAndArray();
    }

    @GetMapping("/all")
    public List<Card> getAllCards(@RequestParam(value ="imageURL", defaultValue = "https://www.google.com") String URL,
                                  @RequestParam(value = "name", defaultValue = "default") String name,
                                  @RequestParam(value = "type", defaultValue = "default") String type,
                                  @RequestParam(value = "rarity", defaultValue = "0") int rarity,
                                  @RequestParam(value = "healthPoints", defaultValue = "0") int healthPoints,
                                  @RequestParam(value = "attack", defaultValue = "0") int attack) {
        return logic.getCards();
    }

    @GetMapping("/CardIDNumber")
    public int getCardsNextID(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return id_count;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable int id) {
        Card card = logic.getCard(id);
        System.out.println(card);
        return card != null ? new ResponseEntity<>(card, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public Card addCard(@RequestBody Card card, HttpServletResponse response) {
        logic.addCard(card);
        id_count++;
        response.setStatus(HttpServletResponse.SC_CREATED);
        return card;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> updateCard(@RequestBody Card card, @PathVariable int id) {
        logic.updateCard(card, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable int id, HttpServletResponse response) {
        if (logic.deleteCard(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}