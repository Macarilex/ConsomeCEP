1. O usuário digita um CEP em uma janela de entrada.
2. O programa consulta os dados do CEP na API ViaCEP.
3. As informações do endereço são exibidas na tela.
4. O programa verifica se o CEP já está salvo no banco de dados:
   - Se não estiver, ele salva os dados do endereço no banco.
   - Se já existir, avisa o usuário que o CEP já está salvo.
5. O programa pergunta se o usuário deseja continuar:
   - Se sim, volta ao passo 1.
   - Se não, o programa encerra.
