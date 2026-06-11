package exceptions;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String mess) {
        super(mess);
    }
}
