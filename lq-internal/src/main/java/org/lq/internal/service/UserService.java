package org.lq.internal.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.lq.internal.domain.user.*;
import org.lq.internal.helper.exception.PVException;
import org.lq.internal.repository.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@ApplicationScoped
public class UserService {

    private final Logger LOG = Logger.getLogger(ProductService.class);

    @Inject
    UserRepository userRepository;

    @Inject
    UserDataRepository userDataRepository;

    @Inject
    TypeDocumentRepository typeDocumentRepository;

    @Inject
    RoleRepository roleRepository;

    @Inject
    GenderUserRepository genderUserRepository;

    @Inject
    StatusUserRepository statusUserRepository;

    public List<UserData> getUsers() throws PVException {
        LOG.infof("@getUsers SERV > Start service to obtain the users");

        List<UserData> users = userDataRepository.listAll();
        LOG.infof("@getUsers SERV > Retrieved list of users");

        if (users.isEmpty()) {
            LOG.warnf("@getUsers SERV > No users found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron usuarios");
        }

        LOG.infof("@getUsers SERV > Finish service to obtain the users");
        return users;
    }

    public UserData validateLogin(LoginDTO loginDTO) throws PVException {
        LOG.infof("@validateLogin SERV > Start service to validate the user");

        User user = userRepository.findByDocumentNumber(loginDTO.getDocument());
        if (user == null) {
            LOG.warnf("@validateLogin SERV > No user found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontró usuario con el número de documento ingresado.");
        }

        if (user.getUserStatusId() != 1){
            LOG.warnf("@validateLogin SERV > No user found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "El usuario con el que intentas acceder esta desactivado.");
        }

        if (!checkPassword(loginDTO.getPassword(), user.getPassword())) {
            LOG.warnf("@validateLogin SERV > Incorrect password");
            throw new PVException(Response.Status.UNAUTHORIZED.getStatusCode(), "Contraseña incorrecta.");
        }

        LOG.infof("@validateLogin SERV > Finish service to validate the user");

        return userDataRepository.findByDocumentNumber(loginDTO.getDocument());
    }

    public void saveUser(UserDTO userDTO) throws PVException {
        LOG.infof("@saveUser SERV > Start service to save a new user");

        if (userRepository.findByDocumentNumber(userDTO.getDocumentNumber()) != null) {
            LOG.warnf("@saveUser SERV > User already exists with document number %s", userDTO.getDocumentNumber());
            throw new PVException(Response.Status.CONFLICT.getStatusCode(), "El usuario ya existe.");
        }

        String encryptedPassword = encryptPassword(userDTO.getPassword());

        LOG.infof("@saveUser SERV > Creating user entity from DTO");
        User user = User.builder()
                .documentNumber(userDTO.getDocumentNumber())
                .userTypeId(userDTO.getUserTypeId())
                .genderId(userDTO.getGenderId())
                .documentTypeId(userDTO.getDocumentTypeId())
                .userStatusId(userDTO.getUserStatusId())
                .firstName(userDTO.getFirstName())
                .secondName(userDTO.getSecondName())
                .firstLastName(userDTO.getFirstLastName())
                .secondLastName(userDTO.getSecondLastName())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .email(userDTO.getEmail())
                .password(encryptedPassword)
                .build();

        LOG.infof("@saveUser SERV > Persisting user with document number %s", userDTO.getDocumentNumber());
        userRepository.persist(user);

        LOG.infof("@saveUser SERV > User saved successfully with document number %s", userDTO.getDocumentNumber());
    }

    private String encryptPassword(String password) throws PVException {
        try {
            return BCrypt.hashpw(password, BCrypt.gensalt());
        } catch (Exception e) {
            LOG.errorf("@encryptPassword SERV > Error encrypting password", e);
            throw new PVException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Error encriptando la contraseña.");
        }
    }

    private boolean checkPassword(String rawPassword, String encryptedPassword) throws PVException {
        try {
            return BCrypt.checkpw(rawPassword, encryptedPassword);
        } catch (Exception e) {
            LOG.errorf("@checkPassword SERV > Error validating password", e);
            throw new PVException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Error validando la contraseña.");
        }
    }

    public void updateUser(UserDTO userDTO) throws PVException {
        LOG.infof("@updateUser SERV > Start service to update user with ID %d", userDTO.getDocumentNumber());

        User existingUser = userRepository.findById(userDTO.getDocumentNumber());
        if (existingUser == null) {
            LOG.warnf("@updateUser SERV > User with ID %d not found", userDTO.getDocumentNumber());
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Usuario no encontrado.");
        }

        existingUser.setUserTypeId(userDTO.getUserTypeId());
        existingUser.setGenderId(userDTO.getGenderId());
        existingUser.setDocumentTypeId(userDTO.getDocumentTypeId());
        existingUser.setUserStatusId(userDTO.getUserStatusId());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setSecondName(userDTO.getSecondName());
        existingUser.setFirstLastName(userDTO.getFirstLastName());
        existingUser.setSecondLastName(userDTO.getSecondLastName());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            String encryptedPassword = encryptPassword(userDTO.getPassword());
            existingUser.setPassword(encryptedPassword);
        }

        LOG.infof("@updateUser SERV > Updating user with ID %d", userDTO.getDocumentNumber());
        userRepository.persist(existingUser);

        LOG.infof("@updateUser SERV > User with ID %d updated successfully", userDTO.getDocumentNumber());
    }

