import java.util.Scanner;


public class Main {

    static String [] nomesCidades = {"Porto", "Aveiro", "Braga", "Coimbra", "Lisboa", "Fátima"};

    private static int [][] distancias = {
                {0,50, 60, 130, 300,200},
                {50, 0, 130,70, 250,140},
                {60, 130,0, 180,370, 250},
                {130,70,180,0,200,90},
                {300,250,370,200,0,130},
                {200,140,250,90,130,0}
    };
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        // Ler a descrição do evento e a data em que este ocorre
        String descricao = scanner.nextLine();
        String dataInicio = scanner.nextLine();

        int[][] matrizPlaneamento = leituraPlaneamento();
        outputMatriz(matrizPlaneamento, descricao, dataInicio);

        int[][] matrizKmPercorrer = KmPercorrer(matrizPlaneamento);
        outputKmPercorrer(matrizKmPercorrer);

        int[] totalKmPorAutocarro = calcularTotalKmPorAutocarro(matrizKmPercorrer);
        outputTotalKmPorAutocarro(totalKmPorAutocarro);

        int totalKmFrota = calcularTotalKmFrota(matrizKmPercorrer);
        outputTotalKmFrota(totalKmFrota);

        int[] maxKmPorDia = maxKmPorDia(matrizKmPercorrer);
        int[] diasComMaxKm = encontrarDiasComMaxKm(maxKmPorDia);

        outputMaxKmPorDia(maxKmPorDia, diasComMaxKm);

        int[][] autocarrosMaisDeUmDiaConsecutivo = AutocarrosMaisde1dia(matrizPlaneamento);
        outputAutocarrosMaisde1dia(autocarrosMaisDeUmDiaConsecutivo);

        int diaMaisTardioTodosAutocarrosNaMesmaCidade = encontrarDiaMaisTardioTodosAutocarrosNaMesmaCidade(matrizPlaneamento);
        exibirDiaMaisTardioTodosAutocarrosNaMesmaCidade(matrizPlaneamento, diaMaisTardioTodosAutocarrosNaMesmaCidade, nomesCidades);

        Histograma(totalKmPorAutocarro, totalKmFrota);

