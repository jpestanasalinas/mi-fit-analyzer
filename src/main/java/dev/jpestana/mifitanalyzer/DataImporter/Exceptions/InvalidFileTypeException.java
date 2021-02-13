package dev.jpestana.mifitanalyzer.DataImporter.Exceptions;

public class InvalidFileTypeException extends RuntimeException {

    public InvalidFileTypeException (String errorMessage) {
        super(errorMessage);
    }
}
