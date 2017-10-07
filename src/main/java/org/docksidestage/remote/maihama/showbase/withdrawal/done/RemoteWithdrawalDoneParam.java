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
package org.docksidestage.remote.maihama.showbase.withdrawal.done;

import org.lastaflute.core.util.Lato;

/**
 * RemoteWithdrawalDoneParam.
 * @author FreeGen
 */
public class RemoteWithdrawalDoneParam {

    public SelectedReason selectedReason;

    public enum SelectedReason {
        //SIT
        //PRD
        //FRT
        //OTH
    }

    public String reasonInput;

    @Override
    public String toString() {
        return Lato.string(this);
    }
}
