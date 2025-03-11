package br.unipar.programacaoweb;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ConsomeCEP {

    public static void main(String[] args) {
        String continuar = "Sim";

        while (continuar.equalsIgnoreCase("Sim")) {
            try {
                System.out.println("Digite o CEP: ");
                String cep = JOptionPane.showInputDialog("Digite o CEP: ");

                String url = "https://viacep.com.br/ws/" + cep + "/json/";
                URL urlViaCEP = new URL(url);
                HttpURLConnection conexao = (HttpURLConnection) urlViaCEP.openConnection();
                conexao.setRequestMethod("GET");

                BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resposta = new StringBuilder();
                String linha;

                while ((linha = leitor.readLine()) != null) {
                    resposta.append(linha).append("\n");
                }
                leitor.close();

                ObjectMapper mapper = new ObjectMapper();
                Endereco endereco = mapper.readValue(resposta.toString(), Endereco.class);
                Date data = new Date();
                SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                String dataFormatada = formatador.format(data);
                System.out.println("Ciadade: " + endereco.getLocalidade());
                System.out.println("Logradouro: " + endereco.getLogradouro());
                System.out.println("Complemento: " + endereco.getComplemento());
                System.out.println("Bairro: " + endereco.getBairro());
                System.out.println("Localidade: " + endereco.getLocalidade());
                System.out.println("UF: " + endereco.getUf());
                System.out.println("IBGE: " + endereco.getIbge());
                System.out.println("SIAFI: " + endereco.getSiafi());
                System.out.println("Data: " + dataFormatada);

                EnderecoDao dao = new EnderecoDao();
                if (dao.buscarPorCep(endereco.getCep()) == null) {
                    endereco.setData(dataFormatada);
                    dao.salvar(endereco);

                    JOptionPane.showMessageDialog(null,"Endereço salvo com sucesso!\n"+"Cidade: " + endereco.getLocalidade() + "\n" + "Estado: " + endereco.getUf());
                } else {
                    JOptionPane.showMessageDialog(null,"Endereço já cadastrado no banco de dados.");
                }

                conexao.disconnect();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Erro ao buscar o CEP: " + e.getMessage());
                e.printStackTrace();
            }


            continuar = JOptionPane.showInputDialog("Deseja continuar? (Sim/Não)");
        }

    }
}
