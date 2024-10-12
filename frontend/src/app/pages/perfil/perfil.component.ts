import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.scss'
})
export class PerfilComponent implements OnInit {

  user: any;

  constructor() {}

  ngOnInit(): void {
    // Simulação de dados do usuário (em um cenário real, você obteria os dados de uma API ou serviço)
    this.user = {
      name: 'João da Silva',
      email: 'joao.silva@email.com',
      orders: [
        { id: 1, date: '2024-09-25', total: 59.99, status: 'Entregue' },
        { id: 2, date: '2024-10-02', total: 45.50, status: 'Aguardando pagamento' }
      ]
    };
  }

  editProfile() {
    // Implementar lógica para edição de perfil
    console.log('Editar perfil');
  }

  changePassword() {
    // Implementar lógica para alteração de senha
    console.log('Alterar senha');
  }

}
