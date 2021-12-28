package org.but.feec.esport.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.esport.api.*;
import org.but.feec.esport.data.AdminRepository;

import java.util.List;

public class AdminService {
    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminDetailView getAdminDetailView(Long id) {
        return adminRepository.findAdminDetailedView(id);
    }

    public List<AdminBasicView> getAdminsBasicView() {
        return adminRepository.getAdminsBasicView();
    }

    public void createAdmin(AdminCreateView adminCreateView) {
        char[] originalPassword = adminCreateView.getPwd();
        char[] hashedPassword = hashPassword(originalPassword);
        adminCreateView.setPwd(hashedPassword);

        adminRepository.createAdmin(adminCreateView);
    }

    public void editAdmin(AdminEditView adminEditView) {
        adminRepository.editAdmin(adminEditView);
    }

    private char[] hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToChar(12, password);
    }
}
