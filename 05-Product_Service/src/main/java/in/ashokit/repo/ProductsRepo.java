package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Products;

public interface ProductsRepo extends JpaRepository<Products, Integer>{

}
