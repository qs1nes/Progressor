package progressor.userservice.common.exception.created_excpetion;

public class NicknameAlreadyExistsException extends RuntimeException {
    public NicknameAlreadyExistsException(String message) {
        super(message);
    }
}
