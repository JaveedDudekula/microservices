package com.eazybank.cards.service.impl;

import com.eazybank.cards.constants.CardsConstants;
import com.eazybank.cards.dto.CardsDto;
import com.eazybank.cards.entity.Cards;
import com.eazybank.cards.exceptions.CardAlreadyExistsException;
import com.eazybank.cards.exceptions.ResourceNotFoundException;
import com.eazybank.cards.mapper.CardsMapper;
import com.eazybank.cards.repository.CardsRepository;
import com.eazybank.cards.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CardsServiceImpl implements CardsService {

    private final CardsRepository cardsRepository;

    @Autowired
    public CardsServiceImpl(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    @Override
    public void createCard(String mobileNumber) {
        if (cardsRepository.findByMobileNumber(mobileNumber).isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with this mobile number: " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards card = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomCardNumber));
        card.setMobileNumber(mobileNumber);
        card.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        card.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setCardType(CardsConstants.CREDIT_CARD);
        return card;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        boolean isUpdated = false;
        Cards existingCard = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "cardNumber", cardsDto.getCardNumber())
        );
        cardsRepository.save(CardsMapper.mapToCards(cardsDto, existingCard));
        isUpdated = true;
        return isUpdated;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        boolean isDeleted = false;
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.delete(card);
        isDeleted = true;
        return isDeleted;
    }
}
