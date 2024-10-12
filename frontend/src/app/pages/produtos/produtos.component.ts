import { Component } from '@angular/core';
import { ProdutoService } from '../../services/produto.service';

@Component({
  selector: 'app-produtos',
  templateUrl: './produtos.component.html',
  styleUrl: './produtos.component.scss'
})
export class ProdutosComponent {

  products: any[] = [];

  constructor(private produtoService: ProdutoService) { }

  ngOnInit(): void {
    // Chamamos o serviço de produtos para obter a lista
    this.produtoService.getProducts().subscribe((data: any[]) => {
      this.products = data;
    });
  }

}
