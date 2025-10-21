package com.ongtangco.trueshot.serviceimpl;

import com.ongtangco.trueshot.config.AppInfoProperties;
import com.ongtangco.trueshot.dto.AppDetailsRequest;
import com.ongtangco.trueshot.entity.AppDetailsData;
import com.ongtangco.trueshot.model.AppDetails;
import com.ongtangco.trueshot.repository.AppDetailsDataRepository;
import com.ongtangco.trueshot.service.AppDetailsService;
import com.ongtangco.trueshot.util.Transform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AppDetailsServiceImpl implements AppDetailsService {

    private final AppDetailsDataRepository repository;
    private final AppInfoProperties appInfoProperties;
    private final Transform<AppDetailsData, AppDetails> toModel = new Transform<>(AppDetails.class);
    private final Transform<AppDetails, AppDetailsData> toEntity = new Transform<>(AppDetailsData.class);

    @PostConstruct
    public void ensureDefaults() {
        if (repository.count() == 0) {
            AppDetailsData defaults = new AppDetailsData();
            defaults.setStoreName(appInfoProperties.getStoreName());
            defaults.setSupportEmail(appInfoProperties.getSupportEmail());
            defaults.setSupportNumber(appInfoProperties.getSupportNumber());
            defaults.setHeroMessage(appInfoProperties.getHeroMessage());
            repository.save(defaults);
        }
    }

    @Override
    public AppDetails getActiveDetails() {
        return repository.findAll().stream()
                .max(Comparator.comparing(AppDetailsData::getId))
                .map(toModel::transform)
                .orElse(null);
    }

    @Override
    public List<AppDetails> getAll() {
        return repository.findAll().stream().map(toModel::transform).collect(Collectors.toList());
    }

    @Override
    public AppDetails save(AppDetailsRequest request) {
        AppDetails details = new AppDetails();
        details.setId(request.getId());
        details.setStoreName(request.getStoreName());
        details.setHeroMessage(request.getHeroMessage());
        details.setSupportEmail(request.getSupportEmail());
        details.setSupportNumber(request.getSupportNumber());
        details.setFaqContent(request.getFaqContent());
        details.setAboutContent(request.getAboutContent());

        AppDetailsData entity = toEntity.transform(details);
        AppDetailsData saved = repository.save(entity);
        return toModel.transform(saved);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
