package test.proxy;

import com.test.proxy.IRepository;
import com.test.proxy.IStreet;
import com.test.proxy.remote.Street;

/**
 *
 *
 */
public class Repository implements IRepository {

    @Override
    public IStreet getProxy(String id) {
        // find(.class,id);
        // select * from bla_bla where id = :id
        IStreet street = new Street();
        street.setId(id);
        street.setName("Тверская");
        return (IStreet)street;
    }
}
