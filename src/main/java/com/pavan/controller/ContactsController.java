package com.pavan.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pavan.entity.Contact;
import com.pavan.repository.ContactRepository;

@Controller
@CrossOrigin(origins = "*")
public class ContactsController {

	@Autowired
	private ContactRepository contactRepo;

	@GetMapping("/")
	public String home() {
		return "dashboard";
	}

	@GetMapping("/addContact")
	public String addContact() {
		return "contacts";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveContact(Contact contact, Model model) {
		List<Contact> l = contactRepo.findByMobile(contact.getMobile());
		if (l != null && l.size() > 0) {
			model.addAttribute("errorMsg", "Mobile Number " + contact.getMobile() + " already exists.");
			return "contacts";
		}
		contactRepo.save(contact);
		return "redirect:/listContacts";
	}

	@GetMapping("/listContacts")
	public String listContacts(Model model) {
		List<Contact> contacts = new ArrayList<Contact>();
		contacts = contactRepo.findAll();
		model.addAttribute("contacts", contacts);
		return "listContacts";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteContact")
	public String deleteContact(@RequestParam(name = "contact") long contact) {
		Contact c = contactRepo.findById(contact).get();
		contactRepo.delete(c);
		return "redirect:/listContacts";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/updateContact")
	public String updateContact(@RequestParam(name = "contact") long contact, Model model) {
		Contact c = contactRepo.findById(contact).get();
		model.addAttribute("contact", c);
		return "contacts";
	}

}
