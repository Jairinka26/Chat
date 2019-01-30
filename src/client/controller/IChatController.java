/*
 * Copyright (c) 1997-2013 InfoReach, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * InfoReach ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with InfoReach.
 *
 * CopyrightVersion 2.0
 */
package client.controller;

import java.io.IOException;

/**
 * TODO: add description
 *
 * @author Irina.Paschenko
 */
public interface IChatController {
    void sendMessage(String outMessage) throws IOException;
    void exit() throws IOException;
}
