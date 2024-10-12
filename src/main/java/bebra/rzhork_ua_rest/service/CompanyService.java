package bebra.rzhork_ua_rest.service;

import bebra.rzhork_ua_rest.model.entity.Company;
import bebra.rzhork_ua_rest.model.entity.Vacancy;
import bebra.rzhork_ua_rest.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Page<Company> getFilteredCompanies(String search, int page) {
        Pageable pageable = PageRequest.of(page, 4);
        search = (search == null) ? "" : search;
        return companyRepository.findFilteredCompanies(search, pageable);
    }

    public Company getCompany(UUID id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }
        return company;
    }
}
