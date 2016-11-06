package com.evansitzes.game.exceptions;

import javax.xml.ws.WebServiceException;

/**
 * Created by evan on 11/6/16.
 */

public class ItemDoesntExistException extends WebServiceException {
    public ItemDoesntExistException(final String message) {
        super(message);
    }
}
