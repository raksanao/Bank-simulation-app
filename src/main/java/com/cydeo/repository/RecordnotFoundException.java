package com.cydeo.repository;

public class RecordnotFoundException  extends RuntimeException{
    public RecordnotFoundException(String message) {
        super(message);
    }
}
