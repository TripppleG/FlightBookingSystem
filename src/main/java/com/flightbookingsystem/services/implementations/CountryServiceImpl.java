package com.flightbookingsystem.services.implementations;

import com.flightbookingsystem.data.entity.Country;
import com.flightbookingsystem.data.enums.CountryCode;
import com.flightbookingsystem.data.repository.CountryRepository;
import com.flightbookingsystem.dto.CountryDTO;
import com.flightbookingsystem.dto.CreateCountryDTO;
import com.flightbookingsystem.dto.UpdateCountryDTO;
import com.flightbookingsystem.exceptions.CountryNotFoundException;
import com.flightbookingsystem.services.CountryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    private CountryDTO convertToCountryDTO(@Valid Country country) {
        return modelMapper.map(country, CountryDTO.class);
    }

    @Override
    public List<CountryDTO> getCountries() {
        return countryRepository.findAll().stream()
                .map(this::convertToCountryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDTO getCountry(CountryCode code) {
        return modelMapper.map(countryRepository.findById(code)
                .orElseThrow(() -> new CountryNotFoundException("Country with code " + code + " not found")), CountryDTO.class);
    }

    @Override
    public Country create(@Valid CreateCountryDTO createCountryDTO) {
        return countryRepository.save(modelMapper.map(createCountryDTO, Country.class));
    }

    @Override
    public Country updateCountry(@Min(1) Long id, @Valid UpdateCountryDTO updateCountryDTO) {
        Country country = modelMapper.map(updateCountryDTO, Country.class);
        country.setId(id);
        return countryRepository.save(country);
    }

    @Override
    public void deleteCountry(CountryCode code) {
        countryRepository.deleteById(code);
    }
}
