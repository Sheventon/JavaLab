package ru.itis.javalab;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ModeValidator implements IParameterValidator {

    @Override
    public void validate(String paramName, String mode) throws ParameterException {
        if (!mode.equals("one-thread") && !mode.equals("multi-thread")) {
            throw new ParameterException("Unknown" + mode + ". Enter one-thread or multi-thread");
        }
    }
}
