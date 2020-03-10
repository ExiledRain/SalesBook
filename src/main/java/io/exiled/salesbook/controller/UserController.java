package io.exiled.salesbook.controller;

import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping("/user")
//@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
//    @Autowired
//    private UserRepo userRepo;
//
//    @GetMapping
//    public String userList(Model model) {
//        model.addAttribute("users", userRepo.findAll());
//
//        return "userList";
//    }
//
//    @GetMapping("{user}")
//    public String userEditForm(@PathVariable User user, Model model) {
//        model.addAttribute("user", user);
//        model.addAttribute("roles", Role.values());
//
//        return "userEdit";
//    }
//
//    @PostMapping
//    public String userSave(
//            @RequestParam String username,
//            @RequestParam Map<String, String> form,
//            @RequestParam("userId") User user
//    ) {
//        if (!StringUtils.isEmpty(username) && !username.equals(user.getUsername())) {
//            user.setUsername(username);
//        }
//
//        Set<String> roles = Arrays.stream(Role.values())
//                .map(Role::name)
//                .collect(Collectors.toSet());
//
//        user.getRoles().clear();
//
//        for (String key : form.keySet()) {
//            if (roles.contains(key)) {
//                user.getRoles().add(Role.valueOf(key));
//            }
//        }
//        userRepo.save(user);
//
//        return "redirect:/user";
//    }
}
