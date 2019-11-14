#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>


void crearRandom2(int nn);
void escribirArchivo2(int **numeros, char *titulo);
void escribir2(int *numeros, char *titulo);
void* ordenamientoBurbuja2(void* arg);

int num_servidores;
int arr_numeros[1000];
int arr_numeros2[1009];
int numerosrandom = 1000;

int main(){

	pthread_t *servidores_thread;
	int** cubetas;
	int** resultado_ordenado;
	int i,k,j, num,relacion,acumulado,z,m;
	int rango1, rango2;
	srand(time(NULL));
	int mod = 0,aux1 = 0;


    //Solicitamos el numero de servidores
	printf("Numero de cubetas? \n");
	scanf("%d", &num_servidores);

    mod = numerosrandom%num_servidores;


   for(m=0;m<9;m++)

   {
	        //numerosrandom = numerosrandom + 1;
	        mod = (numerosrandom)%num_servidores;
	        if(mod != 0) {
	            printf("Aun no\n");
	            numerosrandom = numerosrandom + 1;
	            continue;
	        }
	        else if(mod == 0) {

	            crearRandom2(numerosrandom);

	            printf("Numers totales: %d\n",numerosrandom);

	            //cubetas
	            cubetas = (int **)malloc(num_servidores*sizeof(int *));
	            for(i=0; i < num_servidores; i++)
              		cubetas[i]=(int *)malloc(sizeof(int)*(numerosrandom/num_servidores));

              	resultado_ordenado = (int **)malloc(num_servidores*sizeof(int *));
	            for(i=0; i < num_servidores; i++)
              		resultado_ordenado[i] = (int *)malloc(sizeof(int)*(numerosrandom/num_servidores));

	            rango1 = 0;
	            rango2 = (1000/num_servidores) - 1;
	            int pos=0;
	            int aux;

		            for(i=0; i < num_servidores; i++){
			            for (j = 0; j < 1000; j++){
				            if(arr_numeros2[j] >= rango1 && arr_numeros2[j] <= rango2){
					            cubetas[i][pos] = arr_numeros2[j];
					            pos++;
				            }
			            }
			            pos=0;
			            //Actualizamos el rango
			            aux = rango2;
			            rango1 = rango2+1;
			            rango2 = rango2 + (1000/num_servidores);
		            }

	            printf("Se han armado las cubetas...\n");

	            servidores_thread = (pthread_t *)malloc(num_servidores * sizeof(pthread_t));

	            for (i = 0; i < num_servidores; i++){
		            printf("Enviando Cubeta al servidor %d \n \n ", (i+1));
		            pthread_create(&servidores_thread[i], NULL, &ordenamientoBurbuja2,cubetas[i]);
		            pthread_join(servidores_thread[i],(void *)&resultado_ordenado[i]);
	            }

	            printf("Los servidores han terminado el proceso de ordenamiento...\n \n");
	            escribirArchivo2(resultado_ordenado, "final.txt");
	            free(cubetas);
	            free(resultado_ordenado);
	            free(servidores_thread);
	        system ("pause");

	        break;


	        }
	}

	return 0;
}




void crearRandom2(int nn) {
    int i,num;
    for(i=0;i< nn;i++){
		num = rand()%1000 + 1;
		arr_numeros2[i] = num;
	}
	escribir2(arr_numeros2, "random.txt");
}

void* ordenamientoBurbuja2(void* arg){
	int *lista_numeros;
	int *aux;
	int tam = 0, temp = 0, i,j;
	lista_numeros = (int *)arg;

	tam = numerosrandom/num_servidores;

	for (i=0; i < tam; i++){
    	for (j=0 ; j< tam - 1; j++){
    			if (lista_numeros[j] > lista_numeros[j+1]){
    				temp = lista_numeros[j];
    				lista_numeros[j] = lista_numeros[j+1];
    				lista_numeros[j+1] = temp;
    			}
    	}
    }

    return (void *)lista_numeros;
}

void escribirArchivo2(int **numeros, char *titulo){
	//Creamos el archivo
	int i, j;
	FILE *archivo;
	archivo = fopen(titulo, "r");
	if(!archivo){
		//Se creo el archivo
		archivo = fopen(titulo,"w");
		for(i=0; i < num_servidores; i++){
			fprintf(archivo, "Cubeta %d\n", i+1);
			for(j = 0; j < numerosrandom/num_servidores; j++){
				if(numeros[i][j] != 0)
					fprintf(archivo, "%d, ",numeros[i][j] );
			}
			fprintf(archivo, "\n\n");
		}
        fclose(archivo);
	}
	printf("Se ha creado el archivo %s\n", titulo );
}

void escribir2(int *numeros, char *titulo){
	//Creamos el archivo
	int i, j;
	FILE *archivo;
	archivo = fopen(titulo, "r");
	if(!archivo){
		//Se creo el archivo
		archivo = fopen(titulo,"w");
		for(i=0; i < numerosrandom; i++){
			if(numeros[i] != 0)
				fprintf(archivo, "%d \n",numeros[i]);
		}

        fclose(archivo);
	}
	printf("Se ha creado el archivo %s\n", titulo );
}


