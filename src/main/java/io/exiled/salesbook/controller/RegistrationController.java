//package io.exiled.salesbook.controller;
//
//import io.exiled.salesbook.repos.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class RegistrationController {
//    @Autowired
//    private UserRepo userRepo;
//
//    @GetMapping("/registration")
//    public String register() {
//        return "registration";
//    }
//
////    @PostMapping("/registration")
////    public String addUser(User user, Model model) {
////        User userFromDb = userRepo.findByUsername(user.getUsername());
////
////        if (userFromDb != null) {
////            model.addAttribute("message","Have fun ;) nothing happened");
////            return "registration";
////        }
////        user.setActive(true);
////        user.setRoles(Collections.singleton(Role.USER));
////        userRepo.save(user);
////
////        return "redirect:/login";
////    }
//}
