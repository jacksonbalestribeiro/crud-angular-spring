package com.jackson.crudspring.exepeption;

public class RecordNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(Long id) {
        super("Resgistro não encontardo com o id: " + id);
    }
}
