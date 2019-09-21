#!python3
import socket #Libreria sockets
import traceback #Libreria excepciones

try:
	#Host y puerto del servidor
	servidor = ("127.0.0.1", 1234)

	while True:
		cl = socket.socket(socket.AF_INET, socket.SOCK_DGRAM) 

		print("")
		print("Cliente de datagramas iniciado. Ahorcado... Conectado al servidor.")

		print("")
		dificultad = input("Seleccione la dificultad. Facil = 0. Media = 1. Dificil = 2: ")

		b = dificultad.encode()
		enviado = cl.sendto(b, servidor)

		print("")
		print("Esperando palabra del servidor...")

		bpal = cl.recvfrom(65535)
		palabra = bpal[0].decode()
		cl.close()

		longitud = len(palabra)

		intentos = int(int(longitud)/2) + 1
		listaPalabraAdiv = []
		listaPalabraMost = []
		letra = ""

		print("AHORCADO, solo puedes fallar " + str(intentos) + " veces")

		listaPalabraAdiv = list(palabra)

		for item in listaPalabraAdiv:
			listaPalabraMost.append("_")

		while True:
			print("")
			print(" ".join(listaPalabraMost))
			letra = input("Ingrese una letra: ")

			error = False

			if letra not in listaPalabraAdiv:
				error = True
				intentos = intentos - 1
				print("Letra incorrecta. Intentos disponibles: " + str(intentos))
			else:
				for i, letraPalabra in enumerate(listaPalabraAdiv):
					if letraPalabra == letra:
						listaPalabraMost[i] = letraPalabra

			if intentos <= 0:
				print("")
				print("Has perdido, la palabra a adivinar era: " + palabra)
				break
			elif listaPalabraAdiv == listaPalabraMost:
				print("")
				print("Has ganado, la palabra adivinada es: " + palabra)
				break

		print("")
		jugar=input("Desea continuar:")
		if(jugar == "N" or jugar == "n"):
			break

except Exception as e:
    print(traceback.format_exc())