        int autocarro = scanner.nextInt();
        int dia = scanner.nextInt();
        AutocarroMaisProximo(matrizPlaneamento, autocarro, dia, distancias,nomesCidades);

    }
    public static int[][] leituraPlaneamento() {
        Scanner scanner = new Scanner(System.in);

        // Ler o numero de autocarros - cada L é um; e a quantidade de dias - cada C corresponde a um tb. ter atençao que começa em 0
        int L = scanner.nextInt();
        int C = scanner.nextInt();


        int[][] matrizPlaneamento = new int[L][C];


        for (int i = 0; i < L; i++) {
            for (int j = 0; j < C; j++) {
                matrizPlaneamento[i][j] = scanner.nextInt();
            }
        }

        return matrizPlaneamento;
    }

    public static void outputMatriz(int[][] matriz, String descricao, String dataInicio) {
        System.out.println("b)");
        System.out.println (descricao + ";" + dataInicio);
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("Bus" + i + " : ");
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] KmPercorrer(int[][] matrizPlaneamento) {
        int L = matrizPlaneamento.length;
        int C = matrizPlaneamento[0].length;
        int[][] KmPercorrer = new int[L][C - 1];
        //metemos c-1 porque queremos considerar quantos espaços temos entre os elementos e nao o n de elementos

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < C - 1; j++) {
                int cidadeAtual = matrizPlaneamento[i][j];
                int cidadeDestino = matrizPlaneamento[i][j + 1];
                //AQUI TEM DE SE FAZER J+1 PARA NAO COMPARAR COM O PROPRIO
                KmPercorrer[i][j] = distancias[cidadeAtual][cidadeDestino];
            }
        }

        return KmPercorrer;
    }
    public static void outputKmPercorrer(int[][] KmPercorrer) {
        System.out.println("c)\n");
        for (int i = 0; i < KmPercorrer.length; i++) {
            System.out.print("Bus" + i + " : 0");
            for (int j = 0; j < KmPercorrer[0].length; j++) {
                System.out.printf("%4d ", KmPercorrer[i][j]);
            }
            System.out.println();
        }
    }

    public static int[] calcularTotalKmPorAutocarro(int[][] KmPercorrer) {
        int L = KmPercorrer.length;
        int C = KmPercorrer[0].length;

        int[] totalKmPorAutocarro = new int[L];

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < C; j++) {
                totalKmPorAutocarro[i] += KmPercorrer[i][j];
            }
        }

        return totalKmPorAutocarro;
    }

    public static void outputTotalKmPorAutocarro(int[] totalKmPorAutocarro) {
        System.out.println("d)");
        for (int i = 0; i < totalKmPorAutocarro.length; i++) {
            System.out.println("Bus" + i + " : " + totalKmPorAutocarro[i] + " km");
        }
    }

    public static int calcularTotalKmFrota(int[][] KmPercorrer) {
        int L = KmPercorrer.length;
        int C = KmPercorrer[0].length;

        int totalKmFrota = 0;

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < C; j++) {
                totalKmFrota += KmPercorrer[i][j];
            }
        }

        return totalKmFrota;
    }

    public static void outputTotalKmFrota(int totalKmFrota) {
        System.out.println("e)\nTotal de km a percorrer pela frota = " + totalKmFrota + " km");
    }

    public static int[] maxKmPorDia(int[][] KmPercorrer) {
        int L = KmPercorrer.length;
        int C = KmPercorrer[0].length;

        int[] maxKmPorDia = new int[C];

        for (int j = 0; j < C; j++) {
            int totalKm = 0;
            for (int i = 0; i < L; i++) {
                totalKm += KmPercorrer[i][j];
            }
            maxKmPorDia[j] = totalKm;
        }

        return maxKmPorDia;
    }

    public static int[] encontrarDiasComMaxKm(int[] maxKmPorDia) {
        int valorMaximo = maxKmPorDia[0];

        for (int i = 1; i < maxKmPorDia.length; i++) {
            if (maxKmPorDia[i] > valorMaximo) {
                valorMaximo = maxKmPorDia[i];
            }
        }

        int[] diaComMaiorSoma = new int[maxKmPorDia.length];
        for (int j = 0; j < maxKmPorDia.length; j++) {
            if (maxKmPorDia[j] == valorMaximo) {
                diaComMaiorSoma[j] = 1;
            } else {
                diaComMaiorSoma[j] = 0;
            }
        }

        return diaComMaiorSoma;
    }
    public static void outputMaxKmPorDia(int[] maxKmPorDia, int[] diasComMaxKm) {
        System.out.println("f)");

        int valorMaximo = maxKmPorDia[0];
        for (int i = 1; i < maxKmPorDia.length; i++) {
            if (maxKmPorDia[i] > valorMaximo) {
                valorMaximo = maxKmPorDia[i];
            }
        }

        System.out.println("Máximo de kms num dia: (" + valorMaximo + " km), dias:");

        for (int j = 0; j < diasComMaxKm.length; j++) {
            if (diasComMaxKm[j] == 1) {
                System.out.println(j+1);
            }
        }


    }


    public static int[][] AutocarrosMaisde1dia(int[][] matrizPlaneamento) {
        int L = matrizPlaneamento.length;
        int C = matrizPlaneamento[0].length;

        int[][] autocarrosMaisde1dia = new int[L][C - 1];

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < C - 1; j++) {
                int cidadeAtual = matrizPlaneamento[i][j];
                int cidadeSeguinte = matrizPlaneamento[i][j + 1];

                if (cidadeAtual == cidadeSeguinte) {
                    autocarrosMaisde1dia[i][j] = 1;
                }
            }
        }

        return autocarrosMaisde1dia;
    }

    public static void outputAutocarrosMaisde1dia(int[][] autocarrosMaisde1dia) {
        System.out.println("g)");
        for (int i = 0; i < autocarrosMaisde1dia.length; i++) {

            for (int j = 0; j < autocarrosMaisde1dia[0].length; j++) {
                if (autocarrosMaisde1dia[i][j] == 1) {
                    System.out.print("Autocarros que permanecem mais de 1 dia consecutivo na mesma cidade: Bus" + i);
                }
            }
            System.out.println();
        }
    }

    public static int encontrarDiaMaisTardioTodosAutocarrosNaMesmaCidade(int[][] matrizPlaneamento) {
        int L = matrizPlaneamento.length;
        int C = matrizPlaneamento[0].length;

        int diaMaisTardio = 0;

        for (int j = 0; j < C; j++) {
            boolean todosNaMesmaCidade = true;

            int cidadeAtual = matrizPlaneamento[0][j];  // Inicializa com a cidade do primeiro autocarro

            for (int i = 1; i < L; i++) {
                if (matrizPlaneamento[i][j] != cidadeAtual) {
                    todosNaMesmaCidade = false;
                    break;
                }
            }

            if (todosNaMesmaCidade) {
                diaMaisTardio = j;
            }
        }

        return diaMaisTardio;
    }

    public static void exibirDiaMaisTardioTodosAutocarrosNaMesmaCidade(int[][] matrizPlaneamento ,int diaMaisTardio, String[] nomesCidades) {
        System.out.println("h)\nNo dia " + (diaMaisTardio) + ", todos os autocarros estarão em " + nomesCidades[matrizPlaneamento[0][diaMaisTardio]]);
    }
    public static void Histograma(int[] totalKmPorAutocarro, int totalKmFrota) {
        int L = totalKmPorAutocarro.length;

        System.out.println("\ni)");

        for (int i = 0; i < L; i++) {
            double percentagem = (double) totalKmPorAutocarro[i] / totalKmFrota * 100;
            int numAsteriscos = (int) (percentagem / 10);

            System.out.print("\nBus " + i + " (" + String.format("%.2f", percentagem) + "%)"+ ": ");
            for (int j = 0; j < numAsteriscos; j++) {
                System.out.print("*");
            }

        }
    }

    public static void AutocarroMaisProximo(int[][] matrizPlaneamento, int autocarro, int dia, int[][] distancias, String[] nomesCidades) {
        int L = matrizPlaneamento.length;
        int C = matrizPlaneamento[0].length;

        if (autocarro < 0 || autocarro >= L || dia < 0 || dia >= C) {
            System.out.println("Autocarro ou dia inválidos.");
            return;
        }


        int cidadeAtual = matrizPlaneamento[autocarro][dia];
        int menorDistancia = Integer.MAX_VALUE;
        int autocarroMaisProximo = -1;

        for (int i = 0; i < L; i++) {
            if (i != autocarro) {
                int cidadeOutroAutocarro = matrizPlaneamento[i][dia];
                int distancia = distancias[cidadeOutroAutocarro][cidadeAtual];

                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    autocarroMaisProximo = i;
                }
            }
        }

        String nomeCidadeAtual = nomesCidades[cidadeAtual];
        String nomeCidadeAnterior = nomesCidades[matrizPlaneamento[autocarroMaisProximo][dia]];

        System.out.println("j)\nNo dia " + dia + ", Bus" + autocarro + " estará em " + nomeCidadeAtual +
                ". Bus" + autocarroMaisProximo + " é o mais próximo. Está em " + nomeCidadeAnterior +
                " a " + menorDistancia + " km.");
    }



}
