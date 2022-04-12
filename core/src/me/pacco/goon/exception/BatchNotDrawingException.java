package me.pacco.goon.exception;

public class BatchNotDrawingException extends RuntimeException{
    public BatchNotDrawingException() {
        super("Le batch devrait etre ouvert ( .open() )");
    }
}
