package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Address;
import vn.edu.iuh.fit.backend.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/listAddress", method = RequestMethod.GET)
    public ModelAndView showAddressList() {
        ModelAndView modelAndView = new ModelAndView("address/addresses-paging");
        List<Address> addresses = addressService.findAll();
        modelAndView.addObject("addresses", addresses);
        return modelAndView;
    }

    @GetMapping("/")
    public List<Address> getAllAddresses() {
        return addressService.findAll();
    }

}
