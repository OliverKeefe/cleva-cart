package org.clevacart.dto.mapper;

import jakarta.inject.Inject;
import org.clevacart.domain.model.Allergen;
import org.clevacart.dto.output.AllergenOutputDTO;
import org.clevacart.dto.input.AllergenInputDTO;

public class AllergenMapper {

    public static AllergenOutputDTO toOutputDTO(Allergen allergen) {
        AllergenOutputDTO dto = new AllergenOutputDTO();
        dto.setId(allergen.getId());
        dto.setName(allergen.getName());
        return dto;
    }

    public static AllergenInputDTO toInputDTO(Allergen allergen) {
        AllergenInputDTO dto = new AllergenInputDTO();
            dto.setId(allergen.getId());
            dto.setName(allergen.getName());
            return dto;
    }

    public static Allergen toModel(AllergenInputDTO dto) {
        return new Allergen(dto.getId(), dto.getName());
    }
}
