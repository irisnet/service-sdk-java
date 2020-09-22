package iservice.sdk.module.impl;

import iservice.sdk.entity.Key;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IKeyDAO;

import java.util.HashMap;
import java.util.Map;

/**
 * Default Key DAO Implementation, only for testing purpose, not recommended
 *
 * @author Yelong
 */
public class DefaultKeyDAOImpl implements IKeyDAO {

    private Map<String, Key> keyMap;

    public DefaultKeyDAOImpl() {
        this.keyMap = new HashMap<>();
    }

    @Override
    public void write(String name, Key key) throws ServiceSDKException {
        this.keyMap.put(name, key);
    }

    @Override
    public Key read(String name) {
        return this.keyMap.get(name);
    }

    @Override
    public void delete(String name) throws ServiceSDKException {
        this.keyMap.remove(name);
    }
}
