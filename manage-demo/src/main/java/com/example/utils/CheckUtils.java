package com.example.utils;

import com.example.common.RespSystemCode;
import com.example.common.BizServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class CheckUtils {

    public static void paramValidate(BindingResult bindingResult) {
        StringBuffer fieldErrors = new StringBuffer();
        if (null != bindingResult && bindingResult.hasErrors()) {
            List<FieldError> fieldErrorsList = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrorsList.size(); i++) {
                FieldError fieldError = fieldErrorsList.get(i);
                fieldErrors.append(fieldError.getDefaultMessage());
                if (i != (fieldErrorsList.size() - 1)) {
                    fieldErrors.append(",");
                }
            }
            throw new BizServiceException(RespSystemCode.PARAM_ERROR.getCode(), fieldErrors.toString());
        }
    }

}
