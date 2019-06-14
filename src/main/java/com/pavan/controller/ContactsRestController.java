package com.pavan.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.entity.Contact;
import com.pavan.entity.bean.ApiResponse;
import com.pavan.entity.bean.ContactBean;
import com.pavan.repository.ContactRepository;

@RestController
@CrossOrigin(origins = "*")
public class ContactsRestController {

	@Autowired
	private ContactRepository contactRepo;

	@PostMapping("/rest/saveContact")
	public ApiResponse saveContact(@Valid @RequestBody ContactBean contactBean) {
		
		String message = "";
		
		Contact contact=new Contact();
		
		if(contactBean.getId()==0){
			message = "Contact Added Successfully";
		}else{
			contact.setId(contactBean.getId());	
			message = "Contact Updated Successfully";
		}
		
		contact.setName(contactBean.getName());
		contact.setMobile(contactBean.getMobile());
		
		contactRepo.save(contact);
		
		ApiResponse response = new ApiResponse(HttpStatus.OK, message, null);
		return response;
	}

	@GetMapping("/rest/listContacts")
	public ApiResponse restListContacts() {
		ApiResponse response = new ApiResponse(HttpStatus.OK, null, contactRepo.findAll());
		return response;
	}

	@DeleteMapping("/rest/deleteContact/{contact}")
	public ApiResponse restDeleteContact(@PathVariable(name = "contact") long contact) {
		Contact c = contactRepo.findById(contact).get();
		contactRepo.delete(c);

		ApiResponse response = new ApiResponse(HttpStatus.OK, "Contact Deleted Successfully", null);
		return response;
	}

}
