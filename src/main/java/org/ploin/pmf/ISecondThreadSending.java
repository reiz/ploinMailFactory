/**
 * Copyright [2009] [PLOIN GmbH -> http://www.ploin-gmbh.de]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.ploin.pmf;

import org.ploin.pmf.entity.SendingResult;
import org.ploin.pmf.entity.ServerConfig;

import java.io.Serializable;

/**
 * This interface is responsible for sending the mail. <br>
 * If in the configuration property <code>singleThread</code> is set to <code>false</code>,<br>
 * the implementation of this interface will be running in a second thread.  
 * <br/>
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 77 $<br>
 * $Date: 2010-03-15 13:13:45 +0100 (Mon, 15 Mar 2010) $<br>
 */
public interface ISecondThreadSending extends Serializable, Runnable{

	SendingResult send() throws MailFactoryException;

	ServerConfig getServerConfig(String client, String selectedNode) throws MailFactoryException;

	ServerConfig getLoadbalancedNode() throws MailFactoryException;

}
