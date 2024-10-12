import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';



import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProdutosComponent } from './pages/produtos/produtos.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { CarrinhoComponent } from './pages/carrinho/carrinho.component';
import { CarrinhoModule } from './modules/carrinho/carrinho.module';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { PerfilModule } from './modules/perfil/perfil.module';
import { LoginComponent } from './pages/login/login.component';
import { LoginModule } from './modules/login/login.module';
import { ProdutoModule } from './modules/produto/produto.module';
import { HomeComponent } from './pages/home/home.component';
import { HomeModule } from './modules/home/home.module';

@NgModule({
  declarations: [
    AppComponent,
    ProdutosComponent,
    NavbarComponent,
    CarrinhoComponent,
    PerfilComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,    
    //Material Components
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    
    CarrinhoModule,
    PerfilModule,
    LoginModule,
    ProdutoModule,
    HomeModule
  ],
  providers: [
    provideClientHydration(),
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
