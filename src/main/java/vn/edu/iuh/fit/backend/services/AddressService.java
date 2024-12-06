package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Address;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address findById(long id) {
        return addressRepository.findByAddressId(id);
    }

//    Nếu địa chỉ đã tồn tại thì trả về true, ngược lại trả về false
    public boolean checkExistingAddress(Address address) {
        Address existingAddress = addressRepository.findAddressByNumberAndStreetAndCityAndZipcode(address.getNumber(), address.getStreet(), address.getCity(), address.getZipcode());
        return existingAddress != null;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}
