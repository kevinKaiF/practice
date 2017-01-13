package y2016.m12.d29;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/29
 */
class URLResolver {
    private String IP_REG = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    public Map<String, Object> resolver(String url) {
        validateUrl(url);
        // parse parameters
        Map<String, String> parameters = parseParameters(url);
        url = applyAfterParseParameters(parameters, url);

        // parse schema
        String schema = parseSchema(url);
        url = applyAfterParseSchema(schema, url);

        // parse username and password
        Map<String, String> identity = parseIdentity(url);
        url = applyAfterParseIdentity(identity, url);

        // parse path
        String path = parsePath(url);

        // parse hostname and port
        Map<String, Object> hostAndPort = parseHostAndPort(url);

        Map<String, Object> map = new HashMap<>();
        map.put("parameters", parameters);
        map.put("schema", schema);
        map.put("path", path);
        map.put("username", identity == null ? null : identity.get("username"));
        map.put("password", identity == null ? null : identity.get("password"));
        map.put("host", hostAndPort == null ? null : hostAndPort.get("host"));
        map.put("port", hostAndPort == null ? null : hostAndPort.get("port"));
        return map;
    }

    private Map<String, Object> parseHostAndPort(String url) {
        String host = null;
        int port = 0;
        int index = url.indexOf(":");
        if (index > -1) {
            String hostToMatch = url.substring(0, index);
            if (hostToMatch.matches(IP_REG)) {
                host = hostToMatch;
                url = url.substring(index);
                index = url.indexOf("/");
                if (index > -1) {
                    port = Integer.valueOf(url.substring(1, index));
                } else {
                    port = Integer.valueOf(url.substring(1));
                }

                Map<String, Object> map = new HashMap<>();
                map.put("host", host);
                map.put("port", port);
                return map;
            }
        }

        return null;
    }


    private String parsePath(String url) {
        return url;
    }

    private String applyAfterParseIdentity(Map<String, String> identity, String url) {
        if (identity != null) {
            return url.substring(url.indexOf("@") + 1);
        }
        return url;
    }

    private Map<String, String> parseIdentity(String url) {
        int index = url.indexOf("@");
        if (index > -1) {
            Map<String, String> identity = new HashMap<>();
            String[] usernameAndPassword = url.substring(0, index).split(":");
            identity.put("username", usernameAndPassword[0]);
            identity.put("password", usernameAndPassword[1]);
            return identity;
        }
        return null;
    }

    private String applyAfterParseSchema(String schema, String url) {
        if (schema != null) {
            int index = url.indexOf("://");
            if (index > -1) {
                url = url.substring(index + 3);
            } else {
                index = url.indexOf(":/");
                url = url.substring(index + 2);
            }

            if (url.startsWith("/")) {
                return url.substring(1);
            }
        }
        return url;
    }

    private String parseSchema(String url) {
        int index = url.indexOf("://");
        if (index > -1) {
            return url.substring(0, index);
        } else {
            index = url.indexOf(":/");
            if (index > -1) {
                return url.substring(0, index);
            } else {
                return null;
            }
        }
    }

    private String applyAfterParseParameters(Map<String, String> parameters, String url) {
        if (parameters != null) {
            url = url.substring(0, url.indexOf("?"));
        }

        return url;
    }

    private boolean validateUrl(String url) {
        if (url == null || url.trim().length() == 0) {
            throw new IllegalArgumentException("url is null");
        }

        try {
            new java.net.URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    private Map<String, String> parseParameters(String url) {
        int index = url.indexOf("?");
        if (index > -1) {
            Map<String, String> parameters = new HashMap<String, String>();
            String[] pairs = url.substring(index + 1).replace("&amp;", "&").split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=");
                if (kv[0].trim().length() > 0) {
                    parameters.put(kv[0].trim(), kv[1]);
                }
            }

            return parameters;
        }
        return null;
    }
}