    public void deleteUser(Long userId) throws PVException {
        LOG.infof("@deleteUser SERV > Start service to delete user with ID %d", userId);

        User existingUser = userRepository.findById(userId);
        if (existingUser == null) {
            LOG.warnf("@deleteUser SERV > User with ID %d not found", userId);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Usuario no encontrado.");
        }
        LOG.infof("@deleteUser SERV > Deactivating user with ID %d", userId);
        existingUser.setUserStatusId(2);
        userRepository.persist(existingUser);

        LOG.infof("@deleteUser SERV > User with ID %d deleted successfully", userId);
    }

    public List<TypeDocument> getTypeDocument() throws PVException {
        LOG.infof("@getTypeDocument SERV > Start service to obtain type documents");

        List<TypeDocument> typeDocumentList = typeDocumentRepository.listAll();
        LOG.infof("@getTypeDocument SERV > Retrieved list of type documents");

        if (typeDocumentList.isEmpty()) {
            LOG.warnf("@getTypeDocument SERV > No type documents found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron tipos de documentos.");
        }

        LOG.infof("@getTypeDocument SERV > Finish service to obtain type documents");
        return typeDocumentList;
    }

    public List<TypeUser> getRole() throws PVException {
        LOG.infof("@getRole SERV > Start service to obtain roles");

        List<TypeUser> roleList = roleRepository.listAll();
        LOG.infof("@getRole SERV > Retrieved list of roles");

        if (roleList.isEmpty()) {
            LOG.warnf("@getRole SERV > No roles found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron roles registrados.");
        }

        LOG.infof("@getRole SERV > Finish service to obtain roles");
        return roleList;
    }

    public List<GenderUser> getGenderUser() throws PVException {
        LOG.infof("@getGenderUser SERV > Start service to obtain genders");

        List<GenderUser> genderUserList = genderUserRepository.listAll();
        LOG.infof("@getGenderUser SERV > Retrieved list of genders");

        if (genderUserList.isEmpty()) {
            LOG.warnf("@getGenderUser SERV > No genders found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron géneros registrados.");
        }

        LOG.infof("@getGenderUser SERV > Finish service to obtain genders");
        return genderUserList;
    }

    public List<StatusUser> getStatusUser() throws PVException {
        LOG.infof("@getStatusUser SERV > Start service to obtain user statuses");

        List<StatusUser> statusUserList = statusUserRepository.listAll();
        LOG.infof("@getStatusUser SERV > Retrieved list of user statuses");

        if (statusUserList.isEmpty()) {
            LOG.warnf("@getStatusUser SERV > No user statuses found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron estados de usuario registrados.");
        }

        LOG.infof("@getStatusUser SERV > Finish service to obtain user statuses");
        return statusUserList;
    }

}
