package org.but.feec.esport.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.esport.api.AdminDetailView;
import org.but.feec.esport.api.AdminEditView;
import org.but.feec.esport.data.AdminRepository;

public class AdminService {private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminDetailView getPersonDetailView(Long id) {
        return adminRepository.findAdminDetailedView(id);
    }

    /**public List<AdminDetailView> getPersonsBasicView() {
        return adminRepository.getAdminBasicView();
    }*/

    /**public void createPerson(AdminDetailView adminCreateView) {
        // the following three lines can be written in one code line (only for more clear explanation it is written in three lines
        char[] originalPassword = AdminCreateView.getPwd();
        char[] hashedPassword = hashPassword(originalPassword);
        adminCreateView.setPwd(hashedPassword);

        adminRepository.createPerson(adminCreateView);
    }*/

    public void editPerson(AdminEditView adminEditView) {
        adminRepository.editPerson(adminEditView);
    }

    /**
     * <p>
     * Note: For implementation details see: https://github.com/patrickfav/bcrypt
     * </p>
     *
     * @param password to be hashed
     * @return hashed password
    */
    private char[] hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToChar(12, password);
    }
}
