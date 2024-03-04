package com.mayuresh.student.Controller;

import com.mayuresh.student.Models.Card;
import com.mayuresh.student.Models.Student;
import com.mayuresh.student.Repository.StudentRepository;
import com.mayuresh.student.RequestDTO.CardRequestDto;
import com.mayuresh.student.Response.GenericListResponse;
import com.mayuresh.student.Response.GenericResponse;
import com.mayuresh.student.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CardService cardService;

    @PostMapping("/add")
    public Card addCard(@RequestBody CardRequestDto cardRequestDto){
        return cardService.addCard(cardRequestDto);
    }

    //Doubt ? -> in bi-directiinal mapping you are doing (1-M) in Parent & (M-1) in Child, so F.K will created in Child Table,one Parent table is as it is.
    //this is the example of how Parent have refrence of all childs in bi-directional mapping
    //here u are accessing all the cards of each Studdent
    @GetMapping("/getChilds")
    public List<Card> getChilds(@RequestParam int id){
        Student student =  studentRepository.findById(id).get();
        List<Card> children = new ArrayList<>();
        if (student != null) {
            children = student.getCards();
        }
        else{
            return null;
        }
        return children;
    }


    @GetMapping("/getById")
    public ResponseEntity<GenericResponse<Card>> getById(@RequestParam int id){
        GenericResponse<Card> response = new GenericResponse<Card>();
        try{
            Card card = cardService.getById(id);
            response.setData(card);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            response.setStatus("failed");
            response.setError("Id not found :"+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<GenericListResponse<List<Card>>> getallCards(){
        GenericListResponse listResponse = new GenericListResponse();
        try{
            listResponse.setDataList(cardService.getallCards());
            listResponse.setStatus("Success");
            return ResponseEntity.ok(listResponse);
        }
        catch (Exception e){
            listResponse.setStatus("Failed");
            listResponse.setError("something wrong" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(listResponse);
        }

    }


}
