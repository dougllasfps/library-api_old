package me.cursodsousa.libraryapi.api.dto;

public interface DTOConverter<E,DTO> {

    E toEntity(DTO dto);

    DTO toDTO(E e);
}
