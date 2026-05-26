package com.bfhl.api.service;

import com.bfhl.api.dto.BfhlRequest;
import com.bfhl.api.dto.BfhlResponse;

public interface BfhlService {
    BfhlResponse processRequest(BfhlRequest request);
}
