package com.flightbookingsystem.services.implementations;

import com.flightbookingsystem.data.entity.CreditCard;
import com.flightbookingsystem.data.enums.CreditCardType;
import com.flightbookingsystem.data.repository.CreditCardRepository;
import com.flightbookingsystem.dto.CreateCreditCardDTO;
import com.flightbookingsystem.dto.CreditCardDTO;
import com.flightbookingsystem.dto.UpdateCreditCardDTO;
import com.flightbookingsystem.exceptions.CreditCardNotFoundException;
import com.flightbookingsystem.services.CreditCardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final ModelMapper modelMapper;


    private CreditCardDTO convertToCreditCardDTO(CreditCard CreditCard) {
        return modelMapper.map(CreditCard, CreditCardDTO.class);
    }

    @Override
    public List<CreditCardDTO> getCreditCards() {
        return creditCardRepository.findAll().stream()
                .map(this::convertToCreditCardDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CreditCardDTO getCreditCard(String cardNumber) {
        return modelMapper.map(creditCardRepository.findById(cardNumber)
                .orElseThrow(() -> new CreditCardNotFoundException("Credit card with number " + cardNumber + " not found")), CreditCardDTO.class);
    }

    @Override
    public CreditCard create(@Valid CreateCreditCardDTO createCreditCardDTO) {
        CreditCard creditCard = modelMapper.map(createCreditCardDTO, CreditCard.class);
        creditCard.setCardType(determineCardType(creditCard.getCardNumber()));
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard updateCreditCard(String cardNumber, @Valid UpdateCreditCardDTO updateCreditCardDTO) {
        CreditCard CreditCard = modelMapper.map(updateCreditCardDTO, CreditCard.class);
        CreditCard.setCardNumber(cardNumber);
        return creditCardRepository.save(CreditCard);
    }

    @Override
    public void deleteCreditCard(String cardNumber) {
        creditCardRepository.deleteById(cardNumber);
    }

    @Override
    public CreditCard findByCardNumber(String cardNumber) {
        return creditCardRepository.findByCardNumber(cardNumber);
    }

    @Override
    public CreditCard findByCardNumberAndCvv(String cardNumber, String cvv) {
        return creditCardRepository.findByCardNumberAndCvv(cardNumber, cvv);
    }

    @Override
    public CreditCard findByCardNumberAndCvvAndExpiryDate(String cardNumber, String cvv, LocalDate expiryDate) {
        return creditCardRepository.findByCardNumberAndCvvAndExpiryDate(cardNumber, cvv, expiryDate);
    }

    @Override
    public CreditCard findByPersonalInfoFirstName(String firstName) {
        return creditCardRepository.findByPersonalInfoFirstName(firstName);
    }

    @Override
    public CreditCard findByPersonalInfoLastName(String lastName) {
        return creditCardRepository.findByPersonalInfoLastName(lastName);
    }

    @Override
    public CreditCard findByPersonalInfoFirstNameAndPersonalInfoLastName(String firstName, String lastName) {
        return creditCardRepository.findByPersonalInfoFirstNameAndPersonalInfoLastName(firstName, lastName);
    }

    private CreditCardType determineCardType(String cardNumber) {
        if (cardNumber.startsWith("3")) {
            return CreditCardType.AMERICAN_EXPRESS;
        } else if (cardNumber.startsWith("4")) {
            return CreditCardType.VISA;
        } else if(cardNumber.startsWith("5")) {
            return CreditCardType.MASTERCARD;
        } else
            return CreditCardType.UNKNOWN;
    }
}
