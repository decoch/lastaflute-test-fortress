/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.remote.maihama.showbase.mypage;

import org.dbflute.remoteapi.FlutyRemoteApiRule;
import org.lastaflute.di.helper.misc.ParameterizedRef;
import org.lastaflute.web.servlet.request.RequestManager;

import org.docksidestage.remote.maihama.showbase.RemoteAbstractMaihamaShowbaseBhv;
import org.docksidestage.remote.maihama.showbase.mypage.RemoteMypageReturn;

/**
 * RemoteMaihamaShowbaseBsMypageBhv.
 * @author FreeGen
 */
public abstract class RemoteMaihamaShowbaseBsMypageBhv extends RemoteAbstractMaihamaShowbaseBhv {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /***
     * @param requestManager requestManager. (NotNull)
     */
    public RemoteMaihamaShowbaseBsMypageBhv(RequestManager requestManager) {
        super(requestManager);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    /**
     * request Mypage.<br>
     * <pre>
     * url: /mypage/
     * httpMethod: GET
     * </pre>
     * @return return object. (NotNull)
     */
    public RemoteMypageReturn requestMypage() {
        return doRequestGet(new ParameterizedRef<RemoteMypageReturn>() {
        }.getType(), "/mypage/", noMoreUrl(), noQuery(), rule -> ruleOfMypage(rule));
    }

    /**
     * Rule of Mypage.<br>
     * <pre>
     * url: /mypage/
     * httpMethod: GET
     * </pre>
     * @param rule rule. (NotNull)
     */
    protected void ruleOfMypage(FlutyRemoteApiRule rule) {
    }
}
