import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <app-navbar></app-navbar>
    <router-outlet />
  `,
  styles: []
})
export class AppComponent {
  title = 'frontend';
}
