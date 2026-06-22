package com.jamarlesf.plazoletausers.domain.spi;

import com.jamarlesf.plazoletausers.domain.model.User;

public interface ITokenProviderPort {
    String generateToken(User user);
}
