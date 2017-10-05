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
package org.docksidestage.remote.maihama.showbase.signin;

import java.util.function.Consumer;

import org.dbflute.remoteapi.FlutyRemoteApiRule;
import org.lastaflute.di.helper.misc.ParameterizedRef;
import org.lastaflute.web.servlet.request.RequestManager;

import org.docksidestage.remote.maihama.showbase.RemoteAbstractMaihamaShowbaseBhv;
import org.docksidestage.remote.maihama.showbase.signin.RemoteSigninParam;
import org.docksidestage.remote.maihama.showbase.signin.RemoteSigninReturn;

/**
 * RemoteMaihamaShowbaseBsSigninBhv.
 * @author FreeGen
 */
public abstract class RemoteMaihamaShowbaseBsSigninBhv extends RemoteAbstractMaihamaShowbaseBhv {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /***
     * @param requestManager requestManager. (NotNull)
     */
    public RemoteMaihamaShowbaseBsSigninBhv(RequestManager requestManager) {
        super(requestManager);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    /**
     * Signin.<br>
     * <pre>
     * url: /signin/
     * httpMethod: POST
     * </pre>
     * @param paramLamda The callback for RemoteSigninParam. (NotNull)
     * @return return object. (NotNull)
     */
    public RemoteSigninReturn requestSignin(Consumer<RemoteSigninParam> paramLamda) {
        RemoteSigninParam param = new RemoteSigninParam();
        paramLamda.accept(param);
        return doRequestPost(new ParameterizedRef<RemoteSigninReturn>() {
        }.getType(), "/signin/", noMoreUrl(), param, rule -> ruleOfSignin(rule));
    }

    /**
     * Rule of Signin.<br>
     * <pre>
     * url: /signin/
     * httpMethod: POST
     * </pre>
     * @param rule rule. (NotNull)
     */
    protected void ruleOfSignin(FlutyRemoteApiRule rule) {
    }
}
