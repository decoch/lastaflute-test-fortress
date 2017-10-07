// ===================================================================================
//                                                                                Base
//                                                                                ====
/**
 * Return true if target.
 * @param {Object} api - API.
 * @param {string} api.url - URL.
 * @param {string} api.httpMethod - HttpMethod.
 * @param {string[]} api.consumes - Consumes.
 * @param {string[]} api.produces - Produces.
 * @return true if target.
 */
var target = function(api) { return api.produces.indexOf('application/json') != -1 && api.url.indexOf('/swagger/json') != 0; };

/**
 * Return filtered URL.
 * @param {Object} api - API.
 * @param {string} api.url - URL.
 * @param {string} api.httpMethod - HttpMethod.
 * @param {string[]} api.consumes - Consumes.
 * @param {string[]} api.produces - Produces.
 * @return filtered URL.
 */
var url = function(api) { return api.url; };

var subPackage = function(subPackage) { return subPackage.replace(/_/g, ''); };

// ===================================================================================
//                                                                            Behavior
//                                                                            ========
var behaviorClassGeneration = true;
var behaviorMethodGeneration = true;
var behaviorMethodAccessModifier = 'public';
var frameworkBehaviorClass = 'org.lastaflute.remoteapi.LastaRemoteBehavior';
var abstractBehaviorClassName = function(scheme) {
    return 'RemoteAbstract' + scheme + 'Bhv';
};
var bsBehaviorClassName = function(scheme, className) {
    return 'Remote' + scheme + 'Bs' + className + 'Bhv';
};
var exBehaviorClassName = function(scheme, className) {
    return 'Remote' + scheme + className + 'Bhv';
};
var behaviorRequestMethodName = function(scheme, className, methodName, httpMethod, multipleHttpMethod) {
    return 'request' + methodName + (multipleHttpMethod ? httpMethod : '');
};
var behaviorRuleMethodName = function(scheme, className, methodName, httpMethod, multipleHttpMethod) {
    return 'ruleOf' + methodName + (multipleHttpMethod ? httpMethod : '');
};

// ===================================================================================
//                                                                                Bean
//                                                                                ====
var definitionKey = function(definitionKey) { return definitionKey; };
var unDefinitionKey = function(definitionKey) { return definitionKey; };

var requestExtendsClass = null;
var requestImplementsClasses = null;
var requestClassName = function(scheme, className, httpMethod, multipleHttpMethod) {
    return 'Remote' + className + (multipleHttpMethod ? httpMethod : '') + 'Param';
};
var responseExtendsClass = null;
var responseImplementsClasses = null;
var responseClassName = function(scheme, className, httpMethod, multipleHttpMethod) {
    return 'Remote' + className + (multipleHttpMethod ? httpMethod : '') + 'Return';
};
var nestClassName = function(scheme, className) { return className; };
var fieldName = function(scheme, fieldName) {
    return fieldName.replace(/_./g, function(s) { return s.charAt(1).toUpperCase(); });
};

// ===================================================================================
//                                                                              Option
//                                                                              ======
// when using Eclipse Collections. var yourCollections = 'org.eclipse.collections.api.list.ImmutableList';
var yourCollections = null;