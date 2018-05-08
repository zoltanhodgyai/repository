package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Supplier;

import java.util.List;

public interface SupplierRepository extends Repository<Supplier, Integer> {

    @Transactional(readOnly = true)
    Supplier findSupplierById(Integer id);

    @Transactional
    Supplier save(Supplier supplier);

    @Transactional(readOnly = true)
    List<Supplier> findAll();

    @Transactional
    void deleteSupplierById(Integer id);
}
