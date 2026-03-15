package com.bank.domain.repository;

import com.bank.domain.model.Transfer;

public interface TransferRepository {

    void save(Transfer transfer);

}