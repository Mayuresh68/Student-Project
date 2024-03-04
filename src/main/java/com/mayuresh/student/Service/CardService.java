package com.mayuresh.student.Service;

import com.mayuresh.student.Models.Card;
import com.mayuresh.student.Models.Student;
import com.mayuresh.student.Repository.CardRepository;
import com.mayuresh.student.Repository.StudentRepository;
import com.mayuresh.student.RequestDTO.CardRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    StudentRepository studentRepository;

    public Card addCard(CardRequestDto cardRequestDto) {
        Student student = studentRepository.findById(cardRequestDto.getStudentId()).get();

        Card card = new Card();
        card.setCardNo(cardRequestDto.getCardNo());
        card.setCardType(cardRequestDto.getCardType());
        card.setCvv(cardRequestDto.getCvv());
        card.setStudent(student);

        cardRepository.save(card);

        return  card;
    }

    public Card getById(int id) {
        return cardRepository.findById(id).get();
    }

    public List<Card> getallCards() {
        return cardRepository.findAll();
    }
}
