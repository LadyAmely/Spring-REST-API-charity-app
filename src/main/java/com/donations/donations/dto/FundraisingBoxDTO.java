package com.donations.donations.dto;

import lombok.Getter;

public class FundraisingBoxDTO {

    @Getter
    private Long id;
    private final boolean isAssigned;
    private final boolean isEmpty;

    public FundraisingBoxDTO(Long id, boolean isAssigned, boolean isEmpty) {
        this.id = id;
        this.isAssigned = isAssigned;
        this.isEmpty = isEmpty;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
