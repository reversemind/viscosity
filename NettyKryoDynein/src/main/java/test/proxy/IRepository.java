package test.proxy;

import com.test.proxy.IStreet;

/**
 *
 */
public interface IRepository {
    public IStreet getProxy(String id);
}
