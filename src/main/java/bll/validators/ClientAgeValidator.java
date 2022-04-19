package bll.validators;
import model.Client;
public class ClientAgeValidator implements Validator<Client> {
	private static final int MIN_AGE = 7;
	private static final int MAX_AGE = 30;

	public boolean validate(Client t) {

		if (t.getAge() < 0 ) {

			return false;
		}

		return true;
	}

}
