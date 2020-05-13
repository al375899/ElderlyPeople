package es.uji.ei1027.elderlypeople.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.elderlypeople.model.UserDetails;

@Repository
public class FakeUserProvider implements UserDao {
	final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();

	public FakeUserProvider() {
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			URL url = getClass().getResource("bbdd.txt");
			archivo = new File(url.getPath());
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String usuario;
			while ((usuario = br.readLine()) != null) {
				String[] datos = usuario.split(" ");
				UserDetails user = new UserDetails();
				user.setUsername(datos[0]);
				user.setPassword(passwordEncryptor.encryptPassword(datos[1]));
				user.setType(datos[2]);
				knownUsers.put(datos[0], user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username, String password) {
		UserDetails user = knownUsers.get(username.trim());
		if (user == null)
			return null; // Usuari no trobat
		// Contrasenya
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		if (passwordEncryptor.checkPassword(password, user.getPassword())) {
			// Es deuria esborrar de manera segura el camp password abans de tornar-lo
			return user;
		} else {
			return null; // bad login!
		}
	}

	@Override
	public Collection<UserDetails> listAllUsers() {
		return knownUsers.values();
	}
}