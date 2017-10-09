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
package org.docksidestage.remote.maihama.showbase.product;

import java.util.function.Consumer;

import org.dbflute.remoteapi.FlutyRemoteApiRule;
import org.lastaflute.web.servlet.request.RequestManager;

import org.docksidestage.remote.maihama.showbase.AbstractRemoteMaihamaShowbaseBhv;
import org.docksidestage.remote.maihama.showbase.product.detail.RemoteProductDetailReturn;
import org.docksidestage.remote.maihama.showbase.product.list.search.RemoteProductListSearchParam;
import org.docksidestage.remote.maihama.showbase.product.list.search.RemoteProductListSearchReturn;

/**
 * The base class as generation gap for remote API of product.
 * @author FreeGen
 */
public abstract class BsRemoteMaihamaShowbaseProductBhv extends AbstractRemoteMaihamaShowbaseBhv {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /***
     * @param requestManager The manager of request, LastaFlute component. (NotNull)
     */
    public BsRemoteMaihamaShowbaseProductBhv(RequestManager requestManager) {
        super(requestManager);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    /**
     * Request remote call to /product/detail/{productId}. (auto-generated method)<br>
     * <pre>
     * url: /product/detail/{productId}
     * httpMethod: POST
     * </pre>
     * @param productId The value of path variable for productId. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public RemoteProductDetailReturn requestDetail(Integer productId) {
        return requestDetail(productId, rule -> {});
    }

    /**
     * Request remote call to /product/detail/{productId}. (auto-generated method)<br>
     * <pre>
     * url: /product/detail/{productId}
     * httpMethod: POST
     * </pre>
     * @param productId The value of path variable for productId. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected RemoteProductDetailReturn requestDetail(Integer productId, Consumer<FlutyRemoteApiRule> ruleLambda) {
        return doRequestPost(RemoteProductDetailReturn.class, "/product/detail/{productId}", moreUrl(productId), null, rule -> {
            ruleOfDetailProductId(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /product/detail/{productId}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfDetailProductId(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /product/list/search/{pageNumber}. (auto-generated method)<br>
     * <pre>
     * url: /product/list/search/{pageNumber}
     * httpMethod: POST
     * </pre>
     * @param pageNumber The value of path variable for pageNumber. (NotNull)
     * @param paramLambda The callback for RemoteProductListSearchParam. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public RemoteProductListSearchReturn requestListSearch(Integer pageNumber, Consumer<RemoteProductListSearchParam> paramLambda) {
        return requestListSearch(pageNumber, paramLambda, rule -> {});
    }

    /**
     * Request remote call to /product/list/search/{pageNumber}. (auto-generated method)<br>
     * <pre>
     * url: /product/list/search/{pageNumber}
     * httpMethod: POST
     * </pre>
     * @param pageNumber The value of path variable for pageNumber. (NotNull)
     * @param paramLambda The callback for RemoteProductListSearchParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected RemoteProductListSearchReturn requestListSearch(Integer pageNumber, Consumer<RemoteProductListSearchParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemoteProductListSearchParam param = new RemoteProductListSearchParam();
        paramLambda.accept(param);
        return doRequestPost(RemoteProductListSearchReturn.class, "/product/list/search/{pageNumber}", moreUrl(pageNumber), param, rule -> {
            ruleOfListSearchPageNumber(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /product/list/search/{pageNumber}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfListSearchPageNumber(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /product/list/status. (auto-generated method)<br>
     * <pre>
     * url: /product/list/status
     * httpMethod: POST
     * </pre>
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public java.util.List<Object> requestListStatus() {
        return requestListStatus(rule -> {});
    }

    /**
     * Request remote call to /product/list/status. (auto-generated method)<br>
     * <pre>
     * url: /product/list/status
     * httpMethod: POST
     * </pre>
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected java.util.List<Object> requestListStatus(Consumer<FlutyRemoteApiRule> ruleLambda) {
        return doRequestPost(new org.lastaflute.di.helper.misc.ParameterizedRef<java.util.List<Object>>() {
        }.getType(), "/product/list/status", noMoreUrl(), null, rule -> {
            ruleOfListStatus(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /product/list/status.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfListStatus(FlutyRemoteApiRule rule) {
    }
}