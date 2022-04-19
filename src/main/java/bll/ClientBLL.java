package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.ClientAgeValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class ClientBLL {

    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        validators.add(new ClientAgeValidator());
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client st = clientDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return st;
    }

    /** method used for returning a list that contains all clients
     *
     * @return
     */
    public List<Client> findAllClients() {
        List<Client> st = clientDAO.findAll();
        return st;
    }

    /** method used for inserting a client in database
     *
     * @return
     */
    public void insertClient(Client client) {
        Client st = clientDAO.insert(client);
    }

    /** method used for updating a client
     * returns the updates client
     * @return
     */
    public Client updateClient(Client client) {
        Client st = clientDAO.update(client, "id");
        return st;
    }

    /** method used for deleting a client based on the id
     * @return
     */
    public void deleteClient(int id) {
        clientDAO.delete(id, "id");
    }
}
