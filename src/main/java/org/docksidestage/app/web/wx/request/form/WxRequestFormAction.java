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
package org.docksidestage.app.web.wx.request.form;

import org.docksidestage.app.web.base.FortressBaseAction;
import org.eclipse.collections.api.list.ImmutableList;
import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.JsonResponse;
import org.lastaflute.web.validation.Required;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jflute
 */
@AllowAnyoneAccess
public class WxRequestFormAction extends FortressBaseAction {

    private static final Logger logger = LoggerFactory.getLogger(WxRequestFormAction.class);

    // http://localhost:8151/fortress/wx/request/form/eccolle?strList=sea&strList=land
    @Execute
    public JsonResponse<MyEcColleForm> eccolle(MyEcColleForm form) {
        validateApi(form, messages -> {});
        logger.debug("strList: {}", form.strList);
        return asJson(form); // for visual check
    }

    public static class MyEcColleForm {

        @Required
        public ImmutableList<String> strList;
    }
}
