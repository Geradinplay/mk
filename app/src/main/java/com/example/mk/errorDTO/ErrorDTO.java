package com.example.mk.errorDTO;

import java.util.ArrayList;
import java.util.List;

public class ErrorDTO {
    private List<ErrorItemDTO> errorItems;

    public ErrorDTO(List<ErrorItemDTO> errorItems) {
        this.errorItems = errorItems;
    }
    public ErrorDTO() {
        this.errorItems = new ArrayList<ErrorItemDTO>();
    }
    public List<ErrorItemDTO> getErrorItems() {
        return errorItems;
    }

    public void setErrorItems(List<ErrorItemDTO> errorItems) {
        this.errorItems = errorItems;
    }
}
