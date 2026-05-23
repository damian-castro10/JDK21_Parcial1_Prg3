package ar.edu.unlar.programacion3.parcial1.exception;

public class BateriaInsuficienteException extends RuntimeException {
    public BateriaInsuficienteException(String message) {
        super(message);
    }
}