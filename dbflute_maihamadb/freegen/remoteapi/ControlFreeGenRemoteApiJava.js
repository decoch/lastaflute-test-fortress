/**
 * process.
 * @param {Request} request - request (NotNull)
 */
function process(request) {
    var optionMap = request.optionMap;
    request.enableOutputDirectory();
    manager.makeDirectory(request.generateDirPath);

    scriptEngine.eval('load("./freegen/remoteapi/RemoteApiRule.js");');
    if (optionMap.ruleJsPath && optionMap.ruleJsPath != '') {
        // load application rule settings if exists
        scriptEngine.eval('load("' + optionMap.ruleJsPath + '");');
    }
    var rule = scriptEngine.get('remoteApiRule');

    // load java property type mapping
    var typeMap = scriptEngine.invokeMethod(rule, 'typeMap');

    // only remote api requests here
    manager.info('...Generating remote api: ' + request.requestName);

    // schema name is from part of FreeGen request name e.g. RemoteApiSeaLand => SeaLand
    var scheme = scriptEngine.invokeMethod(rule, 'scheme', request);
    var schemePackage = scriptEngine.invokeMethod(rule, 'schemePackage', scheme);

    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // swagger.json's key
    // _/_/_/_/_/_/_/_/_/_/
    var pathMap = optionMap.jsonMap['paths'];
    var definitionMap = optionMap.jsonMap['definitions'];

    var apiList = [];
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // loop paths's elements, per one API URL: e.g. /sea/land
    // _/_/_/_/_/_/_/_/_/_/
    for (var pathKey in pathMap) {
        var path = pathMap[pathKey];
        for (var methodKey in pathMap[pathKey]) {
            var multipleHttpMethod = pathMap[pathKey].size() > 1;
            var method = path[methodKey];
            var api = new java.util.LinkedHashMap();
            api.scheme = scheme;
            api.package = request.package;
            api.url = pathKey;
            api.httpMethod = methodKey;
            api.multipleHttpMethod = multipleHttpMethod;
            api.consumes = method.consumes;
            api.produces = method.produces;
            api.methodBean = method;
            if (scriptEngine.invokeMethod(rule, 'target', api)) {
                api.url = scriptEngine.invokeMethod(rule, 'url', api);
                apiList.push(api);
            }
        }
    }

    var remoteApiBeanList = [];
    var exBehaviorMap = new java.util.LinkedHashMap();
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // loop api
    // _/_/_/_/_/_/_/_/_/_/
    for(apiIndex in apiList) {
        var api = apiList[apiIndex];
        // +------------------------+
        // |                        |
        // | Generate 'Param' beans |
        // |                        |
        // +------------------------+
        var pathVariables = new java.util.LinkedHashMap();
        var queryProperties = new java.util.LinkedHashMap();
        var formDataProperties = new java.util.LinkedHashMap();
        var bodyProperties = new java.util.LinkedHashMap();
        var paramBean = new java.util.LinkedHashMap();

        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // loop parameters's elements, e.g. in, name, description, required, schema, ...
        // _/_/_/_/_/_/_/_/_/_/
        for(parameterIndex in api.methodBean['parameters']) {
            var parameter = api.methodBean['parameters'][parameterIndex];
            if (parameter.in === 'path') {
                pathVariables[parameter.name] = parameter;
            } else if (parameter.in === 'query') {
                queryProperties[parameter.name] = parameter;
            } else if (parameter.in === 'formData') {
                formDataProperties[parameter.name] = parameter;
            } else if (parameter.in === 'body') {
                var definitionKey = '';
                if (parameter.schema.type && parameter.schema.type === 'array') {
                    definitionKey = parameter.schema.items['$ref'].replace('#/definitions/', '');
                } else if (parameter.schema['$ref']) {
                    definitionKey = parameter.schema['$ref'].replace('#/definitions/', '');
                }
                bodyProperties = definitionMap[scriptEngine.invokeMethod(rule, 'definitionKey', definitionKey)].properties;
                for (bodyPropertyKey in bodyProperties) {
                    var required = definitionMap[scriptEngine.invokeMethod(rule, 'definitionKey', definitionKey)].required;
                    if (required) {
                        bodyProperties[bodyPropertyKey].required = required.contains(bodyPropertyKey);
                    }
                }
                var remoteApiBean = createRemoteApiBean(rule, 'param', api, bodyProperties);
                remoteApiBean.in = 'body';
                remoteApiBean.array = parameter.schema.type === 'array';
                paramBean = remoteApiBean;
                remoteApiBeanList.push(paramBean);
            }
        }

        if (!queryProperties.isEmpty()) {
            var remoteApiBean = createRemoteApiBean(rule, 'param', api, queryProperties);
            remoteApiBean.in = 'query';
            paramBean = remoteApiBean;
            remoteApiBeanList.push(paramBean);
        }
        if (!formDataProperties.isEmpty()) {
            var remoteApiBean = createRemoteApiBean(rule, 'param', api, formDataProperties);
            remoteApiBean.in = 'formData';
            paramBean = remoteApiBean;
            remoteApiBeanList.push(paramBean);
        }

        // +-------------------------+
        // |                         |
        // | Generate 'Return' beans |
        // |                         |
        // +-------------------------+
        var returnProperties = new java.util.LinkedHashMap();
        var returnBean = new java.util.LinkedHashMap();

        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // loop responses's elements, e.g. in, name, description, required, schema, ...
        // _/_/_/_/_/_/_/_/_/_/
        for (responseKey in api.methodBean['responses']) {
            var response = api.methodBean['responses'][responseKey];
            // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
            // only one return bean is targeted.
            // _/_/_/_/_/_/_/_/_/_/
            if (!returnBean.className && response.schema) {
                var array = response.schema.type === 'array';
                var responseSchema = array ? response.schema.items : response.schema;

                if (typeMap[responseSchema.format]) {
                    returnBean.className = typeMap[responseSchema.format];
                    returnBean.array = array;
                } else if (typeMap[responseSchema.type]) {
                    returnBean.className = typeMap[responseSchema.type];
                    returnBean.array = array;
                } else {
                    var definitionKey = responseSchema['$ref'].replace('#/definitions/', '');
                    var returnProperties = definitionMap[scriptEngine.invokeMethod(rule, 'definitionKey', definitionKey)].properties;
                    if (!returnProperties.isEmpty()) {
                        for (returnPropertyKey in returnProperties) {
                            var returnPropertyValue = returnProperties[returnPropertyKey];
                            var required = definitionMap[scriptEngine.invokeMethod(rule, 'definitionKey', definitionKey)].required;
                            if (required) {
                                returnPropertyValue.required = required.contains(returnPropertyKey);
                            }
                        }
                        var remoteApiBean = createRemoteApiBean(rule, 'return', api, returnProperties);
                        remoteApiBean.array = array;
                        returnBean = remoteApiBean;
                        remoteApiBeanList.push(returnBean);
                    }
                }
            }
        }
        keepRemoteApiBehavior(rule, api, pathVariables, paramBean, returnBean, exBehaviorMap);
    }
    processRemoteApiBean(rule, remoteApiBeanList);
    processRemoteApiBhv(rule, request, exBehaviorMap);
}

