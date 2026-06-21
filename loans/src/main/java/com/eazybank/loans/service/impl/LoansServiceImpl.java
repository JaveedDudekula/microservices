package com.eazybank.loans.service.impl;

import com.eazybank.loans.constants.LoansConstants;
import com.eazybank.loans.dto.LoansDto;
import com.eazybank.loans.entity.Loans;
import com.eazybank.loans.exceptions.LoanAlreadyExistsException;
import com.eazybank.loans.exceptions.ResourceNotFoundException;
import com.eazybank.loans.mapper.LoansMapper;
import com.eazybank.loans.repository.LoansRepository;
import com.eazybank.loans.service.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoansServiceImpl implements LoansService {

    private final LoansRepository loansRepository;

    @Autowired
    public LoansServiceImpl(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
    }

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loansOptional = loansRepository.findByMobileNumber(mobileNumber);
        if (loansOptional.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already exists for mobile number: " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdated = false;
        Loans existingLoan = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "loanNumber", loansDto.getLoanNumber())
        );
        loansRepository.save(LoansMapper.mapToLoans(loansDto, existingLoan));
        isUpdated = true;
        return isUpdated;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        boolean isDeleted = false;
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.delete(loans);
        isDeleted = true;
        return isDeleted;
    }
}
