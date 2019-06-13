import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class IP {

	private Map<String, Map<String, Integer>> usuariosMap = new HashMap<>();

	public String ejecutar(String linea)  {

		String ip = null;
		String usuario = null;
		int mens = 0;
		boolean fin = false;
		
		String token;
		int estado = 0;
		String salida = "";
		Scanner s = new Scanner(linea);
		do {
			switch (estado) {
			case 0:

				try {

					token = s.skip("fin|FIN|IP\\s*=\\(").match().group();
					if (token.equalsIgnoreCase("fin")) {
						fin = true;
						
						break;

					} else {
						estado = 1;
						
					}
				} catch (NoSuchElementException e) {
					salida = "Se esperaba 'fin' o 'IP=('";
				
					break;
				}

			case 1:
				try {
					ip = s.skip(
							"(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")
							.match().group();
					estado = 2;
				} catch (NoSuchElementException e) {
					salida = "Se esperaba una IP válida";
					estado = 0;
					
					break;
				}

			case 2:
				try {
					token = s.skip("\\)\\s*mensaje\\=\\(.*\\)\\s*usuario\\=\\(").match().group();
					estado = 3;
				} catch (NoSuchElementException e) {
					salida = "Se esperaba 'mensaje=' y 'usuario='";
					estado = 0;
					
					break;
				}

			case 3:

				try {
					usuario = s.skip("\\p{L}+").match().group();
					estado = 4;
				} catch (NoSuchElementException e) {
					salida = "Se esperaba nombre el nombre del usuario";
					estado = 0;
					
					break;
				}

			case 4:
				try {
					token = s.skip("\\)").match().group();
					if (usuariosMap.containsKey(usuario)) {
						if (usuariosMap.get(usuario).containsKey(ip)) {
							usuariosMap.get(usuario).replace(ip, usuariosMap.get(usuario).get(ip),
									usuariosMap.get(usuario).get(ip) + 1);
							salida = "Nuevo mensaje para la IP " + ip + " del Usuario " + usuario;
							estado = 0;
							token = null;
							s.reset();
						
						} else {
							usuariosMap.get(usuario).put(ip, 1);
							salida = "Nueva IP " + ip + " del Usuario " + usuario;
							estado = 0;
							token = null;
							s.reset();
							
						}
					} else {
						mens = 1;
						usuariosMap.put(usuario, new HashMap<>());
						usuariosMap.get(usuario).put(ip, mens);
						salida = "Nuevo Usuario: " + usuario + " con la IP " + ip;
						estado = 0;
						token = null;
						s.reset();
					
					}

				} catch (NoSuchElementException e) {
					salida = "se esperaba ')'";
					estado = 0;
					
					break;
				}

			}

			
		} while (!fin);

		s.close();
		for (Entry<String, Map<String, Integer>> jugador : usuariosMap.entrySet()) {
			String clave = jugador.getKey();
			salida = clave + "  ->  " + usuariosMap.get(clave);
		}

		return salida;
	}
}

//IP=(192.168.8.8) mensaje=(hola mundo) usuario=(bocheti)