/**
 * generate file.
 * @param {string} src - src (NotNull)
 * @param {string} dest - dest (NotNull)
 * @param {map} data - data (NotNull)
 * @param {boolean} overwite - overwite (NotNull)
 */
function generate(src, dest, data, overwite) {
    if (!java.nio.file.Files.exists(java.nio.file.Paths.get(generator.outputPath, dest)) || overwite) {
        manager.makeDirectory(dest);
        generator.parse(src, dest, 'data', data);
        print('generate("' + dest + '")');
    }
}

/**
 * Keep information of remote api bean.
 * @param {Rule} rule - rrule. (NotNull)
 * @param {string} beanPurposeType - The bean role type. e.g. param, return (NotNull)
 * @param {Api} api - The information of api. (NotNull)
 * @param {Properties} properties - The information of property for the bean. (NotNull)
 */
function createRemoteApiBean(rule, beanPurposeType, api, properties) {
    var schemePackage = scriptEngine.invokeMethod(rule, 'schemePackage', api.scheme);
    var subPackage = scriptEngine.invokeMethod(rule, 'beanSubPackage', api);
    var package = api.package + '.' + schemePackage;
    if (subPackage) {
        package = package + '.' + subPackage;
    }

    var remoteApiBean = new java.util.LinkedHashMap();
    remoteApiBean.api = api;
    remoteApiBean.package = package;
    remoteApiBean.className = scriptEngine.invokeMethod(rule, beanPurposeType + 'ClassName', api, false);
    remoteApiBean.extendsClass = rule[beanPurposeType + 'ExtendsClass'];
    remoteApiBean.implementsClasses = rule[beanPurposeType + 'ImplementsClasses'];
    remoteApiBean.properties = properties;
    remoteApiBean.beanPurposeType = beanPurposeType;
    remoteApiBean.remoteApiExp = api.httpMethod.toUpperCase() + ' ' + api.url;
    return remoteApiBean;
}

