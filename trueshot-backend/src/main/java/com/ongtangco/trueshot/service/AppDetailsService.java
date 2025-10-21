package com.ongtangco.trueshot.service;

import com.ongtangco.trueshot.dto.AppDetailsRequest;
import com.ongtangco.trueshot.model.AppDetails;

import java.util.List;

public interface AppDetailsService {
    AppDetails getActiveDetails();
    List<AppDetails> getAll();
    AppDetails save(AppDetailsRequest request);
    void delete(Integer id);
}
