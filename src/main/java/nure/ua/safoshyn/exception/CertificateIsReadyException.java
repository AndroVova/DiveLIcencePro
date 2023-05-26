package nure.ua.safoshyn.exception;

import nure.ua.safoshyn.entity.Certificate;

public class CertificateIsReadyException extends RuntimeException{
    public CertificateIsReadyException(String id, Certificate entity){
        System.out.println("The certificate with id: '" + id +"' is obtained, by user " + entity.getCustomUser().getName() + "!");
    }
}
