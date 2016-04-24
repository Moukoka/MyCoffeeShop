package com.mouks.rosie.mycoffeeshop.repository;

import android.test.AndroidTestCase;

import com.mouks.rosie.mycoffeeshop.domain.Supplier;
import com.mouks.rosie.mycoffeeshop.repositories.Impl.SupplierRepoImpl;
import com.mouks.rosie.mycoffeeshop.repositories.SupplierRepository;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class SupplierRepoImplTest extends AndroidTestCase {

    private static final String TAG="SETTINGS TEST";
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        //super.setUp();

        SupplierRepository repo = new SupplierRepoImpl(this.getContext());

        // CREATE
        Supplier createEntity = new Supplier.Builder()
                .compName("ELite").contact("Craig").surname("Lez")
                .title("Mr").street("3 Rosmead").sub("KuilsRiver").city("CapeTown").country("RSA").postalCode("7580")
                .phone("074589").fax("08562412").email("Craig@hlo.com").goodType("ingredients").build();
        Supplier insertedEntity = repo.save(createEntity);
        id=insertedEntity.getSupplierId();
        Assert.assertNotNull(TAG + " CREATE", insertedEntity);

        //READ ALL
        Set<Supplier> settings = repo.findAll();
        Assert.assertTrue(TAG+" READ ALL",settings.size()>0);

        //READ ENTITY
        Supplier entity = repo.findById(id);
        Assert.assertNotNull(TAG+" READ ENTITY",entity);



        //UPDATE ENTITY
        Supplier updateEntity = new Supplier.Builder()
                .copy(entity)
                .sub("Brackenfell")
                .build();
        repo.update(updateEntity);
        Supplier newEntity = repo.findById(id);
        Assert.assertEquals(TAG+ " UPDATE ENTITY","TEST47",newEntity.getSuburb());

        // DELETE ENTITY
        repo.delete(updateEntity);
        Supplier deletedEntity = repo.findById(id);
        Assert.assertNull(TAG+" DELETE",deletedEntity);

    }


}