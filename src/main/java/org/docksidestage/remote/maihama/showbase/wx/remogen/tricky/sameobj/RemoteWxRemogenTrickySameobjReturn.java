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
package org.docksidestage.remote.maihama.showbase.wx.remogen.tricky.sameobj;

import org.lastaflute.core.util.Lato;
import org.lastaflute.web.validation.Required;

/**
 * The bean class as return for remote API of POST /wx/remogen/tricky/sameobj.
 * @author FreeGen
 */
public class RemoteWxRemogenTrickySameobjReturn {

    /** The property of resortName. */
    @Required
    public String resortName;

    /** The property of firstPark. (NullAllowed) */
    @javax.validation.Valid
    public ResortParkPart firstPark;

    /**
     * The part class of ResortParkPart.
     * @author FreeGen
     */
    public static class ResortParkPart {

        /** The property of parkName. */
        @Required
        public String parkName;

        /** The property of stageCount. */
        @Required
        public Integer stageCount;
    }

    /** The property of extendedAreas. (NullAllowed) */
    @javax.validation.Valid
    public org.eclipse.collections.api.list.ImmutableList<ExtendedAreaPart> extendedAreas;

    /**
     * The part class of ExtendedAreaPart.
     * @author FreeGen
     */
    public static class ExtendedAreaPart {

        /** The property of direction. */
        @Required
        public String direction;

        /** The property of nextPark. (NullAllowed) */
        @javax.validation.Valid
        public ResortParkPart nextPark;
    }

    /** The property of departmentStore. (NullAllowed) */
    @javax.validation.Valid
    public DepartmentStorePart departmentStore;

    /**
     * The part class of DepartmentStorePart.
     * @author FreeGen
     */
    public static class DepartmentStorePart {

        /** The property of storeName. */
        @Required
        public String storeName;

        /** The property of shopCount. (NullAllowed) */
        public Integer shopCount;

        /** The property of officialShop. (NullAllowed) */
        @javax.validation.Valid
        public OfficialShopPart officialShop;

        /**
         * The part class of OfficialShopPart.
         * @author FreeGen
         */
        public static class OfficialShopPart {

            /** The property of shopName. (NullAllowed) */
            public String shopName;
        }
    }

    @Override
    public String toString() {
        return Lato.string(this);
    }
}
