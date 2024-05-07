package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.ContactNotValidException;
import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.service.Attraction.IAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/attraction")
public class AttractionController {

    @Autowired
    IAttractionService attractionService;

    @GetMapping
    public ResponseEntity<Object> getAttractionList(){
        return ResponseEntity.status(HttpStatus.OK).body(attractionService.getList());
    }

    @PostMapping
    public ResponseEntity<Object> saveUserAttraction(@RequestBody SaveAttractionUserDTO attractionDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(attractionService.save(attractionDTO));
        } catch(NotFoundException | CpfNotValidException | ContactNotValidException | DateOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }


    @PutMapping
    public ResponseEntity<Object> attractionUpdate(@RequestBody UpdateAttractionDTO attraction){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(attractionService.update(attraction));
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(CpfNotValidException | ContactNotValidException | DateOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> attractionDelete(@PathVariable Integer id){
        try {
            attractionService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Atração apagada com sucesso!"));
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }
}
