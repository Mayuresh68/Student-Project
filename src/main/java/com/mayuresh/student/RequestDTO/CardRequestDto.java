package com.mayuresh.student.RequestDTO;

import com.mayuresh.student.Enums.CardType;


public class CardRequestDto {
    private int studentId;

    private String cardNo;

    private int cvv;

    private CardType cardType;

    public CardRequestDto() {
    }

    public CardRequestDto(int studentId, String cardNo, int cvv, CardType cardType) {
        this.studentId = studentId;
        this.cardNo = cardNo;
        this.cvv = cvv;
        this.cardType = cardType;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
}