/**
 * Keep information of remote api behavior.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Api} api - The information of api. (NotNull)
 * @param {PathVariables} pathVariables - The array of path variables. (NotNull)
 * @param {ParamBean} paramBean - The information of param bean. (NotNull)
 * @param {ReturnBean} returnBean - The information of return bean. (NotNull)
 * @param {ExBehaviorMap} exBehaviorMap - The map of behavior information. (NotNull)
 */
function keepRemoteApiBehavior(rule, api, pathVariables, paramBean, returnBean, exBehaviorMap) {
    var schemePackage = scriptEngine.invokeMethod(rule, 'schemePackage', api.scheme);
    var subPackage = scriptEngine.invokeMethod(rule, 'behaviorSubPackage', api);
    var package = api.package + '.' + schemePackage;
    if (subPackage) {
        package = package + '.' + subPackage;
    }
    var className = scriptEngine.invokeMethod(rule, 'exBehaviorClassName', api);
    if (!exBehaviorMap[package + '.' + className]) {
        var exBehavior = new java.util.LinkedHashMap();
        exBehavior.package = package;
        exBehavior.className = className;
        exBehavior.remoteApiExp = subPackage;
        exBehaviorMap[package + '.' + className] = exBehavior;

        var bsBehavior = new java.util.LinkedHashMap();
        bsBehavior.package = package;
        bsBehavior.className = scriptEngine.invokeMethod(rule, 'bsBehaviorClassName', api);
        bsBehavior.remoteApiExp = subPackage;
        bsBehavior.methodList = [];
        exBehavior.bsBehavior = bsBehavior;
    }
    exBehaviorMap[package + '.' + className].bsBehavior.methodList.push({ 'api': api, 'pathVariables': pathVariables, 'paramBean': paramBean, 'returnBean': returnBean });
}

/**
 * Process remote api bean. (generating class)
 * @param {Rule} rule - rule. (NotNull)
 * @param {RemoteApiBeanList} remoteApiBeanList - remoteApiBeanList. (NotNull)
 */
