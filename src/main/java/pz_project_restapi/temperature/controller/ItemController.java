package pz_project_restapi.temperature.controller;

import java.time.*;
import java.util.*;
import javax.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pz_project_restapi.temperature.exception.*;
import pz_project_restapi.temperature.model.*;
import pz_project_restapi.temperature.repository.*;
import pz_project_restapi.temperature.service.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/pz/measurements")
public class ItemController {


    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;


    /**
     * Get all items from databases
     * @return all items in databases
     */
    @GetMapping
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    /**
     * Create new item and safe into database
     *
     * @param item
     * @return save item into database
     */

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    /**
     * Find one item by parameter id in database
     * @param id
     * @return one item find by id
     */
    @GetMapping("{id}")
    public ResponseEntity<Item> getItemById(@PathVariable long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id:" + id));
        return ResponseEntity.ok(item);
    }

    /**
     * Update item by parameter id and save updated item into database
     * @param id
     * @param itemDetails
     * @return updated one item in database by parameter id
     */
    @PutMapping("{id}")
    public ResponseEntity<Item> upddateItem(@PathVariable long id, @RequestBody Item itemDetails) {
        Item updateItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id:" + id));
        updateItem.setTemperature(itemDetails.getTemperature());
        updateItem.setDateAndTime(itemDetails.getDateAndTime());
        itemRepository.save(updateItem);
        return ResponseEntity.ok(updateItem);
    }

    /**
     * Delete one item in database by parameter id
     * @param id
     * @return http status with no content
     */

    //delete item by id REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id:" + id));
        itemRepository.delete(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //GET longest period in days between temperature A and B
    @PostMapping("/period/{temperature}")
    public List<Item> longestPeriodTe (@RequestBody TemperatureForm temperatureForm) {
        return itemService.getPeriodByTemperature(temperatureForm);

    }




}
