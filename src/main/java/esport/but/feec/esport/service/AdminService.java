package esport.but.feec.esport.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import esport.but.feec.esport.api.AdminCreateView;
import esport.but.feec.esport.api.AdminDetailView;
import esport.but.feec.esport.data.AdminRepository;

import java.util.List;

public class AdminService {private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminDetailView getPersonDetailView(Long id) {
        return adminRepository.findAdminDetailedView(id);
    }

    public List<AdminDetailView> getPersonsBasicView() {
        return adminRepository.getPersonsBasicView();
    }

    public void createPerson(AdminDetailView personCreateView) {
        // the following three lines can be written in one code line (only for more clear explanation it is written in three lines
        char[] originalPassword = AdminCreateView.getPwd();
        char[] hashedPassword = hashPassword(originalPassword);
        personCreateView.setPwd(hashedPassword);

        adminRepository.createPerson(personCreateView);
    }

    public void editPerson(AdminEditView personEditView) {
        adminRepository.editPerson(AdminEditView);
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
