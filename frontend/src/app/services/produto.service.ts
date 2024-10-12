import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {

  // Simulação de produtos (em um cenário real, você usaria uma API)
  private produtos = [
    { id: 1, name: 'Brigadeiro', price: 8.90, description: 'Delicioso Cupcake de brigadeiro, sabor mais açucarado, mais doce', imageUrl: 'imagens/cc-brigadeiro-1.jpg' },
    { id: 2, name: 'Morango', price: 9.90, description: 'Cupcake de morango com cobertura, sabor natural e balanceado', imageUrl: 'imagens/cc-morango-1.jpeg' },
    // Adicione mais produtos aqui
  ];

  constructor() { }

  // Função para retornar a lista de produtos
  getProducts(): Observable<any[]> {
    return of(this.produtos); // Simula o retorno de uma API
  }
}