function processRemoteApiBean(rule, remoteApiBeanList) {
    var sortRemoteApiBeanMap = new java.util.TreeMap();
    for (remoteApiBeanListIndex in remoteApiBeanList) {
        var remoteApiBean = remoteApiBeanList[remoteApiBeanListIndex];
        var path = remoteApiBean.package.replace(/\./g, '/') +'/' + remoteApiBean.className + '.java';
        sortRemoteApiBeanMap[path + '@@@@@' + remoteApiBean.api.url] = remoteApiBean;
    }

    var uniqueRemoteApiBeanMap = {};
    for (remoteApiBeanKey in sortRemoteApiBeanMap) {
        var remoteApiBean = sortRemoteApiBeanMap[remoteApiBeanKey];
        var path = remoteApiBean.package.replace(/\./g, '/') + '/' + remoteApiBean.className + '.java';
        if (uniqueRemoteApiBeanMap[path] && uniqueRemoteApiBeanMap[path].properties != remoteApiBean.properties) {
            print('warning duplication! try change path');
            remoteApiBean.className = scriptEngine.invokeMethod(rule, remoteApiBean.beanPurposeType + 'ClassName', remoteApiBean.api, true);
            var path = remoteApiBean.package.replace(/\./g, '/') + '/' + remoteApiBean.className + '.java';
        }
        if (uniqueRemoteApiBeanMap[path] && uniqueRemoteApiBeanMap[path].properties != remoteApiBean.properties) {
            print('warning duplication! skip path');
        } else {
            uniqueRemoteApiBeanMap[path] = remoteApiBean;
        }
    }

    for (remoteApiBeanKey in uniqueRemoteApiBeanMap) {
        var remoteApiBean = uniqueRemoteApiBeanMap[remoteApiBeanKey];
        var path = remoteApiBean.package.replace(/\./g, '/') + '/' + remoteApiBean.className + '.java';
        generate('./remoteapi/RemoteApiBean.vm', path, remoteApiBean, true);
    }
}

/**
 * Process remote api behavior. (generating class)
 * Also generate DI xml.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 * @param {ExBehaviorMap} exBehaviorMap - The map of behavior information. (NotNull)
 */
function processRemoteApiBhv(rule, request, exBehaviorMap) {
    if (!rule['behaviorClassGeneration']) {
        return;
    }
    var scheme = scriptEngine.invokeMethod(rule, 'scheme', request);
    var schemePackage = scriptEngine.invokeMethod(rule, 'schemePackage', scheme);
    var className = scriptEngine.invokeMethod(rule, 'abstractBehaviorClassName', scheme);
    var abstractBehavior = new java.util.LinkedHashMap();
    abstractBehavior.package = request.package + '.' + schemePackage;
    abstractBehavior.className = className;
    abstractBehavior.remoteApiExp = request.requestName;
    abstractBehavior.frameworkBehaviorClass = rule['frameworkBehaviorClass'];
    var path = abstractBehavior.package.replace(/\./g, '/') + '/' + abstractBehavior.className + '.java';
    generate('./remoteapi/RemoteApiAbstractBehavior.vm', path, abstractBehavior, false);

    for (exBehaviorKey in exBehaviorMap) {
        var exBehavior = exBehaviorMap[exBehaviorKey];

        var bsBehavior = exBehavior.bsBehavior;
        bsBehavior.abstractBehavior = abstractBehavior;
        var path = bsBehavior.package.replace(/\./g, '/') + '/' + bsBehavior.className + '.java';
        generate('./remoteapi/RemoteApiBsBehavior.vm', path, bsBehavior, true);

        var path = exBehavior.package.replace(/\./g, '/') + '/' + exBehavior.className + '.java';
        generate('./remoteapi/RemoteApiExBehavior.vm', path, exBehavior, false);
    }

    var container = new java.util.LinkedHashMap();
    container.schemePackage = schemePackage;
    container.exBehaviorMap = exBehaviorMap;
    if (manager.isTargetContainerLastaDi()) {
        var path = scriptEngine.invokeMethod(rule, 'diXmlPath', scheme, request.resourceFile);
        generate('./remoteapi/container/lastadi/RemoteApiDiXml.vm', path, container, true);
    }
    if (manager.isTargetContainerSeasar()) {
        var path = scriptEngine.invokeMethod(rule, 'diconPath', scheme, request.resourceFile);
        generate('./remoteapi/container/seasar/RemoteApiDicon.vm', path, container, true);
    }
}
