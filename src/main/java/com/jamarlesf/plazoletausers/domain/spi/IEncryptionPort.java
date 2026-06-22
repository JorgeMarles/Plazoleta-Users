package com.jamarlesf.plazoletausers.domain.spi;

public interface IEncryptionPort {
    String encryptPassword(String password);
    boolean matches(String password, String encryptedPassword);
}
