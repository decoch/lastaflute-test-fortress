package org.docksidestage.remote.harbor;

import java.util.List;

import org.dbflute.remoteapi.FlutyRemoteApiRule;
import org.dbflute.remoteapi.mapping.FlVacantRemoteMappingPolicy;
import org.docksidestage.remote.harbor.base.RemoteSearchPagingReturn;
import org.docksidestage.remote.harbor.mypage.RemoteMypageProductReturn;
import org.docksidestage.remote.harbor.product.RemoteProductRowReturn;
import org.docksidestage.remote.harbor.product.RemoteProductSearchParam;
import org.docksidestage.remote.harbor.signin.RemoteSigninParam;
import org.lastaflute.core.json.JsonMappingOption;
import org.lastaflute.di.helper.misc.ParameterizedRef;
import org.lastaflute.remoteapi.LastaRemoteBehavior;
import org.lastaflute.remoteapi.receiver.LaJsonReceiver;
import org.lastaflute.remoteapi.sender.body.LaJsonSender;
import org.lastaflute.remoteapi.sender.query.LaQuerySender;
import org.lastaflute.web.api.theme.FaicliUnifiedFailureResult;
import org.lastaflute.web.servlet.request.RequestManager;

/**
 * @author jflute
 */
public class RemoteHarborBhv extends LastaRemoteBehavior {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public RemoteHarborBhv(RequestManager requestManager) {
        super(requestManager);
    }

    // ===================================================================================
    //                                                                          Initialize
    //                                                                          ==========
    @Override
    protected void yourDefaultRule(FlutyRemoteApiRule rule) {
        rule.sendQueryBy(new LaQuerySender(new FlVacantRemoteMappingPolicy()));

        JsonMappingOption jsonMappingOption = new JsonMappingOption();
        rule.sendBodyBy(new LaJsonSender(requestManager, jsonMappingOption));
        rule.receiveBodyBy(new LaJsonReceiver(requestManager, jsonMappingOption));

        rule.handleFailureResponseAs(FaicliUnifiedFailureResult.class);
    }

    @Override
    protected String getUrlBase() {
        return "http://localhost:8090/harbor";
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void requestSignin(RemoteSigninParam param) {
        doRequestPost(void.class, "/lido/auth/signin", noMoreUrl(), param, rule -> {});
    }

    public List<RemoteMypageProductReturn> requestMypage() {
        return doRequestGet(new ParameterizedRef<List<RemoteMypageProductReturn>>() {
        }.getType(), "/lido/mypage", noMoreUrl(), noQuery(), rule -> {});
    }

    public RemoteSearchPagingReturn<RemoteProductRowReturn> requestProductList(RemoteProductSearchParam param) {
        return doRequestPost(new ParameterizedRef<RemoteSearchPagingReturn<RemoteProductRowReturn>>() {
        }.getType(), "/lido/product/list", moreUrl(1), param, rule -> {});
    }
}
