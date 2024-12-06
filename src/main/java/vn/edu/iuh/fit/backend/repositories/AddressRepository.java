package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

     Address findAddressByNumberAndStreetAndCityAndZipcode (String number, String street, String city, String zipcode);

        Address findByAddressId(long id);
}