package myFirstSpringBootApp.validation;

import lombok.RequiredArgsConstructor;
import myFirstSpringBootApp.domain.User;
import myFirstSpringBootApp.repository.UserRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {

    public final UserRepo userRepo;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (validUsername(user)){
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("username").addConstraintViolation();
            return false;
        }
    }

    boolean validUsername (User user){
        if (user.getId() == null) {
            return userRepo.findByUsername(user.getUsername()) == null;
        } else {
            return user.getId() == userRepo.findByUsername(user.getUsername()).getId();
        }
    }
}
